package org.example.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.types.enums.MissionSourceType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionPO {
    /**
     * 任务ID
     */
    private long missionId;
    /**
     * 任务名称
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
    private MissionSourceType missionType;
    /**
     * 任务开始时间
     */
    private long missionCreateTime;
    /**
     * 任务所属人ID
     */
    private long missionOwnerId;
}
