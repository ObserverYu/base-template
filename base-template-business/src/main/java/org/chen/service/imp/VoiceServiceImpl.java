package org.chen.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chen.dao.VoiceMapper;
import org.chen.domain.entity.Voice;
import org.chen.service.IVoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.chen.dao.VoiceMapper;

/**
 * <p>
 * 语音 服务实现类
 * </p>
 *
 * @author YuChen
 * @since 2020-08-24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class VoiceServiceImpl extends ServiceImpl<VoiceMapper, Voice> implements IVoiceService {

}
