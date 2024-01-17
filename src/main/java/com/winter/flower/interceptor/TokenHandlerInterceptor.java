package com.winter.flower.interceptor;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.winter.flower.config.exception.BusinessException;
import com.winter.flower.config.security.RequestContext;
import com.winter.flower.config.security.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

/**
 * Token拦截器
 * @author winter
 * @create 2024-01-15 17:04
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenHandlerInterceptor implements HandlerInterceptor {
    private final SecurityConfiguration securityConfiguration;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader("token");
        String jwtKey = securityConfiguration.getJwtKey();
        // 检查 token 是否合法
        if (StringUtils.isEmpty(token) || !JWTUtil.verify(token, jwtKey.getBytes(StandardCharsets.UTF_8))) {
            throw new ValidateException();
        }
        JWTValidator.of(token).validateDate();

        Map<String, Object> map = JWTUtil.parseToken(token).getPayloads().toBean(Map.class);
        RequestContext.setRequestData(map);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContext.remove();
    }
}
