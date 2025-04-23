
# 学生考勤管理系统

#### 项目介绍
基于Ruoyi+uniapp （前后端分离项目） 实现学生考勤系统
学生考勤（普通、位置、二维码、人脸识别签到）、以及可视化数据展示
使用阿里云人脸识别+OSS存储
微信小程序密码、微信授权登录均可实现！

后期会不断优化、如果对您有帮助，您可以点右上角 "Star" ❤️ 支持一下 谢谢！以此鼓励、谢谢支持呀！
再次也希望可以提供宝贵的需求意建议（issues）、我们将不断改进优化。

## 内置功能
~~~
1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  通知公告：系统通知公告信息发布维护。
~~~

## 基础功能
~~~
1. 学生用户模块：用于录入和管理学生的基本信息，包括姓名、学号、班级、性别等。
2. 学生考勤（口头点名签到、普通、位置、二维码、人脸识别签到）。
3. 班级管理：用于管理学生所属的班级信息，包括班级名称、班级成员等。
4. 教师管理：对教师信息进行管理，包括教师的基本信息、职务等。
5. 请假管理：提供学生请假功能，包括在线请假、请假审批（老师是否同意）和请假记录查询等。
6. 可视化模块：用于对考勤数据进行分析和统计，生成考勤报表，包括学生出勤率、请假次数、迟到次数等。
7. 二维码签到：教师发布二维码--微信移动端学生扫码签到数据
8. 考勤记录管理：用于记录学生的考勤情况，包括请假、迟到、早退、缺勤等信息，并提供查询和统计功能。
~~~

## 更新日志：2024年4月28日
~~~
1. 新增功能：老师后台发布通知信息、学生可以进行查阅，移动端通知公告信息栏目（由富文本展示）。
2. 新增功能：后台管理页面PC端教师提醒（学生发起请假、所属老师登录，后台首页界面进行提醒）。
3. 新增功能：移动端集成腾讯地图、实时定位。
4. 新增功能：学生请假需要上传（生病的图等信息）、由教师进行审核、是否同意。
5. 新增功能：老师口头点名导入EXCEL表格数据、由教师进行审核签到信息录入。
~~~

## 更新日志：2024年12月1日
~~~
1. 优化整合后台配置信息（key）
2. 实现人脸识别和活体检测功能，实时监测人脸是否进入区域。微信小程序实现人脸识别，具体应用场景。
   前端实现人脸信息采集 拍到正面照片 发送给后端可以接入阿里云人脸识别。
~~~

## 更新日志：2024年12月18日
~~~
1. 优化uniapp（移动端）我的请假列表数据展示
~~~

## 更新日志：2025年2月23日
~~~
1. 新增功能：定时任务调度、获取当天缺勤、请假的人员进行、整理推送给相应的辅导员（通过邮件方式发送）
~~~

## 更新日志：2025年3月24日
~~~
1. 新增功能：新增教学管理员（管理自己学院的教师和学生）每门课的出勤率（汇总）、每个班级的出勤率（汇总）。
2. 新增功能：优化学生签到查询界面（树形下拉查询）
~~~

## 更新日志：2025年4月1日

项目持续更新、优化中......

 
## 1、后台效果图展示
<table>
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E9%A6%96%E9%A1%B5.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%8F%91%E8%B5%B7%E7%AD%BE%E5%88%B0.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E7%8F%AD%E7%BA%A7%E8%AF%BE%E7%A8%8B.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E9%80%9A%E7%9F%A5%E5%85%AC%E5%91%8A.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E4%BA%BA%E8%84%B8%E5%BA%93.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E7%AD%BE%E5%88%B0%E6%95%B0%E6%8D%AE.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%8F%AF%E8%A7%86%E5%8C%96.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/aed53134a75a38350e8d5f7769ceb34.png"/></td>
    </tr>
  <tr>
         <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/db01893a8da5edcc9dee5c3c71d9664.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E7%AD%BE%E5%88%B0%E6%95%B0%E6%8D%AE.png"/></td>
    </tr>
</table>

## 2、移动端效果图展示
<table>
    <tr>
       <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E7%99%BB%E5%BD%95.png"/></td>
       <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%AD%A6%E7%94%9F%E7%AB%AF%E9%A6%96%E9%A1%B5.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%B7%A5%E4%BD%9C%E5%8F%B0.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%AD%A6%E7%94%9F%E7%AB%AF%E7%AD%BE%E5%88%B0.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250423154756.png"/></td>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E6%88%91%E7%9A%84.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://gitee.com/ye-sgui/student-attendance/raw/master/%E4%B8%AA%E4%BA%BA%E5%BA%93%E4%B8%8A%E4%BC%A0.png"/></td>
        <td></td>
    </tr>
</table>



### 🔔 使用须知
1.允许用于个人学习、毕业设计、教学案例、公益事业、商业使用;<br>
2.如果商用必须保留版权信息，请自觉遵守;<br>
3.禁止将本项目的代码和资源进行任何形式的出售，产生的一切任何后果责任由侵权者自负。<br>
