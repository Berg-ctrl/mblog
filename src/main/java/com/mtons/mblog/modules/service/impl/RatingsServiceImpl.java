package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.entity.Ratings;
import com.mtons.mblog.modules.repository.RatingsRepository;
import com.mtons.mblog.modules.service.RatingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * @author: yangq
 * @create: 2021-04-13 10:47
 **/
@Service
public class RatingsServiceImpl implements RatingsService {
    @Autowired
    private RatingsRepository ratingsRepository;

    @Override
    public void update(Long userId, Long postId) {
        Ratings ratings = ratingsRepository.findRatings(userId, postId);
        try {
            if(ratings!=null){
                ratings.setRating(ratings.getRating()+5);
                ratingsRepository.save(ratings);
                return;
            }
            ratings = new Ratings();
            ratings.setRating(5L);
            ratings.setUserId(userId);
            ratings.setPostId(postId);
            ratings.setTimestamp(new Timestamp(System.currentTimeMillis()));
            ratingsRepository.save(ratings);
        }catch (Exception e){
            System.out.println("============"+e.getMessage());
        }

    }
}
