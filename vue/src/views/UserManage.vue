<template>
    <div class="user">
      <h1>用户管理</h1>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="username"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" style="margin-top:50px">
            <el-table-column prop="uid" label="uid" width="100">
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="120">
            </el-table-column>
            <el-table-column prop="gender" label="性别" width="80">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="operate" label="操作" width="300" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            style="margin-left: 5px"
                            confirm-button-text='确认'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除此用户？"
                            @confirm="deleteUser(scope.row.uid)"
                    >
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible"  width="400px">
            <el-form label-width="100px">
                <el-form-item label="电话">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="form.gender" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="edit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<style>
    .headerBg {
        background-color: #cccccc;
    }
</style>

<script>
import request from "../utils/request";

export default {
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                username: "",
                dialogFormVisible: false,
                form: {},
            }
        },
        created() {
            //请求分页查询数据
            this.load()
        },
        methods: {
            load(){
                request.get("/admin/user/page?" ,{
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        username: this.username
                    }
                })
                    .then(res=>{
                    console.log(res)
                        this.tableData = res.data.data
                        this.total = res.data.total
                })
            },
            handleSizeChange(pageSize) {
                console.log(`每页 ${pageSize} 条`)
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                console.log(`当前页: ${pageNum}`)
                this.pageNum = pageNum
                this.load()
            },
            handleEdit(row){
                this.form = row
                this.dialogFormVisible = true
            },
            edit(){
                request.put("/admin/user/edit",this.form).then(res => {
                    if (res) {
                        this.$message.success("编辑成功")
                        this.dialogFormVisible = false
                        this.load()
                    }else {
                        this.$message.error("编辑失败")
                    }
                })
            },
            cancel(){
                this.dialogFormVisible = false
                this.load()
            },
            deleteUser(uid){
                request.delete("/admin/user/delete/"+uid).then(res=>{
                    if (res){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
        },
    }
</script>