package com.ysgsignin.wxinfo.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ysgsignin.common.annotation.Log;
import com.ysgsignin.common.core.controller.BaseController;
import com.ysgsignin.common.core.domain.AjaxResult;
import com.ysgsignin.common.enums.BusinessType;
import com.ysgsignin.wxinfo.domain.WxCourse;
import com.ysgsignin.wxinfo.service.IWxCourseService;
import com.ysgsignin.common.utils.poi.ExcelUtil;
import com.ysgsignin.common.core.page.TableDataInfo;

/**
 * 课程信息数据表Controller
 * 
 * @author 叶双
 * @date 2023-07-20
 */
@RestController
@RequestMapping("/wxinfo/course")
public class WxCourseController extends BaseController
{
    @Autowired
    private IWxCourseService wxCourseService;

    /**
     * 查询课程信息数据表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxCourse wxCourse)
    {
        startPage();
        List<WxCourse> list = wxCourseService.selectWxCourseList(wxCourse);
        return getDataTable(list);
    }

    /**
     * 导出课程信息数据表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:export')")
    @Log(title = "课程信息数据表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxCourse wxCourse)
    {
        List<WxCourse> list = wxCourseService.selectWxCourseList(wxCourse);
        ExcelUtil<WxCourse> util = new ExcelUtil<WxCourse>(WxCourse.class);
        util.exportExcel(response, list, "课程信息数据表数据");
    }

    /**
     * 获取课程信息数据表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:query')")
    @GetMapping(value = "/{wxCourseid}")
    public AjaxResult getInfo(@PathVariable("wxCourseid") Long wxCourseid)
    {
        return success(wxCourseService.selectWxCourseByWxCourseid(wxCourseid));
    }

    /**
     * 新增课程信息数据表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:add')")
    @Log(title = "课程信息数据表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxCourse wxCourse)
    {
        return toAjax(wxCourseService.insertWxCourse(wxCourse));
    }

    /**
     * 修改课程信息数据表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:edit')")
    @Log(title = "课程信息数据表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxCourse wxCourse)
    {
        return toAjax(wxCourseService.updateWxCourse(wxCourse));
    }

    /**
     * 删除课程信息数据表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:course:remove')")
    @Log(title = "课程信息数据表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wxCourseids}")
    public AjaxResult remove(@PathVariable Long[] wxCourseids)
    {
        return toAjax(wxCourseService.deleteWxCourseByWxCourseids(wxCourseids));
    }
}
