package com.learn.utils;


import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//把字符串转换为日期的工具类
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {

        if (s == null)
            throw new RuntimeException("请输入正确日期");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //把字符串转换为日期
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("数据类型转换出错");
        }
    }
}
