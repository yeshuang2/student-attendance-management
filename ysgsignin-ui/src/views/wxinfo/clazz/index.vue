<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="班级名称" prop="clazzName">
                <el-input v-model="queryParams.clazzName" placeholder="请输入班级名称" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
            <!-- <el-form-item label="用户名称(学生）" prop="userName">
                <el-input v-model="queryParams.userName" placeholder="请输入用户名称(学生）" clearable @keyup.enter.native="handleQuery" />
            </el-form-item> -->
            <el-form-item label="老师姓名" prop="teacherName">
                <el-input v-model="queryParams.teacherName" placeholder="请输入老师姓名" clearable @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="帐号状态" prop="status">
                <el-select v-model="queryParams.status" placeholder="请选择帐号状态" clearable>
                    <el-option v-for="dict in dict.type.sys_common_status" :key="dict.value" :label="dict.label" :value="dict.value" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['wxinfo:clazz:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['wxinfo:clazz:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['wxinfo:clazz:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['wxinfo:clazz:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table border v-loading="loading" :data="clazzList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="班级ID" align="center" width="65" prop="clazzId" />
            <el-table-column label="班级名称" align="center" width="195" prop="clazzName" />
            <!-- <el-table-column label="用户ID(学生)" align="center" prop="userId" /> -->
            <el-table-column label="对应用户名称(学生）" align="center" prop="userName" label-width="470px" />
            <!-- <el-table-column label="老师ID" align="center" prop="teacherId" /> -->
            <el-table-column label="老师姓名" align="center" prop="teacherName" width="160"/>
                <el-table-column label="帐号状态" align="center" width="95px" prop="status">
                <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
</template>
      </el-table-column>
      <el-table-column label="备注" align="center"  width="200" prop="remark" />
      <el-table-column label="操作" align="center"  width="215" class-name="small-padding fixed-width">
<template slot-scope="scope">
  <el-button size="small" split-button @click="handleqiandaoDialog(scope.row)" type="success">签到类型</el-button>
 &nbsp;&nbsp;
    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['wxinfo:clazz:edit']">
        修改</el-button>
    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['wxinfo:clazz:remove']">删除</el-button>
  <!-- <el-button size="mini" type="text" icon="el-icon-s-promotion" @click="handleDelete(scope.row)" >发起签到</el-button> -->
</template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改班级信息数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="班级名称" prop="clazzName">
          <el-input   v-model="form.clazzName" :readonly="isReadOnly()" placeholder="请输入班级名称" />
        </el-form-item>
        <!-- <el-form-item  label="用户ID(学生)" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID(学生)" />
        </el-form-item> -->

        <el-form-item label="学生信息">
          <el-select  filterable allow-create default-first-option   
          v-model="userIdStudentListVlue" clearable style="width: 100%;"  placeholder="请选择学生信息" multiple>
             <el-option 
                v-for="itemStudent in userIdStudentList"
                 :key="itemStudent.userId"
                :label="itemStudent.userName+'-'+itemStudent.nickName"
                :value="itemStudent.userName"
             ></el-option>
        </el-select>
          <!-- <el-input :required="true" v-model="form.userName" placeholder="请输入用户名称(学生）" /> -->
        </el-form-item>


        <!-- <el-form-item label="老师ID" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入老师ID" />
        </el-form-item> -->

        <el-form-item label="老师姓名">
               <el-select  v-model="userIdTeacherListVlue"  placeholder="请选择老师信息"   clearable style="width: 100%;" multiple>
             <el-option 
                v-for="itemTeachar in userIdTeacherList"
                :key="itemTeachar.userId"
                :label="itemTeachar.userName+'-'+itemTeachar.nickName"
                :value="itemTeachar.userName"
             ></el-option>
        </el-select>
        </el-form-item>

        <el-form-item label="帐号状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择帐号状态">
            <el-option
              v-for="dict in dict.type.sys_common_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--  -->

    <!-- 签到数据对话框 -->
    <el-dialog :title="handleqiandaotitle" :visible.sync="handleqiandaoDialogopen" width="600px" append-to-body>
      <el-form ref="form" :model="formqiandao" :rules="rules" label-width="120px">
              <el-form-item label="班级ID" prop="clazzId">
          <el-input   v-model="formqiandao.clazzId"   disabled="disabled"/>
        </el-form-item>
        <el-form-item label="班级名称" prop="clazzName">
          <el-input   v-model="formqiandao.clazzName"   disabled="disabled"/>
        </el-form-item>

        <el-form-item label="老师姓名" prop="teacherName">
             <el-input   v-model="formqiandao.teacherName"  disabled="disabled" />
        </el-form-item>

          <el-form-item label="签到类型">
    <el-select v-model="formqiandao.qiandaoregion" placeholder="请选择签到类型">
      <el-option label="普通签到" value="普通签到"></el-option>
      <el-option label="二维码签到" value="二维码签到"></el-option>
           <el-option label="位置签到" value="位置签到"></el-option>
    </el-select>
  </el-form-item>

    <el-form-item label="签到截止时间">
     <el-date-picker
      v-model="formqiandao.data1"
      type="datetime"
      placeholder="选择日期时间"
      align="right"
      :picker-options="pickerOptions">
    </el-date-picker>
  </el-form-item>


        <el-form-item label="备注" prop="remark">
          <el-input v-model="formqiandao.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="qiandaosubmitForm">开启签到</el-button>
        <el-button @click="qiandacancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listClazz, getClazz, 
delClazz, addClazz, 
updateClazz, selectwxlistUserInfo,
getBanJiNameClazzInfo } from "@/api/wxinfo/clazz"; //班级api

import { addSignin ,GenerateV2} from "@/api/wxinfo/signin"; //签到api

export default {
    name: "Clazz",
    dicts: ['sys_common_status'],
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 班级信息数据表格数据
            clazzList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                clazzName: null,
                userName: null,
                teacherName: null,
                status: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {},
           // 从用户表进行查询数据 学生       
            userIdStudentListVlue: [],  //学生的空数组
            userIdStudentList: [],
            // 从用户表进行查询的数据 教师
            userIdTeacherListVlue: [], //教师的空数组   
            userIdTeacherList: [],
            Tflag:false,
            // ------------------签到模块----------------
             handleqiandaotitle:"发起签到栏",
            handleqiandaoDialogopen:false, //默认关闭
            formqiandao:{}, //签到的数据
               pickerOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        data1: '', //签到时间
        signinform:{
        signinClazzid: null,
        signinClazzname: null,
        signinTeachername: null,
        signinUsername: null,
        signinStatus: null,
        signinType: null,
        signinStarttime: null,
        signinEndttime: null,
        signinAddimg: null, //签到存放的数据 学生
        }, //签到模块
        wxqiandao_qr_code_url:null, //签到二维码路径
        };
    },

    created() {
        this.getList();
    },
    methods: {
    //  保存签到的信息数据
    // 签到 弹窗
   handleqiandaoDialog(row){
    this.formqiandao.clazzId=row.clazzId
    this.formqiandao.teacherName=row.teacherName
    this.formqiandao.clazzName=row.clazzName
    this.formqiandao.userName=row.userName // 需要签到的用户
        this.handleqiandaoDialogopen=true; //打开弹出层

   },

     // 签到取消按钮
    qiandacancel() {
      this.$nextTick(() => {
             this.handleqiandaoDialogopen = false;
        this.formqiandao.userName=null;         //需要签到的学生学号
        this.formqiandao.qiandaoregion=null;       //签到类型
        this.formqiandao.data1=null; 
        this.formqiandao.remark=null;
      });
        this.handleqiandaoDialogopen = false;
        this.formqiandao.userName=null;         //需要签到的学生学号
        this.formqiandao.qiandaoregion=null;       //签到类型
        this.formqiandao.data1=null; 
        this.formqiandao.remark=null;
    },

    // 签到提交数据
    qiandaosubmitForm() {
         if (this.formqiandao != null) {
         this.signinform.signinClazzid=this.formqiandao.clazzId,
         this.signinform.signinClazzname=this.formqiandao.clazzName,
         this.signinform.signinTeachername=this.formqiandao.teacherName, //发起签到教师名称
         this.signinform.signinUsername=this.formqiandao.userName,       //需要签到的学生学号
         this.signinform.signinType=this.formqiandao.qiandaoregion,       //签到类型
         this.signinform.signinEndttime=this.formqiandao.data1;
                   //  显示二维码 进行判断是否显示二维码
          if (this.signinform.signinType==='二维码签到') {
            GenerateV2("班级ID:"+this.signinform.signinClazzid+"  班级名称:"+this.signinform.signinClazzname+"  签到发起人:"+
            this.signinform.signinTeachername+"老师").then(responseimg=>{
        this.signinform.signinAddimg='http://localhost:8080/upload/'+responseimg.data
            })
 }                                                      
      // this.signinform.signinAddimg=this.wxqiandao_qr_code_url;
      console.log(this.signinform)
      setTimeout(() => {
          addSignin(this.signinform).then(response => {
              this.handleqiandaoDialogopen = false;
               this.$modal.msgSuccess("发起签到成功");
        //清空数据
        this.formqiandao.userName=null;         //需要签到的学生学号
        this.formqiandao.qiandaoregion=null;       //签到类型
        this.formqiandao.data1=null; 
        this.formqiandao.remark=null;
              this.getList();
             }).catch(() => {
               this.$modal.msgError("发起签到失败，请联系管理员");
            });
      }, 400);
          
         }
       
    },

    isReadOnly() {
    //当满足某些条件时， 值存在---设置为只读状态
    return this.form.clazzName ? true : false;
    },
        //   用户表查询学生进行添加班级
    userIdStudentListClick() {
        selectwxlistUserInfo("学生").then((responseS) => {
            this.userIdStudentList = responseS.data
        })
        },
        //  用户表查询教师数据
        userIdTeacherListClick(){
        selectwxlistUserInfo("教师").then((responseT) => {
            this.userIdTeacherList = responseT.data
        })
    },
    
    /** 查询班级信息数据列表 */
    getList() {
        this.loading = true;
        listClazz(this.queryParams).then(response => {
            this.clazzList = response.rows;
            this.total = response.total;
            this.loading = false;
        });
    },
    // 取消按钮
    cancel() {
        this.open = false;
        this.reset();
    },
        // 表单重置
        reset() {
            this.form = {
                clazzId: null,
                clazzName: null,
                userId: null,
                userName: null,
                teacherId: null,
                teacherName: null,
                status: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null, 
                remark: null
            };
            this.resetForm("form");
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm("queryForm");
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.clazzId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.userIdStudentListVlue=[];  //学生的进行空数组
            this.userIdTeacherListVlue=[];  //教师的进行空数组 
          this.userIdStudentListClick(); // 查询学生的数据信息
          this.userIdTeacherListClick(); // 查询老师的数据信息
            this.reset();
            this.open = true;
            this.title = "添加班级信息数据";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const clazzId = row.clazzId || this.ids
            getClazz(clazzId).then(response => {
            this.userIdStudentListVlue=[];  //学生的进行空数组
            this.userIdTeacherListVlue=[];  //教师的进行空数组 
            this.userIdStudentListClick() // 查询学生的数据信息
            this.userIdTeacherListClick() // 查询教师的数据信息
            if(response.data.userName!=""){
 this.userIdStudentListVlue=response.data.userName.split(','); //字符串转换为数组格式
            }
         if(response.data.teacherName!=""){
  this.userIdTeacherListVlue=response.data.teacherName.split(','); //字符串转换为数组格式
            }
                this.form = response.data;
                this.open = true;
                this.title = "修改班级信息数据";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.clazzId != null) {
                        const StudentID = this.userIdStudentListVlue.join(',')   //数组格式转换为字符串
                        const TeacherID = this.userIdTeacherListVlue.join(',')
                       this.form.userName=StudentID //重新赋值操作 学生id
                       this.form.teacherName=TeacherID //重新赋值操作教师id
                        updateClazz(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        const StudentID = this.userIdStudentListVlue.join(',')    //数组格式转换为字符串
                         const TeacherID = this.userIdTeacherListVlue.join(',')
                       this.form.userName=StudentID //重新赋值操作 学生id
                       this.form.teacherName=TeacherID //重新赋值操作教师id
                        addClazz(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const clazzIds = row.clazzId || this.ids;
            this.$modal.confirm('是否确认删除班级信息数据编号为"' + clazzIds + '"的数据项？').then(function() {
                return delClazz(clazzIds);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => {});
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('wxinfo/clazz/export', {
                ...this.queryParams
            }, `clazz_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
<style lang="scss" scope>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>