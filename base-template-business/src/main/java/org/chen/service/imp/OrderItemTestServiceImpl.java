package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.OrderItemTestMapper;
import org.chen.domain.entity.OrderItemTest;
import org.chen.service.IOrderItemTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.OrderItemTestMapper;

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
public class OrderItemTestServiceImpl extends ServiceImpl<OrderItemTestMapper, OrderItemTest> implements IOrderItemTestService {

}
