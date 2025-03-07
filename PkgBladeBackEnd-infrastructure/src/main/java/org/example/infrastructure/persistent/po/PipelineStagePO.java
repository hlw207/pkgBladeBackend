package org.example.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionStageName;

import java.sql.Timestamp;
/**
 * CREATE TABLE pipeline_stage (
 *     mission_stage_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '阶段ID',
 *     mission_stage_name VARCHAR(255) NOT NULL COMMENT '阶段名称',
 *     mission_id BIGINT NOT NULL COMMENT '阶段所属的任务ID',
 *     mission_stage_complete_time TIMESTAMP COMMENT '阶段完成时间',
 *     mission_stage_start_time TIMESTAMP COMMENT '阶段开始时间',
 *     mission_stage_iter INT COMMENT '阶段所属的层数',
 *     mission_stage_status INT COMMENT '阶段状态：FINISHED(0), NOT_STARTED(1), FAILED(2), PROCESSING(3)'
 * ) COMMENT='任务阶段表';
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineStagePO {

    /**
     * 阶段id
     */
    private Long missionStageId;
    /**
     * 阶段名称
     */
    private MissionStageName missionStageName;
    /**
     * 阶段所属的任务的id
     */
    private Long missionId;
    /**
     * 阶段开始时间
     */
    private Timestamp missionStageCompleteTime;
    /**
     * 阶段完成时间，没完成或者失败就是-1
     */
    private Timestamp missionStageStartTime;
    /**
     * 阶段所属的层数
     */
    private Integer missionStageIter;
    /**
     * 阶段状态
     * FINISHED(0),
     * NOT_STARTED(1),
     * FAILED(2),
     * PROCESSING(3);
     */
    private Integer missionStageStatus;
}
