package org.example.domain.Pipeline.service;


import org.example.domain.FutureTaskManager;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.domain.Pipeline.service.thread.ShowAllDependencyTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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
}
