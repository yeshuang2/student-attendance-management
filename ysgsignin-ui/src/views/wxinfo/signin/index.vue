<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" 
    label-width="110px">
      <el-form-item label="签到班级id" prop="signinClazzid">
        <el-input
          v-model="queryParams.signinClazzid"
          placeholder="请输入签到班级id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签到班级名称" prop="signinClazzname">
        <el-input
          v-model="queryParams.signinClazzname"
          placeholder="请输入签到班级名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签到老师的名称" prop="signinTeachername">
        <el-input
          v-model="queryParams.signinTeachername"
          placeholder="请输入签到老师的名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签到的学生" prop="signinUsername">
        <el-input
          v-model="queryParams.signinUsername"
          placeholder="请输入签到的学生"
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
          v-hasPermi="['wxinfo:signin:add']"
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
          v-hasPermi="['wxinfo:signin:edit']"
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
          v-hasPermi="['wxinfo:signin:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wxinfo:signin:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="signinList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="签到ID" width="75" align="center" prop="signinId" />
      <el-table-column label="签到班级ID" width="90" align="center" prop="signinClazzid" />
      <el-table-column label="签到班级名称" width="147" align="center" prop="signinClazzname" />
      <el-table-column label="签到发起人" width="90" align="center" prop="signinTeachername" />
      <el-table-column label="受签学生" width="117" align="center" prop="signinUsername" />
      <el-table-column label="学签到状态" width="117" align="center" prop="signinStatus">
       <template slot-scope="scope">
           <el-tag v-show="scope.row.signinStatus==0"  size="small"><span style="color:red">未签到</span> </el-tag>
          <el-tag v-show="scope.row.signinStatus==1" size="small"><span style="color:green">已签到</span></el-tag>
            <el-tag v-show="scope.row.signinStatus==2" size="small"><span style="color:green">补签到</span></el-tag>
              <el-tag v-show="scope.row.signinStatus==3" size="small"><span style="color:orange">申请请假</span></el-tag>
             <el-tag v-show="scope.row.signinStatus==4" size="small"><span style="color:green">已签到</span></el-tag>
      </template>
      </el-table-column>

      <el-table-column label="签到的类型" align="center" width="91" prop="signinType" />


  <el-table-column label="签到信息-1" width="160" align="center" prop="signinAddimg">
        <!-- prop="signinAddimg" .split(",") -->
        <template v-slot="scope">
        <div v-if="scope.row.signinType==='二维码签到'">
          <span class="images" v-viewer>
            <img
              style="padding: 5px; cursor: pointer"
              width="100px"
              height="100px"
              :src="scope.row.signinAddimg"/>
          </span>
       </div>
         <span  v-else>{{scope.row.signinAddimg}}</span>
    </template>
      </el-table-column>

         <el-table-column label="签到信息-2" align="center" width="91" prop="signinAddimgreturn">
        <template v-slot="scope">
          <span   style="color:orange">{{scope.row.signinAddimgreturn}}</span>
    </template>
       </el-table-column>

      <el-table-column label="发起签到时间" align="center" prop="signinStarttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signinStarttime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="结束签到时间" align="center" prop="signinEndttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signinEndttime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="帐号状态" align="center" prop="status" /> -->
      <el-table-column label="备注" align="center" width="120" prop="remark" />
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wxinfo:signin:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wxinfo:signin:remove']"
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

    <!-- 添加或修改学生签到数据表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="签到班级id" prop="signinClazzid">
          <el-input v-model="form.signinClazzid" placeholder="请输入签到班级id" />
        </el-form-item>
        <el-form-item label="签到班级名称" prop="signinClazzname">
          <el-input v-model="form.signinClazzname" placeholder="请输入签到班级名称" />
        </el-form-item>
        <el-form-item label="签到老师的名称" prop="signinTeachername">
          <el-input v-model="form.signinTeachername" placeholder="请输入签到老师的名称" />
        </el-form-item>
        <el-form-item label="签到的学生" prop="signinUsername">
          <el-input v-model="form.signinUsername" placeholder="请输入签到的学生" />
        </el-form-item>
    <el-form-item label="学生签到状态" prop="signinStatus">
       <el-select v-model="form.signinStatus" placeholder="请输入签到的学生签到状态">
       <el-option label="未签到" value="0"></el-option>
       <el-option label="已签到" value="1"></el-option>
       <el-option label="补签到" value="2"></el-option>
        <el-option label="请假" value="3"></el-option>
                <el-option label="已签到" value="4"></el-option>
    </el-select>
          <!-- <el-input v-model="form.signinAddimg" placeholder="请输入签到的学生签到状态" /> -->
        </el-form-item>

        <el-form-item label="签到信息-1" prop="signinAddimg">
          <el-input v-model="form.signinAddimg" placeholder="请输入签到的学生签到信息-1" />
        </el-form-item>

        <el-form-item label="签到信息-2" prop="signinAddimgreturn">
          <el-input v-model="form.signinAddimgreturn" placeholder="请输入签到的学生签到信息-2" />
        </el-form-item>

        <el-form-item label="发起签到时间" prop="signinStarttime">
          <el-date-picker clearable
            v-model="form.signinStarttime"
           value-format="yyyy-MM-dd HH:mm:ss"
                 type="date"
            placeholder="请选择发起签到时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束签到时间" prop="signinEndttime">
          <el-date-picker clearable
            v-model="form.signinEndttime"
            type="date"
           value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择结束签到时间">
          </el-date-picker>
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
  
  </div>
</template>

<script>
import { listSignin, getSignin, delSignin, addSignin, updateSignin } from "@/api/wxinfo/signin";

export default {
  name: "Signin",
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
      // 学生签到数据表表格数据
      signinList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        signinClazzid: null,
        signinClazzname: null,
        signinTeachername: null,
        signinUsername: null,
        signinStatus: null,
        signinType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        signinEndttime: [
          { required: true, message: "结束签到时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学生签到数据表列表 */
    getList() {
      this.loading = true;
      listSignin(this.queryParams).then(response => {
        this.signinList = response.rows;
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
        signinId: null,
        signinClazzid: null,
        signinClazzname: null,
        signinTeachername: null,
        signinUsername: null,
        signinStatus: null,
        signinAddimg:null,
        signinType: null,
        signinStarttime: null,
        signinEndttime: null,
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
      this.ids = selection.map(item => item.signinId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生签到数据表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const signinId = row.signinId || this.ids
      getSignin(signinId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生签到数据表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.signinId != null) {
            updateSignin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSignin(this.form).then(response => {
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
      const signinIds = row.signinId || this.ids;
      this.$modal.confirm('是否确认删除学生签到数据表编号为"' + signinIds + '"的数据项？').then(function() {
        return delSignin(signinIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wxinfo/signin/export', {
        ...this.queryParams
      }, `signin_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
  .my-label {
    background: #E1F3D8;
  }

  .my-content {
    background: #FDE2E2;
  }
</style>