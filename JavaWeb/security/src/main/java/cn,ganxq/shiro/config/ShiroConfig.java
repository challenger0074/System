package cn;

import jakarta.servlet.Filter;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ShiroConfig {

    @Bean
    public LoginRealm realm() {
        return new LoginRealm(); // Replace with your custom Realm
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000); // 30 minutes
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm(); // Add any necessary configurations here
    }

    @Bean
    public SecurityManager securityManager(LoginRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>(2);
        realms.add(realm);
        realms.add(shiroRealm());
        manager.setRealms(realms); // Configure multiple realms

        // If using session management, uncomment and configure as needed
        // manager.setSessionManager(sessionManager());

        return manager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorizedurl");

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JwtAuthFilter());
        bean.setFilters(filters);

        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/doLogin", "anon"); // Allow anonymous access
        filterChainMap.put("/**", "jwt"); // Require JWT authentication for all other paths

        bean.setFilterChainDefinitionMap(filterChainMap);
        return bean;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public Authorizer authorizer() {
        return new ModularRealmAuthorizer(); // Necessary bean to prevent startup errors
    }
}
