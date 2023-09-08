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
1、将Users文件夹和rainbow-front、end文件同一级目录<br>
2、由于配置了发送验证码的功能，所以必须联网<br>
### 后端
1、克隆项目到本地。<br>
2、在 rainbow-end/src/main/resources/application-mybatis.yaml 文件中配置数据库连接信息。<br>
3、在 rainbow-end/src/main/resources/application.yaml 文件中修改发送验证码的邮箱和项目的全路径。<br>
4、使用 Maven 构建并运行 Spring Boot 后端应用。<br>
````shell
cd rainbow-end
mvn spring-boot:run
````

### 前端
1、进入 rainbow-front 目录。<br>
2、安装依赖<br>
````shell
npm install
````
3、启动Vue
````shell
npm run serve
````

### 数据库
1、创建rainbowpan数据库<br>
2、创建files和users两张表<br>
```sql
create table users
(
    EMAIL    varchar(100) not null comment '邮箱'
        primary key,
    USERNAME varchar(255) null comment '用户名',
    IMG_URL  varchar(255) null comment '头像访问链接',
    PASSWORD varchar(255) null comment '密码'
);
```
```sql
create table files
(
    FILE_ID       varchar(255) not null comment '文件id'
        primary key,
    EMAIL         varchar(100) not null comment '邮箱',
    FILE_PID      varchar(100) null comment '父目录',
    FILE_NAME     varchar(255) null comment '文件名称',
    FILE_PATH     varchar(255) null comment '文件访问路径',
    FILE_SIZE     varchar(100) null comment '文件大小',
    FILE_COVER    varchar(255) null comment '文件封面存储路径',
    CREATE_TIME   varchar(40)  null comment '入库时间',
    UPDATE_TIME   varchar(40)  null comment '最后更新时间',
    FOLDER_TYPE   varchar(10)  null comment '文件类型 0:文件 1:目录',
    FILE_CATEGORY varchar(10)  null comment '0:视频 1:音频 2:图片 3:pdf 4:doc 5:excel 6:txt 7:code 8:zip 9:其他',
    RECOVERY_TIME varchar(40)  null comment '回收时间',
    STATUS        varchar(10)  null comment '0:回收站 1:正常'
);
```
3、运行sql脚本，插入数据。<br>
4、测试账号：admin@qq.com，密码：admin。

# 项目部署到linux服务器
具体参考[部署到服务器](https://www.notion.so/rainbow-jie/de19984c86e64f109c2076800dbf5601?pvs=4)


# 贡献
欢迎提供反馈、报告问题和贡献代码！请提交 Issue 或 Pull 请求。

# 许可证
本项目采用 MIT 许可证。





