package org.chen.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author YuChen
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_test")
public class OrderTest implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "order_id", type = IdType.INPUT)
    private Long orderId;

    private String paySum;

    private Long userId;


}
