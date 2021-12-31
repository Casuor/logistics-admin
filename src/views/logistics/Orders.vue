<template>
  <div>
    <el-card shadow="never">
      <i class="el-icon-search">筛选</i>
      <el-form :inline="true" :model="searchForm" width="10%">
        <el-form-item label="订单编号：">
          <el-input placeholder="请输入订单编号..."
                    @clear="initOrderTable"
                    v-model="searchForm.number"
                    :tabindex="searchForm.tabindex"
                    @focus="searchForm.tabindex ='1'"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="下单时间：">
          <el-date-picker
              v-model="searchForm.date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              @change="HandleDateSearch(searchForm.date)">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单状态：" prop="name">
          <el-select
              v-model="searchForm.status"
              placeholder="请选择订单状态"
              @change="handleSelectChange"
          >
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户姓名：">
          <el-input placeholder="请输入用户名称..."
                    @clear="initOrderTable"
                    v-model="searchForm.username"
                    :tabindex="searchForm.tabindex"
                    @focus="searchForm.tabindex ='2'"
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="产品名称：">
          <el-input placeholder="请输入产品名称..."
                    @clear="initOrderTable"
                    v-model="searchForm.product"
                    :tabindex="searchForm.tabindex"
                    @focus="searchForm.tabindex ='3'"
                    clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button plain @click="searchForm={};initOrderTable();">重置
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button plain
                     @click="HandleSearch(searchForm.number,searchForm.username,searchForm.product,searchForm.tabindex)">
            搜索
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button :loading="downloadLoading" style="margin:0 0 20px 20px;" type="primary" icon="el-icon-document"
                     @click="handleDownload">
            导出订单
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
        :loading="loading"
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
          prop="created"
          fixed="left"
          width="180"
          sortable
          label="订单创建时间">
      </el-table-column>
      <el-table-column
          prop="username"
          width="150"
          label="用户姓名">
      </el-table-column>
      <el-table-column
          prop="platform"
          width="150"
          label="招商平台">
      </el-table-column>
      <el-table-column
          prop="orderproduct"
          width="150"
          label="下单产品">
      </el-table-column>
      <el-table-column
          prop="orderprice"
          width="150"
          label="订单总价">
      </el-table-column>
      <el-table-column
          prop="logplatform"
          width="150"
          label="发货平台">
      </el-table-column>
      <el-table-column
          prop="orderfare"
          width="150"
          label="物流费用">
      </el-table-column>
      <el-table-column
          prop="logstatus"
          width="150"
          label="物流状态">
      </el-table-column>
      <el-table-column
          prop="orderhandler"
          width="150"
          label="订单处理人">
      </el-table-column>
      <el-table-column
          prop="status"
          sortable
          label="订单状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 1" type="success">已处理</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 0" type="danger">未处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="dealdate"
          fixed="right"
          sortable
          width="180"
          label="订单处理时间">
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
  </div>
</template>


<script>
import {parseTime} from "../../utils/index";

export default {
  name: "Orders",
  data() {
    return {
      tableData: [],
      loading: false,
      size: 10,
      total: 0,
      current: 1,
      //start export excel
      downloadLoading: false,
      autoWidth: true,
      bookType: 'xlsx',
      filename: '用户数据',
      //end export excel
      searchForm: {},
      options: [
        {
          value: '1',
          label: '已处理'
        },
        {
          value: '0',
          label: '未处理'
        }],
    };
  },
  created() {
    this.initOrderTable()
  },
  methods: {
    initOrderTable() {
      this.loading = true;
      this.$axios.get('/sys/order/init', {
        params: {
          username: this.searchForm.username,
          number: this.searchForm.number,
          product: this.searchForm.product,
          status: this.searchForm.status,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false;
      })
    },
    //分页选择每页条数改变
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.initOrderTable()
    },
    //处理当前页面改变
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.initOrderTable()
    },
    //table row选择
    handleSelectionChange(value) {
      console.log("选择了：", value)
    },
    HandleSearch(number, name, product, index) {
      console.log(number, name, product, index)
      if (index === "1") {
        this.initOrderTable(number)
      }
      if (index === "2") {
        this.initOrderTable(name)
      }
      if (index === "3") {
        this.initOrderTable(product)
      }
    },
    HandleDateSearch(val) {
      let start = val[0];
      let end = val[1];
      this.loading = true;
      this.$axios.get('/sys/order/interval', {
        params: {
          start: start,
          end: end,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false;
      })
    },
    handleSelectChange() {
      this.loading = true;
      this.$axios.get('/sys/order/status', {
        params: {
          status: this.searchForm.status,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false;
      })
    },
    //导出数据
    handleDownload() {
      this.downloadLoading = true
      import('../../plugins/export_excel').then(excel => {
        const tHeader = ['Id', '用户名', '权限', '手机号', '创建日期', '用户状态']
        const filterVal = ['userId', 'userName', 'userRole', 'phoneNumber', 'signDate', 'userStatus']
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

.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>