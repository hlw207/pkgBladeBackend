package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.FutureTaskManager;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.Pipeline.model.PipelineEntity;
import org.example.domain.Pipeline.model.PipelineStageEntity;
import org.example.domain.Pipeline.service.IPipelineService;
import org.example.domain.Pipeline.vo.PipelineInfo;
import org.example.domain.Pipeline.vo.PipelineResponse;
import org.example.domain.Pipeline.vo.PipelineStage;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/pipeline")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
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
                                                 @RequestBody MultipartFile file) {
        // 保存文件，创建信息写入数据库
        logger.info("get package:  {} {} {}", missionName, missionDescription, missionType);
        long missionOwnerId = StpUtil.getLoginIdAsLong();
        // 构造项目路径
        // 注意这是linux下的路径
        String basePath = "/home/PkgBlade_" + String.valueOf(missionOwnerId) + "_" + missionName;
        String sourceBasePath = "/home/PkgBlade";
        List<String> toCopyFileNames = new ArrayList<>();
        toCopyFileNames.add("compile_script.py");
        toCopyFileNames.add("extract_symbols.py");
        toCopyFileNames.add("find_symbols_in_code.py");
        toCopyFileNames.add("main.py");
        toCopyFileNames.add("output.py");
        toCopyFileNames.add("get_depend.py");
//        toCopyFileNames.add("get_depends.sh");

        // 创建项目目录basePath
        String missionLocation = basePath + "/" + missionName;
        try {
            File dirFile = new File(basePath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File realFile = new File(missionLocation);
            if(file != null)
                file.transferTo(realFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.UN_ERROR.withData(Boolean.FALSE);
        }

        // 复制python文件
        for (String toCopyFileName : toCopyFileNames) {
            Path sourcePath = Paths.get(sourceBasePath +  "/" + toCopyFileName);
            Path destinationPath = Paths.get(basePath +  "/" + toCopyFileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(toCopyFileName + " 文件复制成功！");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseCode.UN_ERROR.withData(Boolean.FALSE);
            }
        }

        // 获取当前登录用户的Id和流水线任务开始时间
        Date date = new Date();
        Timestamp missionCreateTime = new Timestamp(date.getTime());
        // Service内把后续的任务提交到线程池
        pipelineService.addPipeline(missionName, missionDescription, missionLocation, missionCreateTime, missionOwnerId, missionType);
        return ResponseCode.SUCCESS.withData(Boolean.TRUE);
    }

    @PostMapping("/deletePipeline")
    public ResponseResult<Void> deletePipeline(@RequestParam String missionName){
        long userId = StpUtil.getLoginIdAsLong();
        pipelineService.deletePipeline(userId, missionName);
        return ResponseCode.SUCCESS.withData(null);
    }

    @GetMapping("/getPipeline")
    public ResponseResult<List<PipelineResponse>> getPipeline(){
        List<PipelineResponse> pipelineResponseList = pipelineService.getPipeline(StpUtil.getLoginIdAsLong());
        return ResponseCode.SUCCESS.withData(pipelineResponseList);
    }

    // TODO: 应该加一个参数是usrId
    @PostMapping("/startPipeline")
    public ResponseResult<String> startPipeline(@RequestParam String handlePackageName,@RequestParam String missionName) {
        logger.info("get package:  {} {} {}", handlePackageName, missionName);
        long userId = StpUtil.getLoginIdAsLong();
        String result = pipelineService.startPipeline(missionName, userId, handlePackageName);
        return ResponseCode.SUCCESS.withData(result);
    }

    // TODO: 应该加一个参数是usrId
    @PostMapping("/getPipelineResult")
    public ResponseResult<String> getPipelineResult(@RequestParam String missionName) {
        Future<Void> mainTaskFuture = FutureTaskManager.getTaskFuture(missionName + "_" + StpUtil.getLoginId(), Void.class);
        try {
             mainTaskFuture.get(); // 阻塞直到任务完成并返回结果
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ResponseCode.SUCCESS.withData("test");
    }

    @GetMapping("/getPipelineStageInfo")
    public ResponseResult<List<PipelineStage>> getPipelineStageInfo(@RequestParam String missionName){
        List<PipelineStage> pipelineStageEntityList = pipelineService.getPipelineStageInfo(StpUtil.getLoginIdAsLong(), missionName);
        return ResponseCode.SUCCESS.withData(pipelineStageEntityList);
    }

    @GetMapping("/getPipelineStage")
    public ResponseResult<List<PipelineInfo>> getPipelineStage(@RequestParam String missionName, @RequestParam String missionStageName, @RequestParam int lineCount){
        List<PipelineInfo> pipelineInfoList = pipelineService.getPipeLineInfo(StpUtil.getLoginIdAsLong(), missionName, missionStageName, lineCount);
        return ResponseCode.SUCCESS.withData(pipelineInfoList);
    }

    // TODO: 下载软件包

    // TODO: 获取依赖信息

    // TODO: 获取依赖包路径（包名 ＋ 路径）

    // TODO： 获取修改后的包文件
}
