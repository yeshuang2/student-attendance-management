import request from '@/utils/request'
// 生成二维码模块
export function GenerateV2(content) {
  return request({
    url: '/wxcq/generate/v2/' + content,
    method: 'post',
  })
}



// 查询学生签到数据表列表
export function listSignin(query) {
  return request({
    url: '/wxinfo/signin/list',
    method: 'get',
    params: query
  })
}

// 查询学生签到数据表详细
export function getSignin(signinId) {
  return request({
    url: '/wxinfo/signin/' + signinId,
    method: 'get'
  })
}

// 新增学生签到数据表
export function addSignin(data) {
  return request({
    url: '/wxinfo/signin',
    method: 'post',
    data: data
  })
}

// 修改学生签到数据表
export function updateSignin(data) {
  return request({
    url: '/wxinfo/signin',
    method: 'put',
    data: data
  })
}

// 删除学生签到数据表
export function delSignin(signinId) {
  return request({
    url: '/wxinfo/signin/' + signinId,
    method: 'delete'
  })
}
