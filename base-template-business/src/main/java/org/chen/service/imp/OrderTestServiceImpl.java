package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.OrderTestMapper;
import org.chen.domain.entity.OrderTest;
import org.chen.service.IOrderTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.OrderTestMapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuChen
 * @since 2020-08-18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderTestServiceImpl extends ServiceImpl<OrderTestMapper, OrderTest> implements IOrderTestService {

}
