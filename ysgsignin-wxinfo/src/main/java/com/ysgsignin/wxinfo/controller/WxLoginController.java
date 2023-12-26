package com.ysgsignin.wxinfo.controller;

import com.ysgsignin.common.constant.Constants;
import com.ysgsignin.common.core.domain.AjaxResult;
import com.ysgsignin.wxinfo.common.HttpClientUtil;
import com.ysgsignin.wxinfo.common.JsonUtils;
import com.ysgsignin.wxinfo.common.WXSessionModel;
import com.ysgsignin.wxinfo.domain.WxUserInfo;
import com.ysgsignin.wxinfo.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 叶双贵
 * @version: 1.0
 * @编辑时间：2023/7/15 20:46
 **/

@RestController
public class WxLoginController {

    @Autowired
    private IWxUserInfoService wxUserInfoService;

//
//    /**
//     * 微信登录功能
//     *
//     * @param code
//     * @return
//     */
//    @Transactional
//    @PostMapping("/wxlogin")
//    public AjaxResult WxLogin(String code,String nickName,String gender,String avatarUrl) {
//        String url = "https://api.weixin.qq.com/sns/jscode2session"; //之前写法
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("appid", "");
//        param.put("secret", "");
//        param.put("js_code", code);
//        param.put("grant_type", "authorization_code");
//        String wxResult = HttpClientUtil.doGet(url, param);
//        WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
//        System.out.println(wxSessionModel);
//       try {
//           wxUserInfoService.insertWxUserNewInfo(wxSessionModel.getOpenid().toString(),nickName, Long.valueOf(gender),avatarUrl);
//       }catch (Exception e){
//           System.out.println(e);
//       }
//
//        return AjaxResult.success("微信登录成功",token); //返回openid
//    }

}
