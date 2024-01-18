package com.winter.init.service;

import com.winter.init.model.entity.LoginUser;
import com.winter.init.model.entity.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.winter.init.model.entity.User;

/**
 * 帖子点赞服务

 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, LoginUser loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
