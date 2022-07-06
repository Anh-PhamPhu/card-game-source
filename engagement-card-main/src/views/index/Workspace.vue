<template>
    <div class="work-box column">
        <div class="work-header column">
            <div class="title-box row">
                <i class="el-icon-arrow-left" @click="toMy" style="cursor: pointer"></i>
                <div class="title">ルームを作成</div>
            </div>
            <div class="des">もっとワクワークする世の中を</div>
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="form-box column">
            <div class="column">
                <el-form-item label="ルーム名" prop="roomName">
                    <el-input v-model="ruleForm.roomName" placeholder="ルーム名を入力"></el-input>
                </el-form-item>
                <div class="birthday-box column">
                    <label class="el-form-item__label" style="width: 100px;">開始日時・時刻</label>
                    <div class="row">
                      <el-form-item label="" prop="year">
                          <el-select v-model="ruleForm.year" placeholder="年を選択してください" class="birthday-month" style="margin-left: 0;">
                              <el-option label="年" value=""></el-option>
                              <el-option v-for="(year,index) in year" v-if="index >= 1" :key="index" :label="year" :value="year">{{year}}</el-option>
                          </el-select>
                      </el-form-item>
                        <el-form-item label="" prop="month">
                            <el-select v-model="ruleForm.month" placeholder="月を選択してください" class="birthday-month" @change="monthChange">
                                <el-option label="月" value=""></el-option>
                                <el-option v-for="index in 12" v-if="index >= 1" :key="index" :label="index" :value="index"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="" prop="day">
                            <el-select v-model="ruleForm.day" placeholder="日を選択してください" class="birthday-day">
                                <el-option label="日" value=""></el-option>
                                <el-option v-for="index in choosedate" v-if="index >= 1" :key="index" :label="index" :value="index"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="" prop="hour">
                            <el-select v-model="ruleForm.hour" placeholder="時を選択してください" class="birthday-hour">
                                <el-option label="時" value=""></el-option>
                                <el-option v-for="(item,index) in hourrs" :key="index" :label="item" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="" prop="min">
                            <el-select v-model="ruleForm.min" placeholder="分を選択してください" class="birthday-min">
                                <el-option label="分" value=""></el-option>
                                <el-option v-for="index in 60" :key="index" :label="index-1" :value="index-1"></el-option>
                            </el-select>
                        </el-form-item>
                    </div>
                </div>
                <el-form-item label="テーマ" prop="theme">
                    <el-radio-group v-model="ruleForm.theme" class="themebox row" @change="chooseTheme">
                        <el-radio-button v-for="(item, index) in theme":key="index" :label="item" name="theme"></el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item v-if="showinput">
                    <el-input v-model="diy" @input="otherInsert" placeholder="入力してください"></el-input>
                </el-form-item>
                <div class="fun-box row m-column" style="margin-top: 12px;">
					<el-form-item label="手札公開" prop="isPublic">
					    <el-radio-group v-model="ruleForm.isPublic" class="row" style="height: 43px" @change="publicChange">
					        <el-radio-button label="1" name="isPublic">公開</el-radio-button>
					        <el-radio-button label="0" name="isPublic">非公開</el-radio-button>
					    </el-radio-group>
					</el-form-item>
                    <el-form-item label="カードコピー機能" prop="isCopy" class="copyFun" v-if="ruleForm.isPublic == 1">
                        <el-radio-group v-model="ruleForm.isCopy" class="row" style="height: 43px">
                            <el-radio-button label="1" name="isCopy">あり</el-radio-button>
                            <el-radio-button label="0" name="isCopy">なし</el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                </div>
                <div style="display: flex;justify-content: space-between">
                  <el-form-item label="人数" prop="peopleNumber">
                      <el-select v-model="ruleForm.peopleNumber" placeholder="人数を選択" class="select-peopleNum">
                          <el-option label="選んでください" value=""></el-option>
                          <el-option v-for="(num, index) in peopleNumber" :key="index" :label="num" :value="num">{{num}}人</el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="制限時間" prop="peopleNumber">
                      <el-select v-model="ruleForm.turnTime" placeholder="人数を選択" class="select-peopleNum">
                          <el-option label="選んでください" value=""></el-option>
                          <el-option v-for="(num, index) in turnTime" :key="index" :label="num" :value="num">{{num}}s</el-option>
                      </el-select>
                  </el-form-item>
                </div>

                <el-form-item label="公開URL" prop="isCustom" v-if="plan != '1on1プラン' && plan != '基本プラン'">
                    <el-radio-group v-model="ruleForm.isCustom" class="row" @change="changeUrlType">
                        <el-radio-button label="1" name="isCustom" v-model="ruleForm.isCustom">ランダム</el-radio-button>
                        <el-radio-button label="2" name="isCustom" :disabled="plan == '1on1プラン' || plan == '基本プラン' ?true:false" v-model="ruleForm.isCustom">固定</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="" prop="url" class="url-box" v-show="ruleForm.isCustom == 2">
                    <div class="text">{{showurl}}</div>
                    <!-- <el-input v-model="ruleForm.urlCode" placeholder="後ろの部分を入力" v-show="!urlSelectShow"></el-input> -->
                    <el-select v-model="ruleForm.url" placeholder="後ろの部分を入力" @change="Sechange">
                        <el-option label="選んでください" value=""></el-option>
                        <el-option v-for="(item,index) in urls" :key="index" :label="item" :value="item"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="btns-box row row-center">
                <el-button class="save-btn" @click="doSub('ruleForm')">ルームを作成する</el-button>
            </div>
        </el-form>
        <footer></footer>
    </div>
