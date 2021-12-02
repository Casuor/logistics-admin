<template>
  <el-container>
    <el-aside width="49%">
      <el-card shadow="never" class="left-card">
        <i>我要下单</i>
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="招商平台">
            <el-select v-model="form.region" placeholder="请选择活动区域">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="下单时间">
            <el-col :span="11">
              <el-date-picker type="datetime" placeholder="选择日期" v-model="form.date1" style="width: 100%;">
              </el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="产品">
            <el-select v-model="form.region" placeholder="请选择活动区域">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发货量">
            <el-input-number v-model="num" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
          </el-form-item>
          <el-form-item label="发货人">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="发货人联系电话">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="收货人">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="收货人联系电话">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="收货人地址">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="form.desc"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>

      </el-card>
    </el-aside>
    <el-main>
      <el-card shadow="never" class="right-card">
        <i>下单记录</i>
        <!--        <el-empty description="暂时还没下单记录呢，亲"></el-empty>-->
        <el-timeline :reverse="reverse">
          <el-timeline-item placement="top" v-for="(activity, index) in orderHistory" :key="index"
                            :timestamp="activity.timestamp">
            <el-card>
              <h4>{{ activity.content }}</h4>
              <p>{{ activity.name }} 在{{ activity.timestamp }}下了一单，请查收</p>
              <el-divider></el-divider>
              <div class="btn-detail">
                <el-button type="text" @click="table = true" class="btn-order-detail">订单详情</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" @click="orderStatusVisible = true" class="btn-order-detail">订单状态</el-button>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-main>
    <el-drawer
        title="订单详情"
        :visible.sync="table"
        direction="rtl"
        size="50%">
      <el-table :data="gridData">
        <el-table-column property="date" label="日期" width="150"></el-table-column>
        <el-table-column property="name" label="姓名" width="200"></el-table-column>
        <el-table-column property="address" label="地址"></el-table-column>
      </el-table>
    </el-drawer>
    <el-dialog title="订单状态" :visible.sync="orderStatusVisible">
      <el-steps align-center :active="1" finish-status="success"  style="margin-top: 20px">
        <el-step title="下单成功" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="已发货" description="这是一段很长很长很长的描述性文字"></el-step>
      </el-steps>
    </el-dialog>
  </el-container>
</template>

<script>
export default {
  name: "placeOrder",
  data() {
    return {
      table: false,
      orderStatusVisible: false,
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
      reverse: true,
      orderHistory: [{
        id: '1',
        name: '王大锤',
        content: '活动按期开始',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '通过审核',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '创建成功',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '创建成功',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '创建成功',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '创建成功',
        timestamp: '2018-04-03 20:46',
      }, {
        name: '王大锤',
        content: '创建成功',
        timestamp: '2018-04-03 20:46',
      }
      ],
    }
  },
  methods: {
    getOrders(id) {

    }
  }
}
</script>

<style>
.left-card {
  width: 100%;
}

.right-card {
  width: 100%;
}

.el-aside {
  overflow-x: hidden;
}

.el-main {
  padding: 0 0 0 6px;
  overflow-x: hidden;
}

.btn-detail {
  float: right;
}


</style>