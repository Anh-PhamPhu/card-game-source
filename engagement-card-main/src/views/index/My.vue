<template>
  <div class="main">
    <div class="logout-btn" style="cursor:pointer" @click="doLogout()">ログアウト</div>
    <div class="main-warp column">
      <div class="title-box">
        <div class="title">マイページ</div>
        <div class="des">ようこそ、{{nickname}} 様</div>
      </div>
      <div class="lists-box row">
        <div class="lists-item column column-center" @click="goPage('/room/index')">
          <el-badge :value="record" class="item"></el-badge>
          <div class="icon column column-center row-center">
            <!-- <img src="../../assets/images/icon1.png" class="icon1" /> -->
            <el-image src="http://yiju.lanyanceshi.com/ceshiimg/icon1.png" fit="contain"></el-image>
          </div>
          <div>ルームに参加する</div>
        </div>
        <div class="lists-item column column-center" @click="goPage('/workspace')">
          <div class="icon column column-center row-center">
            <!-- <img src="../../assets/images/icon2.png" class="icon2" /> -->
            <el-image src="http://yiju.lanyanceshi.com/ceshiimg/icon2.png" fit="contain"></el-image>
          </div>
          <div>ルームを作成する</div>
        </div>
      </div>
      <div class="lists-box row">
        <div class="lists-item column column-center" @click="toRecord">
          <div class="icon column column-center row-center">
            <el-image src="http://yiju.lanyanceshi.com/ceshiimg/icon3.png" fit="contain"></el-image>
          </div>
          <div>過去の結果を見る</div>
        </div>
        <div class="lists-item column column-center" @click="goPage('/info')">
          <div class="icon column column-center row-center">
            <el-image src="http://yiju.lanyanceshi.com/ceshiimg/icon4.png" fit="contain"></el-image>
          </div>
          <div>個人情報を確認・変更</div>
        </div>
      </div>
      <div class="des-box" v-if="su == 0" @click="goPage('/pay/index')"> 有料会員になり機能を開放しませんか？ </div>
      <div class="des-box" v-else style="background: #fff;color: #63AEB0" @click="goPage('/pay/choose')"> 料金プランを確認
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getUserInfo,
    checkSubscribe,
    getUn
  } from '../../api/api.js';
  export default {
    name: 'app',
    data() {
      return {
        realname: '',
        nickname: '',
        su: 3,
        record:0
      }
    },
    created() {
		// this.$router.go(-1)
      this.userName = localStorage.getItem('userName')
      this.gGamesRecord()
      // this.check()
      // if(!localStorage.removeItem('homeowner')) this.$router.push('/login')
    },
    components: {},
    mounted() {
      this.getUserInfo()
    },
    methods: {
      check(id) {
        let that = this
        let data = {}
        data.customerId = id
        checkSubscribe(id).then(res => {
          console.log(res)
          if(res){
              if (res.code == 200) {
              if (res.result) {
                that.su = 1
                localStorage.setItem("plan",res.result.productListVO.name)
              } else {
                that.su = 0
                localStorage.setItem("plan","1on1プラン")
              }
            }
          }else{
          }
          
        })
      },
      gGamesRecord(){
        let that = this
      let id = localStorage.getItem('homeowner')
      console.log(id,"id")
        getUn(id).then(res =>{
          console.log("un",res)
		  if(res.code == 200){
			  that.record = res.result
		  }
        }).catch(err=>{

        })
      },
      getUserInfo: function() {
        let that = this,
          token = localStorage.getItem('Authorization')
        getUserInfo(token).then((res) => {
          console.log(res)
          if (res) {
            localStorage.setItem("customerId", res.result.customerId)
            that.realname = res.result.realname // 姓名
            that.nickname = res.result.nickname // 昵称
            that.check(res.result.customerId)
          }
        })
      },
      goPage: function(page) {
        this.$router.push(page)
      },
      toRecord(){
         let that = this
          if(that.su == 1){
              that.$router.push("/room/Logs")
          }else{
              that.$router.push("/pay/index")
              
          }
      },
      doLogout: function() {
        localStorage.removeItem('Authorization');
        localStorage.removeItem('homeowner');
        localStorage.removeItem('userEmail');
        localStorage.removeItem('userName');
        this.$router.push('/login')
      }
    }
  }
</script>

<style lang="css" scoped>
  @import '../../assets/css/base.css';

  .main-warp {
    width: 696px;
    height: 692px;
    margin: 0 auto;
    margin-top: 5%;
    padding: 38px 42px;
    padding-bottom: 0
  }

  .logout-btn {
    position: absolute;
    top: 89px;
    right: 108px;
    width: 97px;
    height: 33px;
    border: 1.5px solid #fff;
    box-sizing: border-box;
    border-radius: 6px;
    font-size: 14px;
    line-height: 31px;
    text-align: center;
    color: #fff
  }

  .main-warp .title-box {
    text-align: center;
    margin-bottom: 20px
  }

  .main-warp .title-box .title {
    font-weight: 700;
    font-size: 30px;
    line-height: 35px;
    color: #FFF
  }

  .main-warp .title-box .des {
    font-weight: 400;
    font-size: 16px;
    line-height: 35px;
    color: rgba(255, 255, 255, 0.8)
  }

  .main-warp .lists-box {
    justify-content: space-between
  }

  .main-warp .lists-box .lists-item {
    justify-content: center;
    align-items: center;
    background: #FFF;
    border: 4px solid #c9f1f2;
    box-sizing: border-box;
    box-shadow: 0 6px 8px #63aeb0;
    border-radius: 10px;
    width: 47%;
    height: 215px;
    margin-bottom: 35px;
    position: relative;
    cursor: pointer;
  }

  .main-warp .lists-box .lists-item .icon {
    width: 162px;
    height: 149.25px
  }

  .main-warp .lists-box .lists-item .icon .el-image {
    width: 100%;
    height: 100%;
  }

  .main-warp .lists-box .lists-item div {
    font-weight: 700;
    font-size: 18px;
    line-height: 21px;
    color: #63aeb0
  }

  .main-warp .des-box {
    width: 100%;
    height: 76px;
    line-height: 76px;
    text-align: center;
    background: #d4f0f1;
    border-radius: 10px;
    font-weight: 700;
    font-size: 18px;
    color: #63aeb0;
    cursor: pointer;
  }

  .main-warp .lists-box .lists-item .el-badge {
    position: absolute;
    top: 20px;
    right: 20px;
    width: 27px;
    height: 27px;
  }

  @media (max-width:750px) {
    .main-warp {
      width: 90%;
      padding: 5%;
      margin-top: 10%;
    }

    .main-warp .lists-box .lists-item div {
      font-size: .8rem;
    }

    .main-warp .des-box {
      font-size: .8rem;
    }

    .logout-btn {
      top: .5rem;
      right: .5rem;
    }

    .main-warp .lists-box .lists-item .icon {
      width: 95%;
    }

    .main-warp .lists-box .lists-item .el-badge {
      position: absolute;
      top: 10px;
      right: 10px;
      width: 27px;
      height: 27px;
    }
  }
</style>
