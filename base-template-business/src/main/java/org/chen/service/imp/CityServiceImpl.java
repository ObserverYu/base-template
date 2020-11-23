package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.CityMapper;
import org.chen.domain.entity.City;
import org.chen.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.CityMapper;

/**
 * <p>
 * 城市表 服务实现类
 * </p>
 *
 * @author YuChen
 * @since 2020-08-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}
