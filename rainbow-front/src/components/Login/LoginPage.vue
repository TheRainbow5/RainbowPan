<template>
  <div>
    <div style="text-align: center; margin-top: 50px;">
      <img src="@/assets/logo.png">
      <div style="font-size: 20px; color:gray;">
        <b>Rainbow云端硬盘</b>
      </div>
    </div>
    <!-- 输入框 -->
    <div style="margin-top:50px; margin-left: 50px; margin-right: 50px;">
      <el-form :model="loginForm" :rules="rules" ref="loginForm" status-icon>
        <el-form-item prop="email" style="height: 40px;">
          <el-input tyle="text" placeholder="邮箱" v-model="loginForm.email" prefix-icon="el-icon-user" />
        </el-form-item>
        <el-form-item prop="password" style="height: 40px;">
          <el-input show-password placeholder="密码" v-model="loginForm.password" prefix-icon="el-icon-lock" />
        </el-form-item>
      </el-form>
    </div>
    <!-- 记住我 -->
    <div style="margin-top:10px; margin-left: 50px; margin-right: 50px;">
      <el-row>
        <el-col :span="12" style="text-align: left; ">
          <el-checkbox v-model="loginForm.remember" label="记住密码" />
        </el-col>
        <el-col :span="12" style=" text-align: right;   ">
          <el-link @click="forgetPassword">忘记密码?</el-link>
        </el-col>
      </el-row>
    </div>
    <!-- 登录和注册框 -->
    <div style="margin-top: 40px;">
      <button class="login-btn" @click="login">立即登录</button>
    </div>
    <el-divider>
      <span style="color:gray">没有账号 </span>
    </el-divider>
    <div style="margin-top: 40px;">
      <button class="register-btn" @click="register">注册账号</button>
    </div>
  </div>
</template>

<script >

export default {
  components: {},
  props: {},
  data() {
    return {
      // 登录表单
      loginForm: {
        email: '',
        password: '',
      },
      rules: {
        email: [
          { required: true, message: '请输入电子邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入合法的电子邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      },
    };
  },
  //页面结构渲染后
  mounted() {

  },
  // 定义方法
  methods: {
    /**
     * 登录
     * 1、判断表单是否填写完全
     *  a、没有，则提示
     *  b、有，发送Post请求
     * 2、判断是否登录成功
     *  a、成功，跳转到首页
     *  b、失败，显示提示信息
     */
    login() {
      if (!this.loginForm.email || !this.loginForm.password) {
        this.$message({
          message: "请输入邮箱和密码",
          type: "warning"
        });
      } else {
        // 设置请求参数
        let param = {
          email: this.loginForm.email,
          password: this.loginForm.password
        };
        // 发送请求
        this.$axios.post('user/login', param).then(value => {
          //判断是否登录成功
          if (value.data.status === '0') {
            //保存token到本地
            localStorage.setItem('token', value.data.data.token);
            //保存email到本地
            localStorage.setItem('email', this.loginForm.email);
            //跳转到首页
            this.$router.push('/index');
          }
          else {
            //说明没有注册账号，提示没有该账号
            this.$message({
              message: "邮箱或密码错误，请重新输入",
              type: "warning"
            });
          }
        })
          .catch(() => {
            this.$message({
              message: "发生一些错误，请联系管理员",
              type: "error"
            });
          })
      }
    },
    /**
     * 注册
     */
    register() {
      //跳转到注册界面
      this.$router.push('/welcome-registerPage', () => { }, () => { });
    },
    /**
     * 忘记密码
     */
    forgetPassword() {
      this.$router.push('/welcome-forgetPasswordPage', () => { }, () => { });
    },
  }
};
</script>

<style lang="scss">
.login-btn {
  padding-top: 10px;
  padding-bottom: 10px;
  width: 240px;
  border: none;
  background-color: #425161;
  border-radius: 30px;
  color: white;
  box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
  cursor: pointer;
}

.login-btn:hover {
  filter: brightness(1.1);
}

.login-btn:active {
  transform: scale(0.97);
}

.register-btn {
  padding-top: 10px;
  padding-bottom: 10px;
  width: 240px;
  border: none;
  background-color: #d89739;
  border-radius: 30px;
  color: white;
  box-shadow: 0 4px 4px rgba(127, 127, 127, .3);
  cursor: pointer;
}

.register-btn:hover {
  filter: brightness(1.1);
}

.register-btn:active {
  transform: scale(0.97);
}
</style>