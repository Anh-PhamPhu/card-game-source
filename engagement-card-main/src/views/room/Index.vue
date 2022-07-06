<template>
    <div class="waiting-room-index-box column">
        <div class="waiting-room-header column">
            <div class="title-box row">
                <i class="el-icon-arrow-left" @click="toMy" style="cursor: pointer"></i>
                <div class="title">招待されたルーム</div>
            </div>
            <div class="title" style="font-size: 14px">招待されたルームが表示されない場合は、招待リンクから再度ご参加ください。</div>
        </div>
        <div class="waiting-room-info-box column">
            <div class="item row m-column" v-for="(item, index) in lists">
                <div class="title-box column">
                    <div class="title">{{item.roomName}}</div>
                    <div class="label row m-column">
                        <div><span class="iconfont icon-shijian"></span>{{item.startTime}}</div>
                        <div><span class="iconfont icon-01"></span>{{item.homeownerName}}</div>
                    </div>
                </div>
                <div class="btns-box row row-right">
                    <el-button class="details-btn" @click="toRoom(item.id,item.isHomeownerFlag)">詳細を見る</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import { getInvitationRecord } from '../../api/api.js';
  export default {
    name: 'app',
    data () {
      return {
        lists: [],
        pageNo: 1,
        pageSize: 10,
        userId: ''
      }
    },
    created () {
        this.userId = localStorage.getItem('homeowner')
    },
    components: { },
    mounted () {
        this.getLists()
    },
    methods: {
      getLists: function () {
          let that = this
          let data = {pageNo: that.pageNo, pageSize: that.pageSize, userId: that.userId}
          getInvitationRecord(data).then((res) => {
              console.log(res)
              if(res) {
                  if(res.code == 200){
                      let lists = res.result.list
                      lists.map((item, index) => {
                          item.startTime = that.GLOBAL.timeFormat((item.startTime * 1000))
                      })
                      that.lists = lists
                  }
              }
          })
      },
      toRoom: function (id,homer) {
          let that = this
          that.$router.push({path: '/room/info', query: {roomId: id,home:homer}})
      },
      toMy: function () {
          this.$router.push('/my')
      }
    }
  }
</script>

<style scoped>
@import '../../assets/css/base.css';
.waiting-room-index-box{width:761px;min-height:720px;margin: 0 auto;margin-top:3%;padding:38px 42px;padding-bottom:0;}
.waiting-room-index-box .waiting-room-header{height:60px;line-height:23px}
.waiting-room-index-box .waiting-room-header .el-icon-arrow-left{line-height:25px;color: #fff;font-weight:900}
.waiting-room-index-box .waiting-room-header .title{font-weight:700;font-size:20px;color: #fff;flex:1;text-align:center}
.waiting-room-index-box .waiting-room-header .des{font-weight:400;font-size:12px;line-height:20px;color:#cacaca}
.waiting-room-index-box .waiting-room-info-box {margin-bottom: 20px;}
.waiting-room-index-box .waiting-room-info-box .item{font-size:16px;line-height:42px;background:#FFF;box-shadow:0 10px 20px #63aeb0;border-radius:24px;padding: 30px;margin-bottom: 30px;}
.waiting-room-index-box .waiting-room-info-box .item .title-box { flex: 3; }
.waiting-room-index-box .waiting-room-info-box .item .title{font-weight: 700; font-size: 20px; line-height: 40px; color: #686868; }
.waiting-room-index-box .waiting-room-info-box .item .label{flex:3;color:#bebebe;font-size: 16px;line-height: 23px;}
.waiting-room-index-box .waiting-room-info-box .item .label div{ flex: 1; margin-right: 10px; }
.waiting-room-index-box .waiting-room-info-box .item .label .iconfont { font-size: 18px;line-height: 23px;margin-right: 10px; }
/*  按钮  */
.waiting-room-index-box .btns-box{margin-top:0; flex: 1;}
.waiting-room-index-box .btns-box button{width: 122px;height: 54px;line-height:9px;border-radius:8px;font-size:14px;margin:7px 0!important}
.waiting-room-index-box .btns-box .details-btn {background:#63AEB0;border:1.5px solid #63AEB0;color:#fff;}
@media (max-width: 460px) {
    .waiting-room-index-box{width:90%; min-width: 305px; height:auto; margin-bottom: 3rem; padding: 1rem .5rem;}
    .waiting-room-index-box .waiting-room-info-box {font-size: 1.2rem;}
    .waiting-room-index-box .waiting-room-info-box .item{font-size: .8rem}
}
</style>
