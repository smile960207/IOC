package cn.jiayao.platform;

import cn.jiayao.platform.modular.controller.LoginController;
import cn.jiayao.platform.common.MyApplicationContext;

/**
 * 类 名: PlatformApplication
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/16 : 11:58
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
public class PlatformApplication {

    public static void main(String[] args) throws Exception {
        // 从容器中获取对象(自动首字母小写)
        MyApplicationContext applicationContext = new MyApplicationContext();
        LoginController loginController = (LoginController) applicationContext.getIocBean("LoginController");
        String login = loginController.login();
        System.out.println(login);
    }

}
