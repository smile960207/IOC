package cn.jiayao.platform.modular.dao.impl;

import cn.jiayao.platform.core.annotation.MyMapping;
import cn.jiayao.platform.modular.dao.LoginMapping;

/**
 * 类 名: LoginMapping
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 9:19
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@MyMapping
public class LoginMappingImpl implements LoginMapping {

    @Override
    public String login() {
        return "项目启动成功";
    }
}
