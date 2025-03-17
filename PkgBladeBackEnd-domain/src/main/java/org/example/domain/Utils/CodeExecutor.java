package org.example.domain.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.example.types.ProcessResult;


public class CodeExecutor {
    /**
     * 调用示例：
     * String pythonPath = "python3";  // 或者 "python" 取决于环境
     * String scriptPath = "/path/to/your/script.py"; // 你的 Python 脚本路径
     * @param pythonPath: python路径，就是python或者python3
     * @param scriptPath: 要运行的程序路径
     * @param params: 参数列表
     * @return
     */
    public static CompletableFuture<ProcessResult<String>> runPythonAsync(String pythonPath, String scriptPath, List<String> params) {
        return CompletableFuture.supplyAsync(() -> {
            StringBuilder output = new StringBuilder();
            try {
                // 组合命令: python3 script.py arg1 arg2 arg3 ...
                List<String> command = new java.util.ArrayList<>();
                command.add(pythonPath);
                command.add(scriptPath);
                command.addAll(params); // 把参数添加进去

                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");  // 保留换行符
                }

                int exitCode = process.waitFor();
                return new ProcessResult<>(output.toString().trim(), exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return new ProcessResult<>("Error: " + e.getMessage(), -1);
            }
        });
    }

    public static CompletableFuture<Void> runPythonAsyncNoWait(String pythonPath, String scriptPath, List<String> params, String workingDir) {
        return CompletableFuture.runAsync(() -> {
            try {
                // 组合命令: python3 script.py arg1 arg2 arg3 ...
                List<String> command = new java.util.ArrayList<>();
                command.add(pythonPath);
                command.add(scriptPath);
                command.addAll(params); // 把参数添加进去

                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                // 设置工作目录
                processBuilder.directory(new File(workingDir));

                // TODO: 输出文件先写死了
                File outputFile = new File(workingDir, "result.txt");
                processBuilder.redirectOutput(outputFile);
                Process process = processBuilder.start();

                System.out.println("process start");

                // 不等待进程结束，直接返回
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    

}