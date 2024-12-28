package cn;

import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 这里不做授权校验
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authcToken;

        // 用户登录时传入的用户名称
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 根据用户名查询用户信息
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username) );
        if (user == null) {
            throw new UnknownAccountException("账户不存在!");
        }

        if (!password.equals(user.getPassword())) {
            throw new UnknownAccountException("密码错误!");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }
}
