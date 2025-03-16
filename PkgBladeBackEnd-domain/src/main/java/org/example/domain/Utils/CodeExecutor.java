package org.example.domain.Utils;

import org.example.types.ProcessResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

}