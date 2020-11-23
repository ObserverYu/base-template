package org.chen.util.office;

import lombok.Data;

import java.io.InputStream;

/**
 * 
 *  
 * @author YuChen
 * @date 2020/4/27 15:03
 **/

@Data
public class PdfInputDto {

    //全名
    private String allName;

    // 模板编号
    private String templateNumber;

    // 输出名称
    private String outFileName;

    // 模板
    private InputStream inputStream;
}
