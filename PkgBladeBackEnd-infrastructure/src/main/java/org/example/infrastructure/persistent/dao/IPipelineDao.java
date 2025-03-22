package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.infrastructure.persistent.po.PipelinePO;
import org.example.infrastructure.persistent.po.PipelineStagePO;

import java.util.List;

@Mapper
public interface IPipelineDao {

    void addPipeline(PipelinePO pipeline);
    void addPipeStage(PipelineStagePO pipelineStage);
    List<PipelineResponse> getPipeline(long missionOwnerId);
    void changePipeStageStatus(long missionId, String missionStageName, int missionStageStatus);
    Long getMissionIdByOwnerAndName(long missionOwnerId, String missionName);
    List<PipelineStageEntity> getPipelineStagesByMissionId(long missionId);
}
