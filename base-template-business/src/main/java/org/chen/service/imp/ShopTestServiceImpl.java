package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.ShopTestMapper;
import org.chen.domain.entity.ShopTest;
import org.chen.service.IShopTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.ShopTestMapper;

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
public class ShopTestServiceImpl extends ServiceImpl<ShopTestMapper, ShopTest> implements IShopTestService {

}
