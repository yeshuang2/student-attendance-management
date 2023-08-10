package com.ysgsignin.wxinfo.mapper;

import java.util.List;
import com.ysgsignin.wxinfo.domain.WxClazz;
import org.apache.ibatis.annotations.Param;

/**
 * 班级信息数据Mapper接口
 *
 * @author 叶双贵
 * @date 2023-07-18
 */
public interface WxClazzMapper
{
    /**
     * 查询班级信息数据
     *
     * @param clazzId 班级信息数据主键
     * @return 班级信息数据
     */
    public WxClazz selectWxClazzByClazzId(Long clazzId);

    /**
     * 查询班级信息数据列表
     *
     * @param wxClazz 班级信息数据
     * @return 班级信息数据集合
     */
    public List<WxClazz> selectWxClazzList(WxClazz wxClazz);

    /**
     * 新增班级信息数据
     *
     * @param wxClazz 班级信息数据
     * @return 结果
     */
    public int insertWxClazz(WxClazz wxClazz);

    /**
     * 修改班级信息数据
     *
     * @param wxClazz 班级信息数据
     * @return 结果
     */
    public int updateWxClazz(WxClazz wxClazz);

    /**
     * 删除班级信息数据
     *
     * @param clazzId 班级信息数据主键
     * @return 结果
     */
    public int deleteWxClazzByClazzId(Long clazzId);

    /**
     * 批量删除班级信息数据
     *
     * @param clazzIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxClazzByClazzIds(Long[] clazzIds);


    /**
     * 查询班级信息数据列表
     *
     * @return 班级id 和 班级名称数据集合
     */
    public List<WxClazz> selectWxClazzNameAndList();




    /**
     * 查询个人用户 所在的班级信息数据列表
     *
     * @return  返回自己所在的班级名称
     */
    public List<WxClazz> selectWxClazzLikeNameAndList(@Param("userName") String userName);


}
