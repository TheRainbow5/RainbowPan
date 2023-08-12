<template>
    <div style="width: 100%; height:100%;">
        <!-- 添加新建和上传等按钮 -->
        <div style="width: 100%; height:10%; background-color:blanchedalmond"></div>

        <!-- 显示文件 -->
        <div style="width: 100%; height:90%;  margin-top: 10px;">
            <!-- 内容 -->
            <el-row :gutter="20">
                <el-col :span="8" v-for="colTtem in colItems" :key="colTtem.id">
                    <div class="file-btn">
                        <svg t="1691739427959" class="dir" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="18064" width="30" height="30">
                            <path
                                d="M855.04 385.024q19.456 2.048 38.912 10.24t33.792 23.04 21.504 37.376 2.048 54.272q-2.048 8.192-8.192 40.448t-14.336 74.24-18.432 86.528-19.456 76.288q-5.12 18.432-14.848 37.888t-25.088 35.328-36.864 26.112-51.2 10.24l-567.296 0q-21.504 0-44.544-9.216t-42.496-26.112-31.744-40.96-12.288-53.76l0-439.296q0-62.464 33.792-97.792t95.232-35.328l503.808 0q22.528 0 46.592 8.704t43.52 24.064 31.744 35.84 12.288 44.032l0 11.264-53.248 0q-40.96 0-95.744-0.512t-116.736-0.512-115.712-0.512-92.672-0.512l-47.104 0q-26.624 0-41.472 16.896t-23.04 44.544q-8.192 29.696-18.432 62.976t-18.432 61.952q-10.24 33.792-20.48 65.536-2.048 8.192-2.048 13.312 0 17.408 11.776 29.184t29.184 11.776q31.744 0 43.008-39.936l54.272-198.656q133.12 1.024 243.712 1.024l286.72 0z"
                                p-id="18065" fill="#8a8a8a"></path>
                        </svg>
                        <div class="dir-content">
                            {{ colTtem }}
                        </div>

                        <!-- todo:弹窗 -->
                        <el-popover popper-class="operation-dialog" :visible-arrow="false" placement="bottom" width="150"
                            trigger="click">
                            <!-- 下载 -->
                            <div class="download-btn">

                            </div>
                            <!-- 重命名 -->
                            <div class="resetname-btn">

                            </div>

                            <button class="dir-btn" slot="reference">
                                <i class="el-icon-s-operation"></i>
                            </button>
                        </el-popover>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
export default {
    components: {},
    props: {},
    data() {
        return {
            //数组大小=16
            colItems: [],
            email: '',
        };
    },
    //监听器
    watch: {},

    computed: {


    },
    // 渲染试图前
    created() {
        this.email = localStorage.getItem('email');  //获取邮箱
        //初始化获取创建的文件
        // this.getFiles();
    },
    // 渲染试图后
    methods: {
        // 跳转到最近使用界面
        /**
         * 获取所有文件
         */
        getFiles() {
            let param = {
                email: this.email,
            };
            this.$axios.post('',).thne(value => {

            }).catch(() => {

            });
        },
    },
};
</script>

<style lang="scss" scoped>
.el-row {
    height: 15%;

    &:last-child {
        margin-bottom: 0;
    }
}

.el-col {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    margin-top: 30px;
    border-radius: 20px;

    .file-btn {
        display: flex;
        // 垂直居中
        align-items: center;
        // 水平位置
        justify-content: left;
        border: none;
        width: 90%;
        height: 80%;
        background-color: #f2f6fc;
        border-radius: 15px;

        .dir {
            width: 20%;
        }

        .dir-content {
            display: flex;
            // 垂直居中
            align-items: center;
            height: 100%;
            width: 60%;
            margin-left: 20px;
        }

        .operation-dialog {
            background-color: #edf2fc;
            border-radius: 20px;
            height: 150px;

        }

        .dir-btn {
            display: flex;
            // 垂直居中
            align-items: center;
            // 水平位置
            justify-content: center;
            width: 30px;
            height: 32px;
            border-radius: 50px;
            border: none;
            background-color: #f2f6fc;

            .el-icon-s-operation {
                display: flex;
                // 垂直居中
                align-items: center;
                justify-content: center;
                width: 100%;
                height: 40px;
                border-radius: 50px;
            }

        }

        .dir-btn:hover {
            cursor: pointer;
            background-color: #d3d9e3;
        }

        .dir-btn:active {
            transform: scale(0.95);
        }

    }

    .file-btn:hover {
        filter: brightness(0.95);
    }

}
</style>