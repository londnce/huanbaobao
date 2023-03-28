<template>
    <div style="color: #666;font-size: 14px;">
        <div style="padding-bottom: 20px">
            <b>欢迎你！{{ admin.adminName }}</b>
        </div>
        <el-card>
            欢迎登录
            <el-divider />
            兔年吉祥，万事如意<br><br>
            如果问题请联系开发人员：londnce，微信：londnce
        </el-card>
        <div style="height: 1px; background: #ddd; margin: 20px 0"/>
        <el-row :gutter="30">
            <el-col :span="12">
                <div style="padding: 20px 0; font-size: 20px">环宝宝后台管理系统</div>
                <div>
                    这是专门为环宝宝商城设计的后台管理系统，代码简单，结构清晰。
                </div>
<!--                <div class="m-10">-->
<!--                    <el-button type="primary"><i class="el-icon-link"></i> <a style="color: #fff" href="https://gitee.com/xqnode/pure-design">Gitee源码</a></el-button>-->
<!--                </div>-->
            </el-col>
            <el-col :span="12">
                <div style="padding: 20px 0; font-size: 20px">技术栈</div>
                <el-row>
                    <el-col :span="12" style="line-height: 30px">
                        <div><b>后端</b></div>
                        <div>SpringBoot2</div>
                        <div>Hutool</div>
                        <div>Poi</div>
                        <div>Lombok</div>
                        <div>Mybatis/Mybatis-plus</div>
                    </el-col>
                    <el-col :span="12" style="line-height: 30px">
                        <b>前端</b>
                        <div>ajax</div>
                        <div>Vue2</div>
                        <div>Vue-Router</div>
                        <div>VueX</div>
                        <div>ElementUI</div>
                        <div>Axios</div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}
            }
        }
    }
</script>
