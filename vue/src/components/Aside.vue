<template>
    <el-menu router style="min-height: 100vh; overflow-x: hidden;"
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             class="el-menu-vertical-demo">
        <h3 style="text-align:center;color:white;height:60px;line-height:60px">
            <img src="../assets/logo.png" style="width:20px;position:relative;top:3px;margin-right:5px">
            <span>环宝宝后台管理系统</span>
        </h3>
        <el-menu-item index="/">
            <template slot="title"><i class="el-icon-house"></i>主页</template>
        </el-menu-item>
        <el-submenu index="1">
            <template slot="title"><i class="el-icon-user"></i>用户模块</template>
            <el-menu-item index="/userManage">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-user-solid"></i>用户管理
                </div>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="2">
            <template slot="title"><i class="el-icon-goods"></i>商品模块</template>
            <el-menu-item index="/productManage">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-s-unfold"></i>商品管理
                </div>
            </el-menu-item>
            <el-menu-item index="/categoryManage">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-folder"></i>分类管理
                </div>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title"><i class="el-icon-s-order"></i>订单模块</template>
            <el-menu-item index="/orderManage">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-s-claim"></i>订单管理
                </div>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="4">
            <template slot="title"><i class="el-icon-finished"></i>数据统计</template>
            <el-menu-item index="/dataManage">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-s-data"></i>数据报表
                </div>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="5">
            <template slot="title"><i class="el-icon-set-up"></i>系统管理</template>
            <el-menu-item index="/personal">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-s-custom"></i>管理员个人信息
                </div>
            </el-menu-item>
            <el-menu-item index="/editPw">
                <div style="position: relative; left: 20px;">
                    <i class="el-icon-key"></i>管理员修改密码
                </div>
            </el-menu-item>
        </el-submenu>
    </el-menu>
</template>

<script>
    export default {
        name: "Aside"
    }
</script>

<style scoped>

</style>