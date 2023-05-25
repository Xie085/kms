<p align="center">
    <img alt="logo" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftm-image.tianyancha.com%2Ftm%2Fea2033f7fc37500d3ba15b3fc965174b.jpg&refer=http%3A%2F%2Ftm-image.tianyancha.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1655900589&t=6a16396c62aee7199f39db2645e10d6b" style="width:100px;" width="180px">
</p>
<h1 align="center" style="margin: 10px 0 30px; font-weight: bold;">RuoYi-Vue-KMS</h1>
<h4 align="center">基于ruoyi-vue实现知识管理系统</h4>

### KMS-平台简介

**
利用空闲休息时间基于若依Vue前后端分离代码进行二次开发的KMS知识管理系统。目前系统已经完成前面五期的阶段（全文检索）的开发，目前正在梳理kms知识系统权限，后面改造若依的权限体系，让知识权限更实用。作者总共规划了7期，后续会按规划来做功能升级迭代，当然，如果作者有动力的话（希望得到更多的star），系统会陆续推出更多的实用功能。如果有更多功能推荐，欢迎交流。**

      感谢若依，KMS系统是基于若依技术框架实现，SpringBoot+Vue前后端分离，后端底层依然沿用若依的SpringBoot技术架构，前端同样使用VUE+Element UI实现。

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 特别鸣谢：RuoYi-Vue

### KMS-关于分支

    有了做KMS知识管理系统这个想法之后，我做的第一件事：代码仓库管理，先fork若依vue版的demo，然后基于master拉了一个分支。
    
    为啥要拉一个分支呢？
    
    是这样，我验证了fork过来的代码副本，如果直接在master上修改并提交代码的话，再去同步若依的更新会把当前副本下提交的代码覆盖。而新分支下的提交的代码在同步之后还是存在的。基于此，所以拉了一个新分支。Tip：为啥fork之后还要同步，因为若依vue如果有新代码提交，不管是修复bug，还是功能升级，我们都可以同步到副本上来。

如果大家有其他更方便的方法，欢迎交流！

### KMS-功能规划

<h5>第一期：实现基础功能，包括知识列表，知识新建，知识编辑，知识详情查看</h5>

    1、知识新建使用富文本框作为知识正文内容，实现复杂、好看的知识内容。
    2、编辑功能默认只开放给创建人使用，即本人才能编辑。
    3、知识查看范围，完全公开，公开表示系统所有用户都可以看。
    4、后面整体设计知识权限体系

<h5>第二期：增加知识目录关联、知识附件提交与附件预览</h5>

    1、目录是层级结构，若要删除目录则要将目录下知识转移。
    2、新建页面增加附件上传功能，可以提交Word，Excel，ppt、PDF等主流文件类型。
    3、查看页面支持附件在线预览与下载。
    4、完成附件的上传，保存和更新。

##### 附件开发遇到的问题：详情见wiki文档[第二阶段记录：Vue-KMS系统附件方案](https://gitee.com/chenzuheng001/RuoYi-Vue-KMS/wikis/%E7%AC%AC%E4%BA%8C%E9%98%B6%E6%AE%B5%E8%AE%B0%E5%BD%95%EF%BC%9AVue-KMS%E7%B3%BB%E7%BB%9F%E9%99%84%E4%BB%B6%E6%96%B9%E6%A1%88)

###### 问题1:附件预览查看

    解决方案：
    从测试情况来看，主流浏览器支持在线打开pdf，那么方案就是把office文件转换为pdf文件，并另存储一份，用于在线预览使用。
    转换方案：使用aspose接口转换。 已经编写demo成功测试word，excel可以转换。

###### 问题2:同一文件上传，会生成多个物理文件

    去重方案：
    使用md5值校验文件是否重复。
    具体思路：维护一张att_file表记录每个文件的md5值，当上传新的文件时，用当前文件生成md5，然后去att_file表中比对是否有相同的记录，如果有直接返回该文件信息，不做上传处理。反之，参照新文件上传逻辑。

###### 问题3:知识表单中上传了附件，但知识却没有提交，附件变成了游离数据，应该要清理，因为占用了资源。

    解决方案：
    1、维护一张att_main表，用于关联业务数据，当model_id有值时，代表该条数据（附件）是在使用的。反之，认为附件是无效的。
    2、使用定时任务去做数据检查，发现无效附件数据，进行清除，包括物理文件和表记录。
    3、为了错开上传时间差，定时任务执行的时间范围往前推一天。

