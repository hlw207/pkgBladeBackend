/**
 * HTTP 接口服务
 */
package org.example.trigger.http;
import cn.dev33.satoken.stp.StpUtil;
import org.example.domain.User.service.ILoginService;
import org.example.domain.User.service.IUserService;
import org.example.types.ResponseResult;
import org.example.types.enums.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController {

    private final ILoginService loginService;
    private final IUserService userService;

    @Autowired
    public AuthController(ILoginService loginService, IUserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    public ResponseResult<String> login(@RequestParam String username, @RequestParam String password) {
        if (loginService.login(username, password)) {
            StpUtil.login(userService.getUserIdByName(username));
            return ResponseCode.SUCCESS.withData(StpUtil.getTokenValue());
        } else {
            return ResponseCode.SUCCESS.withData("Invalid username or password!");
        }
    }

    @PostMapping("/register")
    public ResponseResult<String> register(@RequestParam String username, @RequestParam String password) {
        if (loginService.register(username, password)) {
            return ResponseCode.SUCCESS.withData("Register successful!");
        } else {
            return ResponseCode.SUCCESS.withData("Invalid username or password!");
        }
    }
}