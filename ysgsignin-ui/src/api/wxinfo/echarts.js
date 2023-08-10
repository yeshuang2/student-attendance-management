import request from '@/utils/request'
//   数据可视化模块api
// 1、  统计每一个班上的签到数据信息--正常签到和未签到 请假数据
export function getEchartssigninCountSumStatusInfo() {
  return request({
    url: '/wxinfo/signin/getechartssignininfo',
    method: 'get'
  })
}


// 滚动数据api 基本轮播表
export function getDatavEchartssigninCountSumStatusInfo() {
  return request({
    url: '/wxinfo/signin/datavgetechartssignininfo',
    method: 'get'
  })
}

//  获取班级总人数数据信息    单条滚动
export function getDatavdatavgetcalzzbanjisumInfo() {
  return request({
    url: '/wxinfo/signin/datavgetcalzzbanjisumInfo',
    method: 'get'
  })
}
