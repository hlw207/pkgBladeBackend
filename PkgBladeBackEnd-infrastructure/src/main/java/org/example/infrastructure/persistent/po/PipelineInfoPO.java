package org.example.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/*  CREATE TABLE pipeline_info (
      mission_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
      cutting_rate DOUBLE COMMENT '裁剪率',
      cutting_file_num INT COMMENT '裁剪文件数',
      cutting_function_num INT COMMENT '裁剪函数数',
      dependency TEXT COMMENT '软件包依赖',
      handled_package_name TEXT COMMENT '未处理软件包',
      wrong_package_name TEXT COMMENT '问题软件包',
      warning_package_name TEXT COMMENT '警告软件包'
  ) COMMENT='任务信息表';*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PipelineInfoPO {
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
