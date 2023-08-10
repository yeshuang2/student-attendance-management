
# 学生考勤管理系统

#### 项目介绍
基于rouyi+uni-app （前后端分离项目） 实现学生考勤系统
学生考勤（普通、位置、二维码签到）、以及可视化数据展示

## 平台简介
若依是一套全部开源的快速开发平台，毫无保留给个人及企业免费使用。

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存信息查询，命令统计等。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 账户密码
- admin/admin123  

![输入图片说明](%E5%AD%A6%E7%94%9F%E8%80%83%E5%8B%A4%E7%AE%A1%E7%90%86%E6%A8%A1%E5%9D%97%E5%9B%BE.png)


### 学生考勤系统
1. 学生用户模块：用于录入和管理学生的基本信息，包括姓名、学号、班级、性别等。
2. 
3. 班级管理：用于管理学生所属的班级信息，包括班级名称、班级成员等。
4. 教师管理：对教师信息进行管理，包括教师的基本信息、职务等。
5.  教师模块--创建班级--分配学生--学生输入密码--校验老师设置密码--正确方可加入班级
6. 请假管理：提供学生请假功能，包括在线请假、请假审批和请假记录查询等。
7. 可视化模块 用于对考勤数据进行分析和统计，生成考勤报表，包括学生出勤率、请假次数、迟到次数等。
8. 二维码签到--教师发布二维码--学生扫码签到数据
9. 普通按钮签到

考勤记录管理：用于记录学生的考勤情况，包括请假、迟到、早退、缺勤等信息，并提供查询和统计功能。

