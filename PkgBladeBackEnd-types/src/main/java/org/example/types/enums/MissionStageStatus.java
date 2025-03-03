package org.example.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MissionStageStatus {
    FINISHED("finished"),
    NOT_STARTED("not started"),
    FAILED("failed"),
    PROCESSING("processing");
    private String value;
}
