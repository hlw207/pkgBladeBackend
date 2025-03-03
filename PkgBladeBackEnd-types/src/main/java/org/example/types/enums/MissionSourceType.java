package org.example.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MissionSourceType {
    EXE("可执行文件"),
    CODE("项目源码");
    String type;
}
