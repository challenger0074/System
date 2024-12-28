package cn;

import cn.ganxq.dbcontrol.service.IUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    /**
     * 默认使用的token为：{@link org.apache.shiro.authc.UsernamePasswordToken}
     * ，所以需要重写support方法，不然会报错token不匹配
     * <p>或者在realm中设置authenticationTokenClass属性为你想要使用的Token</p>
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*SysUser user = (SysUser)principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = userService.getUserPermissions(userId);

        //用户角色列表
        Set<String> rolesSet = userService.getUserRoles(userId);


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        info.setRoles(rolesSet);
        return info;*/
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
       /* OAuth2Token auth2Token = (OAuth2Token) authenticationToken;
        SimpleAuthenticationInfo info = null;
        String token = auth2Token.getPrincipal();
        String userId;
        try {
            DecodedJWT parse = JwtUtil.parse(token);
            userId = parse.getClaim("userId").toString();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IncorrectCredentialsException("无效的token");
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("过期的token");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new BizException("没有该用户");
        }
        info = new SimpleAuthenticationInfo(user, token, getName());*/
        return new AuthenticationInfo() {
            @Override
            public PrincipalCollection getPrincipals() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }
        };
    }
}
