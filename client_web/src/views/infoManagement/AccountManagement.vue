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
            <el-breadcrumb-item>账号管理</el-breadcrumb-item>
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
              <span style="font-size: large">账号信息管理</span>
              <hr>
            </div>
            <div>
              <el-table :data="tableData.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"
                        border>
                <el-table-column property="userID" label="用户ID" width="120" align="center"></el-table-column>
                <el-table-column property="userName" label="用户名" align="center"></el-table-column>
                <el-table-column property="phone" label="手机号" align="center"></el-table-column>
                <el-table-column property="permission" label="用户权限(1普通用户 0管理员)" align="center"></el-table-column>
                <el-table-column
                    label="操作"
                    align="center">
                  <template slot="header" slot-scope="scope">
                    <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入关键字搜索"/>
                  </template>
                  <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                    <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
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
          <el-dialog title="修改信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input v-model="form.userName" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="手机号" :label-width="formLabelWidth">
                <el-input v-model="form.phone" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="用户权限" :label-width="formLabelWidth">
                <el-select v-model="form.permissions" placeholder="请选择用户权限">
                  <el-option label="0: 管理员" value="0"></el-option>
                  <el-option label="1: 用户" value="1"></el-option>
                </el-select>
              </el-form-item>
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
      total: 20000, // 总记录数
      allUsers: [],
      search:'',
      dialogFormVisible:false,
      formLabelWidth: '80px',
      form: {
        userID: '',
        userName: '',
        phone:'',
        permissions:'',
      }
    };
  },
  mounted() {
    // request.get('/getAllUsers').then(res => {
    //   console.log('all users:',this.allUsers)
    //   this.allUsers = res.data
    //   this.total = this.allUsers.length;
    // })

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
      this.form.userName = row.userName;
      this.form.phone = row.phone;
    },
    // 删除按钮 处理
    handleDelete(index, row) {
      // console.log(index, row);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete('/deleteUserByid/'+row.userID).then(res =>{
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
    formCancel(){
      this.dialogFormVisible = false
    },
    formSubmit(){
      console.log(this.form)
      request.post('/updateUserById',{'form': this.form}).then(res =>{
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
      setTimeout(() => {
        // 计算起始索引
        const startIndex = (this.currentPage - 1) * this.pageSize;
        // 计算结束索引
        const endIndex = Math.min(startIndex + this.pageSize, this.total);
        console.log(startIndex,'~',endIndex)
        // 清空表格数据
        // request.get('/getAllUsers').then(res => {
        //   this.tableData = res.data
        // })
        this.tableData = [];
        request.get('/getUsers/' + startIndex + '-' + endIndex).then(res => {
          this.allUsers = res.data
          console.log('all users:',this.allUsers)
          for (let i = 0; i < this.pageSize; i++) {
            let user = this.allUsers[i]
            console.log(user)
            this.tableData.push({
              userID: user.userID,
              userName: user.userName,
              phone: user.phone,
              permission: user.permission
            });
          }
        })
      }, 1000); // 模拟延迟 300 毫秒
      // 填充表格数据
    }
  },
  created() {
    // 组件创建时加载数据
    // this.mounted();
    this.loadData();
  }
};
</script>