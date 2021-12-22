<template>
  <div>
    <!--start button-->
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input
            placeholder="请输入名称..."
            v-model="searchForm.name"
            @clear="initTables"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="initTables(searchForm.name)">查找</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true;dialogTitle='新增菜单';radioBtnStatus=true;"
                   icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="signalDelete(searchForm.id)" icon="el-icon-delete" :disabled="deleteStatus">
          批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <!--end button-->
    <!--    start table-->
    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        v-loading="loading"
        element-loading-text="拼命加载中"
        stripe
        :border="true"
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="name"
          label="角色名称">
      </el-table-column>
      <el-table-column
          prop="code"
          label="权限编码">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="权限描述">
      </el-table-column>
      <el-table-column
          prop="status"
          label="角色状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.status===1">启用</el-tag>
          <el-tag type="danger" size="small" v-else-if="scope.row.status===0">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="operate"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="assignPermission(scope.row.id)">分配权限</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editFormHandler(scope.row.id);radioBtnStatus=false;">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="signalDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->

    <!--    start Pagination-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        background
        :total="total">
    </el-pagination>
    <!--    END Pagination-->

    <!-- start add dialogForm-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisibleOfInsert" :before-close="closeRoleForm">
      <el-form :model="roleForm" :rules="roleFormRules" ref="roleForm" label-width="100px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="code">
          <el-input v-model="roleForm.code"></el-input>
        </el-form-item>

        <el-form-item label="权限描述" prop="remark">
          <el-input v-model="roleForm.remark"></el-input>
        </el-form-item>
        <el-form-item label="角色状态" prop="status">
          <el-radio-group v-model="roleForm.status">
            <el-radio :label=1>启用</el-radio>
            <el-radio :label=0 :disabled="radioBtnStatus">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose('roleForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('roleForm')">提交</el-button>
      </div>
    </el-dialog>
    <!-- end add dialogForm-->

    <!--    start assignPermission dialog-->
    <el-dialog title="分配权限" :visible.sync="dialogFormVisibleOfPermission" :before-close="closePermissionForm">
      <el-form :model="permissionForm" ref="permissionForm">
        <el-tree
            :data="permissionData"
            show-checkbox
            default-expand-all
            node-key="id"
            ref="permissionDataTree"
            highlight-current
            :props="defaultProps">
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose('permissionForm')">取 消</el-button>
        <el-button type="primary" @click="submitPermissionFormForm('permissionForm')">提交</el-button>
      </div>
    </el-dialog>
    <!--    end assignPermission dialog-->

  </div>
</template>

<script>
export default {
  name: "RoleInfo",
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      dialogFormVisibleOfPermission: false,
      radioBtnStatus: false,
      dialogTitle: "",
      deleteStatus: true,
      loading: false,

      total: 0,
      size: 10,
      current: 1,

      searchForm: {},
      roleForm: {},
      tableData: [],
      multipleSelection: [],

      permissionForm: {},
      permissionData: [],
      defaultProps: {
        children: 'children',
        label: 'name',
      },

      roleFormRules: {
        name: [
          {required: true, message: '请输入菜单名称', trigger: 'blur'},
        ],
        code: [
          {required: true, message: '请选择权限类型', trigger: 'change'}
        ],
        status: [
          {required: true, message: '请至少选择一个状态', trigger: 'blur'}
        ]
      },
    }
  },
  created() {
    this.initTables();
    this.$axios.get('/sys/menu/list').then(res => {
      console.log("permissionData", res.data.data)
      this.permissionData = res.data.data
    })
  },
  methods: {
    //初始化数据，分页及查找
    initTables() {
      this.loading = true;
      console.log("分页：", this.size, this.current)
      this.$axios.get("/sys/role/list", {
        params: {
          name: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
      this.loading = false;
    },
    //编菜单回显
    editFormHandler(id) {
      this.$axios.get('/sys/role/info/' + id).then(res => {
        console.log("role info: ", res.data.data);
        this.roleForm = res.data.data
        this.dialogFormVisibleOfInsert = true;
      })
    },
    //重置form表单数据
    resetForm(formName) {
      this.$refs[formName].resetFields();
      if (formName === "roleForm") {
        this.dialogFormVisibleOfInsert = false;
        this.roleForm = {};
      }
      if (formName === "permissionForm") {
        this.dialogFormVisibleOfPermission = false;
        this.permissionForm = {};
      }

    },
    //关闭对话框行为
    handleClose(formName) {
      if (formName === "roleForm" || formName === "permissionForm") {
        console.log("formName:", formName)
        this.resetForm(formName);
      }
    },
    closeRoleForm() {
      this.resetForm("roleForm");
    },
    closePermissionForm() {
      this.resetForm("permissionForm");
    },
    //删除角色
    signalDelete(id) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 发起del请求
        let ids = []
        if (id) {
          ids.push(id)
        } else {
          this.multipleSelection.forEach(row => {
            ids.push(row.id)
          })
        }
        ids.forEach(id => {
          if (id === 1) {
            this.$message({
              type: 'warning',
              message: '该角色为超级管理员，不可删除!',
            })
          } else {
            this.$axios.post('/sys/role/delete', ids).then((res) => {
              this.initTables();
            })
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //分配权限
    assignPermission(id) {
      this.dialogFormVisibleOfPermission = true;
      this.$axios.get("/sys/role/info/" + id).then(res => {
        console.log("permissionForm:", res.data.data);
        this.$refs.permissionDataTree.setCheckedKeys(res.data.data.menuIds);
        this.permissionForm = res.data.data
      })
    },
    //权限提交
    submitPermissionFormForm(formName) {
      let menuIds = this.$refs.permissionDataTree.getCheckedKeys()

      console.log(menuIds)

      this.$axios.post('/sys/role/premiss/' + this.permissionForm.id, menuIds).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.initTables()
          }
        });
        this.handleClose('permissionForm')
      })
    },
    //处理新增和编辑
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/role/' + (this.roleForm.id ? 'update' : 'insert'), this.roleForm)
              .then(res => {
                console.log("res:", res.data.statusCode)
                this.$message({
                  showClose: true,
                  message: '操作成功!',
                  type: 'success',
                  onClose: () => {
                    this.initTables()
                  }
                });
                this.handleClose('roleForm')
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //表格单选
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.deleteStatus = val.length === 0;
      console.log("select:", this.multipleSelection);
      console.log("length:", this.multipleSelection.length);
    },
    //分页选择每页条数改变
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.initTables()
    },
    //处理当前页面改变
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.initTables()
    }
  }
}
</script>

<style scoped>
.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>