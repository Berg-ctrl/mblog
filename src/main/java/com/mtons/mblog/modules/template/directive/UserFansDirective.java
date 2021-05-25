/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.modules.entity.Attention;
import com.mtons.mblog.modules.repository.AttentionRepository;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取收藏列表
 *
 * @author yangquan
 * @since 3.0
 */
@Component
public class UserFansDirective extends TemplateDirective {
    @Autowired
    private AttentionRepository attentionRepository;

	@Override
	public String getName() {
		return "user_fans";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);
        Page<Attention> result = attentionRepository.findByAuthorId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
