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
  </div>
</template>


<script>
export default {
  name: "purchase",
  data() {
    return {
      formInline: {
        user: '',
        region: ''
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
</style>