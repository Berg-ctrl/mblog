package com.mtons.mblog.modules.service;

import org.springframework.stereotype.Service;

/**
 * @author: yangq
 * @create: 2021-04-13 10:47
 **/
public interface RatingsService {
    void update(Long userId, Long postId);
}
