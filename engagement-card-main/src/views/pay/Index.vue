<template>
  <div class="main">
    <div class="home-btn" @click="goPage('/')">マイページへ</div>
    <div class="pay-main-warp column">
      <div class="title-box">
        <div class="title">料金プラン</div>
      </div>
      <div class="lists-box row m-column">
        <div class="lists-item column" v-for="(item,index) in list">
          <div class="title" v-text="item.name"></div>
          <div class="price-box row">
            <div class="price" v-text="item.price"></div>
          </div>
          <div class="des" v-text="item.describe"></div>
          <div class="item-list" v-for="(ite,idx) in item.jurisdiction"><i class="el-icon-success"></i>{{ite}}</div>
          <div class="btns-box row row-center">
            <el-button v-if="item.price != '無料/月'" @click="tobuy(item.id,item.price,item.describe,item.name)" class="join-btn" style="font-weight: 700 !important">購入する
            </el-button>
          </div>
        </div>
      </div>
    </div>
    <el-dialog style="border-radius: 15px;" :title="dtitle" :visible.sync="centerDialogVisible"
      custom-class="confirmContent" top="30vh" width="378px" center>
      <span v-text="des"></span>
      <div class="colbtns">
        <el-button class="join-btn" style="font-weight: 700 !important" @click="createPrice('month')">1ヶ月プラン</el-button>
        <el-button class="join-btn" style="font-weight: 700 !important" @click="createPrice('year')">1年プラン</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    plan,
    createPrice,
    createSession,
    createCustomer,
    checkSubscribe
  } from '../../api/api.js';
  export default {
    name: 'app',
    data() {
      return {
        links: [],
        centerDialogVisible: false,
        list: [],
        product_id: "",
        price: "",
        dtitle:"",
        des:""
      }
    },
    created() {
      let that = this
      that.getList()
    },
    components: {},
    methods: {
      goPage: function(page) {
        this.$router.push(page)
      },
      tobuy(id, price, des ,title) {
        let that = this
        let customerId = localStorage.getItem("customerId")
        checkSubscribe(customerId).then(res => {
            console.log('ding',res)
            if(res.result != null){
                // if(res.result.productListVO.id == id){
                //     console.log("equal")
                // }
                that.$message({
                  message: "現在のプランを先に解約してください",
                  type: "error",
                });
            }else{
                that.centerDialogVisible = true
                that.product_id = id
                that.des = des
                that.dtitle = title
                let pri = price.split('円')
                that.price = pri[0]
            }
        })
      },
      // 获取充值计划列表
      getList() {
        let that = this
        plan({}).then((res) => {
          console.log('list', res)
          if (res.code == 200) {
            that.list = res.result
          }

        })
      },

      // 创建价格id，客户id,session
      createPrice(e) {
        let that = this
        createPrice({
          currency: "jpy",
          interval: e,
          product: that.product_id,
          unitAmount: that.price
        }).then((res) => {
          console.log("res", res)
          if (res.code == 200) {
            let customer = localStorage.getItem("customerId")
            if(customer){
              createSession({
                price: res.result.id,
                customerId: customer
              }).then((result) => {
                if (result.code == 200) {
                  window.open(result.result.url)
                }
              })
            }else{
              createCustomer({}).then((rest) => {
                console.log(rest)
                if (rest.code == 200) {
                  createSession({
                    price: res.result.id,
                    customerId: rest.result.id
                  }).then((result) => {
                    if (result.code == 200) {
                      window.open(result.result.url)
                    }
                  })
                }
              })
            }
          }
        })
      },
    }
  }
</script>

<style scoped>
  @import '../../assets/css/base.css';

  .pay-main-warp {
    width: 60%;
    height: 692px;
    margin: 0 auto;
    margin-top: 6%;
    padding: 38px 42px;
    padding-bottom: 0
  }

  .colbtns {
    margin-top: 40px;
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
    height: 450px;
    box-shadow: 0px 10px 20px #63AEB0;
    border: none;
    border-radius: 12px;
    background: #fff;
    margin-right: 30px;
    padding: 20px;
    border: 4px solid transparent;
  }

  .pay-main-warp .lists-box .lists-item:hover {
    border: 4px solid #1E7578 !important;
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

 /* .colbtns .join-btn:last-child {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #fff;
    font-weight: 700 !important;
    font-size: 14px;
    width: 100%;
    background: #63AEB0;
    margin: 0 0 12px !important;
  } */

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

    .pay-main-warp .lists-box .lists-item {
      margin: 0;
      margin-bottom: 1.5rem;
      flex: none;
    }
  }
</style>
