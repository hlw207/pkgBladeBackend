package org.example.domain.Pipeline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineInfoEntity {
    /**
     * 任务ID
     */
    private Long missionId;
    /**
     * 裁剪率
     */
    private Double cuttingRate;
    /**
     * 裁剪文件数
     */
    private Integer cuttingFileNum;
    /**
     * 裁剪函数数
     */
    private Integer cuttingFunctionNum;
    /**
     * 软件包依赖
     */
    private String dependency;
    /**
     * 处理软件包
     */
    private String handledPackageName;
    /**
     * 问题软件包
     */
    private String wrongPackageName;
    /**
     * 警告软件包
     */
    private String warningPackageName;
}
