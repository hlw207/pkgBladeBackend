package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.FutureTaskManager;
import org.example.domain.Pipeline.service.IPipelineService;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/dependency")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class DependencyController {

    IPipelineService pipelineService;

    public DependencyController(IPipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @PostMapping("/getPackageDependency")
    public ResponseResult<String> getAllPackageDependencyName(@RequestParam String packageName) {
        String taskName = packageName + "_" + StpUtil.getLoginId();
        Future<String> future = FutureTaskManager.getTaskFuture(taskName, String.class);

        if (future == null) {
            // 任务没有找到
            String result = pipelineService.getPipelineDependency(packageName, StpUtil.getLoginIdAsLong());
            if (result == null) {
                return ResponseCode.UN_ERROR.withException("mission not found");
            } else
                return ResponseCode.SUCCESS.withData(result);
        } else {
            try {
                String result = future.get();
                System.out.println(result);
                pipelineService.addPipelineDependency(packageName, StpUtil.getLoginIdAsLong(), result);
                return ResponseCode.SUCCESS.withData(result);
            } catch (InterruptedException | ExecutionException e) {
                // 任务执行失败
                return ResponseCode.UN_ERROR.withException(e.getMessage());
            } finally {
                FutureTaskManager.removeTask(taskName);
            }
        }
    }
}
