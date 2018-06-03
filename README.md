# 基于ElasticSearch的海量文本检索系统



> 关于我，欢迎关注
> 博客：[雪浪snowWave](http://www.cnblogs.com/team42/)



### 项目介绍

基于ElasticSearch的海量文本检索系统，目前支持txt, doc, docx, pdf, ppt格式文本上传及全文查询。



### 功能实现

- #### 首页

  - 输入查询关键字进行检索
  - 文件上传
    - 点击`添加文件`按钮，弹出模态框
    - 点击`浏览`从本地上传文件，信息录入（作者、类型）并提交表单
    - 表单提交状态信息提示

- #### 列表页

  - 搜索结果统计
  - 数据分页渲染[[pageView插件](https://github.com/liuyunzhuge/blog/blob/master/form/src/js/app/pageView.js)]
  - 全文关键字匹配
    - 显示在摘要部分
    - 文字超出两行使用省略号代替
  - 搜索关键字红色高亮

- #### 详情页

  - 文本内容分段渲染




### 项目截图

- #### 首页-搜索

![首页-搜索](img/search.png)

- #### 首页-搜索

![首页-搜索预测](img/searchPredict.png)


- #### 首页-文件上传

![首页-上传](img/addFile.png)
- #### 列表页

![列表页](img/list.png)
- #### 详情页

![详情页](img/detail.png)



### 参与贡献

1. *Fork*本项目
2. 新建 *Search_xxx* 分支
3. 提交代码
4. 新建 *Pull Request*