package com.mtons.mblog.modules.repository;

import com.mtons.mblog.modules.entity.Attention;
import com.mtons.mblog.modules.entity.Message;
import com.mtons.mblog.modules.entity.Ratings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttentionRepository extends JpaRepository<Attention, Long>, JpaSpecificationExecutor<Ratings> {
    @Query(value = "SELECT * FROM mto_attention WHERE user_id = :userId ", nativeQuery = true)
    List<Attention> findAttention(@Param("userId")Long userId);

    @Query(value = "SELECT count(*) FROM mto_attention WHERE author_id = :userId ", nativeQuery = true)
    String findAttentionCounts(@Param("userId")Long userId);

    @Query(value = "SELECT * FROM mto_attention WHERE user_id = :userId and author_id = :authorId", nativeQuery = true)
    Attention findByUserIdAndAuthorId(@Param("userId")Long userId, @Param("authorId")Long authorId);

    @Modifying
    @Query(value = "delete FROM mto_attention WHERE user_id = :userId and author_id = :authorId", nativeQuery = true)
    void unattention(@Param("userId")Long userId, @Param("authorId")Long authorId);

    Page<Attention> findAllByUserId(Pageable pageable, long userId);

    Page<Attention> findByAuthorId(Pageable pageable, long userId);
}
