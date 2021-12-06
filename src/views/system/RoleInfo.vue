<template>
  <div>
    <!--start button-->
    <el-form :inline="true" :model="roleForm">
      <el-form-item>
        <el-input
            placeholder="请输入名称..."
            v-model="roleForm.roleName"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchItem(roleForm.roleName)">查找</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true" icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="delItem" icon="el-icon-delete" :disabled="delStatus">批量删除
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
        stripe
        :border="true"
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="roleName"
          label="角色名称">
      </el-table-column>
      <el-table-column
          prop="roleId"
          label="权限编码">
      </el-table-column>
      <el-table-column
          prop="roleDescription"
          label="权限描述">
      </el-table-column>
      <el-table-column
          prop="roleStatus"
          label="角色状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tooltip :content="'角色状态: ' +scope.row.roleStatus " placement="top">
            <el-switch
                v-model="scope.row.roleStatus"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="启用"
                inactive-value="关闭">
            </el-switch>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
          prop="operate"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="assignPermission(scope.row.roleId)">分配权限</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editForm(scope.row.roleId)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->

    <!--    start Pagination-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :total="totalPages">
    </el-pagination>
    <!--    END Pagination-->
    <!-- start add dialogForm-->
    <el-dialog title="新增角色" :visible.sync="dialogFormVisibleOfInsert" :before-close="handleClose">
      <el-form :model="roleForm" :rules="rules" ref="searchForm" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleForm.roleName"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="roleId">
          <el-input v-model="roleForm.roleId"></el-input>
        </el-form-item>

        <el-form-item label="权限描述" prop="roleDescription">
          <el-input v-model="roleForm.roleDescription"></el-input>
        </el-form-item>
        <el-form-item label="角色状态" prop="roleStatus">
          <el-radio-group v-model="roleForm.roleStatus">
            <el-radio label="启用"></el-radio>
            <el-radio label="关闭"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfInsert = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('searchForm')">提交</el-button>
      </div>
    </el-dialog>
    <!-- end add dialogForm-->

    <!--    start assignPermission dialog-->
    <el-dialog title="分配权限" :visible.sync="dialogFormVisibleOfPermission">
      <el-tree
          :data="permissionList"
          show-checkbox
          default-expand-all
          node-key="id"
          ref="permissionList"
          highlight-current>
      </el-tree>
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
      delStatus: true,
      currentPage: 1,
      pageSize: 20,
      totalPages: 200,
      roleForm: {
        roleName: '',
        roleId: '',
        roleDescription: '',
        roleStatus: ''
      },
      tableData: [],
      permissionList: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      rules: {
        roleName: [
          {required: true, message: '请输入菜单名称', trigger: 'blur'},
          {min: 3, max: 6, message: '长度在 3 到 6 个字符', trigger: 'blur'}
        ],
        roleId: [
          {required: true, message: '请选择权限类型', trigger: 'change'}
        ],
        roleStatus: [
          {type: 'array', required: true, message: '请至少选择一个状态', trigger: 'change'}
        ]
      },
    }
  },
  created() {
    this.initTables();
  },
  methods: {
    initTables() {
      this.$axios.get('/system/roleInfo/init').then(res => {
        console.log(res.data.data);
        this.tableData = res.data.data;
      })
    },
    searchItem() {
    },
    delItem() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // //发起del请求
        // this.$axios.post('/main/menuList/del' + id).then((res) => {
        //   this.initTables();
        // })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    editForm() {
      this.dialogFormVisibleOfInsert = true;
    },
    confirmToDelete() {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发起del请求
        // this.$axios.post('/main/menuList/del' + id).then((res) => {
        //   this.initTables();
        // })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    assignPermission() {
      this.dialogFormVisibleOfPermission = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交成功
          this.$axios.post('/menuList/add' + (this.addForm.id ? 'edit' : 'add'), this.addForm).then(() => {
            this.$message({
              message: '添加成功！！！',
              type: 'success',
              onClose: () => {
                //更新表中数据
                this.initTables();
              }
            });
          })
          //添加成功后隐藏dialogForm
          this.dialogFormVisibleOfInsert = false;
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogFormVisibleOfInsert = false;
      this.searchForm = {};
    },
    handleClose() {
      this.resetForm('searchForm');
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
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