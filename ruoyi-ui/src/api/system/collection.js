import request from '@/utils/request'

// 查询http数据收集列表
export function listCollection(query) {
  return request({
    url: '/system/collection/list',
    method: 'get',
    params: query
  })
}

// 查询http数据收集详细
export function getCollection(id) {
  return request({
    url: '/system/collection/' + id,
    method: 'get'
  })
}

// 新增http数据收集
export function addCollection(data) {
  return request({
    url: '/system/collection',
    method: 'post',
    data: data
  })
}

// 修改http数据收集
export function updateCollection(data) {
  return request({
    url: '/system/collection',
    method: 'put',
    data: data
  })
}

// 删除http数据收集
export function delCollection(id) {
  return request({
    url: '/system/collection/' + id,
    method: 'delete'
  })
}

// 执行数据收集
export function execCollection(id) {
  return request({
    url: '/system/collection/exec/' + id,
    method: 'get'
  })
}
