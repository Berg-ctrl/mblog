/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.modules.data.FavoriteVO;
import com.mtons.mblog.modules.data.MessageVO;
import com.mtons.mblog.modules.entity.Attention;
import com.mtons.mblog.modules.repository.AttentionRepository;
import com.mtons.mblog.modules.service.FavoriteService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 根据作者取收藏列表
 *
 * @author yangquan
 * @since 3.0
 */
@Component
public class UserAttentionDirective extends TemplateDirective {
    @Autowired
    private AttentionRepository attentionRepository;

	@Override
	public String getName() {
		return "user_attention";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);
        Page<Attention> result = attentionRepository.findAllByUserId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
