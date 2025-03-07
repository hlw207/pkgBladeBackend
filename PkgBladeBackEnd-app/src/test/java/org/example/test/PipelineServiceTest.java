package org.example.test;

import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.repository.IPipelineRepo;
import org.example.domain.Pipeline.service.PipelineService;
import org.example.domain.Pipeline.service.thread.ShowAllDependencyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.domain.FutureTaskManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PipelineServiceTest {

    @Mock
    private IPipelineRepo iPipelineRepo;

    @Mock
    private ThreadPoolExecutor threadPoolExecutor;

    @InjectMocks
    private PipelineService pipelineService;

    private final String missionName = "testMission";
    private final String missionDescription = "Test Description";
    private final String missionLocation = "Test Location";
    private final Timestamp missionCreateTime = new Timestamp(System.currentTimeMillis());
    private final long missionOwnerId = 123L;
    private final int missionType = 1;

    @BeforeEach
    void setUp() {
        // 这里可以初始化一些需要的 mock 行为
    }

    @Test
    void testAddPipeline() {
        // 执行 addPipeline 方法
        pipelineService.addPipeline(missionName, missionDescription, missionLocation, missionCreateTime, missionOwnerId, missionType);

        // 验证 iPipelineRepo.addPipeline 是否被调用
        ArgumentCaptor<PipelineEntity> pipelineCaptor = ArgumentCaptor.forClass(PipelineEntity.class);
        verify(iPipelineRepo, times(1)).addPipeline(pipelineCaptor.capture());

        PipelineEntity capturedPipeline = pipelineCaptor.getValue();
        assertNotNull(capturedPipeline);
        assertEquals(missionName, capturedPipeline.getMissionName());
        assertEquals(missionDescription, capturedPipeline.getMissionDescription());

        // 验证线程池是否执行了任务
        ArgumentCaptor<Runnable> taskCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(threadPoolExecutor, times(1)).execute(taskCaptor.capture());

        Runnable submittedTask = taskCaptor.getValue();
        assertNotNull(submittedTask);

        // 验证 FutureTaskManager 是否存储了任务
        String taskKey = missionName + "_" + missionOwnerId;
        Future<String> futureTask = FutureTaskManager.getTaskFuture(taskKey, String.class);
        assertNotNull(futureTask);
    }
}
