package org.example.domain.Pipeline.service;


import java.sql.Timestamp;

public interface IPipelineService {
    void addPipeline(String missionName, String missionDescription,
                             String missionLocation, Timestamp missionCreateTime,
                             long missionOwnerId, int missionType);
    String startPipeline(String missionName, long missionOwnerId);
}
