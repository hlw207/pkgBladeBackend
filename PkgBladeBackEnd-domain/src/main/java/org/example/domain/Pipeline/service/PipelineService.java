package org.example.domain.Pipeline.service;


import org.example.domain.FutureTaskManager;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineInfoEntity;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.domain.Pipeline.service.thread.ShowAllDependencyTask;
import org.example.domain.Pipeline.service.thread.StartMainTask;
import org.example.domain.Pipeline.vo.PipelineInfo;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.domain.Pipeline.vo.PipelineStage;
import org.example.types.enums.MissionStageName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

        // 创建任务信息表
        PipelineInfoEntity pipelineInfoEntity = PipelineInfoEntity.builder().missionId(nowPipelineId)
                .cuttingFileNum(0)
                .cuttingRate((double) 0)
                .dependency("")
                .unhandledDependency("").build();
        iPipelineRepo.addPipelineInfo(pipelineInfoEntity);

        // 更新任务阶段状态
        iPipelineRepo.changePipeStageStatus(missionOwnerId, missionName, MissionStageName.GET_DEPENDENCY.toString(), 3);
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

    @Override
    public List<PipelineResponse> getPipeline(long missionOwnerId) {
        List<PipelineResponse> pipelineResponseList = iPipelineRepo.getPipeline(missionOwnerId);
        return pipelineResponseList;
    }

    @Override
    public List<PipelineInfo> getPipeLineInfo(long missionOwnerId, String missionName, String missionStageName, int lineCount) {
        processStatus(missionOwnerId, missionName);
        String basePath = "/home/PkgBlade_" + missionOwnerId + "_" + missionName;
        String filePath = basePath + "/" + missionStageName + ".txt";
        // 用于存储结果的列表
        List<PipelineInfo> pipelineInfoList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;

            // 逐行读取文件
            while ((line = reader.readLine()) != null) {
                currentLine++;

                // 如果当前行号小于 lineCount，跳过
                if (currentLine < lineCount) {
                    continue;
                }

                // 处理每一行并转换为 PipelineInfo 对象
                PipelineInfo pipelineInfo = processLine(line);
                if (pipelineInfo != null) {
                    pipelineInfoList.add(pipelineInfo);
                }
            }
        } catch (IOException e) {
            // 处理文件读取异常
            e.printStackTrace();
        }

        return pipelineInfoList;
    }

    @Override
    public List<PipelineStage> getPipelineStageInfo(long missionOwnerId, String missionName) {
        List<PipelineStageEntity> pipelineStageEntityList = iPipelineRepo.getPipelineStagesByMissionId(missionOwnerId, missionName);
        List<PipelineStage> pipelineStageList = new ArrayList<>();
        for (PipelineStageEntity pipelineStageEntity: pipelineStageEntityList){
            PipelineStage pipelineStage = new PipelineStage();
            pipelineStage.setMissionStageName(pipelineStageEntity.getMissionStageName().toString());
            pipelineStage.setMissionStageCompleteTime(pipelineStageEntity.getMissionStageCompleteTime());
            pipelineStage.setMissionStageStartTime(pipelineStageEntity.getMissionStageStartTime());
            pipelineStage.setMissionStageStatus(pipelineStageEntity.getMissionStageStatus());
            pipelineStageList.add(pipelineStage);
        }
        return pipelineStageList;
    }

    @Override
    public void addPipelineDependency(String missionName, long missionOwnerId, String dependency) {
        iPipelineRepo.addPipelineDependency(missionName, missionOwnerId, dependency);
        // 更新任务阶段状态
        iPipelineRepo.changePipeStageStatus(missionOwnerId, missionName, MissionStageName.GET_DEPENDENCY.toString(), 0);
    }

    @Override
    public String getPipelineDependency(String missionName, long missionOwnerId) {
        return iPipelineRepo.getPipelineDependency(missionName, missionOwnerId);
    }

    /**
     * 处理单行数据并转换为 PipelineInfo 对象
     *
     * @param line 文件中的一行数据
     * @return PipelineInfo 对象，如果行数据无效则返回 null
     */
    private PipelineInfo processLine(String line) {
        // 创建 PipelineInfo 对象并设置属性
        PipelineInfo pipelineInfo = new PipelineInfo();
        if(line.startsWith("Wrong: ")){
            pipelineInfo.setInfo(line.substring(7));
            pipelineInfo.setType("wrong");
        } else if (line.startsWith("Info: ")) {
            pipelineInfo.setInfo(line.substring(6));
            pipelineInfo.setType("info");
        } else if (line.startsWith("Command: ")) {
            pipelineInfo.setInfo(line.substring(9));
            pipelineInfo.setType("command");
        }
        return pipelineInfo;
    }

    private void processStatus(long missionOwnerId, String missionName){
        List<PipelineStageEntity> pipelineStageEntityList = iPipelineRepo.getPipelineStagesByMissionId(missionOwnerId, missionName);
        for (PipelineStageEntity pipelineStageEntity: pipelineStageEntityList){
            if(pipelineStageEntity.getMissionStageStatus() == 0)
                continue;
            else {
                if(pipelineStageEntity.getMissionStageName().equals(MissionStageName.GET_DEPENDENCY)){
                    // TODO: 额外判断
                }else {
                    String basePath = "/home/PkgBlade_" + missionOwnerId + "_" + missionName;
                    String filePath = basePath + "/" + pipelineStageEntity.getMissionStageName().toString() + ".txt";
                    // TODO: 判断最后一行是否为Info: pipelineStageEntity.getMissionStageName().toString() is over，则将状态改成0，如何文件不为空，则状态改成3
                    // 读取文件最后一行
                    String lastLine = readLastLine(filePath);

                    // 判断文件内容并更新阶段状态
                    if (lastLine != null) {
                        String expectedEndMessage = "Info: " + pipelineStageEntity.getMissionStageName().toString() + " is over";
                        if (lastLine.equals(expectedEndMessage)) {
                            // 如果最后一行是 "Info: <阶段名称> is over"，则将状态更新为 0（已完成）
                            iPipelineRepo.changePipeStageStatus(missionOwnerId, missionName, pipelineStageEntity.getMissionStageName().toString(), 0);
                        } else {
                            // 如果文件不为空且最后一行不是结束消息，则将状态更新为 3（进行中）
                            iPipelineRepo.changePipeStageStatus(missionOwnerId, missionName, pipelineStageEntity.getMissionStageName().toString(), 3);
                        }
                    }
                }
            }
        }
    }

    /**
     * 读取文件的最后一行
     *
     * @param filePath 文件路径
     * @return 文件的最后一行内容，如果文件为空则返回 null
     */
    private String readLastLine(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String lastLine = null;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            return lastLine;
        } catch (IOException e) {
            // 处理文件读取异常
            e.printStackTrace();
            return null;
        }
    }
}
