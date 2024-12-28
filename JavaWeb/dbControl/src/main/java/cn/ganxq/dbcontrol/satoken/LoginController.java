package cn.ganxq.dbcontrol.satoken;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.satoken.entity.LoginForm;
import cn.ganxq.dbcontrol.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/entry")
@RestController
public class LoginController {
    @Autowired
    IUserService userService;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello Shiro!");
        return "Hello Shiro!";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "please login!";
    }
    @PostMapping("/doLogin")
    public SaResult doLogin(@RequestBody LoginForm loginForm, ServletResponse response) {
        System.out.println("用户：" + loginForm);
        //根据用户名从数据库中查询
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginForm.getUsername()));
        if(user == null){
            return SaResult.error("用户名不存在");
        }
        if(!user.getPassword().equals(loginForm.getPassword())){
            return SaResult.error("密码错误");
        }

        // 第1步，先登录上
        StpUtil.login(user.getId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        //第3步,把用户数据保存到session
        StpUtil.getSession().set("user", user);
        // 第4步，返回给前端
        return SaResult.data(tokenInfo);

    }
    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 创建成功的响应
     */
    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User user) {
        try {
            userService.createUser(user); // 调用服务层方法保存用户
            return ResponseEntity.ok("用户创建成功");
        } catch (Exception e) {
            // 处理异常并返回错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("注册失败: " + e.getMessage());
        }
    }
    // 新增：获取当前登录用户的 session 数据
    @GetMapping("/getSession")
    public SaResult getSession() {
        // 获取当前用户的 session
        Object user = StpUtil.getSession().get("user");
        if (user == null) {
            return SaResult.error("未登录或会话已过期");
        }
        return SaResult.data(user);
    }
    /**
     * 注销
     * @return
     */
    @RequestMapping("/signOut")
    public SaResult signOut() {
        String loginId = null;
        if (StpUtil.isLogin()){
            loginId = (String) StpUtil.getLoginId();
            StpUtil.logout();
        }
        return SaResult.ok("会话ID为 " + loginId + " 的用户注销登录成功");
    }

    /*@RequestMapping("/doLogin")
    public void doLogin(@RequestParam String username, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("用户：" + username + ",登录成功！");
        } catch (Exception e) {
            System.out.println("登录异常" + e.getMessage());
        }
    }*/

}
