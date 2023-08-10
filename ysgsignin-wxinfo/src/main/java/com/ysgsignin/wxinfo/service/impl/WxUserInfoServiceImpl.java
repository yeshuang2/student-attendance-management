package com.ysgsignin.wxinfo.service.impl;

import java.util.List;

import com.ysgsignin.common.constant.Constants;
import com.ysgsignin.common.core.domain.entity.SysUser;
import com.ysgsignin.common.core.domain.model.LoginUser;
import com.ysgsignin.common.exception.ServiceException;
import com.ysgsignin.common.exception.user.UserPasswordNotMatchException;
import com.ysgsignin.common.utils.DateUtils;

import com.ysgsignin.common.utils.MessageUtils;
import com.ysgsignin.common.utils.SecurityUtils;
import com.ysgsignin.common.utils.StringUtils;
import com.ysgsignin.framework.manager.AsyncManager;
import com.ysgsignin.framework.manager.factory.AsyncFactory;
import com.ysgsignin.framework.security.context.AuthenticationContextHolder;
import com.ysgsignin.framework.web.service.TokenService;
import com.ysgsignin.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.ysgsignin.wxinfo.mapper.WxUserInfoMapper;
import com.ysgsignin.wxinfo.domain.WxUserInfo;
import com.ysgsignin.wxinfo.service.IWxUserInfoService;

import javax.annotation.Resource;

/**
 * 微信授权登录表Service业务层处理
 *
 * @author 叶双贵
 * @date 2023-07-15
 */
@Service
public class WxUserInfoServiceImpl implements IWxUserInfoService {
    @Resource
    private WxUserInfoMapper wxUserInfoMapper;

    /**
     * 查询微信授权登录表
     *
     * @param wxUserid 微信授权登录表主键
     * @return 微信授权登录表
     */
    @Override
    public WxUserInfo selectWxUserInfoByWxUserid(Long wxUserid) {
        return wxUserInfoMapper.selectWxUserInfoByWxUserid(wxUserid);
    }

    /**
     * 查询微信授权登录表列表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 微信授权登录表
     */
    @Override
    public List<WxUserInfo> selectWxUserInfoList(WxUserInfo wxUserInfo) {
        return wxUserInfoMapper.selectWxUserInfoList(wxUserInfo);
    }

    /**
     * 新增微信授权登录表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 结果
     */
    @Override
    public int insertWxUserInfo(WxUserInfo wxUserInfo) {
        wxUserInfo.setCreateTime(DateUtils.getNowDate());
        wxUserInfo.setRegisterDate(DateUtils.getNowDate());
        return wxUserInfoMapper.insertWxUserInfo(wxUserInfo);
    }

    /**
     * 修改微信授权登录表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 结果
     */
    @Override
    public int updateWxUserInfo(WxUserInfo wxUserInfo) {
        wxUserInfo.setUpdateTime(DateUtils.getNowDate());
        wxUserInfo.setLastloginDate(DateUtils.getNowDate()); //微信登录更新最后的时间
        return wxUserInfoMapper.updateWxUserInfo(wxUserInfo);
    }

    /**
     * 批量删除微信授权登录表
     *
     * @param wxUserids 需要删除的微信授权登录表主键
     * @return 结果
     */
    @Override
    public int deleteWxUserInfoByWxUserids(Long[] wxUserids) {
        return wxUserInfoMapper.deleteWxUserInfoByWxUserids(wxUserids);
    }

    /**
     * 删除微信授权登录表信息
     *
     * @param wxUserid 微信授权登录表主键
     * @return 结果
     */
    @Override
    public int deleteWxUserInfoByWxUserid(Long wxUserid) {
        return wxUserInfoMapper.deleteWxUserInfoByWxUserid(wxUserid);
    }


    /**
     * @param openid    openid 用户唯一id
     * @param nickName  用户姓名
     * @param gender    性别
     * @param avatarUrl 头像
     */
    @Override
    public void insertWxUserNewInfo(String openid, String nickName, Long gender, String avatarUrl) {
        WxUserInfo wxUserInfo = wxUserInfoMapper.selectWxUserInfoByWxUseropenid(openid);
        System.out.println(wxUserInfo);
        if (wxUserInfo == null) {
            this.insertWxUserInfo(new WxUserInfo(openid, nickName, gender, avatarUrl));
        } else {
            this.updateWxUserInfo(wxUserInfo);
        }
    }



    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;



}


