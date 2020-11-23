package org.chen.util.office;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * pdf入参源文件json说明
 *  
 * @author YuChen
 * @date 2020/4/27 15:13
 **/

@Data
public class PdfMetaInfoDto {

    /**
     * 文件编号 : WJ1007
     */
    private String templateNumber;
    /**
     * 参数是否允许全部为空 : 0-不允许 1-允许
     */
    private String empty;
    /**
     * 文件输出名字
     */
    private String outFileName;
    /**
     * 参数校验 一般情况下 0-可以为null 1-不能为null
     */
    private JSONObject paramValidation;
/*    *//**
     * 写入字体大小
     *//*
    private int frontSize;*/

}
