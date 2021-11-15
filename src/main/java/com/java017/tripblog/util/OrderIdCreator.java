package com.java017.tripblog.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author YuCheng
 * @date 2021/11/10 - 下午 11:01
 */
@Component
public class OrderIdCreator {

    public static String createOrderNumber(){

        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append(format.format(date));
        buffer.append((date.getTime() + "").substring(9));
        buffer.append(getRandNumber(4));
        return buffer.toString();
    }

    public static String getRandNumber(int length){
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        if(result.length()>0){
            return result.toString();
        }
        return null;
    }
}
