package org.example.domain.Pipeline.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PipelineResponse {
    /**
     * 任务ID
     */
    private Long missionId;
    /**
     * 任务名称，也就是软件包名称
     */
    private String missionName;
    /**
     * 任务描述
     */
    private String missionDescription;

    /**
     * 任务对应的文件类型
     */
    private Integer missionType;
    /**
     * 任务开始时间
     */
    private Timestamp missionCreateTime;

}
