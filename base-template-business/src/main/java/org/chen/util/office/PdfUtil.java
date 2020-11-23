package org.chen.util.office;

import cn.hutool.core.collection.CollectionUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.chen.framework.businessex.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.*;
import java.util.Map;

/**
 * pdf处理工具
 *
 * @author YuChen
 * @date 2020/4/26 10:00
 **/

@Slf4j
public class PdfUtil {
    /**
     * 利用模板生成pdf   文字处理
     *
     * @param param 填写表格的字段
     * @param out  处理后的pdf输出的流
     * @param templateInoutStream  模板流
     * @param metaInfo  pdf描述元文件
     * @author YuChen
     * @date 2020/4/26 10:14
     */
    public static void makePdfByTemplateAndStrParam(Map<String,String> param,OutputStream out, InputStream templateInoutStream ,PdfMetaInfoDto metaInfo){
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("ttf/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 读取pdf模板
            reader = new PdfReader(templateInoutStream);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            form.addSubstitutionFont(bf);
            if(CollectionUtil.isNotEmpty(param)){
                for (String key : param.keySet()) {
                    String value = param.get(key);
                    form.setField(key, value);
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            for (int i = 1; i <= reader.getNumberOfPages() ; i++) {
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();
        } catch (IOException e) {
            log.error("pdf生成出现io异常", e);
            log.error("pdf生成出现io异常,number:{},param:{}", metaInfo.getTemplateNumber(), param);
            throw new BusinessException("系统错误,请联系管理员",500);
        } catch (DocumentException e) {
            log.error("pdf生成出现DocumentException,", e);
            log.error("pdf生成出现io异常,number:{},param:{}", metaInfo.getTemplateNumber(), param);
            throw new BusinessException("系统错误,请联系管理员",500);
        }finally {
            try {
                templateInoutStream.close();
            } catch (IOException e) {
                log.warn("文件流关闭失败,number:{}",metaInfo.getTemplateNumber());
            }
        }
    }
    /**
     * 利用模板生成pdf   文字处理
     *
     * @param front                字体文件路径
     * @param out                  处理后的pdf输出的流
     * @return
     * @author YuChen
     * @date 2020/4/26 10:14
     */
    public static void makePdfByTemplate(Map<String,String> datemap, String front,OutputStream out, PdfInputDto template){
        //paramValidation.validate(data.getStrData());
        // 模板路径
        //String templatePath = "D:/PDF/导出PDF.pdf";
        // 生成的新文件路径
        //String newPDFPath = "D:/PDF/成功PDF.pdf";
        PdfReader reader;
        //FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont(front, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font FontChinese = new Font(bf, 5, Font.NORMAL);
            //out = new FileOutputStream(newPDFPath);// 输出流
            //ClassPathResource classPathResource = new ClassPathResource(pdfTemplatesLocation);
            //reader = new PdfReader(templatePath);// 读取pdf模板
            // 读取pdf模板
            reader = new PdfReader(template.getInputStream());
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            //Map<String, String> datemap = data.getStrData();
            form.addSubstitutionFont(bf);
            if(CollectionUtil.isNotEmpty(datemap)){
                for (String key : datemap.keySet()) {
                    String value = datemap.get(key);
                    form.setField(key, value);
                }
            }
/*            //图片类的内容处理
            Map<String, String> imgmap = data.getImgData();
            if(CollectionUtil.isNotEmpty(imgmap)){
                for (String key : imgmap.keySet()) {
                    String value = imgmap.get(key);
                    String imgpath = value;
                    int pageNo = form.getFieldPositions(key).get(0).page;
                    Rectangle signRect = form.getFieldPositions(key).get(0).position;
                    float x = signRect.getLeft();
                    float y = signRect.getBottom();
                    //根据路径读取图片
                    Image image = Image.getInstance(imgpath);
                    //获取图片页面
                    PdfContentByte under = stamper.getOverContent(pageNo);
                    //图片大小自适应
                    image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                    //添加图片
                    image.setAbsolutePosition(x, y);
                    under.addImage(image);
                }
            }*/
            // 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            Font font = new Font(bf, 32);
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            log.error("pdf生成出现io异常", e);
            log.error("pdf生成出现io异常,allName:{},param:{}", template.getAllName(), datemap);
        } catch (DocumentException e) {
            log.error("pdf生成出现DocumentException,", e);
            log.error("pdf生成出现io异常,allName:{},param:{}", template.getAllName(), datemap);
        }
    }

    public static void test(OutputStream outputStream)throws FileNotFoundException {
/*        PdfInputData pdfInputData = new PdfInputData();
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","测试姓名");
        objectObjectHashMap.put("sex","男");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String format1 = format.format(date);
        objectObjectHashMap.put("birth",format1);
        objectObjectHashMap.put("hunyin","未婚");
        objectObjectHashMap.put("parentIdCard","420821199410233013");
        objectObjectHashMap.put("cardId","420821199410233013");
        objectObjectHashMap.put("number","100003");
        pdfInputData.setStrData(objectObjectHashMap);
       // makePdfByTemplate(pdfInputData, "pdf/WJ1007-领取年老一次性计划生育奖励费申请表.pdf","ttf/simfang.ttf",outputStream,new WJ1007Validation());*/
    }


    /**
     * 根据坐标来插入字段
     *
     * @param
     * @return
     * @author YuChen
     * @date 2020/4/26 10:01
     */
    public static void makePdfByLocation() {
        try {
            // Loading an existing document
            File file = new File("D:\\test.pdf");
            PDDocument document = PDDocument.load(file);

            // Retrieving the pages of the document, and using PREPEND mode
            PDPage page = document.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.PREPEND, false);

            String fontFilePath = "D:\\simfang.ttf";

            PDType0Font font = PDType0Font.load(document, new File(fontFilePath));

            // Setting the font to the Content stream
            contentStream.setFont(font, 8);

            // 发票代码
            showTextByLeft(contentStream, "名字", "", 140, 695);
            // 发票号码
            showTextByLeft(contentStream, "性别性别性别性别性别性别", "", 475, 20);

            showTextByLeft(contentStream, "名字2名字2名字2名字2名字2名字", "", 114, 50);
            showTextByLeft(contentStream, "性别2性别2性别2性别2性别2性别", "", 475, 20);

            System.out.println("Content added");

            // Closing the content stream
            contentStream.close();

            // Saving the document
            document.save(new File("D:\\new_VAT_INVOICE.pdf"));
            // Closing the document
            document.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void showTextByLeft(PDPageContentStream overContent, String txt, String def, float x, float y) throws Exception {
        // Begin the Content stream
        overContent.beginText();

        if (null == txt) {
            txt = def;
        }

        // Setting the position for the line
        overContent.newLineAtOffset(x, y);

        // Adding text in the form of string
        overContent.showText(txt);

        // Ending the content stream
        overContent.endText();
    }


}

