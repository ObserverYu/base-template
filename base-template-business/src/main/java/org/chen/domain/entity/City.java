package org.chen.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 城市表
 * </p>
 *
 * @author YuChen
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class City implements Serializable,Cloneable {

    @Override
    public City clone(){
        City clone = null;
        try{
            clone = (City) super.clone();

        }catch(CloneNotSupportedException e){
            // won't happen
            throw new RuntimeException(e);
        }
        return clone;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地区名
     */
    private String name;

    /**
     * 地区code
     */
    private String code;

    /**
     * 地区父code
     */
    private String parentCode;

    /**
     * 城乡分类code
     */
    private String typeCode;

    /**
     * 地区等级 省:1 市:2 区县:3 镇:4 村:5
     */
    private Integer level;


}
