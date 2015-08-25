package mblog.persist.dao;

import mblog.persist.entity.VerifyPO;
import mblog.persist.service.VerifyService;
import mtons.modules.persist.Dao;

/**
 * @author langhsu on 2015/8/14.
 */
public interface VerifyDao extends Dao<VerifyPO> {
    VerifyPO get(long userId, int type);
    VerifyPO getByUserId(long userId);
}