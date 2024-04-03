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
            <el-breadcrumb-item>个人信息</el-breadcrumb-item>
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
              <span style="font-size: large">个人信息管理</span>
              <hr>
            </div>
            <div>
              <el-table
                  :data="tableData.filter(data => !search || data.userID.toLowerCase().includes(search.toLowerCase()))"
                  border>
                <el-table-column prop="userID" label="用户ID" align="center"></el-table-column>
                <el-table-column prop="annual_inc" label="年收入" align="center"></el-table-column>
                <el-table-column prop="emp_length" label="工作年限" align="center"></el-table-column>
                <el-table-column prop="addr_state" label="地区" align="center"></el-table-column>
                <el-table-column prop="home_ownership" label="住房情况" align="center"></el-table-column>
                <el-table-column prop="verification_status" label="认证状态" align="center"></el-table-column>
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
          <el-dialog title="修改个人信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="年收入" :label-width="formLabelWidth">
                <el-input v-model="form.annual_inc" autocomplete="off" style="width: 200px"></el-input>
              </el-form-item>
              <el-form-item label="工作时长" :label-width="formLabelWidth">
                <el-select v-model="form.emp_length" placeholder="请选择工作时长">
                  <el-option value="< 1 year"></el-option>
                  <el-option value="1 year"></el-option>
                  <el-option value="2 years"></el-option>
                  <el-option value="3 years"></el-option>
                  <el-option value="4 years"></el-option>
                  <el-option value="5 years"></el-option>
                  <el-option value="6 years"></el-option>
                  <el-option value="7 years"></el-option>
                  <el-option value="8 years"></el-option>
                  <el-option value="9 years"></el-option>
                  <el-option value="10+ years"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="住房情况">
                <el-radio-group v-model="form.home_ownership">
                  <el-radio label="MORTGAGE"></el-radio>
                  <el-radio label="RENT"></el-radio>
                  <el-radio label="OWN"></el-radio>
                  <el-radio label="OTHER"></el-radio>
                  <el-radio label="NONE"></el-radio>
                  <el-radio label="ANY"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="认证情况">
                <el-radio-group v-model="form.verification_status">
                  <el-radio label="Verified"></el-radio>
                  <el-radio label="Source Verified"></el-radio>
                  <el-radio label="Not Verified"></el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="居住地区" :label-width="formLabelWidth">
                <el-select v-model="form.addr_state" placeholder="请选择居住地区">
                  <el-option value="CA"></el-option>
                  <el-option value="NY"></el-option>
                  <el-option value="TX"></el-option>
                  <el-option value="FL"></el-option>
                  <el-option value="IL"></el-option>
                  <el-option value="NJ"></el-option>
                  <el-option value="PA"></el-option>
                  <el-option value="OH"></el-option>
                  <el-option value="GA"></el-option>
                  <el-option value="VA"></el-option>
                  <el-option value="NC"></el-option>
                  <el-option value="MI"></el-option>
                  <el-option value="MD"></el-option>
                  <el-option value="MA"></el-option>
                  <el-option value="AZ"></el-option>
                  <el-option value="WA"></el-option>
                  <el-option value="CO"></el-option>
                  <el-option value="MN"></el-option>
                  <el-option value="IN"></el-option>
                  <el-option value="MO"></el-option>
                  <el-option value="TN"></el-option>
                  <el-option value="CT"></el-option>
                  <el-option value="NV"></el-option>
                  <el-option value="WI"></el-option>
                  <el-option value="AL"></el-option>
                  <el-option value="OR"></el-option>
                  <el-option value="LA"></el-option>
                  <el-option value="SC"></el-option>
                  <el-option value="KY"></el-option>
                  <el-option value="OK"></el-option>
                  <el-option value="KS"></el-option>
                  <el-option value="AR"></el-option>
                  <el-option value="UT"></el-option>
                  <el-option value="NM"></el-option>
                  <el-option value="HI"></el-option>
                  <el-option value="NH"></el-option>
                  <el-option value="WV"></el-option>
                  <el-option value="MS"></el-option>
                  <el-option value="RI"></el-option>
                  <el-option value="MT"></el-option>
                  <el-option value="DE"></el-option>
                  <el-option value="DC"></el-option>
                  <el-option value="AK"></el-option>
                  <el-option value="WY"></el-option>
                  <el-option value="SD"></el-option>
                  <el-option value="VT"></el-option>
                  <el-option value="NE"></el-option>
                  <el-option value="ME"></el-option>
                  <el-option value="ND"></el-option>
                  <el-option value="ID"></el-option>
                  <el-option value="IA"></el-option>


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
      total: '', // 总记录数
      allPersons: [],
      search: '',
      dialogFormVisible: false,
      formLabelWidth: '80px',
      form: {
        userID: '',
        annual_inc: '',
        addr_state: '',
        emp_length: '',
        home_ownership: '',
        verification_status: ''
      }
    };
  },
  mounted() {
    // 初始化加载数据
    request.get('/getAllPerson').then(res => {
      this.allPersons = res.data
      this.total = this.allPersons.length;
      console.log("用户信息：", this.allPersons)
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
      this.form.annual_inc = row.annual_inc;

    },
    // 删除按钮 处理
    handleDelete(index, row) {
      // console.log(index, row);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete('/deletePersonByid/' + row.userID).then(res => {
          console.log("删除",res)
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
      console.log('form',this.form)
      request.put('/updatePerson',{'form':this.form}).then(res => {
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
          let person = this.allPersons[i]
          console.log(person)
          this.tableData.push({
            userID: person.userID,
            addr_state: person.addr_state,
            annual_inc: person.annual_inc,
            emp_length: person.emp_length,
            home_ownership: person.home_ownership,
            verification_status: person.verification_status
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