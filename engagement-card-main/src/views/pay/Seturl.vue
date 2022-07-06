<template>
  <div class="main">
    <div class="pay-main-warp column">
      <div class="lists-box row m-column">
        <div class="setUrl">
          <div class="setTitle">
            固定URLの設定
          </div>
          <div class="setContent">
            <div style="margin-bottom: 30px;">
              設定した文字がURLに反映されます。
            </div>
            <div>
              例）engagementcard　と設定した場合のURLは、https://OOOO/engagementcard　となる。
            </div>
          </div>
          <!-- 单条url设置 -->
          <div class="setInput" v-if="!multies">
              <el-input v-model="passForm.url1"></el-input>
          </div>
          <!-- 多条url设置 -->
          <div class="setInput" style="height: 350px; overflow-y: scroll;" v-if="multies">
            <el-form :model="passForm" ref="passForm">
              <el-form-item class="for_item" label="NO.1" prop="password">
                <el-input v-model="passForm.url1"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.2" prop="password">
                <el-input v-model="passForm.url2"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.3" prop="password">
                <el-input v-model="passForm.url3"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.4" prop="password">
                <el-input v-model="passForm.url4"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.5" prop="password">
                <el-input v-model="passForm.url5"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.6" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url6"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.7" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url7"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.8" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url8"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.9" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url9"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.10" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url10"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.11" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url11"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.12" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url12"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.13" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url13"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.14" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url14"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.15" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url15"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.16" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url16"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.17" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url17"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.18" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url18"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.19" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url19"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.20" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url20"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.21" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url21"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.22" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url22"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.23" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url23"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.24" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url24"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.25" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url25"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.26" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url26"></el-input>
				<!-- :disabled="passForm.url1 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.27" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url27"></el-input>
				<!-- :disabled="passForm.url2 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.28" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url28"></el-input>
				<!-- :disabled="passForm.url3 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.29" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url29"></el-input>
				<!-- :disabled="passForm.url4 == ''? false : true " -->
              </el-form-item>
              <el-form-item class="for_item" label="NO.30" prop="password" v-if="plan == '法人プラン'">
                <el-input v-model="passForm.url30"></el-input>
				<!--  :disabled="passForm.url5 == ''? false : true " -->
              </el-form-item>
            </el-form>
          </div>
          <div class="warning">
            ※一度決定すると変更することが出来ません
          </div>
          <div class="btns">
            <el-button class="btn_cancel" size="medium" @click="goPage('/my')">マイページへ</el-button>
            <el-button class="btn_confirm" size="medium" @click="settled">決定</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { setUrl,showUrl} from '../../api/api.js';
  export default {
    name: 'app',
    data() {
      return {
        links: [],
        centerDialogVisible: false,
        url: "",
        multies:true,
        price:0,
        proname:"",
        plan:localStorage.getItem("plan"),
        passForm:{
          url1:"",
          url2:"",
          url3:"",
          url4:"",
          url5:"",
          url6:"",
          url7:"",
          url8:"",
          url9:"",
          url10:"",
          url11:"",
          url12:"",
          url13:"",
          url14:"",
          url15:"",
          url16:"",
          url17:"",
          url18:"",
          url19:"",
          url20:"",
          url21:"",
          url22:"",
          url23:"",
          url24:"",
          url25:"",
          url26:"",
          url27:"",
          url28:"",
          url29:"",
          url30:"",
        }
      }
    },
    created() {
      let that = this
      that.price = that.$route.query.price
      that.proname = that.$route.query.name
			console.log(that.$route.query.name)
    },
    components: {},
    mounted() {
      let that = this
      that.showurl()
    },
    methods: {
      goPage: function(page) {
        this.$router.push(page)
      },
      tobuy() {
        let that = this
        that.centerDialogVisible = true
      },
      showurl(){
        let that = this
        showUrl(that.proname).then((res) => {
          console.log(res)
          if(res.code == 200){
			  if(res.result){
				  if(res.result.userUrlList.length != 0){
				    that.passForm.url1 = res.result.userUrlList[0]
				    that.passForm.url2 = res.result.userUrlList[1] || ""
				    that.passForm.url3 = res.result.userUrlList[2] || ""
				    that.passForm.url4 = res.result.userUrlList[3] || ""
				    that.passForm.url5 = res.result.userUrlList[4] || ""
            that.passForm.url6 = res.result.userUrlList[5] || ""
				    that.passForm.url7 = res.result.userUrlList[6] || ""
				    that.passForm.url8 = res.result.userUrlList[7] || ""
				    that.passForm.url9 = res.result.userUrlList[8] || ""
				    that.passForm.url10 = res.result.userUrlList[9] || ""
            that.passForm.url11 = res.result.userUrlList[10] || ""
				    that.passForm.url12 = res.result.userUrlList[11] || ""
				    that.passForm.url13 = res.result.userUrlList[12] || ""
				    that.passForm.url14 = res.result.userUrlList[13] || ""
				    that.passForm.url15 = res.result.userUrlList[14] || ""
            that.passForm.urll6 = res.result.userUrlList[15] || ""
				    that.passForm.url17 = res.result.userUrlList[16] || ""
				    that.passForm.url18 = res.result.userUrlList[17] || ""
				    that.passForm.url19 = res.result.userUrlList[18] || ""
				    that.passForm.url20 = res.result.userUrlList[19] || ""
            that.passForm.url21 = res.result.userUrlList[20] || ""
				    that.passForm.url22 = res.result.userUrlList[21] || ""
				    that.passForm.url23 = res.result.userUrlList[22] || ""
				    that.passForm.url24 = res.result.userUrlList[23] || ""
				    that.passForm.url25 = res.result.userUrlList[24] || ""
            that.passForm.url26 = res.result.userUrlList[25] || ""
				    that.passForm.url27 = res.result.userUrlList[26] || ""
				    that.passForm.url28 = res.result.userUrlList[27] || ""
				    that.passForm.url29 = res.result.userUrlList[28] || ""
				    that.passForm.url30 = res.result.userUrlList[29] || ""
				  }
			  }
          }
        })
      },
      settled(){
        let that = this
        let data = {}
        data.proName = that.proname
        let price = that.price
        if(price == 5000){
          let array = []
          if(that.passForm.url1 != ""){
            array.push(that.passForm.url1)
          }
          if(that.passForm.url2 != ""){
            array.push(that.passForm.url2)
          }
          if(that.passForm.url3 != ""){
            array.push(that.passForm.url3)
          }
          if(that.passForm.url4 != ""){
            array.push(that.passForm.url4)
          }
          if(that.passForm.url5 != ""){
            array.push(that.passForm.url5)
          }
          if(that.passForm.url6 != ""){
            array.push(that.passForm.url6)
          }
          if(that.passForm.url7 != ""){
            array.push(that.passForm.url7)
          }
          if(that.passForm.url8 != ""){
            array.push(that.passForm.url8)
          }
          if(that.passForm.url9 != ""){
            array.push(that.passForm.url9)
          }
          if(that.passForm.url10 != ""){
            array.push(that.passForm.url10)
          }
          if(that.passForm.url11 != ""){
            array.push(that.passForm.url11)
          }
          if(that.passForm.url12 != ""){
            array.push(that.passForm.url12)
          }
          if(that.passForm.url13 != ""){
            array.push(that.passForm.url13)
          }
          if(that.passForm.url14 != ""){
            array.push(that.passForm.url14)
          }
          if(that.passForm.url15 != ""){
            array.push(that.passForm.url15)
          }
          if(that.passForm.url16 != ""){
            array.push(that.passForm.url16)
          }
          if(that.passForm.url17 != ""){
            array.push(that.passForm.url17)
          }
          if(that.passForm.url18 != ""){
            array.push(that.passForm.url18)
          }
          if(that.passForm.url19 != ""){
            array.push(that.passForm.url19)
          }
          if(that.passForm.url20 != ""){
            array.push(that.passForm.url20)
          }
          if(that.passForm.url21 != ""){
            array.push(that.passForm.url21)
          }
          if(that.passForm.url22 != ""){
            array.push(that.passForm.url22)
          }
          if(that.passForm.url23 != ""){
            array.push(that.passForm.url23)
          }
          if(that.passForm.url24 != ""){
            array.push(that.passForm.url24)
          }
          if(that.passForm.url25 != ""){
            array.push(that.passForm.url25)
          }
          if(that.passForm.url26 != ""){
            array.push(that.passForm.url26)
          }
          if(that.passForm.url27 != ""){
            array.push(that.passForm.url27)
          }
          if(that.passForm.url28 != ""){
            array.push(that.passForm.url28)
          }
          if(that.passForm.url29 != ""){
            array.push(that.passForm.url29)
          }
          if(that.passForm.url30 != ""){
            array.push(that.passForm.url30)
          }
          let str = ""
          for(let i = 0;i<array.length;i++){
            if(i == array.length - 1){
              str += array[i]
            }else{
              str += array[i] + ","
            }
          }
          console.log(str)
          data.userUrl = str
        }else{
          data.userUrl = that.passForm.url1
        }

        setUrl(data).then((res)=>{
          console.log(res)
          if(res.code == 200){
            that.$message({
                message: "設置しました",
                type: 'success',
            });
            that.showurl()
          }
        })

      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/base.css';
  .for_item{
    display: flex;
    align-items: center;
    color: #CECECE;
  }

  .btns {
    margin-top: 42px;
  }

  .btn_confirm {
    width: 40%;
    background: #83C3C5;
    color: #fff;
    font-weight: bold !important;
  }

  .btn_confirm:hover {
    width: 40%;
    background: #83C3C5;
    color: #fff;
    font-weight: bold !important;
  }

  .btn_cancel {
    width: 40%;
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
    width: 542px;
    box-sizing: border-box;
    padding: 35px 48px;
    background: #fff;
    margin: 0 auto;
    box-shadow: 0px 10px 20px #63AEB0;
    border-radius: 12px;
    text-align: center;
  }

  .setContent {
    color: #686868;
    font-size: 16px;
    margin-top: 17px;
  }

  .setTitle {
    color: #83C3C5;
    font-size: 20px;
    font-weight: bold;
  }

  .pay-main-warp {
    width: 60%;
    height: 692px;
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
 .main .pay-main-warp .lists-box .setUrl .setInput .el-form .el-form-item .el-form-item__content .el-input .el-input__inner {
    height: 48px;
    background: rgba(189, 189, 189, 0.18) !important;
    border-radius: 8px
  }

  .main .pay-main-warp .lists-box .setUrl .setInput .el-input .el-input__inner {
     height: 48px;
     background: rgba(189, 189, 189, 0.18) !important;
     border-radius: 8px
   }

  .main .pay-main-warp .lists-box .setUrl .setInput .el-form .el-form-item .el-form-item__content{
    flex: 1
  }

  label{
    color: #CECECE !important;
  }
</style>
