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
          <el-submenu index="">
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
                <div style="width: 100%; display: flex; flex-direction: row">
                  <el-input
                      placeholder="请输入查询用户"
                      v-model="input"
                      clearable>
                  </el-input>
                  <el-button style="margin-left: 10px" id="searchInfo_btn" type="primary" icon="el-icon-search"
                             @click="getInfo" :loading="info_loading" plain>查询信息
                  </el-button>
                </div>

                <div
                    style="margin: 10px; height: 120px; display: flex;flex-direction: column;align-items: center; justify-content: center">
                  <img :src="risk_icon" style="width: 100px" alt="">
                  <span>{{ risk_grade }}</span>
                </div>
                <div style="margin: 10px; display: flex; justify-content: center">
                  <el-button
                      id="doReport_btn"
                      style="width: 120px"
                      type="primary"
                      icon="el-icon-edit"
                      @click="doReport"
                      :loading="report_loading"
                      round>生成风险报告
                  </el-button>
                  <el-button id="getRisk_btn"
                             style="width: 120px"
                             type="primary"
                             icon="el-icon-warning"
                             @click="getRisk"
                             :loading="risk_loading"
                             round>查询风险等级
                  </el-button>
                </div>

              </el-card>
              <el-card class="card2_rr"></el-card>
            </div>
            <el-card class="card3_rr">
              <el-table
                  id="allUserTable_rr"
                  ref="singleTable"
                  :data="allUsers"
                  max-height="300"
                  highlight-current-row
                  @current-change="handleCurrentChange"
                  style="width: 100%">
                <!--                <el-table-column type="index" align="center" ></el-table-column>-->
                <el-table-column property="userID" label="用户ID" width="120" align="center"></el-table-column>
                <el-table-column property="userName" label="用户名" align="center"></el-table-column>
                <el-table-column property="phone" label="手机号" align="center"></el-table-column>
                <el-table-column property="permission" label="用户权限(1普通用户 0管理员)" align="center"></el-table-column>
              </el-table>
              <div style="margin-top: 20px">
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
                  <el-table-column prop="loanID" label="贷款ID" width="70" align="center"></el-table-column>
                  <el-table-column prop="loan_amnt" label="贷款金额" width="70" align="center"></el-table-column>
                  <el-table-column prop="int_rate" label="贷款利率" width="70" align="center"></el-table-column>
                  <el-table-column prop="installment" label="每期还款" width="70" align="center"></el-table-column>
                  <el-table-column prop="term" label="贷款期数" width="90" align="center"></el-table-column>
                  <el-table-column prop="grade" label="贷款等级" width="70" align="center"></el-table-column>
                  <el-table-column prop="application_type" label="申请类型" width="100" align="center"></el-table-column>
                  <el-table-column prop="purpose" label="贷款目的" width="140" align="center"></el-table-column>

                </el-table>
              </el-card>
              <el-card class="card5_rr">
                <el-table
                    :data="personInfo"
                    height="80"
                    border
                    style="width: 100%">
                  <el-table-column prop="annual_inc" label="年收入" width="80" align="center"></el-table-column>
                  <el-table-column prop="emp_length" label="工作年限" width="80" align="center"></el-table-column>
                  <el-table-column prop="addr_state" label="地区" width="50" align="center"></el-table-column>
                  <el-table-column prop="home_ownership" label="住房情况" width="100" align="center"></el-table-column>
                  <el-table-column prop="verification_status" label="认证状态" width="120" align="center"></el-table-column>

                </el-table>
              </el-card>
            </div>
            <el-card class="card6_rr">
              <el-table
                  :data="finHealth"
                  height="80"
                  border
                  style="width: 100%">
                <el-table-column prop="dti" label="dti" width="70" align="center"></el-table-column>
                <el-table-column prop="last_pymnt_amnt" label="last_pymnt_amnt" width="120"
                                 align="center"></el-table-column>
                <el-table-column prop="open_acc" label="open_acc" width="80" align="center"></el-table-column>
                <el-table-column prop="pub_rec" label="pub_rec" width="75" align="center"></el-table-column>
                <el-table-column prop="revol_bal" label="revol_bal" width="75" align="center"></el-table-column>
                <el-table-column prop="revol_util" label="revol_util" width="75" align="center"></el-table-column>
                <el-table-column prop="tot_cur_bal" label="tot_cur_bal" width="90" align="center"></el-table-column>
                <el-table-column prop="total_acc" label="total_acc" width="75" align="center"></el-table-column>
                <el-table-column prop="total_pymnt" label="total_pymnt" width="90" align="center"></el-table-column>
                <el-table-column prop="acc_now_delinq" label="acc_now_delinq" width="115"
                                 align="center"></el-table-column>
                <el-table-column prop="delinq_2yrs" label="delinq_2yrs" width="90" align="center"></el-table-column>
                <el-table-column prop="total_rec_late_fee" label="total_rec_late_fee" width="130"
                                 align="center"></el-table-column>
                <el-table-column prop="tot_coll_amt" label="tot_coll_amt" width="100" align="center"></el-table-column>


              </el-table>
            </el-card>
          </div>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
import request from "@/utils/request";
import axios from "axios";

