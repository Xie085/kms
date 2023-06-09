import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listMain(query) {
  return request({
    url: '/kms/main/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getMain(id) {
  return request({
    url: '/kms/main/get/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addMain(data) {
  return request({
    url: '/kms/main/add',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateMain(data) {
  return request({
    url: '/kms/main/edit',
    method: 'post',
    data: data
  })
}

// 删除【请填写功能名称】
export function delMain(id) {
  return request({
    url: '/kms/main/delete/' + id,
    method: 'delete'
  })
}

// 预览附件接口
export function previewAttFile(id) {
  return request({
    url: '/sys/attachment/find/converted',
    method: 'post',
    params: {kmsId: id}
  })
}

// 查询【请填写功能名称】列表
export function listForEs(query) {
  return request({
    url: '/kms/main/listForEs',
    method: 'get',
    params: query
  })
}

// 版本列表接口
export function listVersion(id) {
  return request({
    url: '/kms/main/listVersion',
    method: 'get',
    params: {kmsId: id}
  })
}


