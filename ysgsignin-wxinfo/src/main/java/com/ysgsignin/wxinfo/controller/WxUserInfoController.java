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
import com.ysgsignin.wxinfo.domain.WxUserInfo;
import com.ysgsignin.wxinfo.service.IWxUserInfoService;
import com.ysgsignin.common.utils.poi.ExcelUtil;
import com.ysgsignin.common.core.page.TableDataInfo;

/**
 * 微信授权登录表Controller
 *
 * @author 叶双贵
 * @date 2023-07-15
 */
@RestController
@RequestMapping("/wxinfo/wxuserinfo")
public class WxUserInfoController extends BaseController {


    @Autowired
    private IWxUserInfoService wxUserInfoService;

    /**
     * 查询微信授权登录表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxUserInfo wxUserInfo)
    {
        startPage();
        List<WxUserInfo> list = wxUserInfoService.selectWxUserInfoList(wxUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出微信授权登录表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:export')")
    @Log(title = "微信授权登录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxUserInfo wxUserInfo)
    {
        List<WxUserInfo> list = wxUserInfoService.selectWxUserInfoList(wxUserInfo);
        ExcelUtil<WxUserInfo> util = new ExcelUtil<WxUserInfo>(WxUserInfo.class);
        util.exportExcel(response, list, "微信授权登录表数据");
    }

    /**
     * 获取微信授权登录表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:query')")
    @GetMapping(value = "/{wxUserid}")
    public AjaxResult getInfo(@PathVariable("wxUserid") Long wxUserid)
    {
        return success(wxUserInfoService.selectWxUserInfoByWxUserid(wxUserid));
    }

    /**
     * 新增微信授权登录表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:add')")
    @Log(title = "微信授权登录表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxUserInfo wxUserInfo)
    {
        return toAjax(wxUserInfoService.insertWxUserInfo(wxUserInfo));
    }

    /**
     * 修改微信授权登录表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:edit')")
    @Log(title = "微信授权登录表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxUserInfo wxUserInfo)
    {
        return toAjax(wxUserInfoService.updateWxUserInfo(wxUserInfo));
    }

    /**
     * 删除微信授权登录表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:wxuserinfo:remove')")
    @Log(title = "微信授权登录表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wxUserids}")
    public AjaxResult remove(@PathVariable Long[] wxUserids)
    {
        return toAjax(wxUserInfoService.deleteWxUserInfoByWxUserids(wxUserids));
    }
}
