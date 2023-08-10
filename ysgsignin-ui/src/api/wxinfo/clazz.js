import request from '@/utils/request'
// 新增api 获取班级id 班级名称

// 查询班级信息数据详细
export function getBanJiNameClazzInfo() {
  return request({
    url: '/wxinfo/clazz/selectbanidandname',
    method: 'get'
  })
}

// 查询班级信息数据列表
export function listClazz(query) {
  return request({
    url: '/wxinfo/clazz/list',
    method: 'get',
    params: query
  })
}



// 用户信息 查询学生和老师的数据
export function selectwxlistUserInfo(wxRoleinfo) {
  return request({
    url: '/system/user/selectwxlistUser/' + wxRoleinfo,
    method: 'get',
  })
}



// 查询班级信息数据详细
export function getClazz(clazzId) {
  return request({
    url: '/wxinfo/clazz/' + clazzId,
    method: 'get'
  })
}

// 新增班级信息数据
export function addClazz(data) {
  return request({
    url: '/wxinfo/clazz',
    method: 'post',
    data: data
  })
}

// 修改班级信息数据
export function updateClazz(data) {
  return request({
    url: '/wxinfo/clazz',
    method: 'put',
    data: data
  })
}

// 删除班级信息数据
export function delClazz(clazzId) {
  return request({
    url: '/wxinfo/clazz/' + clazzId,
    method: 'delete'
  })
}
