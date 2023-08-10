package com.ysgsignin.wxinfo.service;

import java.util.List;
import com.ysgsignin.wxinfo.domain.WxCourse;

/**
 * 课程信息数据表Service接口
 * 
 * @author 叶双
 * @date 2023-07-20
 */
public interface IWxCourseService 
{
    /**
     * 查询课程信息数据表
     * 
     * @param wxCourseid 课程信息数据表主键
     * @return 课程信息数据表
     */
    public WxCourse selectWxCourseByWxCourseid(Long wxCourseid);

    /**
     * 查询课程信息数据表列表
     * 
     * @param wxCourse 课程信息数据表
     * @return 课程信息数据表集合
     */
    public List<WxCourse> selectWxCourseList(WxCourse wxCourse);

    /**
     * 新增课程信息数据表
     * 
     * @param wxCourse 课程信息数据表
     * @return 结果
     */
    public int insertWxCourse(WxCourse wxCourse);

    /**
     * 修改课程信息数据表
     * 
     * @param wxCourse 课程信息数据表
     * @return 结果
     */
    public int updateWxCourse(WxCourse wxCourse);

    /**
     * 批量删除课程信息数据表
     * 
     * @param wxCourseids 需要删除的课程信息数据表主键集合
     * @return 结果
     */
    public int deleteWxCourseByWxCourseids(Long[] wxCourseids);

    /**
     * 删除课程信息数据表信息
     * 
     * @param wxCourseid 课程信息数据表主键
     * @return 结果
     */
    public int deleteWxCourseByWxCourseid(Long wxCourseid);
}
