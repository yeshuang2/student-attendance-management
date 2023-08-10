package com.ysgsignin.wxinfo.controller;

import com.ysgsignin.common.core.controller.BaseController;
import com.ysgsignin.common.core.domain.AjaxResult;
import com.ysgsignin.wxinfo.common.BaseResponse;
import com.ysgsignin.wxinfo.util.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 叶双贵
 * @version: 1.0
 * @编辑时间：2023/7/26 11:53
 **/
@RestController
@RequestMapping("/wxcq")
public class QrCodeController {
    private static final String RootPath = "E:\\shFiles\\QRCode\\";
    private static final String FileFormat = ".png";

    private static final ThreadLocal<SimpleDateFormat> LOCALDATEFORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));


    //生成二维码并将其存放于本地目录
    @PostMapping("/generate/v1")
    public BaseResponse generateV1(@RequestParam("content") String content) {
        BaseResponse response = new BaseResponse(true);
        try {
            final String fileName = LOCALDATEFORMAT.get().format(new Date());
            // 二维码生成
            QRCodeUtil.createCodeToFile(content, new File(RootPath), fileName + FileFormat);
        } catch (Exception e) {
            response = new BaseResponse(false, e.getMessage());
        }
        return response;
    }

    //生成二维码并将其返回给前端调用者
    //     学生签到的时候调用这个接口
    @PostMapping("/generate/v2/{content}")
    public AjaxResult generateV2(@PathVariable("content") String content) {

        final String fileName = LOCALDATEFORMAT.get().format(new Date());
        // 二维码生成
        QRCodeUtil.createCodeToFile(content, new File(RootPath), fileName + FileFormat);
        System.out.println(fileName + FileFormat); //文件名称
        return AjaxResult.success("生成图片成功", fileName+FileFormat);
    }

//    //生成二维码并将其返回给前端调用者
//    //     学生签到的时候调用这个接口
//    @PostMapping("/generate/v2/{content}")
//    public BaseResponse generateV2(@PathVariable("content") String content,
//                                   HttpServletResponse servletResponse){
//        BaseResponse response=new BaseResponse(true);
//        try {
//            QRCodeUtil.createCodeToOutputStream(content,servletResponse.getOutputStream());
//        }catch (Exception e){
//            response=new BaseResponse(false,e.getMessage());
//        }
//        return response;
//    }
}
