// .vuepress/config.js
module.exports = {
    title: 'simple spring 中文文档',
    description: 'simple spring中文文档',
    // 注入到当前页面的 HTML <head> 中的标签
    head: [
        ['link', { rel: 'icon', href: '/favicon.ico' }], // 增加一个自定义的 favicon(网页标签的图标)
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
                title: '开发指南',
                path: '/simpleDoc/',
                collapsable: false, //是否展开
            },
            ['./simpleDoc/install/install','安装'],
            ['./simpleDoc/started/started','快速上手'],
            {
                title: '组件',
                sidebarDepth: 1,    // 可选的, 默认值是 1
                children: [
                    {
                        title: '开发指南',
                        path: '/simpleDoc/',
                        collapsable: false, //是否展开
                    }
                ]
            },
            ['./simpleDoc/icon/icon','icon'],
        ]
    }
}
