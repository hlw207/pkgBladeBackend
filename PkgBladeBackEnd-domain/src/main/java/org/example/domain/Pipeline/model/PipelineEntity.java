package org.example.domain.Pipeline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionSourceType;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineEntity {
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
     * 任务对应的文件地址
     */
    private String missionLocation;
    /**
     * 任务对应的文件类型
     */
    private Integer missionType;
    /**
     * 任务开始时间
     */
    private Timestamp missionCreateTime;
    /**
     * 任务所属人ID
     */
    private Long missionOwnerId;
}
