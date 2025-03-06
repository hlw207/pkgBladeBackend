package org.example.infrastructure.persistent.repository;


import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.infrastructure.persistent.dao.IPipelineDao;
import org.example.infrastructure.persistent.po.PipelinePO;
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
    public void addPipeline(PipelineEntity pipelineEntity) {
        PipelinePO pipelinePo = PipelinePO.builder().missionId(pipelineEntity.getMissionId()).missionDescription(pipelineEntity.getMissionDescription())
                .missionLocation(pipelineEntity.getMissionLocation()).missionName(pipelineEntity.getMissionName())
                .missionOwnerId(pipelineEntity.getMissionOwnerId()).missionType(pipelineEntity.getMissionType())
                .missionCreateTime(pipelineEntity.getMissionCreateTime()).build();
        pipelineDao.addPipeline(pipelinePo);
    }
}
