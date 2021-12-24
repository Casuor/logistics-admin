<template>
  <el-container>
    <el-aside width="49%">
      <el-card shadow="never" class="left-card">
        <i>我要下单</i>
        <el-form ref="orderForm" :model="orderForm" label-width="80px">
          <el-form-item label="招商平台">
            <el-select v-model="orderForm.platform" placeholder="请选择下单平台" @change="selectPlatform">
              <el-option
                  v-for="item in platformOptions"
                  :label="item.label" :value="item.label"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="产品">
            <el-select v-model="orderForm.orderproductid" placeholder="请选择下单产品">
              <el-option
                  v-for="item in productOptions"
                  :label="item.label" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发货量">
            <el-input-number v-model="orderForm.orderquality" @change="" :min="1" :max="10"></el-input-number>
          </el-form-item>
          <el-form-item label="发货人">
            <el-input v-model="orderForm.sendername"></el-input>
          </el-form-item>
          <el-form-item label="发货人联系电话">
            <el-input v-model="orderForm.senderphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人">
            <el-input v-model="orderForm.receivername"></el-input>
          </el-form-item>
          <el-form-item label="收货人联系电话">
            <el-input v-model="orderForm.receiverphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人地址">
            <el-input v-model="orderForm.receiveraddress"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="orderForm.orderremark"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('orderForm')">立即创建</el-button>
            <el-button>重置</el-button>
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
    <!--    dialog and drawer-->
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
      <el-steps align-center :active="1" finish-status="success" style="margin-top: 20px">
        <el-step title="下单成功" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="已发货" description="这是一段很长很长很长的描述性文字"></el-step>
      </el-steps>
    </el-dialog>
    <!-- END   dialog and drawer-->

  </el-container>
</template>

<script>
export default {
  name: "PlaceOrder",
  data() {
    return {
      table: false,
      orderStatusVisible: false,
      orderForm: {},
      gridData: [],
      reverse: true,
      platformOptions: [{label: '平台一'}, {label: '平台二'}],
      productOptions: [
        {label: '香蕉', id: 101},
        {label: '柚子', id: 202}
      ],
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
  created() {
    this.$axios.get('/sys/userInfo').then(res => {
      console.log("NavBar:userInfo:", res.data.data)
      this.orderForm.uid = res.data.data.id;
    });
  },
  methods: {
    //创建订单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/order/' + (this.orderForm.id ? 'update' : 'order'), this.orderForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    // this.initProduct()
                  }
                });
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    selectPlatform(value) {
      console.log("已选择：", value)
    },
  }
}
</script>

<style scoped>
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