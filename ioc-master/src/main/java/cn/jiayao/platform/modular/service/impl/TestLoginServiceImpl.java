package cn.jiayao.platform.modular.service.impl;

import cn.jiayao.platform.core.annotation.MyService;
import cn.jiayao.platform.modular.service.LoginService;

@MyService(value = "testLoginServiceImpl")
public class TestLoginServiceImpl implements LoginService {

    @Override
    public String login() {
        return "测试多态情况下依赖注入";
    }
}
