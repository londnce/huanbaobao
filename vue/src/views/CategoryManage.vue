<template>
    <div class="product">
        <h1>分类管理</h1>
        <div style="margin: 10px 0">
           <el-row>
               <el-col>
                   <el-button size="mini" type="primary" @click="addCate">添加分类<i
                           class="el-icon-circle-plus-outline"></i>
                   </el-button>
               </el-col>
           </el-row>
        </div>
        <!--分类表格-->
        <el-table :data="tableData"
                  style="width: 100%;margin-bottom: 20px; "
                  height="500"
                  row-key="id"
                  default-expand-all
                  :tree-props="{children: 'children', hasChildren: 'is_parent'}"
                  border stripe>
            <el-table-column label="编号" prop="id"></el-table-column>
            <el-table-column label="分类名称" prop="name"></el-table-column>
            <el-table-column label="是否有效" prop="status" width="70">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status === 1" type="success">是</el-tag>
                    <el-tag v-else-if="scope.row.status === 0" type="danger">否</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="分类等级" prop="level" width="100">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.level === 1" type="primary">一级</el-tag>
                    <el-tag v-else-if="scope.row.level === 2" type="info">二级</el-tag>
                    <el-tag v-else-if="scope.row.level === 3" type="warning">三级</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createdTime"></el-table-column>
            <el-table-column label="修改时间" prop="modifiedTime"></el-table-column>
            <el-table-column label="修改人" prop="modifiedUser"></el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" @click="editCate(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-button type="danger" size="small" @click="deleteCate(scope.row)">删除<i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加分类弹框 -->
        <el-dialog :visible.sync="addVisible" title="添加分类" width="40%" :before-close="handleClose">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入分类名称"></el-input>
                </el-form-item>
                <el-form-item label="所属分类" prop="parent">
                    <el-select v-model="form.parentLevel" placeholder="请选择分类等级" @change="handleParentLevelChange">
                        <el-option label="无" value="1"></el-option>
                        <el-option label="一级分类" value="2"></el-option>
                        <el-option label="二级分类" value="3"></el-option>
                    </el-select>
                    <el-select v-model="form.parent" placeholder="请选择所属分类" v-if="showCate">
                        <el-option v-for="item in parentOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="分类状态" prop="status">
                    <el-radio-group v-model="form.status">
                        <el-radio label="1">启用</el-radio>
                        <el-radio label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="创建人" prop="createUser">
                    <el-input v-model="form.createUser" disabled autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="AddCateForm">确认</el-button>
            </div>
        </el-dialog>

        <!-- 编辑分类弹框 -->
        <el-dialog :visible.sync="editVisible" title="编辑分类" width="30%" :before-close="handleClose">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入分类名称"></el-input>
                </el-form-item>
                <el-form-item label="所属分类" prop="parent">
                    <el-cascader
                            v-model="form.parentId"
                            :options="categoryOptions"
                            :props="cascaderProps"
                            :disabled="isParent(form)"
                            change-on-select
                    ></el-cascader>
                </el-form-item>
                <el-form-item label="分类状态" prop="status">
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
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="EditCateForm">确认</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "CategoryManage",
        data(){
            return {
                admin: '',
                tableData: [],//商品分类的数据列表，默认为空
                addVisible: false,
                editVisible: false,
                showCate: false,
                cascaderProps: {
                    value: 'id',
                    label: 'name',
                    children: 'children'
                },
                form:{    //表单数据
                    id: '', //分类id
                    name: '', // 分类名称
                    parentId: '', // 父分类
                    status: '1', // 是否有效
                    level: '', //分类等级
                    parentLevel: "", //所属分类等级
                },
                rules: { // 添加/编辑分类表单验证规则
                    name: [
                        { required: true, message: '请输入分类名称', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '请选择是否有效', trigger: 'change' }
                    ]
                },
                parentOptions: [], // 所属分类选项
                categoryOptions: [],//获取所属分类选项
            }
        },
        created() {
            this.load()
        },
        watch: {
            'form.parentLevel': function(value) {
                this.handleParentLevelChange(value);
            }
        },
        methods: {
            //获取商品分类数据
            load() {
               request.get("/admin/category/list").then(res=>{
                   /*循环更改日期格式*/
                   for (let i in res.data) {
                       res.data[i].createdTime = this.dateFormat(res.data[i].createdTime);
                       res.data[i].modifiedTime = this.dateFormat(res.data[i].modifiedTime);
                       for (let j in res.data[i].children){
                           res.data[i].children[j].createdTime = this.dateFormat(res.data[i].children[j].createdTime);
                           res.data[i].children[j].modifiedTime = this.dateFormat(res.data[i].children[j].modifiedTime);
                           for (let a in res.data[i].children[j].children){
                               res.data[i].children[j].children[a].createdTime = this.dateFormat(res.data[i].children[j].children[a].createdTime);
                               res.data[i].children[j].children[a].modifiedTime = this.dateFormat(res.data[i].children[j].children[a].modifiedTime);
                           }
                       }
                   }
                   this.tableData = res.data
               })
            },
            // 打开添加分类弹框
            addCate(){
                this.addVisible = true; // 显示弹框
                // console.log(JSON.parse(localStorage.getItem("admin")))
                this.form.createUser = JSON.parse(localStorage.getItem("admin")).adminName;
            },
            // 关闭弹框时重置表单
            handleClose(done) {
                this.$refs.form.resetFields();
                done();
            },
            // 处理所属分类等级变化
            handleParentLevelChange(level) {
                // 如果是选择无，则清空所属分类选项
                if (level === "1") {
                        this.parentOptions = [];
                        this.showCate = false
                }
                // 如果是选择一级分类，则直接显示一级分类选项
                else if (level === "2") {
                    this.showCate = true
                    request.get("/admin/category/level?level=1").then((res) => {
                        this.parentOptions = res.data;
                        console.log(res)
                    });
                }
                // 如果是选择二级分类，则直接显示二级分类选项
                else if (level === "3"){
                    this.showCate = true
                    request.get("/admin/category/level?level=2").then((res) => {
                        this.parentOptions = res.data;
                        console.log(res)
                    });
                }
            },
            // 打开编辑分类弹框
            editCate(row){
                request.get("/admin/category/level?level="+(row.level-1)).then(res=>{
                    this.categoryOptions = res.data;
                    // 根据选中的分类展示该分类的详情
                    this.form.id = row.id;
                    this.form.name = row.name;
                    this.form.parentId = row.parentId;
                    console.log(row.parentId)
                    this.form.status = row.status;
                    this.form.modifiedUser = JSON.parse(localStorage.getItem("admin")).adminName;
                    // 当所选分类有子分类时，所属分类选项应该为不可选状态
                    this.form.parentIdDisabled = this.isParent(row);

                    this.editVisible = true // 显示弹框
                })
            },
            //VUE日期转换
            dateFormat: function (time) {
                var date = new Date(time);
                var year = date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                    * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                    * */
                var month =
                    date.getMonth() + 1 < 10
                        ? "0" + (date.getMonth() + 1)
                        : date.getMonth() + 1;
                var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                // 拼接
                return year + "-" + month + "-" + day;
            },
            // 根据id删除该分类
            deleteCate(row){
                this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    var id = row.id
                    console.log(id)
                    request.delete("/admin/category/delete/"+id).then(res=>{
                        if (res.status === 200){
                            this.$message.success("删除分类成功")
                            this.load()
                        }else {
                            this.$message.error(res.message || "删除失败,请重试！")
                        }
                    })
                })
            },
            //添加确认
            AddCateForm(){
                request.post('/admin/category/addCate',{
                        name: this.form.name,
                        parentId: this.form.parent,
                        status: this.form.status,
                        level: this.form.parentLevel,
                        createTime: this.form.createTime,
                        createUser: JSON.parse(localStorage.getItem("admin")).adminName,
                        modifiedUser: JSON.parse(localStorage.getItem("admin")).adminName,
                    })
                    .then((res) => {
                        if (res.status === 200) {
                            this.$message.success('添加成功');
                            this.addVisible = false
                            this.load()
                        } else {
                            this.$message.error(res.msg || '添加失败');
                        }
                    })
            },
            //编辑确认
            EditCateForm(){
                request.post('/admin/category/update', {
                    id: this.form.id,
                    name: this.form.name,
                    parentId: this.form.parentId[0],
                    status: this.form.status,
                    level: this.form.level,
                    modifiedTime: this.form.modifiedTime,
                    modifiedUser: JSON.parse(localStorage.getItem("admin")).adminName,
                }).then(res => {
                        if(res.status === 200){
                            this.$message.success('编辑成功')
                            this.editVisible = false
                            this.load()
                        }else{
                            this.$message.error(res.msg || '编辑失败，请重试!')
                        }
                    })
            },
            //判断是否是父分类
            isParent(category) {
                return category.children && category.children.length > 0;
            }
        }
    }
</script>
