package com.mtons.mblog.web.controller.site.user;

import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.entity.Attention;
import com.mtons.mblog.modules.entity.Favorite;
import com.mtons.mblog.modules.event.MessageEvent;
import com.mtons.mblog.modules.repository.AttentionRepository;
import com.mtons.mblog.modules.repository.UserRepository;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.service.RatingsService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author yangquan
 */
@RestController
@RequestMapping("/user")
public class FavorController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RatingsService ratingsService;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 收藏文章
     * @param id
     * @return
     */
    @Transactional
    @RequestMapping("/favor")
    public Result favor(Long id) {
        Result data = Result.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.favor(up.getId(), id);
                ratingsService.update(up.getId(),id);
                sendMessage(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @RequestMapping("/unfavor")
    public Result unfavor(Long id) {
        Result data = Result.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.unfavor(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 发送通知
     * @param userId
     * @param postId
     */
    private void sendMessage(long userId, long postId) {
        MessageEvent event = new MessageEvent("MessageEvent" + System.currentTimeMillis());
        event.setFromUserId(userId);
        event.setEvent(Consts.MESSAGE_EVENT_FAVOR_POST);
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }

    /**
     * 关注作者
     * @param id
     * @return
     */
    @Transactional
    @RequestMapping("/attention")
    public Result attention(Long id) {
        Result data = Result.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                Attention attention1 = attentionRepository.findByUserIdAndAuthorId(up.getId(), id);
                Assert.isNull(attention1, "您已经收藏过此文章");
                Attention attention = new Attention();
                attention.setAuthorId(id);
                attention.setUserId(up.getId());
                attention.setName(userRepository.findById(id).get().getUsername());
                attention.setNamee(up.getUsername());
                attention.setCreated(new Date());
                attentionRepository.save(attention);
                sendMessage(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

    /**
     * 查看关注作者列表
     * @return
     */
    @Transactional
    @RequestMapping("/attentionList")
    public Result attentionList() {
        Result data = Result.failure("操作失败");
            try {
                AccountProfile up = getProfile();
                List<Attention> attention = attentionRepository.findAttention(up.getId());
                data = Result.success(attention);
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        return data;
    }

    /**
     * 取消关注
     * @param id
     * @return
     */
    @RequestMapping("/unattention")
    public Result unattention(Long id) {
        Result data = Result.failure("操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                attentionRepository.deleteById(attentionRepository.findByUserIdAndAuthorId(up.getId(), id).getId());
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }
}
