package org.example.domain.Pipeline.service.thread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.example.domain.Utils.CodeExecutor.runPythonAsync;
import static org.example.domain.Utils.CodeExecutor.runPythonAsyncNoWait;

public class StartMainTask implements Callable<Void> {

    private final String missionName;
    private final Long userId;

    private final String handlePackageName;

    public StartMainTask(String missionName, Long userId, String handlePackageName) {
        this.missionName = missionName;
        this.userId = userId;
        this.handlePackageName = handlePackageName;
    }

    @Override
    public Void call() throws Exception {
        String basePath = "/home/PkgBlade_" + String.valueOf(userId) + "_" + missionName;
        String outputFilePath = basePath + "/result.txt";
        // 检查一下输出文件是不是存在
        File file = new File(outputFilePath);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("文件创建成功: " + outputFilePath);
                } else {
                    System.out.println("文件创建失败: " + outputFilePath);
                }
            } else {
                System.out.println("文件已存在: " + outputFilePath);
            }
        } catch (IOException e) {
            System.out.println("发生错误: " + e.getMessage());
        }

        String mainFilePath = basePath + "/main.py";
        // TODO: 确保main被复制，然后执行即可
        List<String> params = new ArrayList<>();
        params.add(missionName);
        params.add(handlePackageName.toString());
        System.out.println(handlePackageName);
        runPythonAsyncNoWait("python3", mainFilePath, params, basePath);
        return null;
    }
}
