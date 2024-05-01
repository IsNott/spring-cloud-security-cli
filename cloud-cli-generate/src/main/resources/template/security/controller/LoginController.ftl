package ${parent.groupId}.${parent.childLastPackage}.security.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import ${parent.groupId}.${parent.childLastPackage}.common.model.Result;
import ${parent.groupId}.${parent.childLastPackage}.security.jwt.RecruitTokenService;
import ${parent.groupId}.${parent.childLastPackage}.service.IUsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class LoginController {

    @Resource
    private IUsersService usersService;
    @Resource
    private RecruitTokenService tokenService;

    @PostMapping(value = "/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        boolean removedAuthentication = tokenService.removeAuthentication(request);
        return Result.success(removedAuthentication);
    }

}
