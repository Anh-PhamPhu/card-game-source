<template>
  <div class="game-result-warp column">
    <div class="game-result-header">
      <div class="my-btn" @click="toMy()">戻る</div>
      <div class="title-box column column-center">
        <div class="title">チャート分析</div>
        <div class="des">&ensp;</div>
      </div>
    </div>
    <div class="game-result-info-box column">
      <div class="anbox">
        <div class="tabs" >
          <div class="tab-item" :style="index == idx? 'background:#1E7578 !important;color:#fff !important': '' " @click="choose(index)" v-for="(item,index) in details" v-text="item.title"></div>
        </div>
        <div class="ascontent">
          <div>
            <echarts :width="width" :height="height" :id="id" :userid="ids"></echarts>
          </div>
          <div class="textword">
            <div class="wordTitle" v-text="details[idx].inner">

            </div>
            <div class="wordtext">
              <div v-text="details[idx].text1"></div>
              <div v-text="details[idx].text2"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import echarts from '../../components/echarts.vue'
  export default {
    name: 'app',
    data() {
      return {
        isUrl: '', // 网络请求协议
        roomId: '',
        idx:0,
        timeStr: '',
        users: [],
        width: "500px",
        height: "500px",
        id: "rad",
		ids:"",
        details: [{
            title: "専門",
            inner: "専門知識力",
            text1: "自身の専門分野において、知識・技能を高めることが好き。その力を発揮することで人の役に立ち、頼ってもらえる存在になることに価値を感じます。",
            text2: "モチベーションが上がる言葉「頼りにしてます！」"
          },
          {
            title: "組織",
            inner: "リーダーシップ力",
            text1: "仲間とともに勝利や達成目標を目指すことが好き。組織において、周囲を鼓舞し統率することで共通の目的を成しえることに価値を感じます。",
            text2: "モチベーションが上がる言葉「ついていきます！」"
          },
          {
            title: "独自",
            inner: "独自性発揮力",
            text1: "指示や束縛を受けず、自身の考えで筋道や方法を考えるのが好き。自分の信念をもって仕事を完遂させ、周囲を納得させることに価値を感じます。",
            text2: "モチベーションが上がる言葉「あなたに任せます！」"
          },
          {
            title: "安心、安定",
            inner: "確実的安定力",
            text1: "将来に不安のない安心できる状態が好き。目的が明確で、繰り返すこと、続けることで確実に成果が得られる心に余裕をもって就ける仕事に価値を感じます。",
            text2: "モチベーションが上がる言葉「大丈夫です！」"
          },
          {
            title: "創造",
            inner: "新規事業創造力",
            text1: "自らの着眼や発想で新しいものを生み出すことが好き。これまでにないユニークな商品やサービスの開発などで、世の中に画期的な変化をもたらす活動に価値を感じます。",
            text2: "モチベーションが上がる言葉「こんなの初めて！」"
          },
          {
            title: "社会貢献",
            inner: "社会的貢献力",
            text1: "困っている人への手助けを行い、喜んでもらえることが好き。世の中をよりよくするための活動に尽力・協力し、他者を救う社会活動に携わることに価値を感じます。",
            text2: "モチベーションが上がる言葉「感謝しています！」"
          },
          {
            title: "挑戦、問題解決",
            inner: "挑戦的解決力",
            text1: "目の前にある困難な状況に挑み、解決することが好き。これまで解決に至っていない社会や事業上の問題を克服し、新たな道筋を切り拓くことに価値を感じます。",
            text2: "モチベーションが上がる言葉「チャレンジですね！」"
          },
          {
            title: "自然体",
            inner: "合理的柔軟力",
            text1: "仕事も日々の暮らしも自然体で柔軟にこなすことが好き。合理性も踏まえながら、暮らしと仕事をうまく融合し、相乗効果でよりよい結果を出すことに価値を感じます。",
            text2: "モチベーションが上がる言葉「いい感じですね！」"
          },
          {
            title: "伝達",
            inner: "伝達表現力",
            text1: "自身のパフォーマンスで、人を喜ばせることが好き。物事の事象やメッセージを自身の好みの表現方法で、人に伝え、心を揺り動かすことに価値を感じます。",
            text2: "モチベーションが上がる言葉「感動しました！」"
          },
        ]
      }
    },
    components: {
      echarts
    },
    created() {
      // this.roomId    = this.$route.query.roomId // 获取房间信息
      // this.userId    = localStorage.getItem('homeowner') // 当前用户ID
      // this.userEmail = localStorage.getItem('userEmail') // 当前用户email
      // // 初始化用户信息
      // let data = JSON.parse(localStorage.getItem('room_result_' + this.roomId))
      // if(data.minute > 0)this.timeStr = data.minute + '分'
      // this.timeStr += data.second + '秒'
      // this.users = data.users
	 console.log(this.$route.query.id)
	 this.ids = this.$route.query.id
	 this.roomId = this.$route.query.roomid
    },
    mounted() {
		
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
      toMy: function() {
		  let that = this
		this.$router.push({path: '/room/resu', query: {roomId: that.roomId}})
      },
      choose(e){
        let that = this
        that.idx = e
      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/base.css';

  .tabs{
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    margin-top: 30px;
    /* justify-content: center; */
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
    position: relative;
    left: 464px;
  }

  .wordTitle {
    margin-bottom: 15px;
  }

  .textword {
    width: 50%;
	box-sizing: border-box;
	padding-top: 80px;
  }

  .game-result-warp {
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
    height: 100px;
    margin-right: 3rem;
    justify-content: space-between;
  }

  .game-result-warp .game-result-info-box .item .cards-lists .card {
    width: 13%;
  }

  .anbox {
    width: 70vw;
    height: 80vh;
    background: #fff;
    margin: 0 auto;
    border-radius: 17px;
    box-sizing: border-box;
    padding: 0 63px;
  }

  .ascontent {
    display: flex;
    /* align-items: center; */
    justify-content: space-between;
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

  .tab-item{
    font-size: 14px;
    color: #9E9E9E;
    background: #EEEEEE;
    padding: 5px 8px;
    margin: 10px;
    border-radius: 6px;
    cursor: pointer;
  }

  .game-result-warp .btns-box .details-btn {
    background: #63AEB0;
    border: 1.5px solid #63AEB0;
    color: #fff;
  }

  .wordtext {
    font-family: 'Roboto';
    font-style: normal;
    font-weight: 400;
    font-size: 18px;
    line-height: 21px;
    color: #777777;
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
