<template>
  <div class="main-page">
    <iframe src="https://casuormi.neocities.org/" id="mobsf" scrolling="no" frameborder="0" ></iframe>
  </div>
</template>

<script>
export default {
  name: "Index",
  mounted() {
    /**
     * iframe-宽高自适应显示
     */
    function changeMobsfIframe() {
      const mobsf = document.getElementById('mobsf');
      const deviceWidth = document.body.clientWidth;
      const deviceHeight = document.body.clientHeight;
      mobsf.style.width = (Number(deviceWidth) - 200) + 'px'; //数字是页面布局宽度差值
      mobsf.style.height = (Number(deviceHeight) - 131) + 'px'; //数字是页面布局高度差
    }

    changeMobsfIframe()

    window.onresize = function () {
      changeMobsfIframe()
    }
  }
}
</script>

<style scoped>
.main-page {
  width: 100%;
  height: 100%;
}
</style>