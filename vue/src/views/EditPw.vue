<template>
    <el-card style="width: 500px;">
        <el-form label-width="120px" size="small" :model="form" :rules="rules" ref="pass">

            <el-form-item label="原密码" prop="adminPw">
                <el-input v-model="form.adminPw" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPw">
                <el-input v-model="form.newPw" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="confirmPassword">
                <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="edit">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    export default {
        name: "EditPw",
        data() {
            return {
                form: {},
                admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
                rules: {
                    adminPw: [
                        { required: true, message: '请输入原密码', trigger: 'blur' },
                        { min: 3, message: '长度不少于3位', trigger: 'blur' }
                    ],
                    newPw: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { min: 3, message: '长度不少于3位', trigger: 'blur' }
                    ],
                    confirmPassword: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 3, message: '长度不少于3位', trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            this.form.adminName = this.admin.adminName
        },
        methods: {
            edit() {
                this.$refs.pass.validate((valid) => {
                    if (valid) {
                        if (this.form.newPw !== this.form.confirmPassword) {
                            this.$message.error("2次输入的新密码不相同")
                            return false
                        }
                        this.request.post("/admin/editPw", this.form).then(res => {
                            if (res.status === 200) {
                                this.$message.success("修改成功")
                                this.$store.commit("logout")
                            } else {
                                this.$message.error("修改失败")
                            }
                        })
                    }
                })
            },
        }
    }
</script>

<style>
    .avatar-uploader {
        text-align: center;
        padding-bottom: 10px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 138px;
        height: 138px;
        line-height: 138px;
        text-align: center;
    }
    .avatar {
        width: 138px;
        height: 138px;
        display: block;
    }
</style>
