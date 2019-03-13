package com.harry.service.system;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.harry.common.BaseException;
import com.harry.common.BaseService;
import com.harry.common.ResponseCode;
import com.harry.dao.system.HyUserMapper;
import com.harry.entity.system.HyUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author harry
 * @create 2019-03-12 16:09
 **/
@Component
public class HyUserService extends BaseService<HyUser, Integer> {

    private final String KEYS = "USER_KEY";

    @Resource
    private HyUserMapper hyUserMapper;

    @Override
    protected String getRedisKey() {
        return KEYS;
    }

    public void registerAccount(HyUser user){
        String password = user.getPassword();
        String userAccount = user.getUserAccount();
        String userName = user.getUserName();
        if(StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(password)||StringUtils.isEmpty(userName)){
            throw new BaseException(ResponseCode.Param_error, "参数错误");
        }
        user.setCreateTime(new Date());

    }


}
