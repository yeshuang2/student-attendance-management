package com.ysgsignin.wxinfo.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ysgsignin.wxinfo.domain.WxSignin;
import org.apache.ibatis.annotations.Param;

/**
 * 学生签到数据表Mapper接口
 *
 * @author ysg--联系vx17708330051
 * @date 2023-07-27
 */
public interface WxSigninMapper
{
    /**
     * 查询学生签到数据表
     *
     * @param signinId 学生签到数据表主键
     * @return 学生签到数据表
     */
    public WxSignin selectWxSigninBySigninId(Long signinId);

    /**
     * 查询学生签到数据表列表
     *
     * @param wxSignin 学生签到数据表
     * @return 学生签到数据表集合
     */
    public List<WxSignin> selectWxSigninList(WxSignin wxSignin);

    /**
     * 新增学生签到数据表
     *
     * @param wxSignin 学生签到数据表
     * @return 结果
     */
    public int insertWxSignin(WxSignin wxSignin);

    /**
     * 修改学生签到数据表
     *
     * @param wxSignin 学生签到数据表
     * @return 结果
     */
    public int updateWxSignin(WxSignin wxSignin);

    /**
     * 删除学生签到数据表
     *
     * @param signinId 学生签到数据表主键
     * @return 结果
     */
    public int deleteWxSigninBySigninId(Long signinId);

    /**
     * 批量删除学生签到数据表
     *
     * @param signinIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxSigninBySigninIds(Long[] signinIds);


    /**
     * @param UserName 用户名
     * @return 所有通过查询条件的用户记录
     */
    public List<WxSignin>  selectWxSigninBySigninUserName(@Param("UserName") String UserName);


    /**
     * @return 统计每一个班上的签到数据信息 可视化数据
     */
    public List<HashMap<String,Object>>  signinEchartsCountSumStatus();


    /**
     * @return 查询每一个班级 总人数数据 统计
     */
    List<HashMap<String,Object>>  getEchartscalzzbanjiSum();


    /**
     * @param signinUsername  个人登录的账号
     * @return   返回个人请假信息--班级 和 学号返回
     */
    public List<WxSignin> getApplicationmyleaverecord(@Param("signinUsername") String signinUsername);

}
