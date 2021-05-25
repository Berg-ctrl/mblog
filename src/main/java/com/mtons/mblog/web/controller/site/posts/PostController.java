/**
 *
 */
package com.mtons.mblog.web.controller.site.posts;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.lang.Result;
import com.mtons.mblog.modules.data.AccountProfile;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Post;
import com.mtons.mblog.modules.entity.Ratings;
import com.mtons.mblog.modules.entity.User;
import com.mtons.mblog.modules.repository.PostRepository;
import com.mtons.mblog.modules.service.ChannelService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.service.RatingsService;
import com.mtons.mblog.modules.service.UserService;
import com.mtons.mblog.web.controller.BaseController;
import com.mtons.mblog.web.controller.site.Views;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.AbstractList;
import java.util.List;
import java.util.Random;

/**
 * 文章操作
 * @author yangquan
 *
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private UserService userService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private RatingsService ratingsService;

	/**
	 * 发布文章页
	 * @return
	 */
	@GetMapping("/editing")
	public String view(Long id, ModelMap model) {
		model.put("channels", channelService.findAll(Consts.STATUS_NORMAL));
		model.put("editing", true);
		String editor = siteOptions.getValue("editor");
		if (null != id && id > 0) {
			AccountProfile profile = getProfile();
			PostVO view = postService.get(id);

			Assert.notNull(view, "该文章已被删除");
			Assert.isTrue(view.getAuthorId() == profile.getId(), "该文章不属于你");

			Assert.isTrue(view.getChannel().getStatus() == Consts.STATUS_NORMAL, "请在后台编辑此文章");
			model.put("view", view);

			if (StringUtils.isNoneBlank(view.getEditor())) {
				editor = view.getEditor();
			}
		}
		model.put("editor", editor);
		return view(Views.POST_EDITING);
	}

	/**
	 * 提交发布
	 * @param post
	 * @return
	 */
	@PostMapping("/submit")
	public String post(PostVO post) {
		Assert.notNull(post, "参数不完整");
		Assert.state(StringUtils.isNotBlank(post.getTitle()), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(post.getContent()), "内容不能为空");

		AccountProfile profile = getProfile();
		post.setAuthorId(profile.getId());

		// 修改时, 验证归属
		if (post.getId() > 0) {
			PostVO exist = postService.get(post.getId());
			Assert.notNull(exist, "文章不存在");
			Assert.isTrue(exist.getAuthorId() == profile.getId(), "该文章不属于你");

			postService.update(post);
		} else {
			postService.post(post);
		}
		return String.format(Views.REDIRECT_USER_HOME, profile.getId());
	}

	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable Long id) {
		Result data;
		try {
			postService.delete(id, getProfile().getId());
			data = Result.success();
		} catch (Exception e) {
			data = Result.failure(e.getMessage());
		}
		return data;
	}

	@GetMapping("/testPost")
	public String insertPost(){
		for(int i=146;i<300;i++){
			if(postService.get(i)==null) continue;
			postService.get(i).setTitle(String.valueOf(i));
			postService.update(postService.get(i));
		}

//		PostVO postVO = new PostVO();
//		Random random = new Random();
//		for(int i=0;i<100;i++){
//			postVO.setAuthorId(random.nextInt(305)+30);
//			postVO.setContent("testttttttt");
//			postVO.setChannelId(2);
//			postVO.setTitle("testtt");
//			postVO.setEditor("markdown");
//			postVO.setTags("");
//			postVO.setStatus(0);
//			postVO.setThumbnail("");
//			postVO.setId(0);
//			postService.post(postVO);
//		}

		return "ok";
	}

	@GetMapping("/testUser")
	public String insertUser(){
		UserVO user = new UserVO();
		Random random = new Random();
		for(int i=105;i<400;i++){
			user.setId(i);
			user.setPassword("123456");
			user.setName(String.valueOf(i));
			user.setUsername(String.valueOf(i));
			userService.register(user);
		}

		return "ok";
	}

	@GetMapping("/testRates")
	public String insertRate(){
//		Ratings ratings = new Ratings();
//		ratings.setTimestamp(new Timestamp(System.currentTimeMillis()));
//		ratings.setRating(rates);
//		ratings.setPostId(postId);
//		ratings.setUserId(userId);
		Random random = new Random();
		for(int i=0;i<500;i++){
			ratingsService.update((long) random.nextInt(200), (long) random.nextInt(100));
		}

		return "ok";
	}
}
