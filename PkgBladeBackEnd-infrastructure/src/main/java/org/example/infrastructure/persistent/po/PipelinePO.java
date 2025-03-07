package org.example.infrastructure.persistent.po;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
/**
 * CREATE TABLE pipeline (
 *     mission_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
 *     mission_name VARCHAR(255) NOT NULL COMMENT '任务名称，也就是软件包名称',
 *     mission_description TEXT COMMENT '任务描述',
 *     mission_location VARCHAR(255) COMMENT '任务对应的文件地址',
 *     mission_type INT COMMENT '任务对应的文件类型',
 *     mission_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '任务开始时间',
 *     mission_owner_id BIGINT COMMENT '任务所属人ID'
 * ) COMMENT='任务流水线表';
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelinePO {
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
