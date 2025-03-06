package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.service.IPipelineService;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/pipeline")
public class PipelineController {
    private final IDependencyService dependencyService;
    private final IPipelineService pipelineService;

    public PipelineController(IDependencyService dependencyService, IPipelineService pipelineService) {
        this.dependencyService = dependencyService;
        this.pipelineService = pipelineService;
    }

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/createPipeline")
    public ResponseResult<Boolean> createPipeline(@RequestParam String missionName,
                                                 @RequestParam String missionDescription,
                                                 @RequestParam int missionType,
                                                 @RequestParam MultipartFile file) {
        // 保存文件，创建信息写入数据库
        logger.info("get package:  {} {} {}", missionName, missionDescription, missionType);
        String basePath = "./PkgBlade" + StpUtil.getLoginId();
        String datePath = new SimpleDateFormat("/yyyy_MM_dd/").format(new Date());

        String localDir = basePath + datePath;
        String missionLocation = localDir + missionName;
        try {
            File dirFile = new File(localDir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }

            File realFile = new File(missionLocation);
            file.transferTo(realFile);
        } catch (Exception e) {
            return ResponseCode.UN_ERROR.withData(Boolean.FALSE);
        }
        // 获取当前登录用户的Id和流水线任务开始时间
        long missionOwnerId = StpUtil.getLoginIdAsLong();
        Date date = new Date();
        Timestamp missionCreateTime = new Timestamp(date.getTime());
        pipelineService.addPipeline(missionName, missionDescription, missionLocation, missionCreateTime, missionOwnerId, missionType);

        // 把后续的任务提交到线程池
        // 第一个任务就是依赖拉取

        return ResponseCode.SUCCESS.withData(Boolean.TRUE);
    }
}
