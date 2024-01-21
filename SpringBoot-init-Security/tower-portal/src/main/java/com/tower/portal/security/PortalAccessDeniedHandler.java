package com.tower.portal.security;

import cn.hutool.json.JSONUtil;
import com.tower.common.enums.ErrorCode;
import com.tower.common.utils.R;
import com.tower.security.Handler.RestAccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CYY
 * @date 2022年11月26日 下午10:30
 * @description 前台鉴权失败处理器
 */
@Component
public class PortalAccessDeniedHandler extends RestAccessDeniedHandler {

    @Override
    public Object errorMsg() {
        return JSONUtil.parse(
                R.error()
                        .code(ErrorCode.NO_AUTH_ERROR.getCode())
                        .message(ErrorCode.NO_AUTH_ERROR.getMessage())
        );
    }
}
