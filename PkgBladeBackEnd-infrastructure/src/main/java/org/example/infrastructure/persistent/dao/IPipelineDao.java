package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineInfoEntity;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.infrastructure.persistent.po.PipelineInfoPO;
import org.example.infrastructure.persistent.po.PipelinePO;
import org.example.infrastructure.persistent.po.PipelineStagePO;

import java.util.List;

@Mapper
public interface IPipelineDao {

    void addPipeline(PipelinePO pipeline);
    void addPipeStage(PipelineStagePO pipelineStage);
    void addPipelineInfo(PipelineInfoPO pipelineInfoPO);

    List<PipelineResponse> getPipeline(@Param("missionOwnerId") long missionOwnerId);

    void changePipeStageStatus(
            @Param("missionId") long missionId,
            @Param("missionStageName") String missionStageName,
            @Param("missionStageStatus") int missionStageStatus
    );

    Long getMissionIdByOwnerAndName(
            @Param("missionOwnerId") long missionOwnerId,
            @Param("missionName") String missionName
    );

    List<PipelineStageEntity> getPipelineStagesByMissionId(@Param("missionId") long missionId);

    void addPipelineDependency(
            @Param("missionId") long missionId,
            @Param("dependency") String dependency
    );

    void addPipelineHandledPackageName(
            @Param("missionId") long missionId,
            @Param("handledPackageName") String handledPackageName
    );

    void addPipelineWrongAndWarningPackageName(
            @Param("missionId") long missionId,
            @Param("wrongPackageName") String wrongPackageName,
            @Param("warningPackageName") String warningPackageName
    );

    String getPipelineDependency(@Param("missionId") long missionId);

    void deletePipeline(@Param("missionId") long missionId);

    void deletePipelineStage(@Param("missionId") long missionId);

    void deletePipelineInfo(@Param("missionId") long missionId);

    PipelineEntity getPipelineDetail(@Param("missionId") long missionId);

    PipelineInfoEntity getPipelineInfoDetail(@Param("missionId") long missionId);
}
