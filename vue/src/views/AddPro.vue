<template>
    <div style="margin-top: 60px;margin-left:80px;width: 600px" align="center" >
        <span>商品信息</span>
        <el-form :model="ruleForm" :rules="rules" style="margin-top: 20px" ref="ruleForm" label-width="100px">
            <el-form-item label="商品名称" prop="title">
                <el-input v-model="ruleForm.title"></el-input>
            </el-form-item>
            <el-form-item label="商品图片" prop="image">
                <el-upload
                        action= '/admin/upload'
                        accept=".jpg,.png"
                        v-model = ruleForm.image
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        multiple
                        :limit="3"
                        :on-exceed="handleExceed"
                        :file-list="fileList"
                        list-type="picture">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
            </el-form-item>
            <el-form-item
                    label="商品价格"
                    prop="price"
                    :rules="[{ required: true, message: '价格不能为空'},
                             { type: 'number', message: '价格必须为数字值'}]"
            >
                <el-input v-model.number="ruleForm.price"></el-input>
            </el-form-item>
            <el-form-item
                    label="商品库存"
                    prop="num"
                    :rules="[{ required: true, message: '库存不能为空'},
                             { type: 'number', message: '库存必须为数字值'}]"
            >
                <el-input v-model.number="ruleForm.num"></el-input>
            </el-form-item>
            <el-form-item label="一级分类" prop="categoryleveloneId">
                <el-select style="width:500px;" v-model="ruleForm.categoryleveloneId" @change="change1" placeholder="请选择">
                    <el-option
                            v-for="item in options1"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="二级分类" prop="categoryleveltwoId">
                <el-select style="width:500px;" v-model="ruleForm.categoryleveltwoId" @change="change2" placeholder="请选择">
                    <el-option
                            v-for="item in options2"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="三级分类" prop="categorylevelthreeId">
                <el-select style="width:500px;" v-model="ruleForm.categorylevelthreeId" placeholder="请选择">
                    <el-option
                            v-for="item in options3"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
                <el-button type="warning" @click="resetForm('ruleForm')">重置</el-button>
                <el-button type="info" @click="returnForm('ruleForm')">返回</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "AddPro",
        data() {
            return {
                ruleForm: {
                    name: '',
                    image: '',
                    price: '',
                    num: '',
                    categoryleveloneId: '',
                    categoryleveltwoId: '',
                    categorylevelthreeId: ''
                },
                rules: {
                    title: [
                        { required: true, message: '商品名称不能为空', trigger: 'change' }
                    ],
                    categoryleveloneId: [
                        { required: true, message: '一级分类不能为空', trigger: 'blur' }
                    ],
                    categoryleveltwoId: [
                        { required: true, message: '二级分类不能为空', trigger: 'blur' }
                    ],
                    categorylevelthreeId: [
                        { required: true, message: '三级分类不能为空', trigger: 'blur' }
                    ]
                },
                options1: '',
                options2: '',
                options3: ''
            };
        },
        created() {
            request.get('/admin/category/init').then(res => {
                console.log(res.data)
                this.options1 = res.data
            })
        },
        methods: {
            change1(){
                let _this = this
                request.get('/admin/category/children/1/'+this.ruleForm.categoryleveloneId).then(function (resp) {
                    _this.options2 = resp.data
                    _this.ruleForm.categoryleveltwoId = ''
                    _this.options3 = ''
                    _this.ruleForm.categorylevelthreeId = ''
                })
            },
            change2(){
                let _this = this
                request.get('/admin/category/children/2/'+this.ruleForm.categoryleveltwoId).then(function (resp) {
                    _this.options3 = resp.data
                    _this.ruleForm.categorylevelthreeId = ''
                })
            },
            submitForm(formName) {
                let _this = this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        request.post('/admin/product/save',_this.ruleForm).then(function (resp) {
                            if(resp.data){
                                _this.$alert('【'+_this.ruleForm.name+'】添加成功', '', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        _this.$router.push('/productManage')
                                    }
                                });
                            }
                        })
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            returnForm(){
                this.$router.go(-1);
            },
        }
    }
</script>

<style scoped>

</style>