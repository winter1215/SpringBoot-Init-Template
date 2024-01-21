package com.tower.portal.security;

import cn.hutool.json.JSONUtil;
import com.tower.common.enums.ErrorCode;
import com.tower.common.utils.R;
import com.tower.security.Handler.RestAuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * @author CYY
 * @date 2022年11月26日 下午6:00
 * @description 前台认证失败的处理器
 */
@Component
public class PortalAuthenticationEntryPoint extends RestAuthenticationEntryPoint {
    @Override
    public Object errorMsg() {
        return JSONUtil.parse(
                R.error()
                        .code(ErrorCode.AUTHENTICATION_ERROR.getCode())
                        .message(ErrorCode.AUTHENTICATION_ERROR.getMessage())
        );
    }
}
