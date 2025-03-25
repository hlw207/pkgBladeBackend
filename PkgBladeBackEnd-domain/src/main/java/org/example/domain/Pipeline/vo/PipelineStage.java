package org.example.domain.Pipeline.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionStageName;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PipelineStage {

    /**
     * 阶段名称
     */
    private String missionStageName;
    /**
     * 阶段开始时间
     */
    private Timestamp missionStageCompleteTime;
    /**
     * 阶段完成时间，没完成或者失败就是-1
     */
    private Timestamp missionStageStartTime;
    /**
     * 阶段状态
     * FINISHED(0),
     * NOT_STARTED(1),
     * FAILED(2),
     * PROCESSING(3);
     */
    private Integer missionStageStatus;
}
