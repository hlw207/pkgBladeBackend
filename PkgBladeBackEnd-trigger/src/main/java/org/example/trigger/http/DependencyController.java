package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.FutureTaskManager;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/dependency")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class DependencyController {

    @PostMapping("/getPackageDependency")
    public ResponseResult<String> getAllPackageDependencyName(@RequestParam String packageName) {
        String taskName = packageName + "_" + StpUtil.getLoginId();
        Future<String> future = FutureTaskManager.getTaskFuture(taskName, String.class);

        if (future == null) {
            // 任务没有找到
            return ResponseCode.UN_ERROR.withException("mission not found");
        }
        try {
            return ResponseCode.SUCCESS.withData(future.get());
        } catch (InterruptedException | ExecutionException e) {
            // 任务执行失败
            return ResponseCode.UN_ERROR.withException(e.getMessage());
        } finally {
            FutureTaskManager.removeTask(taskName);
        }
    }
}
