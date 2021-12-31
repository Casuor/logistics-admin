<template>
  <div class="block">
    <el-empty description="暂时还没下单记录呢，亲" v-show="empty"></el-empty>
    <el-timeline :reverse="reverse" ref="orders">
      <el-timeline-item placement="top" v-for="(item, index) in orderHistory" :key="index"
                        :timestamp="item.created">
        <el-card>
          <h4 style="color:#5c6e79;">订单号：{{ item.realordernum }}</h4>
          <el-alert effect="dark" type="error" :closable="false" v-show="item.status===0">{{ item.username }}
            在{{
              item.created
            }}下了一单
            {{ item.orderquality }} 件 {{ item.orderproduct }}
          </el-alert>
          <el-alert effect="dark" type="success" :closable="false" v-show="item.status===1">{{ item.username }}
            在{{
              item.created
            }}下了一单
            {{ item.orderquality }} 件 {{ item.orderproduct }}
          </el-alert>
          <el-divider></el-divider>
          <div class="btn-detail">
            <el-button type="text" @click="orderDetailHandler(item.id);"
                       class="btn-order-detail">订单详情
            </el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" @click="orderHandler(item.id)" class="btn-order-detail">
              处理订单
            </el-button>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>

    <el-dialog title="订单详情" :visible.sync="orderDetailVisible" width="800px">
      <el-card shadow="never">
        <el-descriptions>
          <el-descriptions-item label="下单产品">{{ orderDetails.orderproduct }}</el-descriptions-item>
          <el-descriptions-item label="下单数量">{{ orderDetails.orderquality }}</el-descriptions-item>
          <el-descriptions-item label="发货人">{{ orderDetails.sendername }}</el-descriptions-item>
          <el-descriptions-item label="发货人电话">{{ orderDetails.senderphone }}</el-descriptions-item>
          <el-descriptions-item label="收货人">{{ orderDetails.receivername }}</el-descriptions-item>
          <el-descriptions-item label="收货人电话">{{ orderDetails.receiverphone }}</el-descriptions-item>
          <el-descriptions-item label="备注">
            <el-tag size="small">{{ orderDetails.orderremark }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="收货人地址">{{ orderDetails.receiveraddress }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag v-if="orderDetails.status===1">已发货</el-tag>
            <el-tag v-if="orderDetails.status===0">未发货</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </el-dialog>

    <el-dialog title="订单处理" :visible.sync="orderStatusVisible" width="700px">
      <el-form ref="form" :model="dealOrder" label-width="200px">
        <el-form-item label="订单受理人">
          <el-input v-model="dealOrder.orderhandler"></el-input>
        </el-form-item>
        <el-form-item label="发货平台">
          <el-select
              v-model="dealOrder.logplatform"
              placeholder="请选择发货平台"
              @change="handleSelectChange"
          >
            <el-option
                v-for="item in options"
                :key="item.label"
                :label="item.label"
                :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单运费">
          <el-input v-model="dealOrder.orderfare"></el-input>
        </el-form-item>
        <el-form-item label="订单编号">
          <el-input v-model="dealOrder.realordernum"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">完成发货</el-button>
          <el-button @click="orderStatusVisible=false;dealOrder={};">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "OrderHistory",
  data() {
    return {
      orderDetailVisible: false,
      orderStatusVisible: false,
      empty: false,
      reverse: true,
      orderHistory: [],
      orderDetails: {},
      dealOrder: {},
      options: [{
        label: '京东快递'
      },
        {
          label: '韵达快递'
        },
        {
          label: '申通快递'
        }
      ]
    }
  },
  created() {
    this.getOrderCount();
    this.initOrderHistory();
  },
  methods: {
    getOrderCount() {
      this.$axios.get('/sys/order/count').then(res => {
        this.$store.commit("setOrderCount", res.data.data.length)
      })
    },
    initOrderHistory() {
      this.$axios.get('/sys/order/history').then(res => {
        this.orderHistory = res.data.data;
        if (res.data.data.length === 0) {
          this.empty = true;
        }
      })
    },
    orderDetailHandler(id) {
      this.orderDetailVisible = true;
      this.$axios.get('/sys/order/info/' + id).then(res => {
        this.orderDetails = res.data.data;
      })
    },
    orderHandler(id) {
      this.orderStatusVisible = true;
      this.$axios.get('/sys/order/info/' + id).then(res => {
        this.dealOrder = res.data.data;
      })
    },
    handleSelectChange() {
    },
    //处理订单
    onSubmit() {
      this.$axios.post('/sys/order/' + (this.dealOrder.id ? 'deal' : 'order'), this.dealOrder).then(res => {
        console.log("返回的数据：", res.data.data);
        this.$message({
          showClose: true,
          message: '操作成功',
          type: 'success',
          onClose: () => {
            this.initOrderHistory()
            this.getOrderCount()
            this.orderStatusVisible = false;
          }
        });
      })
    }
  }
}
</script>

<style scoped>
.block {
  width: 80%;
  margin: 0 auto;
}

.el-input {
  width: 350px;
}
</style>