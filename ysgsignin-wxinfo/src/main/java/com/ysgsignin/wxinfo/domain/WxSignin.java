package com.ysgsignin.wxinfo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ysgsignin.common.annotation.Excel;
import com.ysgsignin.common.core.domain.BaseEntity;

/**
 * 学生签到数据表对象 wx_signin
 *
 * @author ysg--联系vx17708330051
 * @date 2023-07-27
 */
@Data
public class WxSignin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签到ID */
    private Long signinId;

    /** 签到班级id */
    @Excel(name = "签到班级id")
    private Long signinClazzid;

    /** 签到班级名称 */
    @Excel(name = "签到班级名称")
    private String signinClazzname;

    /** 签到老师的名称 */
    @Excel(name = "签到老师的名称")
    private String signinTeachername;

    /** 签到的学生 */
    @Excel(name = "签到的学生")
    private String signinUsername;

    /** 学生签到状态（0失败默认 1成功） */
    @Excel(name = "学生签到状态", readConverterExp = "0=失败默认,1=成功")
    private String signinStatus;

    /** 签到的类型（普通、二维码、位置） */
    @Excel(name = "签到的类型", readConverterExp = "普=通、二维码、位置")
    private String signinType;

    /** 签到具体类型实现(存放图片、地理位置数据) */
    @Excel(name = "签到具体类型实现", readConverterExp = "签到具体类型实现(存放图片、地理位置数据)")
    private String signinAddimg;

    @Excel(name = "签到扫码返回的数据", readConverterExp = "签到具体类型实现(存放图片、地理位置数据)")
    private String signinAddimgreturn;


    /** 发起签到时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发起签到时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signinStarttime;

    /** 结束签到时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束签到时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signinEndttime;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setSigninId(Long signinId)
    {
        this.signinId = signinId;
    }

    public Long getSigninId()
    {
        return signinId;
    }
    public void setSigninClazzid(Long signinClazzid)
    {
        this.signinClazzid = signinClazzid;
    }

    public Long getSigninClazzid()
    {
        return signinClazzid;
    }
    public void setSigninClazzname(String signinClazzname)
    {
        this.signinClazzname = signinClazzname;
    }

    public String getSigninClazzname()
    {
        return signinClazzname;
    }
    public void setSigninTeachername(String signinTeachername)
    {
        this.signinTeachername = signinTeachername;
    }

    public String getSigninTeachername()
    {
        return signinTeachername;
    }
    public void setSigninUsername(String signinUsername)
    {
        this.signinUsername = signinUsername;
    }

    public String getSigninUsername()
    {
        return signinUsername;
    }
    public void setSigninStatus(String signinStatus)
    {
        this.signinStatus = signinStatus;
    }


    public String getSigninAddimg() {
        return signinAddimg;
    }

    public void setSigninAddimg(String signinAddimg) {
        this.signinAddimg = signinAddimg;
    }

    public String getSigninStatus()
    {
        return signinStatus;
    }
    public void setSigninType(String signinType)
    {
        this.signinType = signinType;
    }

    public String getSigninType()
    {
        return signinType;
    }
    public void setSigninStarttime(Date signinStarttime)
    {
        this.signinStarttime = signinStarttime;
    }

    public Date getSigninStarttime()
    {
        return signinStarttime;
    }
    public void setSigninEndttime(Date signinEndttime)
    {
        this.signinEndttime = signinEndttime;
    }

    public Date getSigninEndttime()
    {
        return signinEndttime;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("signinId", getSigninId())
            .append("signinClazzid", getSigninClazzid())
            .append("signinClazzname", getSigninClazzname())
            .append("signinTeachername", getSigninTeachername())
            .append("signinUsername", getSigninUsername())
            .append("signinStatus", getSigninStatus())
            .append("signinType", getSigninType())
             .append("signinAddimg", getSigninAddimg())
                .append("signinAddimgreturn", getSigninAddimgreturn())
            .append("signinStarttime", getSigninStarttime())
            .append("signinEndttime", getSigninEndttime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
