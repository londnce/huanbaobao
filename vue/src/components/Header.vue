<template>
    <div style="line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 20px;">
            <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-top: 24px">
                <el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 70px; cursor: pointer">
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><span style="text-decoration: none" @click="logout">退出登录</span></el-dropdown-item>
            </el-dropdown-menu>
            <span>{{admin.adminName}}</span>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
                admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
            }
        },
        computed: {
          currentPathName(){
              return this.$store.state.currentPathName;
          }
        },
        watch: {
            currentPathName(newVal, oldVal) {
                console.log(newVal)
            },
        },
        methods: {
            logout(){
                this.$router.push("/login")
                localStorage.removeItem("admin")
                this.$message.success("退出成功")
            },
        }
    }
</script>

