import { createApp, ref, h, provide, getCurrentInstance } from 'vue';

// 创建一个全局弹窗组件
const ModalComponent = {
    props: {
        content: {
            type: String,
            required: true
        }
    },
    setup(props, { emit }) {
        const show = ref(true); // 控制弹窗显示

        // Provide the close function to the modal context
        provide('closeModal', () => {
            show.value = false;
            emit('close'); // 触发关闭事件
        });

        return () => {
            if (!show.value) return null;
            return h(
                'div',
                {
                    class: 'modal-overlay',
                    onClick: () => {
                        const close = getCurrentInstance().appContext.provides['closeModal'];
                        if (close) close(); // 通过提供的 closeModal
                    },
                },
                [
                    h(
                        'div',
                        {
                            class: 'modal-content',
                            onClick: (e) => e.stopPropagation(), // 阻止点击内容区域关闭弹窗
                        },
                        [
                            h('button', {
                                onClick: () => {
                                    const close = getCurrentInstance().appContext.provides['closeModal'];
                                    if (close) close(); // 通过提供的 closeModal
                                }
                            }, 'Close'),
                            h('div', { class: 'modal-body' }, props.content), // 自定义内容
                        ]
                    ),
                ]
            );
        };
    },
};

// 插件函数
const ModalPlugin = {
    install(app) {
        // 创建挂载点
        const mountPoint = document.createElement('div');
        document.body.appendChild(mountPoint);

        // Initialize modalApp instance once
        const modalApp = createApp({
            setup() {
                const content = ref(''); // 弹窗内容

                // Provide content and close function
                provide('setContent', (newContent) => {
                    content.value = newContent;
                });

                provide('closeModal', () => {
                    // Logic to close the modal
                    const instance = getCurrentInstance();
                    instance && instance.emit('close');
                });

                return () => h(ModalComponent, { content: content.value });
            }
        });

        // Mount the modal app once
        modalApp.mount(mountPoint);

        // 添加全局属性 $modal
        app.config.globalProperties.$modal = {
            open(content) {
                const modalAppInstance = getCurrentInstance();
                if (modalAppInstance) {
                    const setContent = modalAppInstance.appContext.provides['setContent'];
                    if (setContent) {
                        setContent(content); // 更新内容
                    }
                }
                // Instead of mounting, just update the existing modal app
                modalApp.mount(mountPoint); // This line is not needed
            },
        };
        app.mixin({
            beforeCreate() {
                provide('$modal', {
                    open(content) {
                        const modalAppInstance = getCurrentInstance();
                        if (modalAppInstance) {
                            const setContent = modalAppInstance.appContext.provides['setContent'];
                            if (setContent) {
                                setContent(content); // 更新内容
                            }
                        }
                        // Instead of mounting, just update the existing modal app
                        modalApp.mount(mountPoint); // This line is not needed
                    },
                })
            }
        })
    },
};

export default ModalPlugin;
