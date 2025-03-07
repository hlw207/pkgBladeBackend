package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.example.infrastructure.persistent.po.PipelinePO;
import org.example.infrastructure.persistent.po.PipelineStagePO;

@Mapper
public interface IPipelineDao {

    void addPipeline(PipelinePO pipeline);
    void addPipeStage(PipelineStagePO pipelineStage);
}
