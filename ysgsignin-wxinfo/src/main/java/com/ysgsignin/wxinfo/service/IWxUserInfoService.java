package com.ysgsignin.wxinfo.service;

import java.util.List;
import com.ysgsignin.wxinfo.domain.WxUserInfo;

/**
 * 微信授权登录表Service接口
 *
 * @author 叶双贵
 * @date 2023-07-15
 */
public interface IWxUserInfoService
{
    /**
     * 查询微信授权登录表
     *
     * @param wxUserid 微信授权登录表主键
     * @return 微信授权登录表
     */
    public WxUserInfo selectWxUserInfoByWxUserid(Long wxUserid);

    /**
     * 查询微信授权登录表列表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 微信授权登录表集合
     */
    public List<WxUserInfo> selectWxUserInfoList(WxUserInfo wxUserInfo);

    /**
     * 新增微信授权登录表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 结果
     */
    public int insertWxUserInfo(WxUserInfo wxUserInfo);

    /**
     * 修改微信授权登录表
     *
     * @param wxUserInfo 微信授权登录表
     * @return 结果
     */
    public int updateWxUserInfo(WxUserInfo wxUserInfo);

    /**
     * 批量删除微信授权登录表
     *
     * @param wxUserids 需要删除的微信授权登录表主键集合
     * @return 结果
     */
    public int deleteWxUserInfoByWxUserids(Long[] wxUserids);

    /**
     * 删除微信授权登录表信息
     *
     * @param wxUserid 微信授权登录表主键
     * @return 结果
     */
    public int deleteWxUserInfoByWxUserid(Long wxUserid);

    /**
     * @param nickName
     * @param gender
     * @param avatarUrl
     */
    void insertWxUserNewInfo(String openid,String nickName, Long gender, String avatarUrl);



}
