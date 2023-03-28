<template>
    <div class="product">
        <h1>商品管理</h1>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入商品" v-model="title"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button size="mini" type="primary" @click="addPro">新增<i
                    class="el-icon-circle-plus-outline"></i>
            </el-button>
        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" style="margin-top:15px">
            <el-table-column prop="id" label="编号" width="80" align="center">
            </el-table-column>
            <el-table-column prop="title" label="商品名称" width="150" align="center">
            </el-table-column>
            <el-table-column prop="categoryThreeName" label="分类名称" width="150">
            </el-table-column>
            <el-table-column prop="price" label="商品价格" width="70" align="center">
            </el-table-column>
            <el-table-column prop="num" label="商品库存" width="70" align="center">
            </el-table-column>
            <el-table-column prop="status" label="商品状态" width="50" align="center">
            </el-table-column>
            <el-table-column prop="createdTime" label="创建时间" width="150" align="center">
            </el-table-column>
            <el-table-column prop="modifiedTime" label="修改时间" width="150" align="center">
            </el-table-column>
            <el-table-column prop="modifiedUser" label="修改人" width="100" align="center">
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <div>
                        <el-button size="mini" type="success" @click="edit(scope.row)">编辑<i
                                class="el-icon-edit"></i>
                        </el-button>
                        <el-popconfirm
                                style="margin-left: 5px"
                                confirm-button-text='确认'
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                title="确定删除此商品？"
                                @confirm="del(scope.row.id)">
                            <el-button size="mini" type="danger" slot="reference" @click="del(scope.row)">删除<i
                                    class="el-icon-remove-outline"></i>
                            </el-button>
                        </el-popconfirm>
                    </div>
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

        <!--编辑弹框-->
        <el-dialog title="商品信息" :visible.sync="dialogFormVisible" width="40%" :before-close="handleClose">
            <el-form label-width="140px" size="small" style="width: 85%;" :model="form">
                <el-form-item prop="title" label="商品名称">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item prop="price" label="商品价格">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item prop="itemType" label="商品系列">
                    <el-input v-model="form.itemType"></el-input>
                </el-form-item>
                <el-form-item prop="num" label="商品库存">
                    <el-input v-model="form.num"></el-input>
                </el-form-item>
                <el-form-item label="商品状态" prop="status">
                    <el-radio-group v-model="form.status">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="修改人" prop="modifiedUser">
                    <el-input v-model="form.modifiedUser" disabled autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "ProductManage",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                dialogFormVisible: false,
                form: {},
            }
        },
        created() {
            //请求分页查询数据
            this.load()
        },
        methods: {
            load() {
                request.get("/admin/product/page?", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        title: this.title
                    }
                })
                    .then(res => {
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
            edit(row) {
                this.form = row
                this.form.modifiedUser = JSON.parse(localStorage.getItem("admin")).adminName;
                this.dialogFormVisible = true
            },
            // 关闭弹框时重置表单
            handleClose(done) {
                this.$refs.form.resetFields();
                done();
            },
            cancel() {
                this.dialogFormVisible = false
                this.load()
            },
            del(id) {
                request.delete("/admin/product/delete/" + id).then(res => {
                    if (res) {
                        this.$message.success("删除成功")
                        this.load()
                    } else {
                        this.$message.error("删除失败")
                    }
                })
            },
            addPro(){
                this.$router.push("/productManage/addPro")
            },
            save(){
                request.put("/admin/product/edit", this.form).then(res => {
                    if (res) {
                        this.$message.success("编辑成功")
                        this.dialogFormVisible = false
                        this.load()
                    } else {
                        this.$message.error("编辑失败")
                    }
                })
            },
        }
    }
</script>

<style>
    .headerBg {
        background-color: #cccccc;
    }
</style>