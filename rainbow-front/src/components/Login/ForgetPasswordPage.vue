<template>
    <div>
        <!-- 标题 -->
        <div style="text-align: center; margin-top: 30px;">
            <div style="font-size: 20px; color:gray;">
                <b>忘记密码</b>
            </div>
        </div>
        <!-- 输入框 -->
        <div style="margin-top:30px; margin-left: 10px; margin-right: 30px;">
            <el-form :model="forgetForm" :rules="rules" ref="forgetForm" status-icon>
                <el-form-item prop="email" style="height: 40px;">
                    <el-input placeholder="电子邮箱地址" autocomplete="on" v-model="forgetForm.email"
                        prefix-icon="el-icon-s-promotion" />
                </el-form-item>
                <el-form-item prop="password" style="height: 40px;">
                    <el-input show-password placeholder="密码" autocomplete="on" v-model="forgetForm.password"
                        prefix-icon="el-icon-lock" />
                </el-form-item>
                <el-form-item prop="repassword" style="height: 40px;">
                    <el-input show-password placeholder="重复密码" autocomplete="on" v-model="forgetForm.repassword"
                        prefix-icon="el-icon-lock" />
                </el-form-item>
                <!-- 邮箱验证码 -->
                <el-form-item prop="validationCode" style="height: 40px;">
                    <el-row>
                        <el-col :span="16" style="text-align: left;">
                            <el-input tyle="text" placeholder="请输入验证码" clearable v-model="forgetForm.validationCode" />
                        </el-col>
                        <el-col :span="8" style=" text-align: right; ">
                            <button v-if="show" class="yzm-btn" @click="getCode">获取验证码</button>
                            <button v-if="!show" class="yzm-btn2" @click="getCode">{{ count }}秒后重新获取</button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item style="height: 40px;">
                    <!-- 注册按钮 -->
                    <button class="forget-btn" @click="submitForm('forgetForm')">重置密码</button>
                </el-form-item>
            </el-form>
        </div>
        <!-- 已经有账号 -->
        <el-divider>
            <span style="color:gray">已有账号？</span>
        </el-divider>
        <div>
            <el-link type="primary" @click="ForgetToLogin">立即登录</el-link>
        </div>
    </div>
</template>

<script>

export default {
    components: {},
    props: {},
    data() {
        // 检查用户名合法性
        var checkUsername = (rule, value, callback) => {
            if (value === '') {
                return callback(new Error('请输入用户名'));
            } else if (!/^[a-zA-Z0-9_]+$/.test(value)) {
                callback(new Error('用户名由数字或字母组成'));
            } else {
                callback();
            }
        };
        // 检查密码的合法性
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.forgetForm.repassword !== '') {
                    this.$refs.forgetForm.validateField('repassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.forgetForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            // 获取验证码按钮
            show: true,
            timer: null,
            count: 5,  //秒数
            // 表单信息
            forgetForm: {
                password: '',
                repassword: '',
                email: '',
                validationCode: '',  //验证码
            },
            securityCode: '',
            // 表单校验规则
            rules: {
                username: [
                    { validator: checkUsername, trigger: 'blur' },
                    { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
                ],
                password: [
                    { validator: validatePass, trigger: 'blur' },
                    { min: 4, max: 20, message: '长度在 4 到 10 个字符', trigger: 'blur' }
                ],
                repassword: [
                    { validator: validatePass2, trigger: 'blur' },
                ],
                email: [
                    { required: true, message: '请输入电子邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入合法的电子邮箱地址', trigger: ['blur', 'change'] }
                ],
                validationCode: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                ]
            },
        };
    },
    // 渲染试图前
    computed: {},
    // 渲染试图后
    methods: {
        /**
       * 跳转到登录界面
       */
        ForgetToLogin() {
            this.$router.push('/', () => { }, () => { });
        },
        /**
        * Todo:获取验证码
        * 1、初始化请求参数
        * 2、发送post请求
        * 3、邮箱接收验证码
        */
        getCode() {
            console.log("******获取验证码******");
            //初始化请求参数
            let param = {
                email: this.forgetForm.email
            }
            if (this.forgetForm.email === '') {
                this.$message({
                    message: "请输入电子邮箱",
                    type: "error"
                });
                return;
            }
            //设置验证码按钮
            this.sendTime();
            //发送post请求
            this.$axios.post('user/securityCode', param).then(value => {
                console.log(value.data);
                if (value.data.status === '0') {
                    this.securityCode = value.data.data.securityCode;
                } else {  //响应失败
                    this.$message({
                        message: "修改失败，请联系管理员",
                        type: "error"
                    });
                }
            }).catch(() => {
                this.$message({
                    message: "发生一些错误，请联系管理员",
                    type: "error"
                });
            });

        },
        sendTime() {
            //设置响应时间
            this.show = false;
            const timeCount = this.count;
            if (!this.timer) {
                this.timer = setInterval(() => {
                    //创建定时器
                    if (this.count > 1 && this.count <= timeCount) {
                        this.count--;
                    } else {
                        //清除定时器
                        clearInterval(this.timer);
                        this.timer = null;
                        this.show = true;
                        this.count = timeCount;
                    }
                }, 1000);
            }
        },
        /**
         * 验证表单
         * @param {表单} formName 
         */
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    //发送注册请求
                    this.modifyPassword();
                } else {
                    console.log('提交失败');
                    return false;
                }
            });
        },
        /**
         * 修改密码
         * 1、判断验证码是否正确
         * 2、发送注册请求
         * @param {注册表单} formName 
         */
        modifyPassword() {
            if (this.securityCode === null) {
                this.$message.error("验证码失效，请重新获取验证码");
                return;
            }
            //判断验证码是否正确
            if (this.securityCode === this.forgetForm.validationCode) {
                //发送请求
                let param = {
                    ...this.forgetForm
                }
                this.$axios.post('user/forgetPassword', param).then(value => {
                    console.log(value.data);
                    if (value.data.status === '0') {
                        this.$message({
                            message: "修改成功",
                            type: "success"
                        });
                    } else {
                        this.$message({
                            message: "修改失败，请联系管理员",
                            type: "error"
                        });
                    }
                    //销毁验证码
                    this.securityCode = null;
                }).catch(() => {
                    this.$message({
                        message: "发生一些错误，请联系管理员",
                        type: "error"
                    });
                })
            } else {
                this.$message({
                    message: "验证码错误",
                    type: "error"
                });
            }
        },
    }
}
</script>

<style lang="css" scoped>
.yzm-btn {
    padding: 11px;
    width: 100%;
    border: none;
    background-color: #425161;
    border-radius: 30px;
    color: white;
    box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
    cursor: pointer;
}

.yzm-btn:hover {
    filter: brightness(1.1);
}

.yzm-btn:active {
    transform: scale(0.97);
}

.forget-btn {
    padding-top: 10px;
    padding-bottom: 10px;
    width: 240px;
    border: none;
    background-color: #f0ae50;
    border-radius: 30px;
    color: white;
    box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
    cursor: pointer;
}

.forget-btn:hover {
    filter: brightness(1.1);
}

.forget-btn:active {
    transform: scale(0.97);
}

.yzm-btn2 {
    padding: 11px;
    width: 100%;
    border: none;
    background-color: rgb(139, 159, 179);
    border-radius: 30px;
    color: white;
    box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
    cursor: not-allowed;
}
</style>