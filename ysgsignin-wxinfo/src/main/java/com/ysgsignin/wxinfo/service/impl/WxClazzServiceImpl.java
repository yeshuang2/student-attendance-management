package com.ysgsignin.wxinfo.service.impl;

import java.util.List;
import com.ysgsignin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ysgsignin.wxinfo.mapper.WxClazzMapper;
import com.ysgsignin.wxinfo.domain.WxClazz;
import com.ysgsignin.wxinfo.service.IWxClazzService;

/**
 * 班级信息数据Service业务层处理
 *
 * @author 叶双贵
 * @date 2023-07-18
 */
@Service
public class WxClazzServiceImpl implements IWxClazzService
{
    @Autowired
    private WxClazzMapper wxClazzMapper;

    /**
     * 查询班级信息数据
     *
     * @param clazzId 班级信息数据主键
     * @return 班级信息数据
     */
    @Override
    public WxClazz selectWxClazzByClazzId(Long clazzId)
    {
        return wxClazzMapper.selectWxClazzByClazzId(clazzId);
    }

    /**
     * 查询班级信息数据列表
     *
     * @param wxClazz 班级信息数据
     * @return 班级信息数据
     */
    @Override
    public List<WxClazz> selectWxClazzList(WxClazz wxClazz)
    {
        return wxClazzMapper.selectWxClazzList(wxClazz);
    }

    /**
     * 新增班级信息数据
     *
     * @param wxClazz 班级信息数据
     * @return 结果
     */
    @Override
    public int insertWxClazz(WxClazz wxClazz)
    {
        wxClazz.setCreateTime(DateUtils.getNowDate());
        wxClazz.setClazzSum(String.valueOf(wxClazz.getUserName().split(",").length));
        return wxClazzMapper.insertWxClazz(wxClazz);
    }

    /**
     * 修改班级信息数据
     *
     * @param wxClazz 班级信息数据
     * @return 结果
     */
    @Override
    public int updateWxClazz(WxClazz wxClazz)
    {
        wxClazz.setUpdateTime(DateUtils.getNowDate());
        wxClazz.setClazzSum(String.valueOf(wxClazz.getUserName().split(",").length));
        return wxClazzMapper.updateWxClazz(wxClazz);
    }

    /**
     * 批量删除班级信息数据
     *
     * @param clazzIds 需要删除的班级信息数据主键
     * @return 结果
     */
    @Override
    public int deleteWxClazzByClazzIds(Long[] clazzIds)
    {
        return wxClazzMapper.deleteWxClazzByClazzIds(clazzIds);
    }

    /**
     * 删除班级信息数据信息
     *
     * @param clazzId 班级信息数据主键
     * @return 结果
     */
    @Override
    public int deleteWxClazzByClazzId(Long clazzId)
    {
        return wxClazzMapper.deleteWxClazzByClazzId(clazzId);
    }

    /**
     * 查询班级信息数据列表
     *
     * @return 班级id 和 班级名称数据集合
     */
    @Override
    public List<WxClazz> selectWxClazzNameAndList() {
        return wxClazzMapper.selectWxClazzNameAndList();
    }

    /**
     * 查询个人用户 所在的班级信息数据列表
     *
     * @param userName
     * @return 返回自己所在的班级名称
     */
    @Override
    public List<WxClazz> selectWxClazzLikeNameAndList(String userName) {
        return wxClazzMapper.selectWxClazzLikeNameAndList(userName);
    }
}
