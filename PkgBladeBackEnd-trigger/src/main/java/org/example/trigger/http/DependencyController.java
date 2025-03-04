package org.example.trigger.http;

import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.Package.service.IDependencyService;
import org.example.domain.User.service.ILoginService;
import org.example.domain.User.service.IUserService;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dependency")
public class DependencyController {

    private final IDependencyService dependencyService;
    @Autowired
    public DependencyController(IDependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @PostMapping("/getPackageDependency")
    public ResponseResult<String> getAllPackageDependencyName(@RequestParam String packageName) {
        String res = dependencyService.getPackageAllDependency(packageName);
        if (res == null) {
            return ResponseCode.UN_ERROR.withData(null);
        } else {
            return ResponseCode.SUCCESS.withData(res);
        }
    }
}
