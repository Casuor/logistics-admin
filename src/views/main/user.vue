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
        <el-select v-model="value" placeholder="用户状态">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-divider direction="vertical"></el-divider>
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true" icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="delUser" icon="el-icon-delete" :disabled="delStatus">批量删除
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
        @selection-change="handleSelectionChange">
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
          prop="roleStatus"
          label="用户状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tooltip :content="'用户状态: ' +scope.row.roleStatus " placement="top">
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
          <el-button type="text" @click="assignRole(scope.row.userId)">分配角色</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="resetPwd(scope.row.userId)">修改密码</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editForm(scope.row.userId)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.userId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->


    <!--    start dialog-->

    <!--    end dialog-->
  </div>
</template>

<script>


export default {
  name: 'User',
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      delStatus: true,
      searchForm: {
        userId: '',
        userName: '',
        phoneNumber: '',
        userRole: '',
        signDate: '',
        roleStatus: true
      },
      options: [
        {
          value: 'normal',
          label: '正常'
        },
        {
          value: 'disable',
          label: '禁用'
        }
      ],
      value: '',
      tableData: [
        {
          userId: '',
          userName: '李大宝',
          phoneNumber: '110',
          userRole: '超级管理员',
          signDate: '2021-11-30',
          roleStatus: true
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

    },
    resetPwd() {

    },
    editForm() {

    },
    confirmToDelete() {

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
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
</style>