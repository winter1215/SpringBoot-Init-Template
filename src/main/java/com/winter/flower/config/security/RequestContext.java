package com.winter.flower.config.security;

import cn.hutool.core.bean.BeanUtil;
import com.winter.flower.model.vo.LoginUserVO;

import java.util.Map;

/**
 * 请求上下文
 * @author winter
 * @create 2024-01-15 17:39
 */
public class RequestContext {
    private static final ThreadLocal<Map<String, Object>> REQUEST_DATA = new ThreadLocal<>();

    public static Long getUserId() {
        Map<String, Object> data = REQUEST_DATA.get();
        if (null == data) {
            return null;
        }
        return (Long) data.get("userId");
    }

    public static LoginUserVO getLoginUser() {
        Map<String, Object> requestData = REQUEST_DATA.get();
        return BeanUtil.toBean(requestData, LoginUserVO.class);
    }

    public static void setRequestData(Map<String, Object> requestData) {
        REQUEST_DATA.set(requestData);
    }

    public static void remove() {
        REQUEST_DATA.remove();
    }
}
