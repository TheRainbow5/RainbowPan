<template>
    <!-- 设置整体布局 -->
    <el-container style="width:100%; height: 100%; background-color: #f7f9fc; ">
        <!-- 头部 -->
        <el-header class="header" height="80px">
            <!-- logo -->
            <div class="logo-btn">
                <a href="/index">
                    <img src="@/assets/logo.png" style="float: left; margin-left: 40px; margin-top: 10px; ">
                    <b style=" color:rgb(97, 93, 93); float: left; margin-left: 10px;  margin-top: 10px;">Rainbow云端硬盘</b>
                </a>
            </div>

            <!-- 搜索 -->
            <div class="search">
                <div class="search-btn">
                    <el-button class="search-log">
                        <svg t="1691246042746" class="icon" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="2101" width="20" height="20">
                            <path
                                d="M960 896l-204.224-204.48a47.104 47.104 0 0 0-14.848-10.112 383.776 383.776 0 0 0 83.008-238.848c0-212.8-172.544-385.28-385.408-385.28-212.736 0-385.344 172.48-385.344 385.344 0 212.8 172.544 385.216 385.344 385.216a383.36 383.36 0 0 0 239.04-83.008c2.432 5.376 5.76 10.432 10.176 14.784l204.096 204.544a48.256 48.256 0 0 0 68.096 0c18.88-18.816 18.88-49.28 0.064-68.16z m-521.536-164.48c-159.552 0-288.96-129.344-288.96-288.96s129.344-289.024 288.96-289.024c159.68 0 289.024 129.408 289.024 289.024s-129.344 288.96-289.024 288.96z"
                                fill="#666666" p-id="2102"></path>
                        </svg>
                    </el-button>
                    <input class="search-input" v-model="searchInput" placeholder="在云端硬盘中搜索" font-size="20">
                </div>
            </div>
            <!-- 设置按钮 -->
            <div class="userInfo">
                <el-button class="setting-btn">
                    <svg t="1691249992863" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="2835" width="20" height="20">
                        <path
                            d="M556.1 960H468c-26 0.1-49.5-15.7-59.3-39.8-5.6-13.6-13.6-34.7-22.3-62.2-10.2-3.7-20.3-7.9-30.2-12.5-25.6 13.3-46.2 22.5-59.8 28.2-24 10.1-51.6 4.7-70-13.8L164 797.6c-18.4-18.4-23.9-46-13.8-70 5.7-13.6 14.9-34.2 28.2-59.8-4.6-9.9-8.7-20-12.5-30.2-27.5-8.7-48.6-16.7-62.2-22.3-24.1-9.8-39.8-33.2-39.8-59.3v-88.2c-0.1-26 15.7-49.5 39.8-59.3 13.6-5.6 34.7-13.6 62.2-22.3 3.7-10.2 7.9-20.3 12.5-30.2-13.3-25.6-22.5-46.2-28.2-59.8-10.1-24-4.7-51.6 13.7-70l62.3-62.4c18.4-18.4 46-23.9 70-13.7 13.6 5.7 34.2 14.9 59.8 28.2 9.9-4.6 20-8.7 30.2-12.5 8.7-27.5 16.7-48.6 22.3-62.2 9.8-24.1 33.2-39.8 59.3-39.8h88.1c26-0.1 49.5 15.7 59.2 39.8 5.6 13.6 13.6 34.7 22.3 62.2 10.2 3.7 20.3 7.9 30.2 12.5 25.6-13.3 46.2-22.5 59.8-28.2 24-10.1 51.6-4.7 70 13.8l62.4 62.3c18.4 18.4 23.9 46 13.8 70-5.7 13.6-14.9 34.2-28.2 59.8 4.6 9.9 8.7 20 12.5 30.2 27.5 8.7 48.6 16.7 62.2 22.3 24.1 9.8 39.9 33.2 39.8 59.3v88.1c0.1 26-15.7 49.5-39.8 59.3-13.6 5.6-34.7 13.6-62.2 22.3-3.7 10.2-7.9 20.3-12.5 30.2 13.3 25.6 22.5 46.2 28.2 59.8 10.1 24 4.7 51.6-13.8 70L797.6 860c-18.4 18.4-46 23.9-70 13.8-13.6-5.7-34.2-14.9-59.8-28.2-9.9 4.6-20 8.7-30.2 12.5-8.7 27.5-16.7 48.6-22.3 62.2-9.8 24-33.2 39.8-59.2 39.7zM355.7 777.5c5.2 0 10.3 1.3 14.9 3.7 16.3 8.6 33.4 15.7 51.1 21.2 10.2 3.2 18.1 11.2 21.2 21.4 9.8 32.8 19 57.4 25.1 72.2h88.2c6.1-14.8 15.3-39.4 25.1-72.2 3-10.2 11-18.3 21.2-21.4 17.6-5.5 34.7-12.6 51.1-21.2 9.5-5 20.8-4.9 30.2 0.2 30.1 16.3 54 27.1 68.8 33.3l62.4-62.3c-6.2-14.8-17-38.7-33.3-68.8-5.1-9.4-5.2-20.7-0.2-30.2 8.6-16.3 15.7-33.4 21.2-51.1 3.2-10.2 11.2-18.1 21.5-21.2 32.8-9.8 57.3-19 72.2-25.1v-88.2c-14.8-6.1-39.4-15.3-72.2-25.1-10.2-3-18.3-11-21.4-21.2-5.5-17.6-12.6-34.7-21.2-51.1-5-9.5-4.9-20.8 0.2-30.2 16.3-30.1 27.1-54 33.3-68.8l-62.3-62.3c-14.8 6.2-38.7 17-68.8 33.3-9.4 5.1-20.7 5.2-30.2 0.2-16.3-8.6-33.4-15.7-51.1-21.2-10.2-3.2-18.1-11.2-21.2-21.4-9.7-32.8-19-57.3-25.1-72.2h-88.2c-6 14.8-15.3 39.2-25 71.8-2.9 10.4-11 18.6-21.3 21.8-17.6 5.5-34.7 12.6-51.1 21.2-9.5 5-20.8 4.9-30.2-0.2-30.1-16.3-54-27.1-68.8-33.3l-62.3 62.3c6.2 14.8 17 38.7 33.3 68.8 5.1 9.4 5.2 20.7 0.2 30.2-8.6 16.3-15.7 33.4-21.2 51.1-3.2 10.2-11.2 18.1-21.4 21.2-32.8 9.7-57.3 19-72.2 25.1V556c14.7 6 39 15.2 71.5 24.9 10.8 3 19.3 11.4 22.4 22.2 5.4 17.1 12.3 33.7 20.6 49.6 5.5 9.8 5.4 21.8-0.3 31.6-16.1 29.7-26.7 53.3-32.9 68l62.3 62.4c14.8-6.2 38.7-17 68.8-33.3 4.5-2.5 9.8-3.9 15.1-3.9z"
                            fill="#9E9D9E" p-id="2836"></path>
                        <path
                            d="M556.1 960H468c-26 0.1-49.5-15.7-59.3-39.8-5.6-13.6-13.6-34.7-22.3-62.2-10.2-3.7-20.3-7.9-30.2-12.5-25.6 13.3-46.2 22.5-59.8 28.2-24 10.1-51.6 4.7-70-13.8L164 797.6c-18.4-18.4-23.9-46-13.8-70 5.7-13.6 14.9-34.2 28.2-59.8-4.6-9.9-8.7-20-12.5-30.2-27.5-8.7-48.6-16.7-62.2-22.3-24.1-9.8-39.8-33.2-39.8-59.3v-88.2c-0.1-26 15.7-49.5 39.8-59.3 13.6-5.6 34.7-13.6 62.2-22.3 3.7-10.2 7.9-20.3 12.5-30.2-13.3-25.6-22.5-46.2-28.2-59.8-10.1-24-4.7-51.6 13.7-70l62.3-62.4c18.4-18.4 46-23.9 70-13.7 13.6 5.7 34.2 14.9 59.8 28.2 9.9-4.6 20-8.7 30.2-12.5 8.7-27.5 16.7-48.6 22.3-62.2 9.8-24.1 33.2-39.8 59.3-39.8h88.1c26-0.1 49.5 15.7 59.2 39.8 5.6 13.6 13.6 34.7 22.3 62.2 10.2 3.7 20.3 7.9 30.2 12.5 25.6-13.3 46.2-22.5 59.8-28.2 24-10.1 51.6-4.7 70 13.8l62.4 62.3c18.4 18.4 23.9 46 13.8 70-5.7 13.6-14.9 34.2-28.2 59.8 4.6 9.9 8.7 20 12.5 30.2 27.5 8.7 48.6 16.7 62.2 22.3 24.1 9.8 39.9 33.2 39.8 59.3v88.1c0.1 26-15.7 49.5-39.8 59.3-13.6 5.6-34.7 13.6-62.2 22.3-3.7 10.2-7.9 20.3-12.5 30.2 13.3 25.6 22.5 46.2 28.2 59.8 10.1 24 4.7 51.6-13.8 70L797.6 860c-18.4 18.4-46 23.9-70 13.8-13.6-5.7-34.2-14.9-59.8-28.2-9.9 4.6-20 8.7-30.2 12.5-8.7 27.5-16.7 48.6-22.3 62.2-9.8 24-33.2 39.8-59.2 39.7zM355.7 777.5c5.2 0 10.3 1.3 14.9 3.7 16.3 8.6 33.4 15.7 51.1 21.2 10.2 3.2 18.1 11.2 21.2 21.4 9.8 32.8 19 57.4 25.1 72.2h88.2c6.1-14.8 15.3-39.4 25.1-72.2 3-10.2 11-18.3 21.2-21.4 17.6-5.5 34.7-12.6 51.1-21.2 9.5-5 20.8-4.9 30.2 0.2 30.1 16.3 54 27.1 68.8 33.3l62.4-62.3c-6.2-14.8-17-38.7-33.3-68.8-5.1-9.4-5.2-20.7-0.2-30.2 8.6-16.3 15.7-33.4 21.2-51.1 3.2-10.2 11.2-18.1 21.5-21.2 32.8-9.8 57.3-19 72.2-25.1v-88.2c-14.8-6.1-39.4-15.3-72.2-25.1-10.2-3-18.3-11-21.4-21.2-5.5-17.6-12.6-34.7-21.2-51.1-5-9.5-4.9-20.8 0.2-30.2 16.3-30.1 27.1-54 33.3-68.8l-62.3-62.3c-14.8 6.2-38.7 17-68.8 33.3-9.4 5.1-20.7 5.2-30.2 0.2-16.3-8.6-33.4-15.7-51.1-21.2-10.2-3.2-18.1-11.2-21.2-21.4-9.7-32.8-19-57.3-25.1-72.2h-88.2c-6 14.8-15.3 39.2-25 71.8-2.9 10.4-11 18.6-21.3 21.8-17.6 5.5-34.7 12.6-51.1 21.2-9.5 5-20.8 4.9-30.2-0.2-30.1-16.3-54-27.1-68.8-33.3l-62.3 62.3c6.2 14.8 17 38.7 33.3 68.8 5.1 9.4 5.2 20.7 0.2 30.2-8.6 16.3-15.7 33.4-21.2 51.1-3.2 10.2-11.2 18.1-21.4 21.2-32.8 9.7-57.3 19-72.2 25.1V556c14.7 6 39 15.2 71.5 24.9 10.8 3 19.3 11.4 22.4 22.2 5.4 17.1 12.3 33.7 20.6 49.6 5.5 9.8 5.4 21.8-0.3 31.6-16.1 29.7-26.7 53.3-32.9 68l62.3 62.4c14.8-6.2 38.7-17 68.8-33.3 4.5-2.5 9.8-3.9 15.1-3.9z"
                            fill="#9E9D9E" p-id="2837"></path>
                        <path
                            d="M512 704c-105.9 0-192-86.1-192-192s86.1-192 192-192 192 86.1 192 192-86.1 192-192 192z m0-320c-70.7 0-128 57.3-128 128s57.3 128 128 128 128-57.3 128-128c-0.1-70.7-57.3-127.9-128-128z"
                            fill="#9E9D9E" p-id="2838"></path>
                    </svg>
                </el-button>
            </div>
            <!-- 个人信息 -->
            <div class="userInfo">
                <el-popover popper-class="user-dialog" :visible-arrow="false" placement="bottom-end" width="300"
                    trigger="click">
                    <!-- 显示个人信息 -->
                    <div class="user-dialog-showUserInfo">
                        <div class="showUserInfo-img">
                            <div class="img-1">
                                <img class="user-img-1" :src="user.defaultUserPic">
                                <!-- 上传头像 -->
                                <el-upload class="change-img" action="#" :http-request="uploadFile" :limit="1" ref="upload"
                                    :before-upload="beforeAvatarUpload" :show-file-list="false" accept=".jpg,.png,.jpeg">
                                    <svg t=" 1691400177871" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                        xmlns="http://www.w3.org/2000/svg" p-id="33195" width="20" height="20">
                                        <path
                                            d="M929.792 197.632h-118.784L757.76 101.888c-9.216-11.776-23.04-20.992-39.424-20.992H469.504c-18.432 0-34.816 9.216-44.032 25.6L371.712 199.68H255.488v-25.6c0-11.776-11.776-23.04-23.04-23.04h-46.592c-13.824 0-23.04 11.776-23.04 23.04v23.04H92.672C41.472 197.632 0 239.104 0 290.304v558.08c0 51.2 41.984 93.184 93.184 93.184h837.12c51.2 0 93.184-41.984 93.184-93.184v-558.08c-0.512-51.2-42.496-92.672-93.696-92.672zM467.456 130.048l2.56-2.56h246.272l39.424 69.632h-327.68l39.424-67.072z m508.928 718.336c0 25.6-20.992 46.592-46.592 46.592H92.672c-25.6 0-46.592-20.992-46.592-46.592v-558.08c0-25.6 20.992-46.592 46.592-46.592h837.12c25.6 0 46.592 20.992 46.592 46.592v558.08z m0 0"
                                            fill="#B0B0B0" p-id="33196"></path>
                                        <path
                                            d="M615.936 360.448c-120.832 0-220.672 99.84-220.672 220.672s99.84 220.672 220.672 220.672 220.672-99.84 220.672-220.672c0.512-120.832-99.84-220.672-220.672-220.672z m0 395.264c-95.232 0-174.592-78.848-174.592-174.592 0-95.232 78.848-174.592 174.592-174.592 95.232 0 174.592 78.848 174.592 174.592 0 95.232-78.848 174.592-174.592 174.592zM302.08 336.896H139.264c-11.776 0-23.04 11.776-23.04 23.04v23.04c0 11.776 11.776 23.04 23.04 23.04H302.08c13.824 0 23.04-11.776 23.04-23.04v-23.04c0.512-11.264-11.264-23.04-23.04-23.04z m0 0"
                                            fill="#B0B0B0" p-id="33197"></path>
                                    </svg>
                                </el-upload>
                            </div>

                        </div>
                        <div class=" showUserInfo-userInfo">
                            <div><b>{{ user.username }}</b></div>
                            <div style=" margin-top: 10px;">{{ user.email }}</div>
                        </div>
                    </div>
                    <!-- 切换账号和退出登录 -->
                    <button class="logout" @click="logout">
                        <svg t="1691379324297" class="logout-logo" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="2433" width="20" height="20">
                            <path
                                d="M563.2 986.763636H93.090909c-51.2 0-93.090909-41.890909-93.090909-93.090909V130.327273c0-51.2 41.890909-93.090909 93.090909-93.090909h470.109091c27.927273 0 46.545455 18.618182 46.545455 46.545454s-18.618182 46.545455-46.545455 46.545455H93.090909v768h470.109091c27.927273 0 46.545455 18.618182 46.545455 46.545454 0 23.272727-18.618182 41.890909-46.545455 41.890909zM730.763636 758.690909c-18.618182-18.618182-18.618182-46.545455 0-65.163636l176.872728-176.872728L698.181818 297.890909l32.581818-32.581818c18.618182-18.618182 46.545455-18.618182 65.163637 0l214.109091 214.109091c18.618182 18.618182 18.618182 46.545455 0 65.163636l-214.109091 214.109091c-13.963636 18.618182-46.545455 18.618182-65.163637 0z"
                                fill="#31394C" p-id="2434"></path>
                            <path
                                d="M935.563636 558.545455H404.945455c-27.927273 0-46.545455-18.618182-46.545455-46.545455s18.618182-46.545455 46.545455-46.545455h530.618181v93.09091z"
                                fill="#31394C" p-id="2435"></path>
                        </svg>
                        <span style="margin-left: 30px;">退出登录</span>
                    </button>

                    <el-button class="user-btn" slot="reference">
                        <img class="user-img" :src="user.defaultUserPic">
                    </el-button>
                </el-popover>
            </div>
        </el-header>


        <el-container style="height: 100%;">
            <!-- 侧标 -->
            <el-aside class="aside" style="width:18%; ">
                <!-- 新建文件夹弹出框 -->
                <el-dialog class="newDir-dialog" title="新文件夹" width="30%" top="30vh" :visible.sync="dialogTableVisible">
                    <input class="newDir-btn-input" placeholder="未命名文件夹" value="未命名文件夹" v-model="fileName" clearable>
                    <div slot="footer" class="dialog-footer">
                        <button class="newDir-btn-cancel" @click="dialogTableVisible = false; fileName = ''">取 消</button>
                        <button class="newDir-btn-ok" @click="dialogTableVisible = false; addNewDir();">确 定</button>
                    </div>
                </el-dialog>
                <!-- 弹出框 -->
                <div style="width: 100%; height: 12%;">
                    <el-popover popper-class="newButton-dialog" :visible-arrow="false" placement="bottom" width="150"
                        trigger="click">
                        <div style="width: 100%; height: 100%; background-color: #f7f9fc; border-radius: 20px;">
                            <!-- 新建文件夹 -->
                            <div class="newDir-btn" @click="dialogTableVisible = true">
                                <i class="el-icon-folder-add" style="margin-left: 15px; margin-right: 10px;"></i>
                                新建文件夹
                            </div>
                            <!-- 文件文件 -->
                            <el-upload :on-success="handleSuccess" :action="this.$global.Url + '/file/uploadFile'" multiple
                                :before-upload="beforeHandle"
                                :data="{ email: user.email, currentDir: this.$store.getters.getCurrentDir }"
                                :on-progress="handleProgress" :headers="{ token: this.token }" :show-file-list="false"
                                class="uploadFile-btn">
                                <div class="addFile-btn">
                                    <i class="el-icon-document" style="margin-left: 15px; margin-right: 10px;"></i>
                                    上传文件
                                </div>
                            </el-upload>
                            <!-- 上传文件夹 -->
                            <!-- <div class="uploadDir-btn">
                                <i class="el-icon-upload" style="margin-left: 15px;  margin-right: 10px;"></i>
                                上传文件夹
                            </div> -->
                        </div>
                        <!-- 新建按钮 -->
                        <button class="newfile-btn" slot="reference">
                            <svg style="margin-right:10px;" t="1691291322162" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3399" width="20" height="20">
                                <path d="M474 152m8 0l60 0q8 0 8 8l0 704q0 8-8 8l-60 0q-8 0-8-8l0-704q0-8 8-8Z"
                                    fill="#000000" p-id="3400"></path>
                                <path d="M168 474m8 0l672 0q8 0 8 8l0 60q0 8-8 8l-672 0q-8 0-8-8l0-60q0-8 8-8Z"
                                    fill="#000000" p-id="3401"></path>
                            </svg>
                            新建
                        </button>
                    </el-popover>


                </div>

                <!-- 菜单栏 -->
                <div class="menu">
                    <div class="menu-1" style="width: 80%;background-color: #f7f9fc;margin-left: 20px;">

                        <el-row class="tac" style="margin-top: 20px;">
                            <el-col :span="24">
                                <el-menu class="el-menu-vertical">
                                    <el-menu-item class="el-submenu-vertical-1" @click="toMyPanPage">
                                        <i class="el-icon-folder"></i>
                                        我的云端硬盘
                                    </el-menu-item>
                                    <el-menu-item class="el-submenu-vertical-2" @click="toRecentPage">
                                        <i class="el-icon-time"></i>
                                        最近使用
                                    </el-menu-item>
                                    <el-menu-item class="el-submenu-vertical-3" @click="toTrashPage">
                                        <i class="el-icon-delete"></i>
                                        回收站
                                    </el-menu-item>
                                    <el-menu-item class="el-submenu-vertical-4">
                                        <i class="el-icon-coin"></i>
                                        存储空间
                                    </el-menu-item>
                                    <div>
                                        <el-progress :percentage="50" color="#909399"></el-progress>
                                    </div>
                                    <!-- 文件上传进度条 -->
                                    <div style="margin-top: 30px;">
                                        <div>
                                            {{ uploadFileName }}
                                        </div>
                                        <el-progress :percentage="uploadPercentage" v-if="uploading" />
                                    </div>
                                </el-menu>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-aside>
            <!-- 主体 -->
            <el-main class="main" style="width:82%;">
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script >

