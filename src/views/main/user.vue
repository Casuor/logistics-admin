<template>
  <div>
    <!--start button-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            placeholder="请输入手机号码..."
            v-model="searchForm.phoneNumber"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchUser(searchForm.phoneNumber)">查找</el-button>
      </el-form-item>
      <el-form-item>
        <el-select v-model="selItemVal" placeholder="用户状态" clearable @change="handleSelChange(selItemVal)">
          <el-option
              v-for="item in options"
              :key="item.selItemVal"
              :label="item.label"
              :value="item.selItemVal">
          </el-option>
        </el-select>
      </el-form-item>
      <el-divider direction="vertical"></el-divider>
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true" icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="delUser(selectRow)" icon="el-icon-delete"
                   :disabled="this.selectRow.length === 0||this.delStatus">批量删除
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
        :default-sort="{prop: 'signDate', order: 'descending'}"
        @selection-change="handleSelectionChange"
        @row-click="handleCurrentChange">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="userId"
          width="100"
          label="用户ID">
      </el-table-column>
      <el-table-column
          prop="userName"
          width="150"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="userRole"
          width="150"
          label="用户权限">
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          width="150"
          label="手机号码">
      </el-table-column>
      <el-table-column
          prop="signDate"
          width="150"
          sortable
          label="创建时间">
      </el-table-column>
      <el-table-column
          prop="userStatus"
          label="用户状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tooltip :content="'用户状态: ' +scope.row.userStatus " placement="top">
            <el-switch
                v-model="scope.row.userStatus"
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
          <el-button type="text" @click="assignRole(scope.row.userId)">分配角色</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="resetPwd(scope.row.userId)">修改密码</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editUserInfo(scope.row.userId)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.userId)">删除</el-button>
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

    <!--    start addForm dialog-->
    <el-dialog title="新增用户" :visible.sync="dialogFormVisibleOfInsert">
      <el-form :model="editUserForm" :rules="editUserFormRules" ref="resetPwdForm" label-width="100px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="editUserForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="用户权限" prop="userRole">
          <el-input v-model="editUserForm.userRole"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="editUserForm.phoneNumber"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfInsert = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('addForm')">确认</el-button>
      </div>
    </el-dialog>
    <!--    end addForm dialog-->

    <!--    start assignRole dialog-->
    <el-dialog title="分配角色" :visible.sync="dialogFormVisibleOfRole">
      <el-tree
          :data="RoleData"
          show-checkbox
          default-expand-all
          node-key="id"
          ref="tree"
          highlight-current>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfRole = false">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确认</el-button>
      </div>
    </el-dialog>
    <!--    end assignRole dialog-->

    <!--    start resetPwd dialog-->
    <el-dialog title="修改密码" :visible.sync="dialogFormVisibleOfResetPwd">
      <el-form :model="resetPwdForm" :rules="resetPwdFormRules" ref="resetPwdForm" label-width="100px">
        <el-form-item label="密码" prop="pwd">
          <el-input v-model="resetPwdForm.pwd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="pwd">
          <el-input v-model="resetPwdForm.pwd"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfResetPwd = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('addForm')">确认</el-button>
      </div>
    </el-dialog>
    <!--    end resetPwd dialog-->

    <!--    start editUserForm dialog-->
    <el-dialog title="编辑" :visible.sync="dialogFormVisibleOfEditForm">
      <el-form :model="editUserForm" :rules="editUserFormRules" ref="resetPwdForm" label-width="100px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="editUserForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="用户权限" prop="userRole">
          <el-input v-model="editUserForm.userRole"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="editUserForm.phoneNumber"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfEditForm = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('addForm')">确认</el-button>
      </div>
    </el-dialog>
    <!--    end editUserForm dialog-->
  </div>
</template>

<script>


export default {
  name: 'User',
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      dialogFormVisibleOfRole: false,
      dialogFormVisibleOfResetPwd: false,
      dialogFormVisibleOfEditForm: false,
      delStatus: true,
      selItemVal: '',
      selectRow: [],
      currentPage: 1,
      pageSize: 20,
      totalPages: 200,
      searchForm: {
        phoneNumber: '',
      },
      resetPwdForm: {
        pwd: '123'
      },
      editUserForm: {
        userName: '李大宝',
        phoneNumber: '110',
        userRole: '超级管理员',
        signDate: '2021-11-30',
        userStatus: '启用'
      },
      resetPwdFormRules: {
        pwd: [
          {required: true, message: '请输入密码，长度为 3 到 6 个的数字', trigger: 'blur'},
          {min: 3, max: 6, message: '长度为 3 到 6 个的数字', trigger: 'blur'}
        ]
      },
      editUserFormRules: {},
      options: [
        {
          selItemVal: 'normal',
          label: '正常'
        },
        {
          selItemVal: 'disable',
          label: '禁用'
        }
      ],
      tableData: [
        {
          userId: '121123',
          userName: '李大宝',
          phoneNumber: '110',
          userRole: '超级管理员',
          signDate: '2021-11-30',
          userStatus: '启用'
        },
        {
          userId: '121123',
          userName: '李大宝',
          phoneNumber: '110',
          userRole: '超级管理员',
          signDate: '2021-11-30',
          userStatus: '启用'
        },
        {
          userId: '121123',
          userName: '李大宝',
          phoneNumber: '110',
          userRole: '超级管理员',
          signDate: '2021-11-30',
          userStatus: '启用'
        }
      ],
      RoleData: [
        {
          id: 1,
          label: '超级管理员',
        },
        {
          id: 2,
          label: '管理员',
        }
      ]
    }
  },
  methods: {
    searchUser() {

    },
    delUser() {

    },
    assignRole() {
      this.dialogFormVisibleOfRole = true
    },
    resetPwd() {
      this.dialogFormVisibleOfResetPwd = true
    },
    editUserInfo() {
      this.dialogFormVisibleOfEditForm = true
    },
    confirmToDelete(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
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
    //根据用户状态筛选
    handleSelChange(value) {
      console.log(value);
    },
    handleSelectionChange(val) {
      this.selectRow = val
      console.log(this.selectRow);
      val.forEach(element => {
        console.log(element)
      })
    },
    handleCurrentChange(rows) {
    },
    submitForm() {

    },
    handleSizeChange() {

    },
  },
  created() {
  }
}
</script>

<style scoped>
.el-divider {
  height: 40px;
}

.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>