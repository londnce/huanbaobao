<template>
  <div class="wrapper">
    <h1 style="margin-top: 100px; margin-bottom: -100px">环宝宝后台管理系统</h1>
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="admin" :rules="rules" ref="adminForm">
        <el-form-item prop="adminName">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model="admin.adminName"></el-input>
        </el-form-item>
        <el-form-item prop="adminPw">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="admin.adminPw"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        admin: {},
        rules: {
          adminName: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}
          ],
          adminPw: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      login() {
        this.$refs['adminForm'].validate((valid) => {
          if (valid) {  // 表单校验合法
            this.request.post("/admin/login", this.admin).then(res => {
              if (res.status === 200){
                localStorage.setItem("admin",JSON.stringify(res.data))    //存储管理员信息到浏览器
                this.$router.push("/")
                this.$message.success("登录成功")
              }else {
                this.$message.error(res.message)
              }
            })
          }
        });
      }
    }
  }
</script>

<style>
  .wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
    overflow: hidden;
  }
</style>