export default {
    inject: ['reload'],
    components: {},
    props: {},

    data() {
        return {
            //文件上传进度条
            uploading: false,
            uploadPercentage: 0,
            uploadFileName: '',
            //搜索调条件
            searchInput: '',
            //新建文件夹弹窗
            fileName: '',
            dialogTableVisible: false,
            //用户信息
            user: {
                email: '',
                username: '',
                defaultUserPic: require('@/assets/user/2.jpeg'), //新用户默认头像
            },
            token: '',


        };
    },
    // 渲染试图前
    created() {
        this.token = localStorage.getItem('token');   //获取token
        //从后台获取用户信息
        this.user.email = localStorage.getItem('email');
        //初始化获取用户名
        this.getUserInfo();
        //获取图片信息
        this.userImage();
    },
    // 渲染视图后
    mounted() {

    },
    methods: {
        beforeHandle(file) {
            this.uploadFileName = file.name;
        },
        /**
         * 文件上传进度条
         */
        handleProgress(event, file, fileList) {
            this.uploading = true;
            // Simulating upload progress
            setTimeout(() => {
                this.uploadPercentage = parseFloat((event.percent).toFixed(2)); // 保留两位小数
            }, 100);
            if (this.uploadPercentage >= 100) {
                this.uploading = false;
            }
        },
        /**
         * 文件上传成功后
         */
        handleSuccess(response) {
            if (response.status === '1') {
                this.$notify({
                    title: '该文件已经存在',
                    position: 'top-right',  //显示位置
                    duration: 3000,
                    type: 'error',
                    offset: 80
                });
            } else if (response.status === "0") {
                //刷新页面
                this.reload();
                this.$notify({
                    title: '文件上传成功',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 3秒关闭
                    type: 'success',
                    offset: 80
                });
            } else {
                this.$notify({
                    title: '发生一些错误，请联系管理员',
                    position: 'top-right',  //显示位置
                    duration: 3000,
                    type: 'error',
                    offset: 80
                });
            }
        },
        /**
         * 新建文件夹
         */
        addNewDir() {
            let param = {
                email: this.user.email,
                fileName: this.fileName,
                currentDir: this.$store.getters.getCurrentDir
            };
            this.$axios.post('index/addDir', param, {
                headers: { token: this.token }
            }).then(value => {
                // console.log(value.data);
                //当前所在文件夹位置
                if (value.data.status === '0') {
                    this.$notify({
                        title: '文件创建成功',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 2秒关闭
                        type: 'success',
                        offset: 80
                    });
                    //刷新界面
                    this.reload();
                } else {
                    this.$notify({
                        title: '文件创建失败',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 2秒关闭
                        type: 'error',
                        offset: 80
                    });
                }
            }).catch(() => {
                this.$notify({
                    title: '发生一些错误，请联系管理员',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'warning',
                    offset: 80
                });
            });
        },
        /**
         * 获取图片
         * 1、修改图片的访问地址
         */
        userImage() {
            let param = {
                email: this.user.email
            }
            this.$axios.post('index/userImageUrl', param, {
                headers: { token: this.token }
            }).then(value => {
                if (value.data.status === '0') {
                    this.user.defaultUserPic = this.$global.Url + "uploadFile/" + value.data.data.imageUrl + "?tempid=" + Math.random();
                } else {
                    this.user.defaultUserPic = require('@/assets/user/2.jpeg');
                }
            }).catch(() => {
                this.$notify({
                    title: '发生一些错误，请联系管理员',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'error',
                    offset: 80
                });
            });
        },
        /**
         * 自定义上传方法
         * @param {文件} file 
         */
        uploadFile(data) {
            var formData = new FormData();
            //拿到file文件存放到formData中
            formData.append("file", data.file);
            formData.append("email", this.user.email);
            //提交到服务器
            this.$axios.post('index/setImage', formData, {
                headers: { 'Content-Type': 'multipart/form-data', token: this.token }
            }).then(value => {
                // console.log(value.data);
                //上传成功之后清除历史记录
                if (value.data.status === '0') {
                    this.$notify({
                        title: '图片上传成功',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 2秒关闭
                        type: 'success',
                        offset: 80
                    });
                } else {
                    this.$notify({
                        title: '图片上传失败，请联系管理员',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 2秒关闭
                        type: 'error',
                        offset: 80
                    });
                }
            }).catch(() => {
                this.$notify({
                    title: '发生一些错误，请联系管理员',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'warning',
                    offset: 80
                });
            });
            this.reload();
            this.userImage();
        },
        /**
         * 判断文件大小和文件格式
         * @param {上传的文件} file 
         */
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$notify({
                    title: '上传头像图片只能是 JPG 格式!',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'error',
                    offset: 80
                });
            }
            if (!isLt2M) {
                this.$notify({
                    title: '上传头像图片大小不能超过 2MB!',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'error',
                    offset: 80
                });
            }
            return isJPG && isLt2M;
        },
        /**
         * 获取用户信息
         */
        getUserInfo() {
            let param = {
                user: this.user,
            };
            this.$axios.post('index/getUserInfo', param, {
                headers: { token: this.token }
            }).then(value => {
                if (value.data.status === '0') {
                    this.user.username = value.data.data.username;
                }
            }).catch(() => {
                this.$notify({
                    title: '发生一些错误，请联系管理员!',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'error',
                    offset: 80
                });
            });
        },
        // 我的云端硬盘
        toMyPanPage() {
            this.$store.commit('saveCurrentDir', '');
            this.$store.commit('saveAbsolutePath', '');
            this.reload();
            this.$router.push('/', () => { }, () => { });
        },
        // 跳转到最近使用
        toRecentPage() {
            this.$router.push('/Index-recentPage', () => { }, () => { });
        },
        toTrashPage() {
            this.$router.push('/Index-trashPage', () => { }, () => { });
        },
        /**
         * 退出登录
         * 1、删除本地token
         * 2、跳转到登录界面
         */
        logout() {
            //删除本地token
            localStorage.removeItem('token');
            //删除用户的email
            localStorage.removeItem('email');
            //跳转到登录界面
            this.$router.push('/');
        },
        /**
         * 重新登录提示框
         */
        reloginDialog() {
            this.$confirm('登录已过期，请重新登录', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //跳转到登录界面
                this.$router.push('/');
            }).catch(() => { });
        },
    }
};
</script >

