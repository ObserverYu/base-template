package org.chen.domain.result;

import org.chen.domain.entity.OrderItemTest;
import org.chen.domain.entity.OrderTest;
import lombok.Data;

import java.util.List;

/**
 * 
 *  
 * @author YuChen
 * @date 2020/8/24 16:37
 **/

@Data
public class OrderInsertResult {

    private OrderTest order;

    private List<OrderItemTest> itemList;
}
