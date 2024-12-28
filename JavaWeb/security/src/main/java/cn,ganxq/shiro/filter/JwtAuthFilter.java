package cn;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Component
public class JwtAuthFilter extends BasicHttpAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
    private static final String LOGIN_FAILURE_MESSAGE = "Login failed: ";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");

        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = getRequestToken((HttpServletRequest) request);
        return (token != null && !token.isEmpty()) ? new JwtToken(token) : null;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken((HttpServletRequest) request);
        if (token == null || token.isEmpty()) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, INVALID_TOKEN_MESSAGE);
            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, LOGIN_FAILURE_MESSAGE + e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        logger.warn("Login failed: {}", e.getMessage());
        return false;
    }

    private String getRequestToken(HttpServletRequest httpRequest) {
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    private void sendErrorResponse(ServletResponse response, HttpStatus status, String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(status.value());
        httpResponse.setContentType(JSON_CONTENT_TYPE);
        httpResponse.getWriter().print("{\"error\": \"" + message + "\"}");
    }
}
