package cn.ganxq.dbcontrol.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/")
public class TestController {

    // 测试登录，浏览器访问： http://localhost:8001/test/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8001/test/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
    // 登录校验：只有登录之后才能进入该方法
    @SaCheckLogin
    @RequestMapping("info")
    public String info() {
        return "查询用户信息";
    }
    // 角色校验：必须具有指定角色才能进入该方法
    @SaCheckRole("super-admin")
    @RequestMapping("addrole")
    public String addrole() {
        return "用户增加";
    }
    // 权限校验：必须具有指定权限才能进入该方法
    @SaCheckPermission("user-add")
    @RequestMapping("add")
    public String add() {
        return "用户增加";
    }
    // 角色权限双重 “or校验”：具备指定权限或者指定角色即可通过校验
    @RequestMapping("userAdd")
    @SaCheckPermission(value = "user.add", orRole = "admin")
    public SaResult userAdd() {
        return SaResult.data("用户信息");
    }
    // 此接口加上了 @SaIgnore 可以游客访问
    @SaIgnore
    @RequestMapping("getList")
    public SaResult getList() {
        // ...
        return SaResult.ok();
    }


}
