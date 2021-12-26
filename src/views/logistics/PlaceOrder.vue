<template>
  <el-container>
    <el-aside width="49%" v-show="asideHidden">
      <el-card shadow="never" class="left-card">
        <i>我要下单</i>
        <el-form ref="orderForm" :model="orderForm" label-width="80px" :rules="orderFormRules">

          <el-form-item label="招商平台" prop="platform">
            <el-select v-model="orderForm.platform" placeholder="请选择下单平台" @change="selectPlatform">
              <el-option
                  v-for="item in platformOptions"
                  :label="item.label" :value="item.label"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="产品" prop="orderproduct">
            <el-select v-model="orderForm.orderproduct" placeholder="请选择下单产品">
              <el-option
                  v-for="item in productOptions"
                  :label="item.name" :value="item.name"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="发货量" prop="orderquality">
            <el-input-number v-model="orderForm.orderquality" @change="" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="发货人" prop="sendername">
            <el-input v-model="orderForm.sendername"></el-input>
          </el-form-item>
          <el-form-item label="发货人联系电话" prop="senderphone">
            <el-input v-model="orderForm.senderphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人" prop="receivername">
            <el-input v-model="orderForm.receivername"></el-input>
          </el-form-item>
          <el-form-item label="收货人联系电话" prop="receiverphone">
            <el-input v-model="orderForm.receiverphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人地址" prop="receiveraddress">
            <el-input v-model="orderForm.receiveraddress"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="orderForm.orderremark"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('orderForm')">立即创建</el-button>
            <el-button @click="resetForm('orderForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-aside>

    <el-main style="overflow: hidden;">
      <el-card shadow="never" class="right-card" style="overflow-y:scroll;">
        <i>下单记录</i>
        <el-empty description="暂时还没下单记录呢，亲" v-show="hidden"></el-empty>
        <el-timeline :reverse="reverse" ref="orders" v-show="show">
          <el-timeline-item placement="top" v-for="(item, index) in orderHistory" :key="index"
                            :timestamp="item.created">
            <el-card>
              <h4 style="color:#5c6e79;">订单号：{{ item.virtualordernum }}</h4>
              <el-alert effect="dark" type="error" :closable="false">{{ user.username }} 在{{
                  item.created
                }}下了一单
                {{ item.orderquality }} 件 {{ item.orderproduct }}
              </el-alert>
              <el-divider></el-divider>
              <div class="btn-detail">
                <el-button type="text" @click="orderDetailHandler(item.id);asideHidden=false;"
                           class="btn-order-detail">
                  订单详情
                </el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" @click="orderStatusVisible = true" class="btn-order-detail">订单状态</el-button>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-main>
    <!-- start  drawer-->
    <el-drawer
        title="订单详情"
        :visible.sync="tableDrawerVisible"
        direction="rtl"
        :before-close="handleClose"
        size="50%">
      <el-card shadow="never">
        <el-descriptions>
          <el-descriptions-item label="发货平台">{{ orderForm.platform }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">{{ orderForm.status }}</el-descriptions-item>
          <el-descriptions-item label="下单产品">{{ orderForm.orderproduct }}</el-descriptions-item>
          <el-descriptions-item label="下单数量">{{ orderForm.orderquality }}</el-descriptions-item>
          <el-descriptions-item label="发货人">{{ orderForm.sendername }}</el-descriptions-item>
          <el-descriptions-item label="发货人电话">{{ orderForm.senderphone }}</el-descriptions-item>
          <el-descriptions-item label="收货人">{{ orderForm.receivername }}</el-descriptions-item>
          <el-descriptions-item label="收货人电话">{{ orderForm.receiverphone }}</el-descriptions-item>
          <el-descriptions-item label="备注">
            <el-tag size="small">{{ orderForm.orderremark }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="收货人地址">{{ orderForm.receiveraddress }}</el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-card shadow="never">
        <i>修改订单</i>
        <el-form ref="orderForm" :model="orderForm" label-width="100px" :rules="orderFormRules">
          <el-form-item label="招商平台" prop="platform">
            <el-select v-model="orderForm.platform" placeholder="请选择下单平台" @change="selectPlatform">
              <el-option
                  v-for="item in platformOptions"
                  :label="item.label" :value="item.label"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="产品" prop="orderproduct">
            <el-select v-model="orderForm.orderproduct" placeholder="请选择下单产品">
              <el-option
                  v-for="item in productOptions"
                  :label="item.name" :value="item.name"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="发货量" prop="orderquality">
            <el-input-number v-model="orderForm.orderquality" @change="" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="发货人" prop="sendername">
            <el-input v-model="orderForm.sendername"></el-input>
          </el-form-item>
          <el-form-item label="发货人联系电话" prop="senderphone">
            <el-input v-model="orderForm.senderphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人" prop="receivername">
            <el-input v-model="orderForm.receivername"></el-input>
          </el-form-item>
          <el-form-item label="收货人联系电话" prop="receiverphone">
            <el-input v-model="orderForm.receiverphone"></el-input>
          </el-form-item>
          <el-form-item label="收货人地址" prop="receiveraddress">
            <el-input v-model="orderForm.receiveraddress"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="orderForm.orderremark"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('orderForm');orderForm={};">提交修改</el-button>
            <el-button @click="resetForm('orderForm');">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

    </el-drawer>
    <!-- END  drawer-->
    <!--start dialog order status-->
    <el-dialog title="订单状态" :visible.sync="orderStatusVisible">
      <el-steps align-center :active="1" finish-status="success" style="margin-top: 20px">
        <el-step title="下单成功" description="下单成功"></el-step>
        <el-step title="已发货" description="已发货"></el-step>
      </el-steps>
    </el-dialog>
    <!--    end dialog order status-->
  </el-container>
</template>

<script>
export default {
  name: "PlaceOrder",
  data() {
    return {
      tableDrawerVisible: false,
      orderStatusVisible: false,
      orderForm: {},
      reverse: true,
      show: true,
      hidden: false,
      asideHidden: true,
      platformOptions: [{label: '平台一'}, {label: '平台二'}],
      productOptions: [],
      orderFormRules: {
        platform: [
          {required: true, message: '请选择选择招商平台', trigger: 'blur'}
        ],
        orderproduct: [
          {required: true, message: '请选择产品', trigger: 'blur'}
        ],
        orderquality: [
          {required: true, message: '请填写发货量', trigger: 'blur'}
        ],
        sendername: [
          {required: true, message: '请填写发货人姓名', trigger: 'blur'}
        ],
        senderphone: [
          {required: true, message: '请填写发货人联系电话', trigger: 'blur'}
        ],
        receivername: [
          {required: true, message: '请填写收货人姓名', trigger: 'blur'}
        ],
        receiverphone: [
          {required: true, message: '请填写收货人联系电话', trigger: 'blur'}
        ],
        receiveraddress: [
          {required: true, message: '请填写收货人地址', trigger: 'blur'}
        ]
      },
      orderHistory: [],
      user: {},
    }
  },
  created() {
    this.initOrderInfo();
    this.$axios.get('/sys/product/list').then(res => {
      this.productOptions = res.data.data.records;
    })
    this.user = this.$store.state.order.userInfo
    this.orderForm.uid = this.user.id;
  },
  methods: {
    getOrderCount() {
      this.$axios.get('/sys/order/count').then(res => {
        this.$store.commit("setOrderCount", res.data.data.length)
      })
    },
    //初始化订单信息
    initOrderInfo() {
      this.loading = true;
      this.$axios.get('/sys/order/list').then(res => {
        this.orderHistory = res.data.data;
        console.log("order history:", res.data.data)
        this.loading = false
        if (res.data.data.length === 0) {
          this.show = false;
          this.hidden = true;
        }
      })
    },
    //创建订单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/order/' + (this.orderForm.id ? 'update' : 'order'), this.orderForm)
              .then(res => {
                this.asideHidden = true;
                this.tableDrawerVisible = false;
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.initOrderInfo()
                    this.getOrderCount()
                  }
                });
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //订单修改
    orderDetailHandler(id) {
      this.tableDrawerVisible = true;
      this.$axios.get('/sys/order/info/' + id).then(res => {
        this.orderForm = res.data.data;
        console.log("orderDetail", this.orderForm)
      })
    },
    handleClose() {
      this.asideHidden = true;
      this.tableDrawerVisible = false;
      this.orderForm = {};
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
  height: 100%;
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

.el-input {
  width: 350px;
}

span {
  /*margin-top: 25px;*/
}
</style>