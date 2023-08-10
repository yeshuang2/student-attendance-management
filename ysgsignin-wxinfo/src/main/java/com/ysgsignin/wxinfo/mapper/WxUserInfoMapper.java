package com.ysgsignin.wxinfo.mapper;

import java.util.List;
import com.ysgsignin.wxinfo.domain.WxUserInfo;

/**
 * 微信授权登录表Mapper接口
 *
 * @author 叶双贵
 * @date 2023-07-15
 */
public interface WxUserInfoMapper
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
     * 删除微信授权登录表
     *
     * @param wxUserid 微信授权登录表主键
     * @return 结果
     */
    public int deleteWxUserInfoByWxUserid(Long wxUserid);

    /**
     * 批量删除微信授权登录表
     *
     * @param wxUserids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxUserInfoByWxUserids(Long[] wxUserids);

    /**
     * @param openid 查询用户是否存在 存在就不进行新增数据
     */
    WxUserInfo selectWxUserInfoByWxUseropenid(String openid);
}
