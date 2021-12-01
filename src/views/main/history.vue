<template>
  <div class="block">
    <el-timeline :reverse="reverse">
      <el-timeline-item placement="top" v-for="(activity, index) in orderHistory" :key="index"
                        :timestamp="activity.timestamp">
        <el-card>
          <h4>{{ activity.content }}</h4>
          <p>{{ activity.name }} 在{{ activity.timestamp }}下了一单，请查收</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {
  name: "history",
  data() {
    return {
      reverse: true,
      orderHistory: [{
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
      }]
    }
  }
}
</script>

<style scoped>
.block {
  width: 80%;
  margin: 0 auto;
}

</style>