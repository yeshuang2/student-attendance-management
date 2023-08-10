package com.ysgsignin.framework.web.service;

import javax.annotation.Resource;

import com.alibaba.fastjson2.JSONObject;
import com.ysgsignin.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ysgsignin.common.constant.CacheConstants;
import com.ysgsignin.common.constant.Constants;
import com.ysgsignin.common.constant.UserConstants;
import com.ysgsignin.common.core.domain.entity.SysUser;
import com.ysgsignin.common.core.domain.model.LoginUser;
import com.ysgsignin.common.core.redis.RedisCache;
import com.ysgsignin.common.exception.ServiceException;
import com.ysgsignin.common.exception.user.BlackListException;
import com.ysgsignin.common.exception.user.CaptchaException;
import com.ysgsignin.common.exception.user.CaptchaExpireException;
import com.ysgsignin.common.exception.user.UserNotExistsException;
import com.ysgsignin.common.exception.user.UserPasswordNotMatchException;
import com.ysgsignin.common.utils.DateUtils;
import com.ysgsignin.common.utils.MessageUtils;
import com.ysgsignin.common.utils.StringUtils;
import com.ysgsignin.common.utils.ip.IpUtils;
import com.ysgsignin.framework.manager.AsyncManager;
import com.ysgsignin.framework.manager.factory.AsyncFactory;
import com.ysgsignin.framework.security.context.AuthenticationContextHolder;
import com.ysgsignin.system.service.ISysConfigService;
import com.ysgsignin.system.service.ISysUserService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Resource
    private SysUserMapper userMapper;



    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        // 验证码校验
        validateCaptcha(username, code, uuid);
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }



    /**
     * 微信登录
     *
     * @param decryptResult 登录凭据 只能用一次
     * @return
     */
    public String wxLogin(String decryptResult,String NickName,String Gender){
        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(decryptResult);
        //  String unionid = jsonObject.getString("unionid");
        String openId = jsonObject.getString("openId");
        //还可以获取其他信息 依据openid判别数据库中是否有该用户
        //依据openid查询用户信息
        SysUser wxUser = userMapper.selectWxUserByOpenId(openId);
        //假如查不到，则新增，查到了，则更新
        SysUser user = new SysUser();
        if (wxUser == null) {
            // 新增
//            user.setUserName(NickName);//
            user.setUserName(getStringRandom(8));// 生成7位随机用户名
            user.setNickName(NickName); //用户昵称
            user.setSex(Gender);
            user.setWxRoleinfo("学生");
            //            wxUser.setUnionId(unionid);
            user.setOpenId(openId);
            user.setCreateTime(DateUtils.getNowDate());
            //新增 用户w
            userMapper.insertUser(user);
        }else {
            //更新
//            user.setUserName(getStringRandom(7));// 生成7位随机用户名
            user = wxUser;
            user.setNickName(NickName);
            user.setSex(Gender);
            user.setUpdateTime(DateUtils.getNowDate());
            userMapper.updateUser(user);
        }
        //组装token信息
        LoginUser loginUser = new LoginUser();
        loginUser.setOpenId(openId);
        //假如有的话设置
        loginUser.setUser(user);
        loginUser.setUserId(user.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }
    //生成随机用户名，数字和字母组成,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //当前日期时间方法 string类型
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy");
            String datetime = tempDate.format(new java.util.Date());
        //参数length，表明生成几位随机数  20230751121
        for (int i = 0; i < length; i++) {
            val += random.nextInt(10);
//            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
//            //输出字母仍是数字
//            if ("char".equalsIgnoreCase(charOrNum)) {
//                //输出是大写字母仍是小写字母
//                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
//                val += (char) (random.nextInt(26) + temp);
//            } else if ("num".equalsIgnoreCase(charOrNum)) {
//                val += String.valueOf(random.nextInt(10));
//            }
        }

        return datetime+val;
    }


    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }




}
