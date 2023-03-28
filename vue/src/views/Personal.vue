<template>
    <el-card style="width: 500px;">
        <el-form label-width="100px" size="small">
            <el-form-item label="管理员账号">
                <el-input v-model="form.adminName" disabled autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="管理员邮箱">
                <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="edit">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    import {serverIp} from "../../public/config";

    export default {
        name: "Personal",
        data() {
            return {
                serverIp: serverIp,
                form: {},
                admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}
            }
        },
        created() {
            this.getAdmin().then(res => {
                console.log(res)
                this.form = res
            })
        },
        methods: {
            async getAdmin() {
                return (await this.request.get("/admin/" + this.admin.adminName)).data
            },
            edit(){
                this.request.put("/admin/editAdmin",this.form).then(res => {
                    if (res) {
                        this.$message.success("编辑成功")
                        this.load()
                        this.$emit('refreshuAdmin')
                    }else {
                        this.$message.error("编辑失败")
                    }
                })
            },
        }
    }
</script>