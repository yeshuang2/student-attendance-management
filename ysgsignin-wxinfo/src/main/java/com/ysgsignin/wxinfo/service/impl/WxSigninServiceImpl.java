package com.ysgsignin.wxinfo.service.impl;

import java.util.*;

import com.ysgsignin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ysgsignin.wxinfo.mapper.WxSigninMapper;
import com.ysgsignin.wxinfo.domain.WxSignin;
import com.ysgsignin.wxinfo.service.IWxSigninService;

/**
 * 学生签到数据表Service业务层处理
 *
 * @author ysg--联系vx17708330051
 * @date 2023-07-27
 */
@Service
public class WxSigninServiceImpl implements IWxSigninService {
    @Autowired
    private WxSigninMapper wxSigninMapper;

    /**
     * 查询学生签到数据表
     *
     * @param signinId 学生签到数据表主键
     * @return 学生签到数据表
     */
    @Override
    public WxSignin selectWxSigninBySigninId(Long signinId) {
        return wxSigninMapper.selectWxSigninBySigninId(signinId);
    }

    /**
     * 查询学生签到数据表列表
     *
     * @param wxSignin 学生签到数据表
     * @return 学生签到数据表
     */
    @Override
    public List<WxSignin> selectWxSigninList(WxSignin wxSignin) {
        return wxSigninMapper.selectWxSigninList(wxSignin);
    }

    /**
     * 新增学生签到数据表
     *
     * @param wxSignin 学生签到数据表
     * @return 结果
     */
    @Override
    public String insertWxSignin(WxSignin wxSignin) {
        System.out.println("签到:" + wxSignin);
//        拿到数据进行解析
        String[] studentName = wxSignin.getSigninUsername().split(",");
        for (int i = 0; i < studentName.length; i++) {
            wxSignin.setSigninUsername(studentName[i]);
            wxSignin.setSigninStarttime(DateUtils.getNowDate()); //开启签到时间
            wxSignin.setCreateTime(DateUtils.getNowDate());
            switch (wxSignin.getSigninType()) {
                case "普通签到":
                    wxSignin.setSigninAddimg(wxSignin.getSigninType());
                    System.out.println("普通签到");
                    break;
                case "二维码签到":
                    System.out.println("二维码签到:" + wxSignin.getSigninAddimg());
                    wxSignin.setSigninAddimg(wxSignin.getSigninAddimg());
                    System.out.println("二维码签到");
                    break;
                case "位置签到":
                    wxSignin.setSigninAddimg(wxSignin.getSigninType());
                    System.out.println("位置签到");
                    break;
            }
            wxSigninMapper.insertWxSignin(wxSignin); //批量新增 拿到不同的学生需要的签到数据
        }
        return wxSignin.getSigninType();
    }


    /**
     * 微信端 学生完成签到要求 修改学生签到数据表
     *
     * @param wxSignin 学生签到数据表
     * @return 结果
     */
    @Override
    public int updateWxSignin(WxSignin wxSignin) {
        wxSignin.setUpdateTime(DateUtils.getNowDate());
        return wxSigninMapper.updateWxSignin(wxSignin);
    }

    /**
     * 批量删除学生签到数据表
     *
     * @param signinIds 需要删除的学生签到数据表主键
     * @return 结果
     */
    @Override
    public int deleteWxSigninBySigninIds(Long[] signinIds) {
        return wxSigninMapper.deleteWxSigninBySigninIds(signinIds);
    }

    /**
     * 删除学生签到数据表信息
     *
     * @param signinId 学生签到数据表主键
     * @return 结果
     */
    @Override
    public int deleteWxSigninBySigninId(Long signinId) {
        return wxSigninMapper.deleteWxSigninBySigninId(signinId);
    }

    /**
     * @param UserName 用户名
     * @return 所有通过查询条件的用户记录
     */
    @Override
    public List<WxSignin> selectWxSigninBySigninUserName(String UserName) {
        return wxSigninMapper.selectWxSigninBySigninUserName(UserName);
    }

    /**
     * @return 统计每一个班上的签到数据信息 可视化数据
     * 正常签到和未签到 请假数据
     */
    @Override
    public List<HashMap<String,Object>> signinEchartsCountSumStatus() {
        List<HashMap<String, Object>> hashMaps = wxSigninMapper.signinEchartsCountSumStatus();
        return hashMaps;
    }


    /**
     * @return 统计每一个班上的签到数据信息 可视化数据
     * 正常签到和未签到 请假数据
     * 滚动数据栏
     */
    @Override
    public List<Collection<Object>> DataVsigninEchartsCountSumStatus() {
        List<HashMap<String,Object>> hashMaps = wxSigninMapper.signinEchartsCountSumStatus();
        List<Collection<Object>> list=new ArrayList<>();
        for (HashMap<String, Object> hashMap:hashMaps) {
            list.add(hashMap.values());
        }
        return list;
    }

    /**
     * @return  查询每一个班级 总人数数据 统计
     */
    @Override
    public  List<HashMap<String, Object>> getEchartscalzzbanjiSum() {
        List<HashMap<String, Object>> echartscalzzbanjiSum = wxSigninMapper.getEchartscalzzbanjiSum();
        return echartscalzzbanjiSum;
    }

    /**
     * @param signinUsername 个人登录的账号
     * @return 返回个人班级请假的数据
     */
    @Override
    public List<WxSignin> getApplicationmyleaverecord(String signinUsername) {
        List<WxSignin> applicationmyleaverecord = wxSigninMapper.getApplicationmyleaverecord(signinUsername);
        return applicationmyleaverecord;
    }

}
