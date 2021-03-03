package cn.jiayao.platform.modular.controller;

import cn.jiayao.platform.core.annotation.MyAutowired;
import cn.jiayao.platform.core.annotation.MyController;
import cn.jiayao.platform.core.annotation.Value;
import cn.jiayao.platform.modular.service.LoginService;

@MyController
public class LoginController {

    @Value(value = "ioc.scan.pathTest")
    private String pathTest;

//    @MyAutowired(value = "loginServiceImplTest")
    @MyAutowired
    private LoginService loginServiceImplTest;

    @MyAutowired
    private LoginService testLoginServiceImpl;

    public String login() {
        return loginServiceImplTest.login();
    }

}
