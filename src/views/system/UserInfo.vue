<template>
  <div>
    <!--start button-->
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input placeholder="请输入用户名称..." v-model="searchForm.username" clearable @clear="initUserInfo"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="initUserInfo(searchForm.username)">查找</el-button>
      </el-form-item>

      <el-form-item>
        <el-select v-model="searchForm.status" placeholder="用户状态" clearable
                   @change="handleSelChange">
          <el-option v-for="item in userStatusTree" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-divider direction="vertical"></el-divider>
      <el-form-item>
        <el-button type="primary"
                   @click="dialogFormVisibleOfInsert = true;dialogTitle='新增用户';radioBtnStatus = true;editUserForm={};selectDisabled=false;"
                   icon="el-icon-circle-plus-outline">新增
          <!--          v-if="hasPermission('system:userInfo:insert')"-->
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="deleteUser(searchForm.id)" icon="el-icon-delete"
                   :disabled="delBtnStatus">批量删除
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button :loading="downloadLoading" style="margin:0 0 20px 20px;" type="primary" icon="el-icon-document"
                   @click="handleDownload">
          导出用户
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
        v-loading="loading"
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        :default-sort="{prop: 'created', order: 'ascending'}"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="id"
          width="100"
          label="用户ID">
      </el-table-column>
      <el-table-column
          prop="username"
          width="150"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop=""
          width="150"
          label="角色名称">
        <template slot-scope="scope">
          <el-tag v-for="item in scope.row.sysRoles" :key="item.id">{{ item.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="phone"
          width="150"
          label="手机号码">
      </el-table-column>
      <el-table-column
          prop="created"
          width="200"
          sortable
          label="创建时间">
      </el-table-column>
      <el-table-column
          prop="status"
          label="用户状态"
          width="150"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 1" type="success">启用</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="operate"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="assignRole(scope.row.id)">分配角色</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="resetPwd(scope.row.id,scope.row.username)">重置密码</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editUserInfo(scope.row.id);radioBtnStatus=false;selectDisabled=true;">编辑
          </el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->


    <!--    start Pagination-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :total="total">
    </el-pagination>
    <!--    END Pagination-->

    <!--    start insert and edit form dialog-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisibleOfInsert">
      <el-form :model="editUserForm" :rules="editUserFormRules" ref="editUserForm" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editUserForm.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-alert
              title="初始密码为1207"
              :closable="false"
              type="info"
              effect="dark"
              style="line-height: 12px;"
          ></el-alert>
        </el-form-item>
        <el-form-item label="用户权限" prop="role">
          <el-select v-model="editUserForm.roleId" multiple placeholder="请选择角色" @change="selectRole"
                     :disabled="selectDisabled">
            <el-option
                v-for="item in roleTreeData"
                :key="item.roleId"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="editUserForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="editUserForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0" :disabled="radioBtnStatus">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfInsert = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('editUserForm')">确认</el-button>
      </div>
    </el-dialog>
    <!--    end insert and edit form dialog-->

    <!--    start assignRole dialog-->
    <el-dialog title="分配角色" :visible.sync="dialogFormVisibleOfRole" width="600px">
      <el-form :model="assignRoleForm" ref="assignRoleForm">
        <el-tree
            :data="roleTreeData"
            show-checkbox
            default-expand-all
            node-key="id"
            ref="roleTreeDataTree"
            highlight-current
            :props="defaultProps"
        >
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfRole = false">取 消</el-button>
        <el-button type="primary" @click="submitAssignRole('assignRoleForm')">确认</el-button>
      </div>
    </el-dialog>
    <!--    end assignRole dialog-->
  </div>
</template>

<script>
import {parseTime} from '../../utils/index'


export default {
  name: 'UserInfo',
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      dialogFormVisibleOfRole: false,
      dialogFormVisibleOfResetPwd: false,
      selectDisabled: false,
      loading: false,
      dialogTitle: '',
      activeValue: "启用",
      inactiveValue: "禁用",
      radioBtnStatus: false,


      //start export excel
      downloadLoading: false,
      autoWidth: true,
      bookType: 'xlsx',
      filename: '用户数据',
      //end export excel


      delBtnStatus: true,
      selItemVal: '',
      multipleSelection: [],

      current: 1,
      size: 10,
      total: 0,

      searchForm: {},
      assignRoleForm: {},

      defaultProps: {
        children: 'children',
        label: 'name',
      },
      roleTreeData: [],
      userStatusTree: [
        {
          value: '1',
          label: '启用'
        }, {
          value: '0',
          label: '禁用'
        },
      ],
      tableData: [],
      editUserForm: {
        roleId: ''
      },
      editUserFormRules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入正确的手机号', trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    this.initUserInfo()
    this.$axios.get("/sys/role/list").then(res => {
      this.roleTreeData = res.data.data.records
      console.log("角色树：", this.roleTreeData);
    })
  },
  methods: {
    //初始化表格
    initUserInfo() {
      this.loading = true;
      this.$axios.get('/sys/user/list', {
        params: {
          name: this.searchForm.username,
          current: this.current,
          size: this.size
        }
      }).then((res) => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        if (this.total === 0) {
          this.$notify({
            title: '提示',
            dangerouslyUseHTMLString: true,
            message: '<strong><i>查不到这个逼啊，亲！</i></strong>'
          });
          return;
        }
        this.loading = false;
      })
    },
    //分配角色
    assignRole(id) {
      if (id === 1) {
        this.$message({
          message: '该用户是超级管理员，不可编辑！',
          type: 'error'
        });
        return;
      }
      this.dialogFormVisibleOfRole = true
      this.$axios.get('/sys/user/info/' + id).then(res => {
        this.assignRoleForm = res.data.data
        this.editUserForm.roleId = res.data.data.id
        let roleIds = []
        res.data.data.sysRoles.forEach(row => {
          roleIds.push(row.id)
        })
        // console.log(roleIds)
        this.$refs.roleTreeDataTree.setCheckedKeys(roleIds)
      })
    },
    //提交分配角色请求
    submitAssignRole(formName) {
      if (formName === "assignRoleForm") {
        let roleIds = this.$refs.roleTreeDataTree.getCheckedKeys()

        console.log("roleIds:", this.assignRoleForm.id)

        this.$axios.post('/sys/user/role/' + this.assignRoleForm.id, roleIds).then(res => {
          this.$message({
            showClose: true,
            message: '操作成功！',
            type: 'success',
            onClose: () => {
              this.initUserInfo()
            }
          });
          this.dialogFormVisibleOfRole = false
        })
      }
      if (formName === "editUserForm") {
        let roleIds = this.editUserForm.roleId
        console.log("roleIds 调式:", roleIds)
        //拿不到当前新增用户id
        this.$axios.post('/sys/user/role/' + this.editUserForm.id, roleIds);
      }
    },
    selectRole(value) {
      console.log("selectRole: " + value)
    },
    //重置密码
    resetPwd(id, username) {
      if (id === 1) {
        this.$message({
          message: '该用户是超级管理员，不可编辑！',
          type: 'error'
        });
        return;
      }
      this.$confirm('将重置用户【' + username + '】的密码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('/sys/user/repass/' + id).then((res) => {
        })
        this.$message({
          type: 'success',
          message: '重置成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    //编辑用户信息
    editUserInfo(id) {
      if (id === 1) {
        this.$message({
          message: '该用户是超级管理员，不可编辑！',
          type: 'error'
        });
        return;
      }
      this.dialogTitle = "编辑用户"
      this.$axios.get('/sys/user/info/' + id).then(res => {
        this.editUserForm = res.data.data
        let roleIds = []
        res.data.data.sysRoles.forEach(row => {
          roleIds.push(row.id)
        })
        this.editUserForm.roleId = roleIds;
        console.log("测试：", this.editUserForm.roleId)
        this.dialogFormVisibleOfInsert = true
      })
    },
    //根据用户状态筛选
    handleSelChange(value) {
      console.log("select:", value)
      this.loading = true;
      this.$axios.get('/sys/user/search', {
        params: {
          status: this.searchForm.status,
          current: this.current,
          size: this.size
        }
      }).then((res) => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false;
      })
    },
    //多选multipleSelection删除
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delBtnStatus = val.length === 0;
    },
    //删除用户信息
    deleteUser(id) {
      if (id === 1) {
        this.$message({
          message: '该用户是超级管理员，不可删除！',
          type: 'error'
        });
        return;
      }
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = []
        if (id) {
          ids.push(id)
        } else {
          this.multipleSelection.forEach(row => {
            ids.push(row.id)
          })
        }
        // 发起del请求
        this.$axios.post('/sys/user/delete', ids).then((res) => {
          this.initUserInfo();
        })
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
    //新增或编辑用户信息
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('userForm调试1:', this.editUserForm);
          this.$axios.post('/sys/user/' + (this.editUserForm.id ? 'update' : 'insert'), this.editUserForm)
              .then(res => {
                this.editUserForm.id = res.data.data.id;
                this.submitAssignRole(formName)
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.initUserInfo()
                  }
                });
                this.dialogFormVisibleOfInsert = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //分页：
    //分页选择每页条数改变
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.initUserInfo()
    },
    //处理当前页面改变
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.initUserInfo()
    },
    //数据导出
    handleDownload() {
      this.downloadLoading = true
      import('../../plugins/export_excel').then(excel => {
        const tHeader = ['Id', '用户名', '权限', '手机号', '创建日期', '用户状态']
        const filterVal = ['id', 'username', 'roleId', 'phone', 'created', 'status']
        const list = this.tableData
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
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