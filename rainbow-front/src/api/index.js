import axios from "axios";

//允许跨域携带cookie信息，用于服务器识别
axios.defaults.withCredentials = true;
// 默认服务器地址
//let prefixUrl = window.location.protocol + "//" + location.host + "/";
axios.defaults.baseURL = 'http://localhost:9090'  //设置全局的url地址

/**
 * Post请求
 * @param {请求地址} url
 * @param {请求参数} param
 * @param {*} failure
 * @param {*} error
 */
// function commonPost(url, param) {
//     axios.post(url, param, {
//         headers: {
//             "Content-Type": 'application/x-www/form-urlencoded'
//         },
//     }).then(data => {
//         if (data.success) {
//             console.log("发送成功");
//         } else {
//             this.$message({
//                 message: "警告哦，这是一条警告消息",
//                 type: "warning"
//             });
//         }
//     }).catch(() => {
//         this.$message({
//             message: "发生一些错误，请联系管理员",
//             type: "error"
//         });
//     })
// }

/**
 * 通用的GET请求
 * @param {请求地址} url
 * @param {*} failure
 * @param {*} error
 */
// function commonGet(url) {
//     //格式化请求url
//     axios.post(url, param).then(data => {
//         if (data.success) {
//             console.log("发送成功");
//         } else {
//             this.$message({
//                 message: "发生一些错误，请联系管理员",
//                 type: "warning"
//             });
//         }
//     }).catch(() => {
//         this.$message({
//             message: "警告哦，这是一条警告消息",
//             type: "error"
//         });
//     })
// }

// export { commonGet, commonPost }