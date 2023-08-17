const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        // 项目启动端口之后会变成5050
        port: 5050,
        proxy: {
            '/api': {
                target: 'http://localhost:9090', // 后端服务器地址
                changeOrigin: true
            }
        }
    }
})
