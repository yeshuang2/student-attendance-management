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
import com.ysgsignin.wxinfo.domain.WxSignin;
import com.ysgsignin.wxinfo.service.IWxSigninService;
import com.ysgsignin.common.utils.poi.ExcelUtil;
import com.ysgsignin.common.core.page.TableDataInfo;

/**
 * 学生签到数据表Controller
 *
 * @author ysg--联系vx17708330051
 * @date 2023-07-27
 */
@RestController
@RequestMapping("/wxinfo/signin")
public class WxSigninController extends BaseController
{
    @Autowired
    private IWxSigninService wxSigninService;

    /**
     * 查询学生签到数据表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:signin:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxSignin wxSignin)
    {
        startPage();
        List<WxSignin> list = wxSigninService.selectWxSigninList(wxSignin);
        return getDataTable(list);
    }

    /**
     * 导出学生签到数据表列表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:signin:export')")
    @Log(title = "学生签到数据表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxSignin wxSignin)
    {
        List<WxSignin> list = wxSigninService.selectWxSigninList(wxSignin);
        ExcelUtil<WxSignin> util = new ExcelUtil<WxSignin>(WxSignin.class);
        util.exportExcel(response, list, "学生签到数据表数据");
    }

    /**
     * 获取学生签到数据表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:signin:query')")
    @GetMapping(value = "/{signinId}")
    public AjaxResult getInfo(@PathVariable("signinId") Long signinId)
    {
        return success(wxSigninService.selectWxSigninBySigninId(signinId));
    }

    /**
     * UserName 根据用户名称去查询数据
     * 获取学生签到数据表详细信息
     */
    @GetMapping(value = "wxusername/{UserName}")
    public AjaxResult getWxSigninBySigninUserNameInfo(@PathVariable("UserName") String UserName)
    {
        return success(wxSigninService.selectWxSigninBySigninUserName(UserName));
    }


    /**
     * @return   统计每一个班上的签到数据信息--正常签到和未签到 请假数据
     *            可视化数据
     */
    @GetMapping(value = "/getechartssignininfo")
    public AjaxResult getEchartssigninCountSumStatusInfo()
    {
        return success(wxSigninService.signinEchartsCountSumStatus());
    }


    /**
     * @return   统计每一个班上的签到数据信息--正常签到和未签到 请假数据
     *            可视化数据   基本轮播表
     */
    @GetMapping(value = "/datavgetechartssignininfo")
    public AjaxResult getdatavEchartssigninCountSumStatusInfo()
    {
        return success(wxSigninService.DataVsigninEchartsCountSumStatus());
    }

    /**
     * @return   统计每一个班上的总人数数据--进行显示
     *    显示echarts 玫瑰花格尔的可视化图 和  DataV的排名轮播表 同一个接口实现
     *
     */
    @GetMapping(value = "/datavgetcalzzbanjisumInfo")
    public AjaxResult getEchartscalzzbanjiSumInfo()
    {
        return success(wxSigninService.getEchartscalzzbanjiSum());
    }


    /**
     * @param signinUsername  个人登录的账号
     * @return   返回个人请假信息--班级 和 学号返回
     */
    @GetMapping(value = "/getmyleaverecord/{signinUsername}")
    public AjaxResult getApplicationMyLeaveRecord(@PathVariable("signinUsername") String signinUsername)
    {
        return success(wxSigninService.getApplicationmyleaverecord(signinUsername));
    }



    /**
     * 新增学生签到数据表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:signin:add')")
    @Log(title = "学生签到数据表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxSignin wxSignin)
    {
//        String s = wxSigninService.insertWxSignin(wxSignin);
        return success(wxSigninService.insertWxSignin(wxSignin));
    }

    /**
     * 修改学生签到数据表
     */
    @Log(title = "学生签到数据表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxSignin wxSignin)
    {
        return toAjax(wxSigninService.updateWxSignin(wxSignin));
    }

    /**
     * 删除学生签到数据表
     */
    @PreAuthorize("@ss.hasPermi('wxinfo:signin:remove')")
    @Log(title = "学生签到数据表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{signinIds}")
    public AjaxResult remove(@PathVariable Long[] signinIds)
    {
        return toAjax(wxSigninService.deleteWxSigninBySigninIds(signinIds));
    }
}
