# RainbowPan
这是一个基于 Spring Boot+Vue的前后端分离云端网盘项目，旨在提供一个简单而功能强大的文件存储和管理解决方案。用户可以通过网页界面上传、下载、管理和共享文件。

## 功能特性
* 界面友好：使用 Vue 前端框架创建了一个直观且易于使用的用户界面。（over）
* 用户认证与授权：用户可以注册账户，登录系统，并根据角色进行文件访问控制。（over）
* 文件管理：用户可以创建文件夹、删除文件、文件重命名等。（over）
* 文件上传与下载：用户可以上传文件到云端，也可以下载已上传的文件。（over）
* 搜索功能：用户可以通过关键词搜索文件和文件夹。（over）
* 文件预览：用户可以预览视频、文档等（over）


## 技术栈
### 后端
* Spring Boot：用于构建强大的 Java 后端应用程序。
* JWT：提供用户认证和授权功能。
* Mybatis-Plus：简化数据访问层的开发。
* MySQL：作为数据库存储用户和文件信息。

### 前端
* Vue.js：流行的前端框架，用于构建响应式用户界面。
* Vuex：用于集中状态管理。
* Vue Router：实现前端路由，实现单页面应用程序。
* Element UI：基于 Vue.js 的 UI 组件库，提供丰富的用户界面组件。

## 快速开始
#### 重要
1、必须在项目文件夹下创建Users目录<br>
2、由于配置了发送验证码的功能，所以必须联网<br>
### 后端
1、克隆项目到本地。<br>
2、在 rainbow-end/src/main/resources/application-mybatis.yaml 文件中配置数据库连接信息。<br>
3、在 rainbow-end/src/main/resources/application.yaml 文件中修改发送验证码的邮箱和项目的全路径。<br>
4、使用 Maven 构建并运行 Spring Boot 后端应用。<br>
````
cd rainbow-end
mvn spring-boot:run
````

### 前端
1、进入 rainbow-front 目录。<br>
2、安装依赖<br>
````
npm install
````
3、启动Vue
````
npm run serve
````
# 贡献
欢迎提供反馈、报告问题和贡献代码！请提交 Issue 或 Pull 请求。

# 许可证
本项目采用 MIT 许可证。





