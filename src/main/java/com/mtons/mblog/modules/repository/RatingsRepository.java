package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Post;
import com.mtons.mblog.modules.entity.Ratings;
import com.mtons.mblog.modules.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long>, JpaSpecificationExecutor<Ratings> {

    @Query(value = "SELECT * FROM mto_ratings WHERE user_id = :userId AND post_id = :postId ", nativeQuery = true)
    Ratings findRatings(@Param("userId")Long userId, @Param("postId")Long postId);
}
