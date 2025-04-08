package org.example.domain.Pipeline.service;



import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineInfoEntity;
import org.example.domain.Pipeline.vo.LDDInfo;
import org.example.domain.Pipeline.vo.PipelineInfo;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.domain.Pipeline.vo.PipelineStage;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

public interface IPipelineService {
    void addPipeline(String missionName, String missionDescription,
                             String missionLocation, Timestamp missionCreateTime,
                             long missionOwnerId, int missionType);
    String startPipeline(String missionName, long missionOwnerId, String handlePackageName);
    List<PipelineResponse> getPipeline(long missionOwnerId);
    List<PipelineInfo> getPipeLineInfo(long missionOwnerId, String missionName, String missionStageName, int lineCount);
    List<PipelineStage> getPipelineStageInfo(long missionOwnerId, String missionName);
    void addPipelineDependency(String missionName, long missionOwnerId, String dependency);
    String getPipelineDependency(String missionName, long missionOwnerId);
    void deletePipeline(long missionOwnerId, String missionName);
    PipelineEntity getPipelineDetail(long missionOwnerId, String missionName);
    PipelineInfoEntity getPipelineInfoDetail(long missionOwnerId, String missionName);
    List<LDDInfo> getLDDInfo(long missionOwnerId, String missionName);
    List<String> getPackage(long missionOwnerId, String missionName);
}
