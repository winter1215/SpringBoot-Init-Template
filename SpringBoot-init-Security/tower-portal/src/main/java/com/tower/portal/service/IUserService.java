package com.tower.portal.service;

import com.tower.common.model.dto.UpdateUserDto;
import com.tower.common.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tower.common.utils.R;
import com.tower.common.model.dto.RegisterUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CYY&winter
 * @since 2022-11-20
 */
public interface IUserService extends IService<User> {
    int register(RegisterUser registerUser);

    /**
     * 用户登录
     * @return 登录成功返回token失败抛出异常交于GlobalExceptionHandler统一发送error消息
     */
    String login(String username, String password);

    /**
     * 通过用户名获取user对象(实现UserDetails的对象)
     * @param username 用户名
     * @return user对象,不存在返回null
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 通过用户名从redis或者数据库获取用户，redis不存在会从数据库查询并将结果存入redis如果数据库中不存在则返回null
     * @param username 用户名
     * @return 用户对象或者null
     */
    User getUserByUsername(String username);

    /**
     * 更新用户，通过用户名更新redis的user和通过id更新数据库的user
     * @param user user
     */
    int updateUser(User user);

    /**
     * 忘记密码，验证验证码是否正确，再通过邮箱获取对应用户更改其密码
     *
     * @param code 验证码
     * @param email 邮箱
     * @param password 新密码
     * @return 是否成功
     */
    R forgetPassword(String code, String email, String password);

    /**
     * 更新用户密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    R updatePassword(String oldPassword, String newPassword);

    /**
     * 更新用户信息
     * @param updateUserDto:
     * @return: void
     * @author: winter
     * @date: 2023/5/8 下午3:33
     * @description:
     */
    int updateUserDetail(UpdateUserDto updateUserDto);
}
