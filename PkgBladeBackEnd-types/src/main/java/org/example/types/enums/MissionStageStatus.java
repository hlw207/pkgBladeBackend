package org.example.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MissionStageStatus {
    FINISHED(0),
    NOT_STARTED(1),
    FAILED(2),
    PROCESSING(3);
    private int value;
}
