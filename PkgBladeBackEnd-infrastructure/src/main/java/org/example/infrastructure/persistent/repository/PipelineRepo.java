package org.example.infrastructure.persistent.repository;


import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.infrastructure.persistent.dao.IPipelineDao;
import org.example.infrastructure.persistent.po.PipelinePO;
import org.example.infrastructure.persistent.po.PipelineStagePO;
import org.example.types.enums.MissionStageName;
import org.example.types.enums.MissionStageStatus;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


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

    @Override
    public List<PipelineResponse> getPipeline(long missionOwnerId) {
        return pipelineDao.getPipeline(missionOwnerId);
    }

    @Override
    public void changePipeStageStatus(long missionOwnerId, String missionName, String missionStageName, int missionStageStatus) {
        long missionId = pipelineDao.getMissionIdByOwnerAndName(missionOwnerId, missionName);
        pipelineDao.changePipeStageStatus(missionId, missionStageName, missionStageStatus);
    }

    @Override
    public List<PipelineStageEntity> getPipelineStagesByMissionId(long missionOwnerId, String missionName) {
        long missionId = pipelineDao.getMissionIdByOwnerAndName(missionOwnerId, missionName);
        return pipelineDao.getPipelineStagesByMissionId(missionId);
    }

    @Override
    public void addPipelineDependency(String missionName, long missionOwnerId, String dependency) {
        long missionId = pipelineDao.getMissionIdByOwnerAndName(missionOwnerId, missionName);
        pipelineDao.addPipelineDependency(missionId, dependency);
    }

    @Override
    public String getPipelineDependency(String missionName, long missionOwnerId) {
        long missionId = pipelineDao.getMissionIdByOwnerAndName(missionOwnerId, missionName);
        return pipelineDao.getPipelineDependency(missionId);
    }


}
