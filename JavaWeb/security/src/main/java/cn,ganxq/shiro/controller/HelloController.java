package cn;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD})
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello Shiro!");
        return "Hello Shiro!";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "please login!";
    }
    @GetMapping("/doLogin")
    public String doLogin(LoginForm loginForm, ServletResponse response) {
        System.out.println("用户：" + loginForm);
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        try {
            subject.login(new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword()));
            System.out.println("用户：" + loginForm.getUsername() + ",登录成功！");
        } catch (Exception e) {
            System.out.println("登录异常" + e.getMessage());
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String jwtToken = JwtUtil.generateToken(subject.getPrincipal().toString());

        httpResponse.setHeader("Authorization", "Bearer " + jwtToken);
        return "login";

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
