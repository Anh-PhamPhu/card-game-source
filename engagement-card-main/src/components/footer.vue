<template>
    <div class="footer-box">
        <div class="footer-text"> <span>利用規約</span>  · <span>{{text}}</span>  <span @click="toFeedback">問い合わせ</span> </div>
        <!-- <div class="footer-text">{{copyright}}</div> -->
    </div>
</template>
<script>
  export default {
    data () {
      return {
        text: 'プライバシーポリシー',
        copyright: '＠All rights deserved'
      }
    },
    components: {},
    methods:{
      toFeedback(){
        this.$router.push('/feedback/feedback')
      }
    }
  }
</script>
<style>
    span{
      cursor: pointer;
    }
    .footer-box {
        width: 100%;
        height: 80px;
        /* position: fixed;
        bottom: 0; */
        margin-top: 5rem;
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        line-height: 30px;
        color: #FFF;
        text-align: center;
    }
    @media (max-width: 460px) {
        .footer-box {
            position: inherit !important;
        }
    }
</style>
