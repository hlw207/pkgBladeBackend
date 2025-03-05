package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.Package.service.IDependencyService;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/pipeline")
public class PipelineController {
    private final IDependencyService dependencyService;

    public PipelineController(IDependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/createPipeline")
    public ResponseResult<Boolean> createPipeline(@RequestParam String missionName,
                                                 @RequestParam String missionDescription,
                                                 @RequestParam String missionType,
                                                 @RequestParam MultipartFile file) {
        // 保存文件，创建信息写入数据库
        logger.info("get package:  {} {} {}", missionName, missionDescription, missionType);
        String basePath = "./PkgBlade" + StpUtil.getLoginId();
        String datePath = new SimpleDateFormat("/yyyy_MM_dd/").format(new Date());

        String localDir = basePath + datePath;

        try {
            File dirFile = new File(localDir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            String finalPath = localDir + missionName;
            File realFile = new File(finalPath);
            file.transferTo(realFile);
        } catch (Exception e) {
            return ResponseCode.UN_ERROR.withData(Boolean.FALSE);
        }

        return ResponseCode.SUCCESS.withData(Boolean.TRUE);




    }
}
