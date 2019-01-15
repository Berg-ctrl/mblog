package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.repository.NotifyDao;
import com.mtons.mblog.modules.data.NotifyVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Notify;
import com.mtons.mblog.modules.service.NotifyService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.modules.utils.BeanMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author langhsu on 2015/8/31.
 */
@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyDao notifyDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    @Transactional(readOnly = true)
    public Page<NotifyVO> findByOwnId(Pageable pageable, long ownId) {
        Page<Notify> page = notifyDao.findAllByOwnIdOrderByIdDesc(pageable, ownId);
        List<NotifyVO> rets = new ArrayList<>();

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        page.getContent().forEach(po -> {
            NotifyVO no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }

        });

        // 加载
        Map<Long, PostVO> posts = postService.findMapByIds(postIds);
        Map<Long, UserVO> fromUsers = userService.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void send(NotifyVO notify) {
        if (notify == null || notify.getOwnId() <=0 || notify.getFromId() <= 0) {
            return;
        }

        Notify po = new Notify();
        BeanUtils.copyProperties(notify, po);
        po.setCreated(new Date());

        notifyDao.save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public int unread4Me(long ownId) {
        return notifyDao.countByOwnIdAndStatus(ownId, Consts.UNREAD);
    }

    @Override
    @Transactional
    public void readed4Me(long ownId) {
        notifyDao.updateReadedByOwnId(ownId);
    }
}