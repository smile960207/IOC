package cn.jiayao.platform.modular.service.impl;

import cn.jiayao.platform.core.annotation.MyAutowired;
import cn.jiayao.platform.core.annotation.MyService;
import cn.jiayao.platform.modular.dao.LoginMapping;
import cn.jiayao.platform.modular.service.LoginService;

/**
 * 类 名: LoginServiceImpl
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 9:15
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@MyService(value = "loginServiceImplTest")
public class LoginServiceImpl implements LoginService {

    @MyAutowired
    private LoginMapping loginMapping;

    @Override
    public String login() {
        return loginMapping.login();
    }
}
