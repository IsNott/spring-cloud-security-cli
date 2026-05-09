package org.nott.cli.security.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.nott.cli.common.model.Result;
import org.nott.cli.security.jwt.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class LoginController {
    @Resource
    private TokenService tokenService;

    @PostMapping(value = "/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        boolean removedAuthentication = tokenService.removeAuthentication(request);
        return Result.success(removedAuthentication);
    }

}
