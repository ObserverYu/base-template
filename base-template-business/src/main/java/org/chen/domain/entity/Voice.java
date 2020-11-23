package org.chen.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 语音
 * </p>
 *
 * @author YuChen
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Voice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "voice_id", type = IdType.AUTO)
    private Long voiceId;

    /**
     * 语音名字
     */
    private String name;

    /**
     * mp3url
     */
    private String url;

    /**
     * 语音描述
     */
    private String contentDesc;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 备注
     */
    private String remark;


}