export default {
  data() {
    const user = "admin";
    const riskIconsList = [
      'zero_risk.png',
      'low_risk.png',
      'mid_risk.png',
      'high_risk.png',
      'loading_risk.png'
    ]
    let risk_icon = require('@/assets/pictrues/loading_risk.png')
    return {
      loanInfo: '',
      personInfo: '',
      finHealth: '',
      risk_loading: false,
      report_loading: false,
      info_loading: false,
      okSelect_loading: false,
      user,
      currentRow: null,
      input: '',
      risk_grade: '暂无查询',
      risk_icon,
      allUsers: '',
      requestPermission: false,
      riskIconsList
    }
  },
  mounted() {
    request.get('/getAllUsers').then(res => {
      this.allUsers = res.data
    })
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
      this.loanInfo = ''
      this.personInfo = ''
      this.finHealth = ''
      let name = this.currentRow.userName
      this.input = name
    },
    getInfo() {
      let dataComplete = true // 影响 this.requestPermission 的值，进而允许发送请求
      // 向后端发送加载三个表信息的请求
      if (this.input == null) {
        this.$notify.error({
          title: '错误',
          message: '输入为空',
        });
      } else {
        let val = this.input
        // loanTable
        request.get('/getLoansByName/' + val).then(res => {
          if (res.code === 400) {
            dataComplete = false
            this.$notify.error({
              title: '错误',
              message: res.msg
            });
          }
          this.loanInfo = res.data
        })
        // borrowerTable
        request.get('/getBorrInfoByName/' + val).then(res => {
          if (res.code === 400) {
            dataComplete = false
            this.$notify.error({
              title: '错误',
              message: res.msg
            });
          }
          this.personInfo = Array(1).fill(res.data)
        })
        // finHealthTable
        request.get('/getFinInfoByName/' + val).then(res => {
          console.log(res)
          console.log(res.code)
          if (res.code === 400) {
            dataComplete = false
            this.$notify.error({
              title: '错误',
              message: res.msg
            });
          }
          console.log(res.data)
          this.finHealth = Array(1).fill(res.data)
        })
      }
      this.requestPermission = dataComplete
    },
    doReport() {
      this.report_loading = true;
      // 在这里可以执行加载数据或其他操作
      setTimeout(() => {
        if (this.input == null) {
          this.$notify.error({
            title: "错误",
            message: "用户名为空！！"
          })
        } else {
          if (this.requestPermission) {
            request.get('/doReport/' + this.input).then(res => {
              console.log(res)
              if (res.code == 200) {
                this.$notify.success({
                  title: '生成报告成功',
                  message: res.data
                });
                window.open(res.data)
              } else {
                this.$notify.error({
                  title: '风险报告生成失败',
                  message: res.data
                });
              }
            })

          } else {
            this.$notify.error({
              title: '错误',
              message: '信息不全，无法生成风险报告'
            });
          }

        }
        this.report_loading = false;
      }, 2000); // 假设加载需要2秒完成
    },
    getRisk() {
      console.log(this.allUsers)
      this.risk_loading = true;
      // let data =
      // 在这里可以执行加载数据或其他操作
      setTimeout(() => {
        console.log('查询风险等级',)
        if (this.requestPermission) {
          // 准备POST的数据 25 个特征
          let loan = this.loanInfo[0]
          let person = this.personInfo[0]
          let fin = this.finHealth[0]
          let postData = {
            "loan_amnt": loan.loan_amnt,
            "term": loan.term,
            "int_rate": loan.int_rate,
            "installment": loan.installment,
            "grade": loan.grade,
            "emp_length": person.emp_length,
            "home_ownership": person.home_ownership,
            "annual_inc": person.annual_inc,
            "verification_status": person.verification_status,
            "purpose": loan.purpose,
            "addr_state": person.addr_state,
            "dti": fin.dti,
            "delinq_2yrs": fin.delinq_2yrs,
            "open_acc": fin.open_acc,
            "pub_rec": fin.pub_rec,
            "revol_bal": fin.revol_bal,
            "revol_util": fin.revol_util,
            "total_acc": fin.total_acc,
            "total_pymnt": fin.total_pymnt,
            "total_rec_late_fee": fin.total_rec_late_fee,
            "last_pymnt_amnt": fin.last_pymnt_amnt,
            "application_type": loan.application_type,
            "acc_now_delinq": fin.acc_now_delinq,
            "tot_coll_amt": fin.tot_coll_amt,
            "tot_cur_bal": fin.tot_cur_bal
          }
          // 向后端发送生成报告请求
          request.post('/getRisk', {"features": postData}).then(res => {
            // res:  {"msg": "低风险 Low Risk","code": 200, "index": 1} index:
            let iconUrl = this.riskIconsList[res.index]
            this.risk_icon = require('@/assets/pictrues/' + iconUrl)
            // this.risk_icon = require('@/assets/pictrues/zero_risk.png')
            this.risk_grade = res.msg
            this.$notify.info({
              title: '风险等级',
              message: res.msg
            });
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '信息不全，无法进行风险预测'
          });
        }

        this.risk_loading = false;
      }, 2000); // 假设加载需要2秒完成
    }

  }
};
</script>

<style>


</style>