<style lang="scss" scoped> // 头部

 .header {
     padding: 0%;
     display: flex;
     flex-direction: row;

     // logo
     .logo-btn {
         width: 20%;
         height: 100%;
         text-align: center;
         line-height: 60px;
     }

     // 搜索框
     .search {
         width: 70%;
         height: 100%;

         // 输入框
         .search-btn {
             // 垂直居中
             display: flex;
             align-items: center;
             justify-content: left;

             margin-top: 10px;
             height: 65%;
             width: 700px;
             background-color: #edf2fc;
             margin-left: 20px;
             border: none;
             border-radius: 50px;
         }

         .search-log {
             background-color: #edf2fc;
             float: left;
             padding: 0%;
             margin-left: 10px;
             width: 40px;
             height: 40px;
             border: none;
             border-radius: 50px;
         }

         .search-log:hover {
             filter: brightness(0.9);
         }

         .search-log :active {
             transform: scale(0.9);
         }

         .search-input {
             background-color: #edf2fc;
             outline: none;
             font-size: 15px;
             float: left;
             margin-left: 10px;
             width: 600px;
             height: 40px;
             border: none;
         }
     }

     .userInfo {
         display: flex;
         align-items: center;
         justify-content: center;
         width: 10%;
         height: 100%;


         .setting-btn {
             border-radius: 50px;
             margin-right: 20px;
             background-color: #f7f9fc;
             padding: 0%;
             width: 40px;
             height: 40px;
             border: none;
         }

         .setting-btn:hover {
             filter: brightness(0.9);
         }

         .setting-btn:active {
             transform: scale(0.95);
         }

         // 个人信息
         .user-btn {
             display: flex;
             align-items: center;
             justify-content: center;
             margin-right: 30px;
             background-color: #f7f9fc;
             padding: 0%;
             width: 50px;
             height: 50px;
             border-radius: 50%;
             border: none;

             //图片
             .user-img {
                 width: 40px;
                 border-radius: 50px;
             }
         }

         .user-btn:hover {
             cursor: pointer;
             border: solid #eee;
         }

         .user-btn:active {
             transform: scale(0.95);
         }
     }
 }

 .aside {
     .newDir-dialog {
         .newDir-btn-input {
             border: 2px, solid #edf2fc;
             height: 40px;
             width: 100%;
             outline: none;
         }

         .dialog-footer {
             display: flex;
             align-items: center;
             justify-content: right;

             .newDir-btn-cancel {
                 font-size: small;
                 background-color: white;
                 border: none;
                 width: 70px;
                 height: 40px;
                 margin-right: 40px;
                 border-radius: 10px;
             }

             .newDir-btn-cancel:hover {
                 background-color: #edf2fc;
                 cursor: pointer;
                 // filter: brightness(0.9);
             }

             .newDir-btn-cancel:active {
                 transform: scale(0.95);
             }

             .newDir-btn-ok {
                 font-size: small;
                 background-color: white;
                 border: none;
                 width: 70px;
                 height: 40px;
                 border-radius: 10px;
                 margin-right: 10px;
             }

             .newDir-btn-ok:hover {
                 background-color: #edf2fc;
                 cursor: pointer;
                 // filter: brightness(0.9);
             }

             .newDir-btn-ok:active {
                 transform: scale(0.95);
             }
         }

     }



     .newfile-btn {
         display: flex;
         align-items: center;
         justify-content: center;
         margin-left: 20px;
         margin-bottom: 20px;
         background-color: white;
         width: 120px;
         height: 50px;
         border: none;
         border-radius: 20px;
         box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
     }

     .newfile-btn:hover {
         background-color: #edf2fc;
         cursor: pointer;
         // filter: brightness(0.9);
     }

     .newfile-btn:active {
         transform: scale(0.95);
     }

     .menu {
         display: flex;
         align-items: top;
         width: 100%;
         height: 82%;

         .menu-1 {

             .el-menu-vertical {
                 height: 40px;
                 background-color: #f7f9fc;
             }

             .el-submenu-vertical-1 {
                 display: flex;
                 align-items: center;
                 justify-content: left;
                 height: 40px;
                 border-radius: 20px;
             }

             .el-submenu-vertical-1:hover {
                 filter: brightness(0.9);
             }

             .el-submenu-vertical-2 {
                 display: flex;
                 align-items: center;
                 justify-content: left;
                 height: 40px;
                 border-radius: 20px;
             }

             .el-submenu-vertical-2:hover {
                 filter: brightness(0.9);
             }

             .el-submenu-vertical-3 {
                 display: flex;
                 align-items: center;
                 justify-content: left;
                 height: 40px;
                 border-radius: 20px;
             }

             .el-submenu-vertical-3:hover {
                 filter: brightness(0.9);
             }

             .el-submenu-vertical-4 {
                 margin-top: 100px;
                 display: flex;
                 align-items: center;
                 justify-content: left;
                 float: buttom;
                 height: 40px;
                 border-radius: 20px;
             }

             .el-submenu-vertical-4:hover {
                 filter: brightness(0.9);
             }
         }
     }
 }

 .main {
     padding: 1%;
     background-color: white;
     margin-bottom: 20px;
     margin-right: 40px;
     border-radius: 30px;
 }
