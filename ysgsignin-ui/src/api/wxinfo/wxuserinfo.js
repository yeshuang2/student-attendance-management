import request from '@/utils/request'

// 查询微信授权登录表列表
export function listWxuserinfo(query) {
  return request({
    url: '/wxinfo/wxuserinfo/list',
    method: 'get',
    params: query
  })
}

// 查询微信授权登录表详细
export function getWxuserinfo(wxUserid) {
  return request({
    url: '/wxinfo/wxuserinfo/' + wxUserid,
    method: 'get'
  })
}

// 新增微信授权登录表
export function addWxuserinfo(data) {
  return request({
    url: '/wxinfo/wxuserinfo',
    method: 'post',
    data: data
  })
}

// 修改微信授权登录表
export function updateWxuserinfo(data) {
  return request({
    url: '/wxinfo/wxuserinfo',
    method: 'put',
    data: data
  })
}

// 删除微信授权登录表
export function delWxuserinfo(wxUserid) {
  return request({
    url: '/wxinfo/wxuserinfo/' + wxUserid,
    method: 'delete'
  })
}
