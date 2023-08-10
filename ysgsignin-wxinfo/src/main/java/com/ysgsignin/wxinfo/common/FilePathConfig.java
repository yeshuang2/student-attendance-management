package com.ysgsignin.wxinfo.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author: 叶双贵
 * @version: 1.0
 * @编辑时间：2023/7/28 16:37
 **/
@Configuration
public class FilePathConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");//        registry.addResourceHandler("/uploadPath/**").addResourceLocations("file:/data/uploadPath/drugques/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/shFiles/QRCode/");

    }

}
