<template>
  <div class="login">
    <el-row type="flex" justify="center">
      <el-col :xl="6" :lg="7" style="margin-right: 10px">
        <h2>华寿物流管理</h2>
        <el-image :src="require('@/assets/login.svg')" style="height: 200px;width: 200px;"></el-image>
        <h5>作者：aki</h5>
      </el-col>
      <el-col :span="1">
        <el-divider direction="vertical"></el-divider>
      </el-col>
      <el-col :xl="6" :lg="7">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px">
          <el-form-item label="用户名" prop="name">
            <el-input v-model="loginForm.name" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-input v-model="loginForm.code" style="width: 170px;float: left;" placeholder="请输入验证码"></el-input>
            <el-image :src="codeImage"
                      style="width: 75px;padding-left: 5px;float: left;border-radius: 4px;height: 40px;"
                      @click="getCodeImage"></el-image>
          </el-form-item>
          <el-form-item>
            <el-button plain type="primary" @click="registerForm('loginForm')" style="margin-left: 6px;">注册</el-button>
            <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import qs from "qs";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        name: '',
        password: '',
        code: '',
        token: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'change'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'change'},
          {min: 5, max: 5, message: '长度为 5 个字符', trigger: 'blur'}
        ]
      },
      codeImage: null
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login?' + qs.stringify(this.loginForm)).then(res => {
            console.log("登录提交了！", res)
            const jwt = res.headers['Authorization']
            console.log('JWT:', jwt)
            this.$store.commit('SET_TOKEN', jwt)
            this.$router.push('/main/index')
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    registerForm(formName) {
      this.$refs[formName].register();
    },
    getCodeImage() {
      this.$axios.get('/captcha').then(res => {
        console.log('/captcha');
        console.log(res);
        this.loginForm.token = res.data.data.token;
        this.codeImage = res.data.data.codeImage;
        this.loginForm.code = "";
      });
    }
  },
  created() {
    this.getCodeImage()
  }
}
</script>

<style scoped>
.login {
  background-image: url('../assets/bg.png');
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-width: 1000px;
  /*z-index: -10;*/
  zoom: 1;
  background-color: #fff;
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
}

.el-row {
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
}

.el-form-item {
  width: 350px;
}

.el-divider {
  height: 300px;
}
</style>

