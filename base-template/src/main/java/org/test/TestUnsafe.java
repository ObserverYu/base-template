package org.test;

import org.chen.util.office.PdfInputDto;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author YuChen
 * @date 2020/9/3 15:24
 **/

public class TestUnsafe {


    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Class<Unsafe> unsafeClass = Unsafe.class;
        Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe) theUnsafe.get(null);
    }

    private static void testGetField()throws Exception{
        PdfInputDto pdfInputDto = new PdfInputDto();
        pdfInputDto.setAllName("fuck");
        Class<? extends PdfInputDto> aClass = pdfInputDto.getClass();
        Field allName = aClass.getDeclaredField("allName");
        allName.setAccessible(true);
        Object o1 = allName.get(pdfInputDto);
        System.out.println(o1);
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = getUnsafe();

        PdfInputDto pdfInputDto = new PdfInputDto();
        pdfInputDto.setAllName("前");
        pdfInputDto.setOutFileName("namenamete");
        System.out.println(pdfInputDto);
        long allName = unsafe.objectFieldOffset(PdfInputDto.class.getDeclaredField("allName"));
        System.out.println(allName);
        unsafe.putObject(pdfInputDto,allName,"后");
        System.out.println(pdfInputDto);
    }
}
