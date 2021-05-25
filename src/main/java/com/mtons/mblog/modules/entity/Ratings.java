package com.mtons.mblog.modules.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: yangq
 * @create: 2020-12-20 14:16
 **/
@Entity
@Table(name = "mto_ratings")
@Data
public class Ratings implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private Long userId;

    /**
     * 博客id
     */
    @Column(name = "post_id", nullable = false, length = 64)
    private Long postId;

    /**
     * 打分
     */
    @Column(name = "rating", nullable = false, length = 64)
    private Long rating;

    /**
     * 时间戳
     */
    @Column(name = "timestamp")
    private Timestamp timestamp;
}
