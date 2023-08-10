package com.ysgsignin.wxinfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ysgsignin.common.annotation.Excel;
import com.ysgsignin.common.core.domain.BaseEntity;

/**
 * 课程信息数据表对象 wx_course
 * 
 * @author 叶双
 * @date 2023-07-20
 */
public class WxCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程id */
    private Long wxCourseid;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String wxCoursename;

    /** 课程学分 */
    @Excel(name = "课程学分")
    private String wxCourseCode;

    /** 授课班级名称 */
    @Excel(name = "授课班级名称")
    private String wxCourseClazzid;

    /** 授课老师id */
    @Excel(name = "授课老师id")
    private Long wxCourseTeacherid;

    /** 授课老师姓名 */
    @Excel(name = "授课老师姓名")
    private String wxCourseTeachername;

    public void setWxCourseid(Long wxCourseid) 
    {
        this.wxCourseid = wxCourseid;
    }

    public Long getWxCourseid() 
    {
        return wxCourseid;
    }
    public void setWxCoursename(String wxCoursename) 
    {
        this.wxCoursename = wxCoursename;
    }

    public String getWxCoursename() 
    {
        return wxCoursename;
    }
    public void setWxCourseCode(String wxCourseCode) 
    {
        this.wxCourseCode = wxCourseCode;
    }

    public String getWxCourseCode() 
    {
        return wxCourseCode;
    }
    public void setWxCourseClazzid(String wxCourseClazzid) 
    {
        this.wxCourseClazzid = wxCourseClazzid;
    }

    public String getWxCourseClazzid() 
    {
        return wxCourseClazzid;
    }
    public void setWxCourseTeacherid(Long wxCourseTeacherid) 
    {
        this.wxCourseTeacherid = wxCourseTeacherid;
    }

    public Long getWxCourseTeacherid() 
    {
        return wxCourseTeacherid;
    }
    public void setWxCourseTeachername(String wxCourseTeachername) 
    {
        this.wxCourseTeachername = wxCourseTeachername;
    }

    public String getWxCourseTeachername() 
    {
        return wxCourseTeachername;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wxCourseid", getWxCourseid())
            .append("wxCoursename", getWxCoursename())
            .append("wxCourseCode", getWxCourseCode())
            .append("wxCourseClazzid", getWxCourseClazzid())
            .append("wxCourseTeacherid", getWxCourseTeacherid())
            .append("wxCourseTeachername", getWxCourseTeachername())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
