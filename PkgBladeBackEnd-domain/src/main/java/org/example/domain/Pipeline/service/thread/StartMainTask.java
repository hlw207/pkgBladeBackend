package org.example.domain.Pipeline.service.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.example.domain.Utils.CodeExecutor.runPythonAsync;

public class StartMainTask implements Callable<Void> {

    private final String missionName;
    private final Long userId;

    public StartMainTask(String missionName, Long userId) {
        this.missionName = missionName;
        this.userId = userId;
    }

    @Override
    public Void call() throws Exception {
        String basePath = "D:/PkgBlade_" + String.valueOf(userId) + "_" + missionName;
        String outputFilePath = basePath + "/result.txt";
        String mainFilePath = basePath + "/main.py";
        // TODO: 确保main被复制，然后执行即可
        List<String> params = new ArrayList<>();
        params.add(missionName);
        params.add(" > " + basePath + outputFilePath);
        runPythonAsync("python3", mainFilePath, params);
        return null;
    }
}
