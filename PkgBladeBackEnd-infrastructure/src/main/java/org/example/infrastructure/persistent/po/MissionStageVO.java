package org.example.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionStageName;
import org.example.types.enums.MissionStageStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionStageVO {

    /**
     * 阶段id
     */
    private long missionStageId;
    /**
     * 阶段名称
     */
    private MissionStageName missionStageName;
    /**
     * 阶段所属的任务的id
     */
    private long missionId;
    /**
     * 阶段开始时间
     */
    private long missionStageCompleteTime;
    /**
     * 阶段完成时间，没完成或者失败就是-1
     */
    private long missionStageStartTime;
    /**
     * 阶段所属的层数
     */
    private int missionStageIter;
    /**
     * 阶段状态
     */
    private MissionStageStatus missionStageStatus;
}
