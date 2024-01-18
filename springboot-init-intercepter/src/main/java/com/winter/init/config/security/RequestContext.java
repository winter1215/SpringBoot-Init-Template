package com.winter.init.config.security;

import cn.hutool.core.bean.BeanUtil;
import com.winter.init.model.entity.LoginUser;
import com.winter.init.model.vo.LoginUserVO;

import java.util.Map;

/**
 * 请求上下文
 * @author winter
 * @create 2024-01-15 17:39
 */
public class RequestContext {
    private static final ThreadLocal<LoginUser> REQUEST_DATA = new ThreadLocal<>();

    public static Long getUserId() {
        LoginUser loginUser = REQUEST_DATA.get();
        if (null == loginUser) {
            return null;
        }
        return loginUser.getId();
    }

    public static LoginUser getLoginUser() {
        return REQUEST_DATA.get();
    }

    public static void setRequestData(LoginUser loginUser) {
        REQUEST_DATA.set(loginUser);
    }

    public static void remove() {
        REQUEST_DATA.remove();
    }
}
