package cn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Auther: Administrator
 * @Date:
 * @Description: CORS配置类
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:1648"); // 允许的来源
        corsConfiguration.addAllowedHeader("*"); // 允许的请求头
        corsConfiguration.addAllowedMethod("*"); // 允许的请求方法
        corsConfiguration.setMaxAge(3600L); // 预检请求的缓存时间
        corsConfiguration.setAllowCredentials(true); // 是否允许带上cookie
        corsConfiguration.addExposedHeader("Authorization"); // 暴露的响应头
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对所有请求路径应用CORS配置
        return new CorsFilter(source);
    }
}
