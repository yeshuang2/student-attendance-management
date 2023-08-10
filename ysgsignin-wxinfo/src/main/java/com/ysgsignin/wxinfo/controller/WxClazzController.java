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
import com.ysgsignin.wxinfo.domain.WxClazz;
import com.ysgsignin.wxinfo.service.IWxClazzService;
import com.ysgsignin.common.utils.poi.ExcelUtil;
import com.ysgsignin.common.core.page.TableDataInfo;

/**
 * 班级信息数据Controller
 *
 * @author 叶双贵
 * @date 2023-07-18
 */
@RestController
@RequestMapping("/wxinfo/clazz")
public class WxClazzController extends BaseController
{
    @Autowired
    private IWxClazzService wxClazzService;

    /**
     * 查询班级信息数据列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxClazz wxClazz)
    {
        startPage();
        List<WxClazz> list = wxClazzService.selectWxClazzList(wxClazz);
        return getDataTable(list);
    }

    /**
     * 导出班级信息数据列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:export')")
    @Log(title = "班级信息数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxClazz wxClazz)
    {
        List<WxClazz> list = wxClazzService.selectWxClazzList(wxClazz);
        ExcelUtil<WxClazz> util = new ExcelUtil<WxClazz>(WxClazz.class);
        util.exportExcel(response, list, "班级信息数据数据");
    }

    /**
     * 获取班级信息数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:query')")
    @GetMapping(value = "/{clazzId}")
    public AjaxResult getInfo(@PathVariable("clazzId") Long clazzId)
    {
        return success(wxClazzService.selectWxClazzByClazzId(clazzId));
    }

    /**
     * 新增班级信息数据
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:add')")
    @Log(title = "班级信息数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxClazz wxClazz)
    {
        return toAjax(wxClazzService.insertWxClazz(wxClazz));
    }

    /**
     * 修改班级信息数据
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:edit')")
    @Log(title = "班级信息数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxClazz wxClazz)
    {
        return toAjax(wxClazzService.updateWxClazz(wxClazz));
    }

    /**
     * 删除班级信息数据
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:clazz:remove')")
    @Log(title = "班级信息数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{clazzIds}")
    public AjaxResult remove(@PathVariable Long[] clazzIds)
    {
        return toAjax(wxClazzService.deleteWxClazzByClazzIds(clazzIds));
    }



    /**
     * 获取班级id 和 班级名称 数据详细信息
     *  给 课程进行复制操作
     */
    @GetMapping(value = "/selectbanidandname")
    public AjaxResult getBanJiNameInfo() {
        return success(wxClazzService.selectWxClazzNameAndList());
    }


    /**
     * API 微信接口
     * 查询个人用户 所在的班级信息数据列表
     * @return  返回自己所在的班级名称
     */
    @GetMapping(value = "/wxlike/{userName}")
    public AjaxResult WxClazzLikeNameAndList(@PathVariable("userName") String userName)
    {
        return success(wxClazzService.selectWxClazzLikeNameAndList(userName));
    }

}
