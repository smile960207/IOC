package cn.jiayao.platform.modular.controller;

import cn.jiayao.platform.core.annotation.MyAutowired;
import cn.jiayao.platform.core.annotation.MyController;
import cn.jiayao.platform.core.annotation.Value;
import cn.jiayao.platform.modular.service.LoginService;

/**
 * 类 名: LoginController
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 8:58
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
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
