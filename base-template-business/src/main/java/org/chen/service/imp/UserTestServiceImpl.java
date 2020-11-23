package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.UserTestMapper;
import org.chen.domain.entity.UserTest;
import org.chen.service.IUserTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.UserTestMapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuChen
 * @since 2020-08-24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserTestServiceImpl extends ServiceImpl<UserTestMapper, UserTest> implements IUserTestService {

}
