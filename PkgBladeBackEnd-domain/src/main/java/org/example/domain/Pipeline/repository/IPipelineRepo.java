package org.example.domain.Pipeline.repository;


import org.example.domain.Pipeline.model.PipelineEntity;

public interface IPipelineRepo {
    Long addPipeline(PipelineEntity pipelineEntity);
    void addPipelineStage(PipelineEntity pipelineEntity);
}
