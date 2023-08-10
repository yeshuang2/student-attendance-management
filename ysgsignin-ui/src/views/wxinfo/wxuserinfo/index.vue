<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="微信用户昵称" prop="wxUsernickname">
        <el-input
          v-model="queryParams.wxUsernickname"
          placeholder="请输入微信用户昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="wxUsergender">
        <el-select v-model="queryParams.wxUsergender" placeholder="请选择性别0为男,1为女" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="注册日期" prop="registerDate">
        <el-date-picker clearable
          v-model="queryParams.registerDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择注册日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="最后登录日期" prop="lastloginDate">
        <el-date-picker clearable
          v-model="queryParams.lastloginDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最后登录日期">
        </el-date-picker>
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
          v-hasPermi="['wxinfo:wxuserinfo:add']"
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
          v-hasPermi="['wxinfo:wxuserinfo:edit']"
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
          v-hasPermi="['wxinfo:wxuserinfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wxinfo:wxuserinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxuserinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="微信登录id" align="center" prop="wxUserid" />
      <el-table-column label="用户唯一标识" align="center" prop="wxUseropenid" />
      <el-table-column label="微信用户昵称" align="center" prop="wxUsernickname" />
      <el-table-column label="性别" align="center" prop="wxUsergender">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.wxUsergender"/>
        </template>
      </el-table-column>
      <el-table-column label="用户头像" align="center" prop="wxUseravatarurl" >
         <template v-slot="scope">
          <span class="images" v-viewer>
            <img
              style="padding: 5px; cursor: pointer"
              width="100px"
              height="100px"
              :src="scope.row.wxUseravatarurl"
            />
          </span>
        </template>
</el-table-column>

      <el-table-column label="是否为管理员" align="center" prop="wxIsadmin">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.wxIsadmin"/>
        </template>
      </el-table-column>
      <el-table-column label="注册日期" align="center" prop="registerDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registerDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后登录日期" align="center" prop="lastloginDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastloginDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户状态" align="center" prop="wxUserstatus">
              <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.wxUserstatus"/>
        </template>
    </el-table-column>
      <!-- <el-table-column label="微信用户code 前端传来的" align="center" prop="wxUsercode">
      </el-table-column> -->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wxinfo:wxuserinfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wxinfo:wxuserinfo:remove']"
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

    <!-- 添加或修改微信授权登录表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="用户唯一标识" prop="wxUseropenid">
          <el-input v-model="form.wxUseropenid" placeholder="请输入用户唯一标识" />
        </el-form-item>
        <el-form-item label="微信用户昵称" prop="wxUsernickname">
          <el-input v-model="form.wxUsernickname" placeholder="请输入微信用户昵称" />
        </el-form-item>
        <el-form-item label="性别0为男,1为女" prop="wxUsergender">
          <el-select v-model="form.wxUsergender" placeholder="请选择性别0为男,1为女">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户头像图片的 URL" prop="wxUseravatarurl">
          <el-input v-model="form.wxUseravatarurl" placeholder="请输入用户头像图片的 URL" />
        </el-form-item>
        <el-form-item label="是否为管理员" prop="wxIsadmin">
          <el-select v-model="form.wxIsadmin" placeholder="请选择是否为管理员">
            <el-option
              v-for="dict in dict.type.sys_common_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册日期" prop="registerDate">
          <el-date-picker clearable
            v-model="form.registerDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择注册日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后登录日期" prop="lastloginDate">
          <el-date-picker clearable
            v-model="form.lastloginDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后登录日期">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="微信用户code 前端传来的" prop="wxUsercode">
    <el-input v-model="form.wxUsercode" placeholder="微信用户code" />
        </el-form-item> -->

     <el-form-item label="用户状态" prop="wxUserstatus">
          <el-select v-model="form.wxUserstatus" placeholder="请选择用户状态">
            <el-option
              v-for="dict in dict.type.sys_common_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
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
  </div>
</template>

<script>
import { listWxuserinfo, getWxuserinfo, delWxuserinfo, addWxuserinfo, updateWxuserinfo } from "@/api/wxinfo/wxuserinfo";

export default {
  name: "Wxuserinfo",
  dicts: ['sys_common_status', 'sys_user_sex'],
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
      // 微信授权登录表表格数据
      wxuserinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wxUsernickname: null,
        wxUsergender: null,
        registerDate: null,
        lastloginDate: null,
        wxUserstatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wxUsergender: [
          { required: true, message: "性别0为男,1为女不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信授权登录表列表 */
    getList() {
      this.loading = true;
      listWxuserinfo(this.queryParams).then(response => {
        this.wxuserinfoList = response.rows;
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
        wxUserid: null,
        wxUseropenid: null,
        wxUsernickname: null,
        wxUsergender: null,
        wxUseravatarurl: null,
        wxIsadmin: null,
        registerDate: null,
        lastloginDate: null,
        wxUserstatus: null,
        wxUsercode: null,
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
      this.ids = selection.map(item => item.wxUserid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加微信授权登录表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wxUserid = row.wxUserid || this.ids
      getWxuserinfo(wxUserid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信授权登录表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wxUserid != null) {
            updateWxuserinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWxuserinfo(this.form).then(response => {
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
      const wxUserids = row.wxUserid || this.ids;
      this.$modal.confirm('是否确认删除微信授权登录表编号为"' + wxUserids + '"的数据项？').then(function() {
        return delWxuserinfo(wxUserids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wxinfo/wxuserinfo/export', {
        ...this.queryParams
      }, `wxuserinfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
