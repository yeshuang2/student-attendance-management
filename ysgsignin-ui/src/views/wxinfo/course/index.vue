<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="wxCoursename">
        <el-input
          v-model="queryParams.wxCoursename"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程学分" prop="wxCourseCode">
        <el-input
          v-model="queryParams.wxCourseCode"
          placeholder="请输入课程学分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wxinfo:course:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wxinfo:course:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wxinfo:course:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wxinfo:course:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table :row-class-name="tableRowClassName"  border v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程ID" width="65" align="center" prop="wxCourseid" />
      <el-table-column label="课程名称" align="center" width="195" prop="wxCoursename" />
      <el-table-column label="课程学分" align="center" width="85" prop="wxCourseCode" />
      <el-table-column label="授课班级名称" align="center" prop="wxCourseClazzid" label-width="400px"  />
      <!-- <el-table-column label="授课老师id" align="center" prop="wxCourseTeacherid" /> -->
      <!-- <el-table-column label="授课老师姓名" align="center" prop="wxCourseTeachername" /> -->
      <el-table-column label="备注" align="center" width="202" prop="remark" />
      <el-table-column label="操作" align="center" width="115" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wxinfo:course:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wxinfo:course:remove']"
          >删除</el-button>
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

    <!-- 添加或修改课程信息数据表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="860px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="课程名称"  width="98" prop="wxCoursename">
          <el-input v-model="form.wxCoursename"  placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程学分" width="108" prop="wxCourseCode">
          <el-input v-model="form.wxCourseCode" placeholder="请输入课程学分" />
        </el-form-item>

        <el-form-item label="授课班级名称" width="408">
        <el-select  filterable allow-create default-first-option   
          v-model="CourseClazzidListVlue" clearable style="width: 100%;"  placeholder="请选择授课班级信息" multiple>
             <el-option 
                v-for="itemclazz in classidNameList"
                 :key="itemclazz.clazzId"
                :label="itemclazz.clazzName"
                :value="itemclazz.clazzName"
             ></el-option>
        </el-select>

          <!-- <el-input v-model="form.wxCourseClazzid" placeholder="请输入授课班级名称" /> -->
        </el-form-item>
        <!-- <el-form-item label="授课老师id" prop="wxCourseTeacherid">
          <el-input v-model="form.wxCourseTeacherid" placeholder="请输入授课老师id" />
        </el-form-item> -->
        <!-- <el-form-item label="授课老师姓名" prop="wxCourseTeachername">
          <el-input v-model="form.wxCourseTeachername" placeholder="请输入授课老师姓名" />
        </el-form-item> -->
        <el-form-item label="备注" prop="remark" width="108">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCourse, getCourse, delCourse, addCourse, updateCourse } from "@/api/wxinfo/course"; //获取课程信息
import {getBanJiNameClazzInfo } from "@/api/wxinfo/clazz"; //获取班级信息
export default {
  name: "Course",
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
      // 课程信息数据表表格数据
      courseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wxCoursename: null,
        wxCourseCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      CourseClazzidListVlue:[], //班级信息
      classidNameList:[], //数据初始化的值
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询课程信息数据表列表 */
    getList() {
      this.loading = true;
      listCourse(this.queryParams).then(response => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
      getClazzIDName(){
            // 获取班级的id 和 班级名称
            getBanJiNameClazzInfo().then((res)=>{
              this.classidNameList=res.data
            })
      },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        wxCourseid: null,
        wxCoursename: null,
        wxCourseCode: null,
        wxCourseClazzid: null,
        wxCourseTeacherid: null,
        wxCourseTeachername: null,
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
      this.ids = selection.map(item => item.wxCourseid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getClazzIDName(); //获取班级的信息
      this.reset();
       this.CourseClazzidListVlue=[];  //班级的进行空数组 
      this.open = true;
      this.title = "添加课程信息数据表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wxCourseid = row.wxCourseid || this.ids
      getCourse(wxCourseid).then(response => {
           this.CourseClazzidListVlue=[];  //班级的进行空数组 
            this.getClazzIDName()          // 查询教班级id 名称
            if(response.data.wxCourseClazzid!=""){
           this.CourseClazzidListVlue=response.data.wxCourseClazzid.split(','); //字符串转换为数组格式
            }
        this.form = response.data;
        this.open = true;
        this.title = "修改课程信息数据表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wxCourseid != null) {
          const wxCourseClazz = this.CourseClazzidListVlue.join(',')   //数组格式转换为字符串
         this.form.wxCourseClazzid=wxCourseClazz //重新赋值操作 班级id 名称
            updateCourse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
       
          } else {
            const CourseClazzID = this.CourseClazzidListVlue.join(',')    //数组格式转换为字符串
              this.form.wxCourseClazzid=CourseClazzID //重新赋值操作 班级id 名称
            addCourse(this.form).then(response => {
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
      const wxCourseids = row.wxCourseid || this.ids;
      this.$modal.confirm('是否确认删除课程信息数据表编号为"' + wxCourseids + '"的数据项？').then(function() {
        return delCourse(wxCourseids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wxinfo/course/export', {
        ...this.queryParams
      }, `course_${new Date().getTime()}.xlsx`)
    },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex %2=== 0) {
          return 'warning-row';
        } else if (rowIndex %2=== 1) {
          return 'success-row';
        }
        return '';
      }
  }
};
</script>
<style>
  .el-table .warning-row {
    background: #e7e5d0;
  }

  .el-table .success-row {
    background: #c1a299;
  }
</style>