</template>

<script>
  import { getUserInfo, createRoom, showUrl, chooseMember} from '../../api/api.js';
  import { mapMutations } from 'vuex';
  import {footer} from "../../components/footer.vue"
  export default {
    name: 'app',
    data () {
      return {
        showinput:false,
        req:false,
        isUrl: '', // 网络请求协议
        peopleNumber: [1, 2, 3, 4],
        // 表单规则
        urlShow: true,
        urlSelectShow: false,
        showurl:"",
        // 主题
        theme: [
		  '人生',
          '仕事',
          '恋愛',
          '家族',
          '子育て',
          'チームやコミュニティ',
          '求める人物像',
          '企業文化',
          'その他'
        ],
        urls:[],
        diy:"",
        url: '',  // 公开邀请链接
        level:  0,   // 会员级别
        hourrs:[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
        ruleForm: {
          homeowner: '1505124724596801538', // 房主的用户id
          // roomName: '测试房间1',  // 房间名
          roomName: '',  // 房间名
          year: '2022',      // 开始时间年份
          month: '4',     // 开始时间月份
          day: '6',       // 开始时间天
          hour: '0',      // 开始时间时
          min: '1',       // 开始时间分
          startTime: '', // 开始时间
          theme: '人生', // 主题
          isCopy: 0,     // 复制功能
          isPublic: 0,   // 卡片显示功能
          peopleNumber: 1, // 人数
          isCustom: 1,   // 公開URL类型
          urlCode: '',    // 公開URL
          url:"",
          turnTime:"30"
        },
        turnTime:[15,20,25,30,35,40,45,50,55,60],
        year:["2022","2023","2024","2025"],
        choosedate:30,
        plan:localStorage.getItem("plan"),
        rules: {
          roomName: [
            { required: true, message: 'ルーム名を入力', trigger: 'blur' }
          ],
          homeowner: [
            { required: true, message: 'ルーム名を入力', trigger: 'blur' }
          ],
          year: [
            { required: true, message: '年を選択してください', trigger: 'change' }
          ],
          month: [
            { required: true, message: '月を選択してください', trigger: 'change' }
          ],
          day: [
            { required: true, message: '日を選択してください', trigger: 'change' }
          ],
          hour: [
            { required: true, message: '時を選択してください', trigger: 'change' }
          ],
          min: [
            { required: true, message: '分を選択してください', trigger: 'change' }
          ],
          theme: [
            { required: true, message: 'テーマを選択してください', trigger: 'change' }
          ],
          isCopy: [
            { required: true, message: 'コピー機能', trigger: 'change' }
          ],
          isPublic: [
            { required: true, message: 'カード表示機能', trigger: 'blur' }
          ],
          peopleNumber: [
            { required: true, message: '人数を選択', trigger: 'change' }
          ],
          isCustom: [
            { required: true, message: '人数を選択', trigger: 'change' }
          ]
        }
      }
    },
    components: { },
    mounted () {
      let that = this
        this.isUrl = this.GLOBAL.getHttps()
        this.ruleForm.homeowner = localStorage.getItem('homeowner');
        this.getUserInfo()
        this.showUrl()
        this.choseM()
        this.getTime()
        document.addEventListener("visibilitychange",that.handleVisible)
    },
    destroyed() {
      let that = this
      document.addEventListener("visibilitychange",that.handleVisible)
    },
	created() {
		document.removeEventListener("keyup", this.ss);
	},
    methods: {
      Sechange(e){
        console.log(e)
        let that = this
        that.ruleForm.url = e
        that.showurl = that.url + e
      },

      otherInsert(e){
        let that = this
        that.ruleForm.theme = e
        console.log(e)
      },

	  handleVisible(e){
      // alert(123321)
      console.log("e",e)
    },

	  publicChange(e){
		console.log(e)
		let that = this
		// that.ruleForm.isCopy = e
	  },

	  getTime(){
		  let that = this
		  let date = new Date()
		  console.log("date",date)
		  let month = date.getMonth()+1
		  let day = date.getDate()
		  let hour = date.getHours()
		  let minute = date.getMinutes()
		  that.ruleForm.month = month
		  that.ruleForm.day = day
		  that.ruleForm.hour = hour
		  that.ruleForm.min = minute
	  },

        showUrl(){
          let that = this
          showUrl({}).then((res) => {
            console.log("url显示",res)
            if(res.code == 200){
              that.urls = res.result.userUrlList
            }
          })
        },
        choseM(){
          let that = this
          chooseMember({}).then((res) => {
            console.log("res1",res)
            if(res.result == 0){
              that.peopleNumber=[1, 2]
            }else if(res.result == 1){
              that.peopleNumber=[1, 2 ,3 ,4]
            }
          })
        },
        toRoom: function () {
            this.$router.push('/waiting')
        },
        toMy: function () {
            this.$router.push('/my')
        },
        changeUrlType: function (e) {
            this.ruleForm.publicURLType = e
            if (e === '2') {
                this.urlShow = true
                this.rules.url = []
                this.rules.url.push({ required: true, message: '後ろの部分を入力', trigger: 'blur' })
            } else {
                this.urlShow = false
                this.rules.url = ""
            }
        },
        getUserInfo: function () {
            let that = this,
                token = localStorage.getItem('Authorization')
            getUserInfo(token).then((res) => {
                if(res) {
                    that.ruleForm.homeowner = res.result.id  // 会员ID

                    that.level = res.result.level
                    // if(that.level == 0) {
                    //     that.url   = that.isUrl + '://yiju.lanyanceshi.com/cardgame/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.GLOBAL.randomString(8)
                    //     that.urlShow = false
                    //     that.ruleForm.isCustom = 1
                    // }else{
                    //   that.url   = that.isUrl + '://yiju.lanyanceshi.com/cardgame/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.ruleForm.url
                    //   that.showurl = that.isUrl + '://yiju.lanyanceshi.com/cardgame/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.ruleForm.url
                    // }


                     if(that.level == 0) {
                        that.url   = that.isUrl + '://dev.engagement-card.com/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.GLOBAL.randomString(8)
                        that.urlShow = false
                        that.ruleForm.isCustom = 1
                    }else{
                      that.url   = that.isUrl + '://dev.engagement-card.com/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.ruleForm.url
                      that.showurl = that.isUrl + '://dev.engagement-card.com/#/room/info?invite=' + that.ruleForm.homeowner + '&' + 'code=' + that.ruleForm.url
                    }
                }
            })
        },
        chooseTheme(e){
          let that = this
          if(e == 'その他'){
            that.showinput = true
          }else{
            that.showinput = false
          }
        },
        monthChange(e) {
      console.log(e);
      let that = this;
      let year = that.ruleForm.year;
      if ((year % 100 != 0 && year % 4 == 0) || year % 400 == 0) {
        if (
          e == 1 ||
          e == 3 ||
          e == 5 ||
          e == 7 ||
          e == 8 ||
          e == 10 ||
          e == 12
        ) {
          that.choosedate = 31;
          return false;
        }
        if (e == 2) {
          that.choosedate = 29;
          return false;
        }
        if (e == 2 || e == 4 || e == 6 || e == 9 || e == 11) {
          that.choosedate = 30;
          return false;
        }
      } else {
        if (
          e == 1 ||
          e == 3 ||
          e == 5 ||
          e == 7 ||
          e == 8 ||
          e == 10 ||
          e == 12
        ) {
          that.choosedate = 31;
          return false;
        }
        if (e == 2) {
          that.choosedate = 28;
          return false;
        }
        if (e == 2 || e == 4 || e == 6 || e == 9 || e == 11) {
          that.choosedate = 30;
          return false;
        }
      }
    },
        doSub: function (formName) {
            let that = this
            let data = that.ruleForm
            let password = that.GLOBAL.randomString(6);
            let url = that.isUrl + '://yiju.lanyanceshi.com/#/room/info?'
            let urlCode = that.GLOBAL.randomString(8)
            if(that.level == 0) {
                url += 'invite=' + that.ruleForm.homeowner + '&' + 'code=' + urlCode;
            } else {
                url += 'invite=' + that.ruleForm.homeowner + '&' + 'code=' + data.url
                urlCode = data.url
            }
            let timeStr   = data.year + '-' + data.month + '-' + data.day + ' ' + data.hour + ':' + data.min
            let startTime = that.GLOBAL.strToTime(timeStr)
            // that.req = true
            that.$refs[formName].validate((valid) => {
                if (valid) {
                    let formData = {
                        homeowner: data.homeowner,
                        isCopy:    data.isCopy,
                        isCustom:  data.isCustom,
                        isPublic:  data.isPublic,
                        password:  password,
                        peopleNumber: data.peopleNumber,
                        roomName:     data.roomName,
                        startTime:    startTime,
                        theme: data.theme,
                        url: urlCode,
                        turnTime: data.turnTime
                    };
                    if(that.req == false){
                      that.req = true
                      createRoom(formData).then((res) => {
                        console.log(res)
                        if(res) {
                            that.$message({
                                message: '作成しました',
                                type:    'success',
                                onClose: function () {
                                    that.req = false
                                    that.$router.push({path: '/waiting', query: {roomId: res.result}})
                                }
                            })
                        }
                    })
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
  }
</script>

<style scoped>
@import '../../assets/css/base.css';
.work-box {width:696px;/* height:820px; */margin:0 auto;margin-top:3%; background:#FFF;box-shadow:0 10px 20px #63aeb0;border-radius:24px;padding:38px 42px;padding-bottom:40px}
.work-box .work-header{height:60px;line-height:23px}
.work-box .work-header .el-icon-arrow-left{line-height:25px;color:#83c3c5;font-weight:900}
.work-box .work-header .title{font-weight:700;font-size:20px;color:#83c3c5}
.work-box .work-header .des{font-weight:400;font-size:12px;line-height:20px;color:#cacaca}
.work-box .form-box{width:100%;margin:0 auto;color:#686868}
.work-box .form-item{width:50%;padding-left:20px}
.work-box .fun-box .copyFun{margin-right:80px}
.work-box .birthday-year{width:113px}
.work-box .birthday-month,.birthday-day,.birthday-hour,.birthday-min{width:82px;margin-left:25px}
.work-box .select-peopleNum{width:322px}

/*  按钮  */
.work-box .btns-box{margin-top:10px}
.work-box .btns-box button{width:196px;height:39px;line-height:9px;border-radius:8px;font-size:14px;margin:7px 0!important}
.work-box .btns-box .save-btn {background:#83c3c5;border:1.5px solid #83c3c5;color:#fff; width: auto;}
#app >>> .footer-box { position: inherit !important; margin-top: 50px; }
@media (max-width: 460px) {
    .work-box{width:90%; min-width: 305px; height:auto; margin-bottom: 3rem; padding: 1rem .5rem;}
    .work-box .form-box{width:100%; margin:0 auto;color:#686868}
    .work-box .form-box div {width: 100%;}
    .work-box .form-item{justify-content:space-between}
    .work-box .form-item .el-form-item{margin-right:0;}
    .work-box .form-box .el-input--suffix .el-input__inner { padding-right: 0; }
    .work-box .el-form-item { margin-bottom: 10px; }
    /*  性别选择  */
    .work-box .form-box .el-input__inner{margin:.5rem auto; margin-bottom:3px;}
    .work-box .form-box .el-radio-group{width: 100%; height: auto; margin:.5rem auto;flex-wrap: wrap;}
    .work-box .form-box .el-radio-group .el-radio-button { margin-top: .5rem; width: 33%; }
    .work-box .form-box .el-radio-button__inner { margin: 0; }
    .work-box .birthday-box .el-form-item { flex: 1; }
    .work-box .birthday-box .el-form-item .el-form-item__content { width: 95%; }
    .work-box .birthday-box .birthday-year{ }
    .work-box .birthday-month,.work-box .birthday-day,.work-box .birthday-hour,.work-box .birthday-min{margin-left:0}
    .work-box .url-box .el-form-item__content{display:-webkit-flex;display:flex;flex-direction:column}
    .work-box .url-box .el-form-item__content .el-input{ width: auto; }
    .work-box .btns-box button{width:auto;}
    .work-box .btns-box .save-btn{margin: 1rem auto !important;}
}
</style>
<style>
  .work-box .el-form-item__label{text-align:left;line-height:20px;width:auto!important}
  .work-box .el-form-item.is-required:not(.is-no-asterisk) .el-form-item__label-wrap>.el-form-item__label:before,.work-box .el-form-item.is-required:not(.is-no-asterisk)>.el-form-item__label:before{content:''}
  .work-box .el-form-item__content{margin-left:0!important}
  .work-box .el-radio-group{height:86px;margin:8px auto}
  .work-box .el-radio-button__inner{border-radius:8px!important;background:rgba(189,189,189,0.18);margin-right:30px;height:43px;line-height:20px;border:0;color:#9c9c9c;margin-bottom: 15px;}
  .work-box .themebox .el-radio-button__inner {width: auto;}
  .work-box .el-radio-button__inner:hover{color:#50A0A3}
  .work-box .form-item:first-child{padding-left:0;padding-right:20px}
  .work-box .el-form-item{display:-webkit-flex;display:flex;flex-direction:column}
  .work-box .url-box .el-form-item__content{display:-webkit-flex;display:flex;flex-direction:row}
  .work-box .url-box .el-form-item__content .el-input{width: 181px;}
  .work-box .url-box .el-form-item__content .text{font-size:14px;line-height:57px;color:#bebebe;width:400px}
  .work-box .form-box .el-input__inner,.work-box .change-box .el-input__inner{height:43px;margin:8px auto;margin-bottom:3px;border:0;background:rgba(189,189,189,0.18);border-radius:8px;color:#c0c4cc;}
  .work-box .el-radio-button:first-child .el-radio-button__inner{margin-left:0;border-left:none}
  .work-box .el-radio-button__orig-radio:checked+.el-radio-button__inner{color:#FFF;background-color:#83c3c5;border-color:#83c3c5;-webkit-box-shadow:-1px 0 0 0 #83c3c5;box-shadow:-1px 0 0 0 #83c3c5}
  .work-box .el-checkbox__input.is-checked .el-checkbox__inner{background-color:#83c3c5;border-color:#83c3c5}
  .work-box .el-checkbox__input.is-checked .el-checkbox__inner:hover{border-color:#83c3c5}
  .work-box .el-form-item .el-input__suffix { margin: 5px 0; }
</style>
