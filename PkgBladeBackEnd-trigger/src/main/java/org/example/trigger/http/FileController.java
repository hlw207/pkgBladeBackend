package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequestMapping("/file")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @PostMapping("/pkgAdd")
    public ResponseResult<Void> saveFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, @RequestParam("description") String description) {
        String pkgName = file.getOriginalFilename();
        logger.info("get package:  {} {} {}", pkgName, fileName, description);
        String basePath = "./PkgBlade" + (Long) StpUtil.getLoginId();
        String datePath =
                new SimpleDateFormat("/yyyy_MM_dd/").format(new Date());

        String localDir = basePath + datePath;

        try {
            File dirFile = new File(localDir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            String finalPath = localDir + pkgName;
            File realFile = new File(finalPath);
            file.transferTo(realFile);
        } catch (Exception e) {
            return ResponseCode.UN_ERROR.withException(e.getMessage());
        }
        return ResponseCode.SUCCESS.withData(null);
    }
}
