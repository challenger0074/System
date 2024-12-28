// myPlugin.js

import { provide } from 'vue'
import useCounter from './useCounter'

export default {
    install: (app) => {
        const { counter, increment } = useCounter()

        // 在全局提供计数器和增量函数
        app.config.globalProperties.$counter = counter
        app.config.globalProperties.$increment = increment

        // 在组件树的根部提供计数器和增量函数
        app.mixin({
            beforeCreate() {
                provide('$counter', counter)
                provide('$increment', increment)
            }
        })
    }
}

