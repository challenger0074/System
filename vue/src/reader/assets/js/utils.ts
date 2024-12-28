
const obj2 = {}


obj2.install = (app) => {
  console.log("api自定义插件安装成功")
  app.config.globalProperties.$common = {
    defaultImage(e) {
      e.target.src =
          new URL(`../images/tu.png`, import.meta.url).href;
    },
    test(){
      console.log("test2")
    },
    api: '/api',
  };
}
export default obj2

