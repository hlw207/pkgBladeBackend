package org.example.domain.Pipeline.repository;


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
    String getPipelineDependency(String missionName, long missionOwnerId);

}
