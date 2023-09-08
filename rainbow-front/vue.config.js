const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        // 生产环境时开启，访问前端的url地址（服务器的ip地址）
        // host: '192.168.218.131',
        // 项目启动的端口号
        port: 5050,
    }
})
