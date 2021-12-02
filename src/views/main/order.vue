<template>
  <div>
    <el-card shadow="never">
      <i class="el-icon-search">筛选</i>
      <el-form :inline="true" :model="formInline" width="10%">
        <el-form-item label="订单编号：" prop="name">
          <el-input></el-input>
        </el-form-item>
        <el-form-item label="下单时间：" prop="name">
          <el-date-picker
              v-model="value1"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单状态：" prop="name">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户姓名：" prop="name">
          <el-input></el-input>
        </el-form-item>
        <el-form-item label="产品名称：" prop="name">
          <el-input></el-input>
        </el-form-item>
        <el-form-item>
          <el-button plain>重置
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button plain>搜索
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card shadow="never" class="main-card">
      <i class="el-icon-document">数据列表</i>
    </el-card>

    <!--    start table-->
    <el-table
        class="main-table"
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
          prop="userName"
          width="150"
          label="招商平台">
      </el-table-column>
      <el-table-column
          prop="userRole"
          width="150"
          label="用户姓名">
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          width="150"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          width="150"
          label="产品数量">
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          width="150"
          label="运费">
      </el-table-column>
      <el-table-column
          prop="phoneNumber"
          width="150"
          label="发货平台">
      </el-table-column>
      <el-table-column
          prop="signDate"
          width="150"
          sortable
          label="订单创建时间">
      </el-table-column>
      <el-table-column
          prop="signDate"
          width="150"
          sortable
          label="下单时间">
      </el-table-column>
      <el-table-column
          prop="signDate"
          width="150"
          sortable
          label="订单编号">
      </el-table-column>
      <el-table-column
          prop="operate"
          fixed="right"
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
  </div>
</template>


<script>
export default {
  name: "order",
  data() {
    return {
      formInline: {
        user: '',
        region: ''
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value1: '',
      value2: ''
    };
  }
};
</script>


<style scoped>
.main-card {
  margin-top: 25px;
  width: 100%;
}

.main-table {
  margin-top: 25px;
}
</style>