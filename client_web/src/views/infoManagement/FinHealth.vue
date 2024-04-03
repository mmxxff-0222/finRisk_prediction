<template>
  <div>
    <el-container>
      <!--侧边栏-->
      <el-aside style="width: 200px; min-height: 100vh ">
        <div
            style="height: 60px;background-color: yellowgreen;color: antiquewhite;display: flex; align-items: center;justify-content: center">
          logo
        </div>
        <el-menu :default-active="$route.path" router>
          <el-menu-item index="/home">
            <template>
              <i class="el-icon-s-home"></i>
              <span>首页</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/userProfile">
            <template>
              <i class="el-icon-pie-chart"></i>
              <span>用户画像</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/riskReport">
            <template>
              <i class="el-icon-warning-outline"></i>
              <span>风险报告</span>
            </template>
          </el-menu-item>
          <el-menu-item index="/webCrawler">
            <template>
              <i class="el-icon-aim"></i>
              <span>爬虫补充</span>
            </template>
          </el-menu-item>
          <el-submenu>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/accountManagement">
              <template>
                <i class="el-icon-user"></i>
                <span>账号管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/loanInfo">
              <template>
                <i class="el-icon-money"></i>
                <span>借款信息</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/personInfo">
              <template>
                <i class="el-icon-tickets"></i>
                <span>个人信息</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/finHealth">
              <template>
                <i class="el-icon-s-finance"></i>
                <span>财务健康</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <!--      头部区域-->
        <el-header style="height: 60px;background-color: AliceBlue;display: flex; align-items:center ">
          <el-breadcrumb style="text-size-adjust: revert" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>金融健康</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex:1;width: 0;display: flex;align-items: center;justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px;padding: 5px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex;align-items: center; cursor: default">
                <img src="@/assets/logo/touxiang.png" style="width: 40px; height: 40px">
                <span style="padding: 5px" v-model="user">{{ user }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>
        <!--      主体区域-->
        <el-main style="background-color: cornflowerblue; display: flex;flex-direction: column">
          <el-card style="flex: 1" shadow="always">
            <div slot="header">
              <span style="font-size: large">金融健康信息管理</span>
              <hr>
            </div>
            <div>
              <el-table
                  :data="tableData.filter(data => !search || data.loanID.toLowerCase().includes(search.toLowerCase()))"
                  border>
                <el-table-column prop="userID" label="用户ID" align="center"></el-table-column>
                <el-table-column prop="dti" label="dti" width="70" align="center"></el-table-column>
                <el-table-column prop="last_pymnt_amnt" label="last_pymnt_amnt" width="120" align="center"></el-table-column>
                <el-table-column prop="open_acc" label="open_acc" width="80" align="center"></el-table-column>
                <el-table-column prop="pub_rec" label="pub_rec" width="75" align="center"></el-table-column>
                <el-table-column prop="revol_bal" label="revol_bal" width="75" align="center"></el-table-column>
                <el-table-column prop="revol_util" label="revol_util" width="75" align="center"></el-table-column>
                <el-table-column prop="tot_cur_bal" label="tot_cur_bal" width="90" align="center"></el-table-column>
                <el-table-column prop="total_acc" label="total_acc" width="75" align="center"></el-table-column>
                <el-table-column prop="total_pymnt" label="total_pymnt" width="90" align="center"></el-table-column>
                <el-table-column prop="acc_now_delinq" label="acc_now_delinq" width="115" align="center"></el-table-column>
                <el-table-column prop="delinq_2yrs" label="delinq_2yrs" width="90" align="center"></el-table-column>
                <el-table-column prop="total_rec_late_fee" label="total_rec_late_fee" width="130" align="center"></el-table-column>
                <el-table-column prop="tot_coll_amt" label="tot_coll_amt" width="100" align="center"></el-table-column>
                <el-table-column
                    label="操作"
                    width="200"
                    align="center">
                  <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">Edit
                    </el-button>
                    <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">Delete
                    </el-button>
                  </template>
                </el-table-column>

                <!-- 添加其他表格列 -->
              </el-table>
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-sizes="[10, 20, 50]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
              </el-pagination>
            </div>
          </el-card>
          <el-dialog title="修改贷款信息" :visible.sync="dialogFormVisible">
            <el-form :model="form" style="display: flex">
              <div style="flex: 1">
                <el-form-item label="dti" :label-width="formLabelWidth">
                  <el-input v-model="form.dti" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="last_pymnt_amnt" :label-width="formLabelWidth">
                  <el-input v-model="form.last_pymnt_amnt" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="open_acc" :label-width="formLabelWidth">
                  <el-input v-model="form.open_acc" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="pub_rec" :label-width="formLabelWidth">
                  <el-input v-model="form.pub_rec" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="revol_bal" :label-width="formLabelWidth">
                  <el-input v-model="form.revol_bal" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="revol_util" :label-width="formLabelWidth">
                  <el-input v-model="form.revol_util" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="tot_cur_bal" :label-width="formLabelWidth">
                  <el-input v-model="form.tot_cur_bal" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
              </div>
              <div style="flex: 1; margin-right: 20px" >
                <el-form-item label="total_acc" :label-width="formLabelWidth" >
                  <el-input v-model="form.total_acc" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="total_pymnt" :label-width="formLabelWidth">
                  <el-input v-model="form.total_pymnt" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="acc_now_delinq" :label-width="formLabelWidth">
                  <el-input v-model="form.acc_now_delinq" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="delinq_2yrs" :label-width="formLabelWidth">
                  <el-input v-model="form.delinq_2yrs" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="total_rec_late_fee" :label-width="formLabelWidth">
                  <el-input v-model="form.total_rec_late_fee" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="tot_coll_amt" :label-width="formLabelWidth">
                  <el-input v-model="form.tot_coll_amt" autocomplete="off" style="width: 200px"></el-input>
                </el-form-item>
              </div>



            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="formCancel">取 消</el-button>
              <el-button type="primary" @click="formSubmit">确 定</el-button>
            </div>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<style>
.el-menu-item:hover, .el-submenu__title:hover {
  color: dodgerblue;
}

.el-breadcrumb__item {
  color: brown;
}

</style>
<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      tableData: [], // 表格数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页显示条数
      total: '', // 总记录数
      allFinInfo:[],
      search: '',
      dialogFormVisible: false,
      formLabelWidth: '140px',
      form: {
        userID: '',
        dti: '',
        last_pymnt_amnt: '',
        open_acc: '',
        pub_rec: '',
        revol_bal: '',
        revol_util: '',
        tot_cur_bal: '',
        total_acc: '',
        total_pymnt: '',
        acc_now_delinq: '',
        delinq_2yrs: '',
        total_rec_late_fee: '',
        tot_coll_amt: ''

      }
    };
  },
  mounted() {
    request.get('/getAllFinInfo').then(res => {
      this.allFinInfo = res.data
      this.total = this.allFinInfo.length;
      console.log("金融健康信息：", this.allFinInfo)
    })

  },
  methods: {
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    // 编辑按钮 处理
    handleEdit(index, row) {
      console.log(index, row);
      this.dialogFormVisible = true
      this.form.userID = row.userID;
      this.form.dti = row.dti;
      this.form.last_pymnt_amnt = row.last_pymnt_amnt;
      this.form.open_acc = row.open_acc;
      this.form.pub_rec = row.pub_rec;
      this.form.revol_bal = row.revol_bal;
      this.form.revol_util = row.revol_util;
      this.form.tot_cur_bal = row.tot_cur_bal;
      this.form.total_acc = row.total_acc;
      this.form.total_pymnt = row.total_pymnt;
      this.form.acc_now_delinq = row.acc_now_delinq;
      this.form.delinq_2yrs = row.delinq_2yrs;
      this.form.total_rec_late_fee = row.total_rec_late_fee;
      this.form.tot_coll_amt = row.tot_coll_amt;


    },
    // 删除按钮 处理
    handleDelete(index, row) {
      // console.log(index, row);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete('/deleteFinInfoByid/' + row.userID).then(res => {
          console.log(res)
          location.reload(true);
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 处理表单
    formCancel() {
      this.dialogFormVisible = false
    },
    formSubmit() {
      request.put('/updateFininfo',{'form':this.form}).then(res => {
        console.log(res)
        location.reload(true);
      })
      this.dialogFormVisible = false
    },
    // 处理每页显示条数改变
    handleSizeChange(size) {
      this.pageSize = size;
      // 根据每页显示条数重新加载数据
      this.loadData();
    },
    // 处理当前页码改变
    handleCurrentChange(page) {
      this.currentPage = page;
      // 根据当前页码重新加载数据
      this.loadData();
    },
    // 加载数据
    loadData() {
      // 模拟从后端获取数据的过程，这里使用 setTimeout 模拟异步请求延迟
      console.log('total:', this.total)
      setTimeout(() => {
        // 计算起始索引
        const startIndex = (this.currentPage - 1) * this.pageSize;
        // 计算结束索引
        const endIndex = Math.min(startIndex + this.pageSize, this.total);
        console.log(startIndex, '~', endIndex)
        this.tableData = [];
        // 填充表格数据
        for (let i = startIndex; i < endIndex; i++) {
          let finInfo = this.allFinInfo[i]
          console.log(finInfo)
          this.tableData.push({
            userID: finInfo.userID,
            dti: finInfo.dti,
            last_pymnt_amnt: finInfo.last_pymnt_amnt,
            open_acc: finInfo.open_acc,
            pub_rec: finInfo.pub_rec,
            revol_bal: finInfo.revol_bal,
            revol_util: finInfo.revol_util,
            tot_cur_bal: finInfo.tot_cur_bal,
            total_acc: finInfo.total_acc,
            total_pymnt: finInfo.total_pymnt,
            acc_now_delinq: finInfo.acc_now_delinq,
            delinq_2yrs: finInfo.delinq_2yrs,
            total_rec_late_fee: finInfo.total_rec_late_fee,
            tot_coll_amt: finInfo.tot_coll_amt
          });
        }
      }, 1000); // 模拟延迟 300 毫秒
    }
  },
  created() {
    // 组件创建时加载数据
    // this.mounted();
    this.loadData();
  }
};
</script>