<h5>第三期：实现知识版本管理与状态管理</h5>

    1、知识第一次新建可以为草稿知识，当再次编辑，若觉得可以发布了，就提交为发布知识。反之，暂存为草稿状态。
    2、当发布后的知识创建新版本之后，则产生新版本！版本号+1，新旧版本知识独立存在，互不影响！
    3、在查看页面增加知识版本列表。

<h5>第四期：知识关联（暂时从规划中剔除）</h5>

    场景是：多个知识之间是有前后置关系的，类似一本书的章节，这个场景的功能，我看了一些自媒体的文章创建工具里，可以通过插入超链接串起一个文章目录来实现，所以富文本框可以做到，就先搁置。

这期功能改为实现标签管理，知识标签化。

<h5>第五期：全文检索（后期重点）</h5>

    参考对比网上的检索方案，最终决定使用elasticsearch.本项目采用的是elasticsearch-7.17.0版本
    不要安装kibana连es，推荐：elasticvue插件,只需要安装浏览器插件即可。

![输入图片说明](ruoyi-ui/public/5-1.png)
下载地址：https://download.csdn.net/download/kingchen2010/86922649

<h5>第六期：知识点评</h5>

    你创建的知识是否受欢迎，是否受到大家的喜爱，评分打星来体现。
    同时可以留言与回复，进行友好互动。

<h5>第七期：流程审批</h5>

    目前主流的工作流开源框架有Activiti、Camunda、Flowable。如何抉择？有建议吗？

### 系统部分截图

![输入图片说明](ruoyi-ui/public/1-1.png)
![输入图片说明](ruoyi-ui/public/1-2.png)
![输入图片说明](ruoyi-ui/public/4-1.png)
![输入图片说明](ruoyi-ui/public/search-01.png)
![输入图片说明](ruoyi-ui/public/search-02.png)
![输入图片说明](ruoyi-ui/public/7-1.png.png)
![输入图片说明](ruoyi-ui/public/7-2.png)
![输入图片说明](ruoyi-ui/public/7-3.png)
![输入图片说明](ruoyi-ui/public/7-4.png)
![输入图片说明](ruoyi-ui/public/7-5.png)

### 版本内容更新

    2022/5/23 上传第一期功能。
    
    2022/5/28 上传第二期功能。
    
    2022/5/29 附件预览查看、使用md5值校验文件是否重复、附件机制。
    
    2022/6/28 上传第三期功能。
    
    2022/9/10 实现知识全文检索功能，实现过程移步到wiki里的[ElasticSearch+Kibana实现全文检索方案](https://gitee.com/chenzuheng001/RuoYi-Vue-KMS/wikis/ElasticSearch+Kibana%E5%AE%9E%E7%8E%B0%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2%E6%96%B9%E6%A1%88%E2%80%94%E2%80%94SpringBoot%E6%95%B4%E5%90%88)
   
    2022/9/11 梳理知识权限体系，让系统更加实用！实现代码可以查看wiki里的[KMS知识数据权限过滤](https://gitee.com/chenzuheng001/RuoYi-Vue-KMS/wikis/KMS%E7%9F%A5%E8%AF%86%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%E8%BF%87%E6%BB%A4)
    
    2022/9/24 vue增加bpmn-js依赖，开始流程之旅。详情点击[在项目中使用bpmn.js，开启流程设计之旅](https://gitee.com/chenzuheng001/RuoYi-Vue-KMS/wikis/Vue/%E5%9C%A8%E9%A1%B9%E7%9B%AE%E4%B8%AD%E4%BD%BF%E7%94%A8bpmn.js%EF%BC%8C%E5%BC%80%E5%90%AF%E6%B5%81%E7%A8%8B%E8%AE%BE%E8%AE%A1%E4%B9%8B%E6%97%85?sort_id=6089960)

    2022/10/1 增加流程模板维护，知识目录关联流程模板，则在该目录下创建知识都要走当前模板配置的流程审批。

    2022/10/2 提交了审批组件，实现了通过操作。
    
    2022/10/5 增加历史审批记录。
    
    2022/10/6 增加阅读者表，流程审批人默认拥有该知识的查看权限，方便审批。
    
    

