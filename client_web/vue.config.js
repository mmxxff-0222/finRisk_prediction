const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 7777
  },
  chainWebpack: config =>{
    config.plugin('html')
        .tap(args => {
          args[0].title = "金融信贷风险分析平台";
          return args;
        })
  }
})
