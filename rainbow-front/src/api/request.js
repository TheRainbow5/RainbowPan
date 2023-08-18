import axios from "axios";

//允许跨域携带cookie信息，用于服务器识别
axios.defaults.withCredentials = true;
// 默认服务器地址
axios.defaults.baseURL = 'http://localhost:9090'  //设置全局的url地址

/**
 * 发送通用的 GET 请求
 * @param {string} url 请求的URL
 * @returns {Promise} 包含响应数据的 Promise 对象
 */
function commonGet(url) {
    return axios({
        method: 'get',
        url: url,
        headers: { token: localStorage.getItem('token') }
    })
}

/**
 * 发送通用的 POST 请求
 * @param {string} url 请求的URL
 * @param {object} params 请求的数据
 * @returns {Promise} 包含响应数据的 Promise 对象
 */
function commonPost(url, params) {
    return axios({
        method: 'post',
        url: url,
        params, params,
        headers: {
            'Content-Type': 'application/json',
            token: localStorage.getItem('token')
        }
    });
}

/**
 * 发送通用的 POST 请求（包含文件上传）
 * @param {string} url 请求的URL
 * @param {object} data 请求的数据，包括文件
 * @returns {Promise} 包含响应数据的 Promise 对象
 */
function commonUploadFile(url, formData) {
    return axios({
        method: 'post',
        url: url,
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data',
            token: localStorage.getItem('token')
        }
    });
}



export { commonPost, commonGet, commonUploadFile }


// /**
//  * 下载附件
//  * @param path 接口地址
//  * @param param  请求参数
//  */
// function downloadGet(path, param) {
//     var url = baseUrl + path + param
//     axios({
//         method: 'get',
//         url: url,
//         responseType: 'blob',
//         headers: { 'token': localStorage.getItem('token') }
//     }).then(res => {
//         resolveBlob(res, res.data.type)
//     })
// }
// //不同文件标识
// const mimeMap = {
//     xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
//     xls: 'application/vnd.ms-excel',
//     zip: 'application/zip',
//     jpg: 'image/jpg',
//     jpeg: 'image/jpeg',
//     png: 'image/png',
//     doc: 'application/msword',
//     docx: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
//     ppt: 'application/vnd.ms-powerpoint',
//     txt: 'text/plain',
//     pdf: 'application/pdf'
// }
// /**
//  * 解析blob响应内容并下载
//  * @param {*} res blob响应内容
//  * @param {String} mimeType MIME类型
//  */
// export function resolveBlob(res, mimeType) {
//     const aLink = document.createElement('a')
//     var blob = new Blob([res.data], { type: mimeType })
//     // 从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
//     var patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
//     var contentDisposition = decodeURI(res.headers['content-disposition'])
//     var result = patt.exec(contentDisposition)
//     var fileName = result[1]
//     fileName = fileName.replace(/\"/g, '')
//     aLink.href = URL.createObjectURL(blob)
//     aLink.setAttribute('download', fileName) // 设置下载文件名称
//     document.body.appendChild(aLink)
//     aLink.click()
//     document.body.removeChild(aLink);
// }


// export { downloadGet }