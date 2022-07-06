<template>
  <div class="main">
    <div class="pay-main-warp column">
      <div class="lists-box row m-column">
        <div class="setUrl">
          <i class="el-icon-arrow-left" @click="goback"></i>
          <div class="setTitle">
            問い合わせフォーム
          </div>
          <div class="setContent">
            <div style="margin-bottom: 30px;">
              もっとワクワークする世の中を
            </div>
          </div>
          <div class="feedContent">
            <div class="feed-title">
              お問合せカテゴリー
            </div>
            <el-select style="width: 100%;" v-model="value" placeholder="お問合せカテゴリーを選択">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
            <div class="feed-title" style="margin-top: 47px;">
              お問い合わせ内容
            </div>
            <el-input type="textarea" resize="none" :rows="15" placeholder="お問合せ内容を入力" v-model="textarea">
            </el-input>
          </div>
          <div class="btns">
            <el-button class="btn_confirm" size="medium" @click="sendMail()">送信</el-button>
            <el-button class="btn_cancel" size="medium" @click="goback()">マイページへ</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import {
    getsendMail
  } from '../../api/api.js';
  export default {
    name: 'app',
    data() {
      return {
        links: [],
        centerDialogVisible: false,
        url: "",
        multies: true,
        passForm: {
          url1: "",
          url2: "",
          url3: "",
          url4: "",
          url5: ""
        },
        options: [{
          value: '商品に関する問い合わせ1',
          label: '商品に関する問い合わせ'
        }, {
          value: 'プランの問い合わせ2',
          label: 'プランの問い合わせ'
        }, {
          value: 'その他',
          label: 'その他'
        }],
        value: '',
        textarea: ""
      }
    },
    created() {},
    components: {},
    methods: {
      goback() {
        this.$router.push('/my')
      },
      sendMail() {
        let data = {
          content: this.textarea,
          subject: this.value,

        }
        let that = this
        getsendMail(data).then(res => {
          console.log(123, res)
          if(res.code == 200){
            that.$message({
            	message: "送信しました",
            	type: "success",
            });
            that.goback()
          }
        }).catch(err => {
          console.log(321, res)
        })
      },
      goPage: function(page) {
        this.$router.push(page)
      },
      tobuy() {
        let that = this
        that.centerDialogVisible = true
      }
    },

  }
</script>

