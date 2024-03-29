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
        <el-main style="background-color: darkseagreen">
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
export default {
  data() {
    let user = "admin";
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: Array(20).fill(item),
      user
    }
  },
  methods: {
    handleFull() {
      document.documentElement.requestFullscreen()
    }
  }
};
</script>