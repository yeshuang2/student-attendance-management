package com.ysgsignin.wxinfo.service.impl;

import java.util.List;
import com.ysgsignin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ysgsignin.wxinfo.mapper.WxCourseMapper;
import com.ysgsignin.wxinfo.domain.WxCourse;
import com.ysgsignin.wxinfo.service.IWxCourseService;

/**
 * 课程信息数据表Service业务层处理
 * 
 * @author 叶双
 * @date 2023-07-20
 */
@Service
public class WxCourseServiceImpl implements IWxCourseService 
{
    @Autowired
    private WxCourseMapper wxCourseMapper;

    /**
     * 查询课程信息数据表
     * 
     * @param wxCourseid 课程信息数据表主键
     * @return 课程信息数据表
     */
    @Override
    public WxCourse selectWxCourseByWxCourseid(Long wxCourseid)
    {
        return wxCourseMapper.selectWxCourseByWxCourseid(wxCourseid);
    }

    /**
     * 查询课程信息数据表列表
     * 
     * @param wxCourse 课程信息数据表
     * @return 课程信息数据表
     */
    @Override
    public List<WxCourse> selectWxCourseList(WxCourse wxCourse)
    {
        return wxCourseMapper.selectWxCourseList(wxCourse);
    }

    /**
     * 新增课程信息数据表
     * 
     * @param wxCourse 课程信息数据表
     * @return 结果
     */
    @Override
    public int insertWxCourse(WxCourse wxCourse)
    {
        wxCourse.setCreateTime(DateUtils.getNowDate());
        return wxCourseMapper.insertWxCourse(wxCourse);
    }

    /**
     * 修改课程信息数据表
     * 
     * @param wxCourse 课程信息数据表
     * @return 结果
     */
    @Override
    public int updateWxCourse(WxCourse wxCourse)
    {
        wxCourse.setUpdateTime(DateUtils.getNowDate());
        return wxCourseMapper.updateWxCourse(wxCourse);
    }

    /**
     * 批量删除课程信息数据表
     * 
     * @param wxCourseids 需要删除的课程信息数据表主键
     * @return 结果
     */
    @Override
    public int deleteWxCourseByWxCourseids(Long[] wxCourseids)
    {
        return wxCourseMapper.deleteWxCourseByWxCourseids(wxCourseids);
    }

    /**
     * 删除课程信息数据表信息
     * 
     * @param wxCourseid 课程信息数据表主键
     * @return 结果
     */
    @Override
    public int deleteWxCourseByWxCourseid(Long wxCourseid)
    {
        return wxCourseMapper.deleteWxCourseByWxCourseid(wxCourseid);
    }
}
