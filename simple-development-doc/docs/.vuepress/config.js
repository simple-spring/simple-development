// .vuepress/config.js
module.exports = {
    title: 'simple spring 中文文档',
    description: 'simple spring中文文档',
    // 注入到当前页面的 HTML <head> 中的标签
    head: [
        ['link', {rel: 'icon', href: '/favicon.ico'}], // 增加一个自定义的 favicon(网页标签的图标)
    ],
    markdown: {
        lineNumbers: true // 代码块显示行号
    },
    themeConfig: {
        smoothScroll: true,
        nav: [
            {text: 'Home', link: '/'},
            {text: '车鑫', link: 'https://diangc.cn'},
            {text: '南方银谷', link: 'http://www.nfyg.com.cn'},
        ],
        sidebar: [
            {
                title: '简介',
                path: '/simpleDoc/simple/simple',
            },
            {
                title: '环境',
                path: '/simpleDoc/environment/environment',
            },
            {
                title: '快速入门',
                sidebarDepth: 1,    // 可选的, 默认值是 1
                path: "/simpleDoc/started/started",
                children: [
                    {title: "安装", path: "/simpleDoc/started/install/install"}
                ]
            },
            {
                title: '整体设计',
                path: '/simpleDoc/design/design',
            },
            {
                title: '基础组件',
                path: '/simpleDoc/baseComponent/baseComponent',
            },
            {
                title: '高级组件',
                path: '/simpleDoc/advancedComponent/advancedComponent',
            },
            {
                title: '关于我们',
                path: '/simpleDoc/aboutus/aboutus',
            }
        ]
    }
}
