package org.example.infrastructure.persistent.dao;


import org.apache.ibatis.annotations.Mapper;
import org.example.infrastructure.persistent.po.PipelinePO;

@Mapper
public interface IPipelineDao {

    void addPipeline(PipelinePO pipeline);
}