</style>
<style lang="scss">
// 新建按钮痰弹窗
.newButton-dialog {
    background-color: #edf2fc;
    border-radius: 20px;
    // height: 150px; //上传文件夹
    height: 100px;

    // 新建文件夹
    .newDir-btn {
        display: flex;
        align-items: center;
        justify-content: left;
        width: 100%;
        height: 50px;
        border-top-left-radius: 20px;
        border-top-right-radius: 20px;
    }

    .newDir-btn:hover {
        background-color: #e2e9fa;
        cursor: pointer;
    }

    .newDir-btn:active {
        transform: scale(0.95);
    }

    // 上传文件
    .uploadFile-btn {
        display: flex;
        align-items: center;
        justify-content: left;
        width: 100%;
        height: 50px;
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;

        .addFile-btn {
            display: flex;
            align-items: center;
            justify-content: left;
            width: 150px;
            height: 50px;
        }
    }

    .uploadFile-btn:hover {
        background-color: #e2e9fa;
        cursor: pointer;
    }

    .uploadFile-btn:active {
        transform: scale(0.95);
    }

    // 上传文件夹
    .uploadDir-btn {
        display: flex;
        align-items: center;
        justify-content: left;
        width: 100%;
        height: 50px;
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
    }

    .uploadDir-btn:hover {
        background-color: #e2e9fa;
        cursor: pointer;
    }

    .uploadDir-btn:active {
        transform: scale(0.95);
    }
}


