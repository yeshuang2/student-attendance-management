import request from '@/utils/request'

// 查询课程信息数据表列表
export function listCourse(query) {
  return request({
    url: '/wxinfo/course/list',
    method: 'get',
    params: query
  })
}

// 查询课程信息数据表详细
export function getCourse(wxCourseid) {
  return request({
    url: '/wxinfo/course/' + wxCourseid,
    method: 'get'
  })
}

// 新增课程信息数据表
export function addCourse(data) {
  return request({
    url: '/wxinfo/course',
    method: 'post',
    data: data
  })
}

// 修改课程信息数据表
export function updateCourse(data) {
  return request({
    url: '/wxinfo/course',
    method: 'put',
    data: data
  })
}

// 删除课程信息数据表
export function delCourse(wxCourseid) {
  return request({
    url: '/wxinfo/course/' + wxCourseid,
    method: 'delete'
  })
}
