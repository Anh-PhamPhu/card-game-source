<template>
  <div class="game-result-warp column">
    <div class="game-result-header">
      <div class="my-btn" @click="toMy()"  style="cursor: pointer">戻る</div>
      <div class="title-box column column-center">
        <div class="title">結果</div>
        <div class="des">プレイ時間：{{formatSeconds(users[0].turnTime)}}</div>
      </div>
    </div>
    <div class="game-result-info-box column">
      <div class="item row m-column" v-for="(item, index) in users">
        <div class="title">
          <div class="tig">{{item.userRole == 1?'ファシリテーター':'プレイヤー'}}</div>
          <div class="name">{{item.userName}}</div>
          <div class="fenxi" @click="fenxi(item)" v-if="plan == '法人プラン'"  style="cursor: pointer">
            チャート分析をみる
          </div>
        </div>
        <div class="cards-lists row-flex row m-column">
          <el-image v-if='item.isShowCard == 1' v-for="(item2, index2) in item.urls" :key="index2" class="card" :src="item2" fit="contain">
          </el-image>
          <el-image v-if="item.isShowCard == 0" v-for="(item2, index2) in item.urls" :key="index2" class="card" :src="cardsBack" fit="contain">
		    </el-image>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import { getLogDetail } from '../../api/api.js';
  export default {
    name: 'app',
    data() {
      return {
        isUrl: '', // 网络请求协议
        roomId: '',
        timeStr: '',
        users: [],
		plan:"",
		cardsBack: require('@/assets/images/card-back.png'), // 卡片背面
      }
    },
    components: {},
    created() {
      this.roomId = this.$route.query.roomId // 获取房间信息
      this.userId = localStorage.getItem('homeowner') // 当前用户ID
      this.userEmail = localStorage.getItem('userEmail') // 当前用户email
      // 初始化用户信息
	  this.getlog(this.$route.query.roomId)
	  let plan = localStorage.getItem("plan")
	  this.plan = plan
    },
    methods: {
      toRoom: function(id) {
        let that = this
        that.$router.push({
          path: '/room/info',
          params: {
            roomId: id
          }
        })
      },
      formatSeconds(value) {
        var secondTime = parseInt(value);// 秒
        var minuteTime = 0;// 分
        var hourTime = 0;// 小时
        if(secondTime > 60) {//如果秒数大于60，将秒数转换成整数
            //获取分钟，除以60取整数，得到整数分钟
            minuteTime = parseInt(secondTime / 60);
            //获取秒数，秒数取余，得到整数秒数
            secondTime = parseInt(secondTime % 60);
            //如果分钟大于60，将分钟转换成小时
            if(minuteTime > 60) {
                //获取小时，获取分钟除以60，得到整数小时
                hourTime = parseInt(minuteTime / 60);
                //获取小时后取余的分，获取分钟除以60取余的分
                minuteTime = parseInt(minuteTime % 60);
            }
        }
        var result = "" + parseInt(secondTime) + "秒";

        if(minuteTime > 0) {
          result = "" + parseInt(minuteTime) + "分" + result;
        }
        if(hourTime > 0) {
          result = "" + parseInt(hourTime) + "時間" + result;
        }
        console.log('result',result);
        return result;
    },
	  getlog(id){
		let that = this
		getLogDetail(id).then(res => {
			console.log(res)
			if(res.code == 200){
      let results = []
        if(that.plan == '基本プラン'){
          for(let i = 0; i < res.result.length; i++){
            if(res.result[i].currentUserFlag == 1){
              res.result[i].isShowCard = 1
              res.result[i].userCard = JSON.parse(res.result[i].userCard)
              res.result[i].urls = []
              for(let j = 0; j < res.result[i].userCard.length; j++){
              	if(res.result[i].userCard[j].id < 10){
              		res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_0' + res.result[i].userCard[j] + '.png' )
              	}else{
              		res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_' + res.result[i].userCard[j] + '.png' )
              	}
              }
             
            }else{
              res.result[i].isShowCard = 0
              res.result[i].userCard = JSON.parse(res.result[i].userCard)
              res.result[i].urls = []
              for(let j = 0; j < res.result[i].userCard.length; j++){
              	if(res.result[i].userCard[j].id < 10){
              		res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_0' + res.result[i].userCard[j] + '.png' )
              	}else{
              		res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_' + res.result[i].userCard[j] + '.png' )
              	}
              }
            }
             results.push(res.result[i])
          }
        }else{
          for(let i = 0; i < res.result.length; i++){
          	res.result[i].userCard = JSON.parse(res.result[i].userCard)
          	res.result[i].urls = []
          	for(let j = 0; j < res.result[i].userCard.length; j++){
          		if(res.result[i].userCard[j].id < 10){
          			res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_0' + res.result[i].userCard[j] + '.png' )
          		}else{
          			res.result[i].urls.push('http://yiju.lanyanceshi.com/cards/card_' + res.result[i].userCard[j] + '.png' )
          		}

          	}
            results.push(res.result[i])
          }
        }
				that.users = results
			}
		})
	  },
      toMy: function() {
        this.$router.push('/room/Logs')
      },
      fenxi: function(e) {
				let that = this
		  console.log(e.userCard)
		  let str = ""
		  for(let i = 0; i < e.userCard.length; i++){
			  if(e.userCard[i] == "01"){
				  e.userCard[i] = 1
			  }else if(e.userCard[i] == "02"){
				  e.userCard[i] = 2
			  }else if(e.userCard[i] == "03"){
				  e.userCard[i] = 3
			  }else if(e.userCard[i] == "04"){
				  e.userCard[i] = 4
			  }else if(e.userCard[i] == "05"){
				  e.userCard[i] = 5
			  }else if(e.userCard[i] == "06"){
				  e.userCard[i] = 6
			  }else if(e.userCard[i] == "07"){
				  e.userCard[i] = 7
			  }else if(e.userCard[i] == "08"){
				  e.userCard[i] = 8
			  }else if(e.userCard[i] == "09"){
				  e.userCard[i] = 9
			  }
			  if(i == e.userCard.length - 1){
				 str += e.userCard[i]
			  }else{
				  str += e.userCard[i] + ','
			  }
		  }
		  console.log(str)
		  this.$router.push({path: '/game/analysis', query: {id: str,roomid: that.roomId}})
      },
    }
  }
