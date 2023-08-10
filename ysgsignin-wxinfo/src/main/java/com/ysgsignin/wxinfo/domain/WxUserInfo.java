package com.ysgsignin.wxinfo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ysgsignin.common.annotation.Excel;
import com.ysgsignin.common.core.domain.BaseEntity;

/**
 * 微信授权登录表对象 wx_user_info
 *
 * @author 叶双贵
 * @date 2023-07-15
 */
@Data
public class WxUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 微信登录id */
    private Long wxUserid;

    /** 用户唯一标识 */
    @Excel(name = "用户唯一标识")
    private String wxUseropenid;

    /** 微信用户昵称 */
    @Excel(name = "微信用户昵称")
    private String wxUsernickname;

    /** 性别0为男,1为女 */
    @Excel(name = "性别0为男,1为女")
    private Long wxUsergender;

    /** 用户头像图片的 URL */
    @Excel(name = "用户头像图片的 URL")
    private String wxUseravatarurl;

    /** 是否为管理员 */
    @Excel(name = "是否为管理员")
    private Long wxIsadmin;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;


    /** 注册日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerDate;

    /** 最后登录日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastloginDate;

    /**  用户状态 状态 0 可用 1 封禁 */
    @Excel(name = " 用户状态 状态 0 可用 1 封禁")
    private Long wxUserstatus;

    /** 微信用户code 前端传来的 */
    @Excel(name = "微信用户code 前端传来的")
    private String wxUsercode;

    public WxUserInfo(String wxUseropenid, String wxUsernickname, Long wxUsergender, String wxUseravatarurl) {
        this.wxUseropenid = wxUseropenid;
        this.wxUsernickname = wxUsernickname;
        this.wxUsergender = wxUsergender;
        this.wxUseravatarurl = wxUseravatarurl;
    }

    public WxUserInfo() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wxUserid", getWxUserid())
            .append("wxUseropenid", getWxUseropenid())
            .append("wxUsernickname", getWxUsernickname())
            .append("wxUsergender", getWxUsergender())
            .append("wxUseravatarurl", getWxUseravatarurl())
            .append("wxIsadmin", getWxIsadmin())
            .append("registerDate", getRegisterDate())
            .append("lastloginDate", getLastloginDate())
            .append("wxUserstatus", getWxUserstatus())
            .append("wxUsercode", getWxUsercode())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
