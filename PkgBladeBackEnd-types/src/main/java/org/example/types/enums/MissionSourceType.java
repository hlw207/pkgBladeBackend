package org.example.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MissionSourceType {
    EXE(0),
    CODE(1);
    int type;
}
