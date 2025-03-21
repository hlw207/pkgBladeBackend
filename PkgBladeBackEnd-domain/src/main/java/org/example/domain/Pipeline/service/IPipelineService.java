package org.example.domain.Pipeline.service;


import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.vo.PipelineInfo;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

public interface IPipelineService {
    void addPipeline(String missionName, String missionDescription,
                             String missionLocation, Timestamp missionCreateTime,
                             long missionOwnerId, int missionType);
    String startPipeline(String missionName, long missionOwnerId);
    List<PipelineResponse> getPipeline(long missionOwnerId);
    List<PipelineInfo> getPipeLineInfo(long missionOwnerId, String missionName, String missionStageName, int lineCount);
    List<PipelineStageEntity> getPipelineStageInfo(long missionOwnerId, String missionName);
}