<style scoped>
  @import '../../assets/css/base.css';

  .feed-title {
    text-align: left;
    color: #686868;
    font-size: 16px;
  }

  .el-icon-arrow-left {
    line-height: 25px;
    color: #83c3c5;
    font-weight: 900;
    position: absolute;
    left: 40px;
    top: 40px;
  }

  .for_item {
    display: flex;
    align-items: center;
    color: #CECECE;
  }

  .btns {
    margin-top: 18px;
  }

  .btn_confirm {
    width: 30%;
    background: #83C3C5;
    color: #fff;
    font-weight: bold !important;
  }

  .btn_confirm:hover {
    width: 30%;
    background: #83C3C5;
    color: #fff;
    font-weight: bold !important;
  }

  .btn_cancel {
    width: 30%;
    border: 1px solid #83C3C5;
    color: #83C3C5;
  }

  .warning {
    color: #FF8181;
    font-size: 14px;
    margin-top: 17px;
  }

  .setInput {
    margin-top: 42px;
  }


  .setUrl {
    width: 772px;
    box-sizing: border-box;
    padding: 35px 48px;
    background: #fff;
    margin: 0 auto;
    box-shadow: 0px 10px 20px #63AEB0;
    border-radius: 12px;
    text-align: center;
    position: relative;
  }

  .setContent {
    color: #CACACA;
    font-size: 12px;
  }

  .setTitle {
    color: #83C3C5;
    font-size: 20px;
    font-weight: bold;
  }

  .pay-main-warp {
    width: 60%;
    /* height: 692px; */
    margin: 0 auto;
    margin-top: 6%;
    padding: 38px 42px;
    padding-bottom: 0
  }

  .colbtns {
    margin-top: 30px;
  }

  .confirmContent {
    border-radius: 20px;
  }

  .home-btn {
    position: absolute;
    top: 6rem;
    left: 24rem;
    border: 1.5px solid #fff;
    box-sizing: border-box;
    border-radius: 6px;
    font-size: 14px;
    line-height: 20px;
    text-align: center;
    color: #fff;
    padding: 5px 15px;
  }

  .pay-main-warp .title-box {
    text-align: center;
    margin-bottom: 40px
  }



  .pay-main-warp .title-box .title {
    font-weight: 700;
    font-size: 30px;
    line-height: 35px;
    color: #FFF
  }

  .pay-main-warp .lists-box {
    justify-content: space-between
  }

  .pay-main-warp .lists-box .lists-item {
    justify-content: flex-start;
    align-items: flex-start;
    flex: 1;
    position: relative;
    height: 355px;
    box-shadow: 0px 10px 20px #63AEB0;
    border: none;
    border-radius: 12px;
    background: #fff;
    margin-right: 30px;
    padding: 20px;
  }

  .pay-main-warp .lists-box .lists-item:hover {
    border: 4px solid #1E7578;
    cursor: pointer;
  }

  .pay-main-warp .lists-box .lists-item:last-child {
    margin: 0;
  }

  .pay-main-warp .lists-box .lists-item div.title {
    font-weight: 500;
    font-size: 12px;
    line-height: 20px;
    color: #686868;
  }

  .pay-main-warp .lists-box .lists-item div.price-box {}

  .pay-main-warp .lists-box .lists-item div.price-box .price {
    font-weight: 700;
    font-size: 22px;
    line-height: 32px;
    color: #6D6D6D;
    margin-right: 5px;
  }

  .pay-main-warp .lists-box .lists-item div.price-box .price-unit {
    font-weight: 500;
    font-size: 12px;
    line-height: 38px;
    color: #BEBEBE;
  }

  .pay-main-warp .lists-box .lists-item div.des {
    font-size: 12px;
    line-height: 17px;
    color: #9F9B9B;
    padding: 10px 0;
  }

  .pay-main-warp .lists-box .lists-item div.item-list {
    font-weight: 500;
    font-size: 12px;
    line-height: 27px;
    color: #686868;
  }

  .pay-main-warp .lists-box .lists-item .btns-box {
    width: calc(100% - 40px);
    position: absolute;
    bottom: 23px;
  }

  .pay-main-warp .lists-box .lists-item .btns-box .join-btn {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #63AEB0;
    font-weight: 700 !important;
    font-size: 14px;
    width: 100%;
  }

  .colbtns .join-btn {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #63AEB0;
    font-weight: 700 !important;
    font-size: 14px;
    width: 100%;
    margin: 0 0 12px !important;
  }

  .colbtns .join-btn:last-child {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #fff;
    font-weight: 700 !important;
    font-size: 14px;
    width: 100%;
    background: #63AEB0;
    margin: 0 0 12px !important;
  }

  .pay-main-warp .lists-box .lists-item .btns-box .join-btn span {
    font-weight: bolder !important;
    font-size: 14px;
  }

  .pay-main-warp .lists-box .checked {
    background: #1E7578;
    box-shadow: 0px 10px 20px #63AEB0;
  }

  .pay-main-warp .lists-box .checked div {
    color: #fff !important;
  }

  .pay-main-warp .lists-box .checked .btns-box .join-btn span {
    color: #1E7578;
  }

  @media(max-width:460px) {
    .pay-main-warp {
      width: 90%;
      padding: 5%;
      height: auto;
      margin-bottom: 3rem;
      margin-top: 3rem;
    }

    .home-btn {
      position: absolute;
      top: .5rem;
      left: 1rem;
    }

    .m-column {
      display: flex !important;
      flex-direction: unset;
    }

    .setUrl {
      width: 400px !important;
      box-sizing: border-box;
      padding: 20px !important;
    }

    .btn_cancel {
      width: 45% !important;
      text-align: center;
    }

    .btn_confirm {
      width: 45% !important;
    }

    .pay-main-warp .lists-box .lists-item {
      margin: 0;
      margin-bottom: 1.5rem;
      flex: none;
    }
  }
</style>
<style type="text/css">
  .main .pay-main-warp .lists-box .setUrl .feedContent .el-select .el-input .el-input__inner {
    height: 48px;
    background: rgba(189, 189, 189, 0.18) !important;
    border-radius: 8px
  }

  .main .pay-main-warp .lists-box .setUrl .el-textarea textarea {
    background: rgba(189, 189, 189, 0.18) !important;
  }

  .main .pay-main-warp .lists-box .setUrl .setInput .el-form .el-form-item .el-form-item__content {
    flex: 1
  }

  label {
    color: #CECECE !important;
  }
</style>
