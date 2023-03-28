<template>
  <div>
    <el-container class="home_container" style="min-height:100vh">
    <el-container>
      <el-aside width="200px" style="box-shadow:2px 0 6px rgb(0 21 41 / 35%)">
        <Aside />
      </el-aside>
    <el-container>
      <el-header style="text-align: right; background-color: aliceblue;">
        <Header :admin="admin"/>
      </el-header>
      <el-main>
        <router-view @refreshuAdmin="getAdmin"/>
      </el-main>
    </el-container>
    </el-container>
  </el-container>
  </div>
</template>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>

<script>
import Aside from "../components/Aside";
import Header from "../components/Header";
export default {
  data(){
    return{
      admin: {}
    }
  },
  created() {
    //从后台获取最新的admin数据
    this.getAdmin()
  },
  components: {
    Header,
    Aside,
  },
  methods: {
    //获取管理员的最新数据
    getAdmin() {
      let adminName = localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")).adminName : ""
      if (adminName){
        //从后台获取管理员数据
        this.request.get("/admin/adminName"+adminName).then(res=>{
          //重新赋值后台的最新admin数据
          this.admin = res.data
        })
      }
    }
  }
}
</script>

