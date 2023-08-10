package com.ysgsignin.web.controller.system;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson2.JSONObject;
import com.ysgsignin.common.core.domain.model.WxLoginBody;
import com.ysgsignin.common.utils.sign.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ysgsignin.common.constant.Constants;
import com.ysgsignin.common.core.domain.AjaxResult;
import com.ysgsignin.common.core.domain.entity.SysMenu;
import com.ysgsignin.common.core.domain.entity.SysUser;
import com.ysgsignin.common.core.domain.model.LoginBody;
import com.ysgsignin.common.utils.SecurityUtils;
import com.ysgsignin.framework.web.service.SysLoginService;
import com.ysgsignin.framework.web.service.SysPermissionService;
import com.ysgsignin.system.service.ISysMenuService;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }




        /** AppId */
    @Value("${wx-app.appId}")
    private String appId;

    /** AppSecret */

    @Value("${wx-app.appSecret}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/wxlogin")
    public AjaxResult wxLogin(@RequestBody WxLoginBody wxLoginBody) {
        String code = wxLoginBody.getCode();
        //秘钥
        String encryptedIv = wxLoginBody.getEncryptedIv();
        //加密数据
        String encryptedData = wxLoginBody.getEncryptedData();
        String NickName = wxLoginBody.getNickName();
        String Gender = wxLoginBody.getGender();
        //向微信服务器发送恳求获取用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appId + "&secret="
                + appSecret + "&js_code="
                + code + "&grant_type=authorization_code";
        String res = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        //获取session_key和openid
        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        //解密
        String decryptResult = "";
        try {
            //假如没有绑定微信敞开渠道，解析结果是没有unionid的。
            decryptResult = decrypt(sessionKey, encryptedIv, encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("微信登录失败！");
        }
        if (StringUtils.hasText(decryptResult)) {
            //假如解析成功,获取token
            String token = loginService.wxLogin(decryptResult,NickName,Gender);
            AjaxResult ajax = AjaxResult.success();
            ajax.put(Constants.TOKEN, token);
            return ajax;
        } else {
            return AjaxResult.error("微信登录失败！");
        }
    }
    /**
     * AES解密
     */
    private String decrypt(String sessionKey,String encryptedIv,String encryptedData) throws Exception{
        // 转化为字节数组
        byte[] key = Base64.decode(sessionKey);
        byte[] iv = Base64.decode(encryptedIv);
        byte[] encData = Base64.decode(encryptedData);
        // 假如密钥缺乏16位，那么就补足
        int base =16;
        if (key.length % base !=0) {
            int groups = key.length / base +(key.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp,(byte) 0);
            System.arraycopy(key,0,temp,0,key.length);
            key = temp;
        }
        // 假如初始向量缺乏16位，也补足
        if (iv.length % base !=0) {
            int groups = iv.length / base +(iv.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp,(byte) 0);
            System.arraycopy(iv,0,temp,0,iv.length);
            iv = temp;
        }
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        String resultStr = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key,"AES");
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
            resultStr = new String(cipher.doFinal(encData),"UTF-8");
        } catch (Exception e){
            //            logger.info("解析错误");
            e.printStackTrace();
        }
        // 解析加密后的字符串
        return resultStr;
    }



    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
