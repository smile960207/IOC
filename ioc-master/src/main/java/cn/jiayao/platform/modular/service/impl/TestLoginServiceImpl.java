package cn.jiayao.platform.modular.service.impl;

import cn.jiayao.platform.core.annotation.MyService;
import cn.jiayao.platform.modular.service.LoginService;

/**
 * 类 名: TestLoginServiceImpl
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/18 : 17:17
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@MyService(value = "testLoginServiceImpl")
public class TestLoginServiceImpl implements LoginService {

    @Override
    public String login() {
        return "测试多态情况下依赖注入";
    }
}
