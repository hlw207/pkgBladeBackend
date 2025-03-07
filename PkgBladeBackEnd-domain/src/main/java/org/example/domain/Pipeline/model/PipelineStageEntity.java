package org.example.domain.Pipeline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionStageName;

import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineStageEntity {
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
