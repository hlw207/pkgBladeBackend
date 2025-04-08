package org.example.domain.Pipeline.repository;


import org.apache.ibatis.annotations.Param;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineInfoEntity;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.vo.PipelineResponse;

import java.util.List;

public interface IPipelineRepo {
    Long addPipeline(PipelineEntity pipelineEntity);
    void addPipelineStage(PipelineEntity pipelineEntity);
    void addPipelineInfo(PipelineInfoEntity pipelineInfoPO);

    List<PipelineResponse> getPipeline(long missionOwnerId);
    void changePipeStageStatus(long missionOwnerId, String missionName, String missionStageName, int missionStageStatus);
    List<PipelineStageEntity> getPipelineStagesByMissionId(long missionOwnerId, String missionName);
    void addPipelineDependency(String missionName, long missionOwnerId, String dependency);
    void addPipelineHandledPackageName(String missionName, long missionOwnerId, String handledPackageName);
    void addPipelineWrongAndWarningPackageName(String missionName, long missionOwnerId, String wrongPackageName, String warningPackageName);
    String getPipelineDependency(String missionName, long missionOwnerId);
    void deletePipeline(long missionOwnerId, String missionName);
    PipelineEntity getPipelineDetail(long missionOwnerId, String missionName);
    PipelineInfoEntity getPipelineInfoDetail(long missionOwnerId, String missionName);

}
