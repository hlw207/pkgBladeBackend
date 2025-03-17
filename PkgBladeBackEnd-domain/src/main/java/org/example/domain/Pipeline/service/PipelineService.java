package org.example.domain.Pipeline.service;


import org.example.domain.FutureTaskManager;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.domain.Pipeline.service.thread.ShowAllDependencyTask;
import org.example.domain.Pipeline.service.thread.StartMainTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class PipelineService implements IPipelineService{

    @Resource
    private final IPipelineRepo iPipelineRepo;

    @Resource
    private final IDependencyService iDependencyService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;


    public PipelineService(IPipelineRepo iPipelineRepo, IDependencyService iDependencyService) {
        this.iPipelineRepo = iPipelineRepo;
        this.iDependencyService = iDependencyService;
    }

    /**
     * 提交任务表单后，写入数据库，把依赖树获取任务写入线程池
     * @param missionName
     * @param missionDescription
     * @param missionLocation
     * @param missionCreateTime
     * @param missionOwnerId
     * @param missionType
     */
    @Override
    public void addPipeline(String missionName, String missionDescription,
                            String missionLocation, Timestamp missionCreateTime,
                            long missionOwnerId, int missionType) {
        PipelineEntity nowPipeline = PipelineEntity.builder().missionType(missionType).missionDescription(missionDescription)
                .missionCreateTime(missionCreateTime).missionLocation(missionLocation).missionOwnerId(missionOwnerId)
                .missionName(missionName).build();

        Long nowPipelineId =  iPipelineRepo.addPipeline(nowPipeline);
        nowPipeline.setMissionId(nowPipelineId);

        // 提交获取所有依赖的任务
        ShowAllDependencyTask showAllDependencyTask = new ShowAllDependencyTask(iDependencyService, missionName);
        FutureTask<String> showAllDependencyTaskFuture = new FutureTask<>(showAllDependencyTask);
        threadPoolExecutor.execute(showAllDependencyTaskFuture);
        FutureTaskManager.addTask(missionName + "_" + missionOwnerId, showAllDependencyTaskFuture);

        // 创建数据库表
        iPipelineRepo.addPipelineStage(nowPipeline);
    }

    /**
     * 在提交后能展示初步的依赖树后，正式开始任务
     * @param missionName
     * @param missionOwnerId
     */

    @Override
    public String startPipeline(String missionName, long missionOwnerId) {
        Future<String> dependencyFuture = FutureTaskManager.getTaskFuture(missionName + "_" + missionOwnerId, String.class);
        String result = "";
        try {
            result = dependencyFuture.get(); // 阻塞直到任务完成并返回结果
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // TODO: 提交正式运行任务
        StartMainTask startMainTask = new StartMainTask(missionName, missionOwnerId);
        FutureTask<Void> startMainTaskFuture = new FutureTask<>(startMainTask);
        threadPoolExecutor.execute(startMainTaskFuture);
        FutureTaskManager.addTask(missionName + "_" + missionOwnerId, startMainTaskFuture);

        return result;
    }
}
