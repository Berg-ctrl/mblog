package com.mtons.mblog.modules.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: yangq
 * @create: 2021-05-21 14:12
 **/
@Entity
@Table(name = "mto_attention")
@Data
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private Long userId;

    /**
     * 作者id
     */
    @Column(name = "author_id", nullable = false, length = 64)
    private Long authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "namee")
    private String namee;

    @Column(name = "created")
    private Date created;

}
