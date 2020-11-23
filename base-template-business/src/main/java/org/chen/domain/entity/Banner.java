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
 * store首页banner表
 * </p>
 *
 * @author YuChen
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "banner_id", type = IdType.AUTO)
    private Long bannerId;

    /**
     * 逻辑删除（0-未删除，1-删除）
     */
    private Integer isDeleted;

    /**
     * 类型 1-门店首页banner 2-门店首页按钮图 3-门店首页广告 4-官网banner 5-官网logo 6-服务介绍图片 7-小程序logo 8-applogo 9-成功案例
     */
    private Integer type;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 图片超链接(按钮没有此字段)
     */
    private String linkUrl;

    /**
     * 排序
     */
    private Integer sort;

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

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String secondTitle;

    /**
     * 头部logo
     */
    private String logo;

    /**
     * 状态 0正常 -1 删除
     */
    private Integer status;


}
