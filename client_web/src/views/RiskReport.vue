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
            <el-breadcrumb-item :to="{ path: '/homeview' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
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
        <el-main class="main_container_rr" style="background-color: cornflowerblue">
          <div class="block1_rr">
            <div class="group1_rr">
              <el-card class="card1_rr">
                <el-input
                    placeholder="请输入查询用户"
                    v-model="input"
                    clearable>
                </el-input>
                <div style="margin: 10px; height: 120px; display: flex;flex-direction: column;align-items: center; justify-content: center" >
                  <img src="@/assets/pictrues/loading_risk.png" style="width: 100px" alt="">
                  <span >{{risk_grade}}</span>
                </div>
                <div style="margin: 10px; display: flex; justify-content: center" >
                  <el-button
                      id="doReport_btn"
                      style="width: 120px"
                      type="primary"
                      icon="el-icon-edit"
                      @click="doReport"
                      :loading="report_loading"
                       round>生成风险报告</el-button>
                  <el-button id="getRisk_btn"
                             style="width: 120px"
                             type="primary"
                             icon="el-icon-warning"
                             @click="getRisk"
                             :loading="risk_loading"
                             round>查询风险等级</el-button>
                </div>

                </el-card>
              <el-card class="card2_rr"></el-card>
            </div>
            <el-card class="card3_rr" >
              <el-table
                  id="allUserTable_rr"
                  ref="singleTable"
                  :data="tableData"
                  max-height="300"
                  highlight-current-row
                  @current-change="handleCurrentChange"
                  style="width: 100%">
                <el-table-column
                    type="index"
                    width="50">
                </el-table-column>
                <el-table-column
                    property="date"
                    label="日期"
                    width="120">
                </el-table-column>
                <el-table-column
                    property="name"
                    label="姓名"
                    width="120">
                </el-table-column>
                <el-table-column
                    property="address"
                    label="地址">
                </el-table-column>
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="selectRow()">确定选择</el-button>
                <el-button @click="setCurrent()">取消选择</el-button>
              </div>
            </el-card>
          </div>
          <div class="block2_rr">
            <div class="group3_rr">
              <el-card class="card4_rr">
                <el-table
                    :data="loanInfo"
                    height="80"
                    border
                    style="width: 100%">
                  <el-table-column
                      prop="date"
                      label="日期"
                      width="180">
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      label="姓名"
                      width="180">
                  </el-table-column>
                  <el-table-column
                      prop="address"
                      label="地址">
                  </el-table-column>
                </el-table>
              </el-card>
              <el-card class="card5_rr">
                <el-table
                    :data="personInfo"
                    height="80"
                    border
                    style="width: 100%">
                  <el-table-column
                      prop="date"
                      label="日期"
                      width="180">
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      label="姓名"
                      width="180">
                  </el-table-column>
                  <el-table-column
                      prop="address"
                      label="地址">
                  </el-table-column>
                </el-table>
              </el-card>
            </div>
            <el-card class="card6_rr">
              <el-table
                  :data="finHealth"
                  height="80"
                  border
                  style="width: 100%">
                <el-table-column
                    prop="date"
                    label="日期"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="name"
                    label="姓名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="address"
                    label="地址">
                </el-table-column>
              </el-table>
            </el-card>
          </div>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
export default {
  data() {
    let user = "admin";
    let tableData = [];
    for (let i = 0; i < 10; i++){
      let tmp={
        date: '2016-05-1'+i,
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }
      tableData.push(tmp)
    }
    const loanInfo = [tableData[0]];
    const personInfo = [tableData[0]];
    const finHealth = [tableData[0]];


    return {
      loanInfo,
      personInfo,
      finHealth,
      risk_loading: false,
      report_loading: false,
      tableData,
      user,
      currentRow: null,
      input:'',
      risk_grade:'暂无查询'
    }
  },
  methods: {
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    selectRow(){
      if (this.currentRow == null){
        this.$notify.error({
          title: '错误',
          message: '未从表格中选择任何信息'
        });
      }else{
        let val = this.currentRow.name
        this.input = val
        console.log(val)
      }
    },
    doReport(){
      console.log(this.input)
      this.loading = true;
      // 在这里可以执行加载数据或其他操作
      setTimeout(() => {
        // TODO: 向后端发送生成报告请求
        this.loading = false;
      }, 2000); // 假设加载需要2秒完成
    },
    getRisk(){
      this.loading = true;
      // 在这里可以执行加载数据或其他操作
      setTimeout(() => {
        // TODO: 向后端发送生成报告请求
        this.loading = false;
      }, 2000); // 假设加载需要2秒完成
    }

  }
};
</script>

<style>



</style>
