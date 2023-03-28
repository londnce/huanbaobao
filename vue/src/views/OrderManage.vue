<template>
    <div class="order">
        <h1>订单管理</h1>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入联系人" v-model="recvName"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
        </div>
        <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
            <el-table-column type="index" label="序号" width="50"></el-table-column>
            <el-table-column prop="orderTime" label="日期" width="150"></el-table-column>
            <el-table-column label="订单信息">
                <el-table-column prop="recvName" label="姓名/联系人" width="120"></el-table-column>
                <el-table-column label="收货地址">
                    <el-table-column prop="provinceName" label="省份" width="120"></el-table-column>
                    <el-table-column prop="cityName" label="市区" width="120"></el-table-column>
                    <el-table-column prop="address" label="详细地址" width="150"></el-table-column>
                    <el-table-column prop="phone" label="联系电话" width="100"></el-table-column>
                    <el-table-column prop="title" label="商品" width="200"></el-table-column>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="status" label="订单状态" width="100">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status === -1" type="danger">已取消</el-tag>
                    <el-tag v-else-if="scope.row.status === 0" type="info">未支付</el-tag>
                    <el-tag v-else-if="scope.row.status === 1" type="success">已支付</el-tag>
                    <el-tag v-else-if="scope.row.status === 2" type="warning">未收货</el-tag>
                    <el-tag v-else-if="scope.row.status === 3" type="success">已收货</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" @click="editOrder(scope.row)">发货<i class="el-icon-edit"></i></el-button>
                    <el-button type="danger" size="small" @click="cancelOrder(scope.row)">取消<i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--分页区域-->
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[ 5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name: "OrderManage",
        data(){
            return{
                tableData: [], //订单的数据列表，默认为空
                from: {}, //表单数据
                total: 0, //数据条数
                pageNum: 1, //偏移量
                pageSize: 5, //分页大小
                recvName: '', //联系人
            }
        },
        created() {
            this.load()
        },
        methods: {
            //获取订单列表数据
            load(){
                request.get("/admin/order/list",{
                    params:{
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        recvName: this.recvName,
                    }
                })
                    .then((res)=>{
                    if (res.status === 200){
                        /*循环更改日期格式*/
                        for (let i in res.data.list) {
                            res.data.list[i].orderTime = this.dateFormat(res.data.list[i].orderTime);
                        }
                        console.log(res)
                        this.tableData = res.data.list
                        this.total = res.data.total
                    }
                })
            },
            //显示每页大小
            handleSizeChange(pageSize) {
                console.log(`每页 ${pageSize} 条`)
                this.pageSize = pageSize
                this.load()
            },
            //显示当前页码
            handleCurrentChange(pageNum) {
                console.log(`当前页: ${pageNum}`)
                this.pageNum = pageNum
                this.load()
            },
            //发货
            editOrder(){

            },
            //取消订单
            cancelOrder(){

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
        },
    }
</script>
