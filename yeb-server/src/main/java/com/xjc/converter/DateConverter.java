package com.xjc.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author : XJC
 * @Description :日期转换器
 * @create : 2022/1/9 19:01
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
