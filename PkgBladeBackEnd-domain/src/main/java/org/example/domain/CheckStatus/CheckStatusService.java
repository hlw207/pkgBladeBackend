package org.example.domain.CheckStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckStatusService implements ICheckStatusService{
    @Override
    public String check(Long userId, String missionName) {
        String resultFilePath = "D:/PkgBlade_" + String.valueOf(userId) + "_" + missionName + "/result.txt";
        Path path = Paths.get(resultFilePath);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            // 读取不到东西就抛出错误
            throw new RuntimeException(e);
        }

    }
}
