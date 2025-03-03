package org.example.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MissionStageName {
    GET_DEPENDENCY("依赖拉取"),
    COMPILE("软件编译"),
    SLIMMING("依赖裁剪"),
    SYMBOL("符号分析"),
    FILE_CUTTING("文件裁剪"),
    FUNCTION_CUTTING("函数裁剪"),
    GENERATE("依赖生成");
    private String value;
}

