package cn.jiayao.platform.modular.service.impl;

import cn.jiayao.platform.core.annotation.MyAutowired;
import cn.jiayao.platform.core.annotation.MyService;
import cn.jiayao.platform.modular.dao.LoginMapping;
import cn.jiayao.platform.modular.service.LoginService;

@MyService(value = "loginServiceImplTest")
public class LoginServiceImpl implements LoginService {

    @MyAutowired
    private LoginMapping loginMapping;

    @Override
    public String login() {
        return loginMapping.login();
    }
}