</script>

<style scoped>
  @import '../../assets/css/base.css';

  .fenxi {
    margin: 0 auto;
    margin-top: 10px;
    text-align: center;
    color: #6a6a6a;
    font-size: 14px;
    height: 30px;
    line-height: 30px;
    border-radius: 6px;
    padding: 0px 15px;
    background-color: #FFFFFF;
    border: 2.5px solid #adadad;
  }

  .my-btn {
    border: 1.5px solid #fff;
    box-sizing: border-box;
    border-radius: 6px;
    font-size: 14px;
    line-height: 31px;
    text-align: center;
    color: #fff;
    padding: 3px 15px;
  }

  .game-result-warp {
    width: 1200px;
    height: auto;
    margin: 0 auto;
    margin-top: 3%;
    padding: 38px 42px;
    padding-bottom: 0;
  }

  .game-result-warp .game-result-header {
    margin-bottom: 10px;
    position: relative;
  }

  .game-result-warp .game-result-header .my-btn {
    width: 127px;
    border: 2px solid #FFF;
    box-sizing: border-box;
    border-radius: 6px;
    font-weight: 700;
    font-size: 14px;
    line-height: 23px;
    color: #FFF;
    position: absolute;
    top: 1.8rem;
  }

  .game-result-warp .game-result-header .title {
    font-weight: 700;
    font-size: 30px;
    line-height: 3rem;
    color: #fff;
    width: 130px;
  }

  .game-result-warp .game-result-header .des {
    font-size: 20px;
    line-height: 3rem;
    color: #fff;
  }

  .game-result-warp .game-result-info-box {
    margin-bottom: 20px;
  }

  .game-result-warp .game-result-info-box .item {
    background: #FFF;
    box-shadow: 0 10px 20px #63aeb0;
    border-radius: 24px;
    padding: 1.3rem 0;
    margin-bottom: 20px;
  }

  .game-result-warp .game-result-info-box .item .title-box {
    flex: 3;
  }

  .game-result-warp .game-result-info-box .item .title {
    padding: 1rem 3rem;
    text-align: center;
    width: 165px;
  }

  .game-result-warp .game-result-info-box .item .title .tig {
    font-weight: 700;
    font-size: 14px;
    line-height: 1.6rem;
    color: #C8C8C8;
  }

  .game-result-warp .game-result-info-box .item .title .name {
    font-weight: 700;
    font-size: 22px;
    line-height: 2rem;
    text-align: center;
    color: #737373;
  }

  .game-result-warp .game-result-info-box .item .cards-lists {
    /* height: 100px; */
    margin-right: 3rem;
    justify-content: space-between;
  }

  .game-result-warp .game-result-info-box .item .cards-lists .card {
    width: 14%;
  }

  /*  按钮  */
  .game-result-warp .btns-box {
    margin-top: 0;
    flex: 1;
  }

  .game-result-warp .btns-box button {
    width: 122px;
    height: 54px;
    line-height: 9px;
    border-radius: 8px;
    font-size: 14px;
    margin: 7px 0 !important
  }

  .game-result-warp .btns-box .details-btn {
    background: #63AEB0;
    border: 1.5px solid #63AEB0;
    color: #fff;
  }

  @media (max-width: 460px) {
    .game-result-warp {
      width: 90%;
      min-width: 305px;
      height: auto;
      margin-bottom: 3rem;
      padding: 1rem .5rem;
    }

    .game-result-warp .game-result-info-box {
      font-size: 1.2rem;
    }

    .game-result-warp .game-result-info-box .item {
      font-size: .8rem
    }
  }
</style>
