// 自写插件

import Toast from './Toast.vue'
import {createApp, provide} from 'vue'

const obj = {}

// 当main.js执行到.use(toast)代码(安装插件)时，会执行obj的install函数
obj.install = (app) => {
    /* Vue3的自定义插件 */
    // 1.实例化并绑定组件
    const toastConstructor = createApp(Toast);
    const instance = toastConstructor.mount(document.createElement('div'));
    console.log("instance存在:", instance); // 检查实例是否存在

    // 2.将挂载的Node添加到body中
    document.body.appendChild(instance.$el);
    console.log("Toast 组件已添加到 body:", document.body.contains(instance.$el)); // 验证是否添加到 body

    // 3. 输出当前 body 的子节点
    console.log("当前 body 的子节点:", document.body.children);

    // 4. 定义全局($toast即是此插件的名称)
    app.config.globalProperties.$toast = instance;
    // 在组件树的根部提供实例，使其在组件树中的所有子组件都可以访问
    app.mixin({
        beforeCreate() {
            provide('$toast', instance)
        }
    })
}

export default obj;

