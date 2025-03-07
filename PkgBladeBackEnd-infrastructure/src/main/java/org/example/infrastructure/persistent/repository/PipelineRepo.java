package org.example.infrastructure.persistent.repository;


import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.infrastructure.persistent.dao.IPipelineDao;
import org.example.infrastructure.persistent.po.PipelinePO;
import org.example.infrastructure.persistent.po.PipelineStagePO;
import org.example.types.enums.MissionStageName;
import org.example.types.enums.MissionStageStatus;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class PipelineRepo implements IPipelineRepo {

    @Resource
    private final IPipelineDao pipelineDao;

    public PipelineRepo(IPipelineDao pipelineDao) {
        this.pipelineDao = pipelineDao;
    }

    @Override
    public Long addPipeline(PipelineEntity pipelineEntity) {
        PipelinePO pipelinePo = PipelinePO.builder().missionId(pipelineEntity.getMissionId()).missionDescription(pipelineEntity.getMissionDescription())
                .missionLocation(pipelineEntity.getMissionLocation()).missionName(pipelineEntity.getMissionName())
                .missionOwnerId(pipelineEntity.getMissionOwnerId()).missionType(pipelineEntity.getMissionType())
                .missionCreateTime(pipelineEntity.getMissionCreateTime()).build();
        pipelineDao.addPipeline(pipelinePo);
        return pipelinePo.getMissionId();
    }

    @Override
    public void addPipelineStage(PipelineEntity pipelineEntity) {
        for (MissionStageName stageName : MissionStageName.values()) {
            // 构造 PipelineStagePO 对象
            PipelineStagePO pipelineStagePO = PipelineStagePO.builder().missionStageName(stageName)
                    .missionStageIter(0).missionStageStatus(MissionStageStatus.NOT_STARTED.getValue()).missionStageCompleteTime(null)
                    .missionStageStartTime(null).missionId(pipelineEntity.getMissionId()).build();

            pipelineDao.addPipeStage(pipelineStagePO);
        }
    }


}
