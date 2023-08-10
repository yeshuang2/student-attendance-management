package com.ysgsignin.wxinfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ysgsignin.common.annotation.Excel;
import com.ysgsignin.common.core.domain.BaseEntity;

/**
 * 班级信息数据对象 wx_clazz
 *
 * @author 叶双贵
 * @date 2023-07-18
 */
public class WxClazz extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班级ID */
    private Long clazzId;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String clazzName;

    @Excel(name = "班级总人数")
    private String clazzSum;


    /** 用户ID(学生) */
    @Excel(name = "用户ID(学生)")
    private Long userId;

    /** 用户名称(学生） */
    @Excel(name = "用户名称(学生）")
    private String userName;

    /** 老师ID */
    @Excel(name = "老师ID")
    private Long teacherId;

    /** 老师姓名 */
    @Excel(name = "老师姓名")
    private String teacherName;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setClazzId(Long clazzId)
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId()
    {
        return clazzId;
    }
    public void setClazzName(String clazzName)
    {
        this.clazzName = clazzName;
    }

    public String getClazzName()
    {
        return clazzName;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setTeacherId(Long teacherId)
    {
        this.teacherId = teacherId;
    }

    public Long getTeacherId()
    {
        return teacherId;
    }
    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getTeacherName()
    {
        return teacherName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }


    public String getClazzSum() {
        return clazzSum;
    }

    public void setClazzSum(String clazzSum) {
        this.clazzSum = clazzSum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("clazzId", getClazzId())
            .append("clazzName", getClazzName())
            .append("userId", getUserId())
            .append("userName", getUserName())
                .append("clazzSum", getClazzSum())
            .append("teacherId", getTeacherId())
            .append("teacherName", getTeacherName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