// 个人信息气泡框 
.user-dialog {
    align-items: center;
    justify-content: center;
    background-color: #edf2fc;
    border-radius: 20px;
    height: 200px;

    .user-dialog-showUserInfo {
        display: flex;
        width: 100%;
        height: 50%;
        background-color: white;
        border-top-left-radius: 20px;
        border-top-right-radius: 20px;

        // 头像
        .showUserInfo-img {
            width: 30%;
            height: 100%;
            // display: flex;
            // align-items: center;
            // justify-content: left;

            .img-1 {
                width: 100%;
                height: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                position: relative;

                .user-img-1 {
                    width: 70px;
                    height: 70px;
                    border-radius: 50px;
                }

                .change-img {
                    position: absolute;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    right: 7px;
                    bottom: 10px;
                    width: 30px;
                    height: 30px;
                    border-radius: 50px;
                    background-color: white;

                }


                .change-img:active {
                    transform: scale(0.95);
                }
            }


        }

        // 用户信息
        .showUserInfo-userInfo {
            align-items: center;
            justify-content: left;
            margin-top: 20px;
            width: 70%;
            height: 100%;
            margin-left: 20px;
        }


    }

    .logout {
        display: flex;
        align-items: center;
        justify-content: left;
        margin-top: 10px;
        border: none;
        width: 100%;
        background-color: white;
        height: 40px;
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;

        .logout-logo {
            margin-left: 15px;
        }
    }

    .logout:hover {
        background-color: #e2e9fa;
        cursor: pointer;
    }

    .logout:active {
        transform: scale(0.95);
    }


}
</style>