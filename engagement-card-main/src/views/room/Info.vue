<template>
    <div class="waiting-room-in-box column">
        <div class="waiting-header column">
            <div class="title-box row">
                <i class="el-icon-arrow-left" @click="toBack()" style="cursor: pointer"></i>
                <div class="title">待機室</div>
                <el-button @click="deleteRoom" v-if="home == 1" class="join-btn" style="font-weight: 700 !important">
                    ルームを削除
                </el-button>
            </div>
        </div>
        <div class="waiting-info-box column">
            <h5>基本情報</h5>
            <div class="item row">
                <div class="label">ルーム名</div>
                <div class="text">{{ info.roomName }}</div>
            </div>
            <div class="item row">
                <div class="label">ファシリテーター</div>
                <div class="text">{{ info.homeownerName }}</div>
            </div>
            <div class="item row">
                <div class="label">開始時刻</div>
                <div class="text">{{ info.startTime }}</div>
            </div>
            <div class="item row">
                <div class="label">テーマ</div>
                <div class="text">{{ info.theme }}</div>
            </div>
            <div class="item row">
                <div class="label">手札公開</div>
                <div class="text">{{ info.isPublic }}</div>
            </div>
            <div class="item row">
                <div class="label">カードコピー機能</div>
                <div class="text">{{ info.isCopy }}</div>
            </div>
            <div class="item row">
                <div class="label">人数</div>
                <div class="text">{{ info.peopleNumber }}人</div>
            </div>
        </div>
        <div class="btns-box row row-center" v-if="isJoin == false && home != 1">
            <el-button class="save-btn" @click="doJoin">参加する</el-button>
        </div>
        <div class="waiting-des-box column row-center" v-if="status == 'waiting'">
            <div>ファシリテーターがプレイヤーを選択中です。</div>
            <div>しばらくお待ちください。</div>
        </div>

        <el-dialog title="招待コードを入力してください" top="250px" :visible.sync="dialogFormVisible" class="change-box">
            <el-form :model="passForm" :rules="passRules" ref="passForm">
                <el-form-item prop="password">
                    <el-input v-model="passForm.password" show-password autocomplete="off" placeholder=""></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer btns-box row row-center">
                <el-button class="save-btn" @click="doSub('passForm')">続ける</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { createSocket, WSClose } from '../../utils/websocket.js'
import { getUserInfo, getUserInfoById, getRoomDetail, getRoomDetailByUid, insertInvitationLog, delRoom } from '../../api/api.js';
import { mapMutations } from 'vuex';
export default {
    name: 'app',
    data() {
        var passwordValidator = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('パスワードを入力'));
            } else if (value !== this.info.password) {
                callback(new Error('招待コードを入力してください'));
            } else {
                callback();
            }
        };
        return {
            isUrl: '', // 网络请求协议
            tags: this.GLOBAL.tags,
            userId: '', // 用户 ID
            userEmail: '', // 用户 email
            userName: '', // 用户名
            roomId: '', // 房间ID
            code: '', // 邀请码
            invite: '', // 邀请用户
            isJoin: false, // 是否加入房间
            // 房间信息
            info: {
                roomName: '',
                homeownerName: '',
                startTime: '',
                theme: '',
                isCopy: '',
                isPublic: '',
                peopleNumber: '',
                url: '',
                password: ''
            },
            home: 0,
            // waiting  等待
            status: 'uncommitted',
            dialogFormVisible: false,
            passForm: {
                password: ''   // 密码
            },
            passRules: {
                password: [
                    { required: true, validator: passwordValidator, message: '招待コードを入力', trigger: 'blur' }
                ]
            },
            lists: [],
        }
    },
    created() {
        let that = this
        that.isUrl = that.GLOBAL.getHttps()
        // 接受参数
        that.roomId = that.$route.query.roomId
        that.invite = that.$route.query.invite
        that.code = that.$route.query.code
        that.home = that.$route.query.home
        that.userId = localStorage.getItem('homeowner')
        that.userEmail = localStorage.getItem('userEmail')
        that.userName = localStorage.getItem('userName')
        that.isJoin = localStorage.getItem('join_' + that.roomId)
        // 更新参加状态
        if (that.roomId) {
            // 获取详情信息
            that.getRoomDetail()
        }
        if (that.invite && that.code) {
            that.getRoomDetailByUid(that.invite, that.code)
        }
    },
    watch: {
        roomId: {
            // 立即监听
            immediate: true,
            // 深度监听
            deep: true,
            handler(val) {
                console.log('监听', val);
                if (val) {
                    this.isJoin = localStorage.getItem('join_' + this.roomId) ? localStorage.getItem('join_' + this.roomId) : false;
                    this.updateJoin(this.isJoin)
                    if (!this.isJoin) this.insertInvitationLog()
                    this.getroominfo()
                }
            }
        }
    },
    components: {},
    mounted() {
        window.addEventListener('onmessageWS', this.getsocketData)
    },
    methods: {
        ...mapMutations(['updateJoin']),
        toBack: function () {
            history.go(-1)
        },
        deleteRoom() {
            let that = this
            delRoom(that.roomId).then(res => {
                console.log(res)
                if (res.code == 200) {
                    that.$message({
                        message: res.message,
                        type: 'success'
                    });
                    setTimeout(function () {
                        that.$router.go(-1);
                    }, 1000)
                }
            })
        },
        getsocketData: function (e) {
            console.log(e.detail.data)
            if (e.detail.data == '发生连接错误') return false
            let data = JSON.parse(e.detail.data), newUser = {}, isNew = true, that = this
            if (data.result && data.operateMsg == '游戏开始，向每位玩家分牌') {
                let result = JSON.parse(data.result)
                console.log("result", result)
                let userCards = JSON.parse(data.result)
                let playerIds = Object.keys(userCards), players = [], count = playerIds.length
                localStorage.setItem('room_usercards' + that.roomId, data.result)
                if (playerIds.length == 1) {
                    that.$router.push({ path: '/game/index1', query: { roomId: that.roomId } })
                } else if (playerIds.length == 2) {
                    that.$router.push({ path: '/game/index2', query: { roomId: that.roomId } })
                } else if (playerIds.length == 3) {
                    that.$router.push({ path: '/game/index3', query: { roomId: that.roomId } })
                } else if (playerIds.length == 4) {
                    that.$router.push({ path: '/game/index', query: { roomId: that.roomId } })
                }
                // WSClose()
            } else {
                this.lists.forEach((item, index) => {
                    if (item.id == data.id) {
                        isNew = false
                        // 判断在线状态
                        if (data.lineStatus != item.lineStatus) {
                            this.lists[index].lineStatus = data.lineStatus
                        }
                    }
                })
                if (isNew) {
                    let tag = '観戦者'
                    if (data.id == this.info.homeowner) tag = '房主'
                    newUser = {
                        email: data.email,
                        id: data.id,
                        lineStatus: data.lineStatus,
                        onlineNumber: data.onlineNumber,
                        realname: data.realname,
                        username: data.username,
                        tag: tag,
                        checked: false,
                        isSkip: false,
                        sort: 0,
                    }
                    if (data.id = this.userId) {
                        newUser.isHomewer = true
                    }
                    console.log(newUser)
                    this.lists.push(newUser)
                }
                // console.log('WS数据',JSON.parse(e.detail.data))
                // console.log(JSON.stringify(this.lists))
                localStorage.setItem('room_' + this.roomId, JSON.stringify(this.lists))
                // localStorage.removeItem('room_' + this.roomId)
            }
        },
        // 获取用户信息
        getUserInfo: function () {
            let that = this,
                token = localStorage.getItem('Authorization')
            getUserInfo(token).then((res) => {
                console.log(res)
                if (res) {
                    that.ruleForm = {
                        realname: res.result.realname,  // 姓名
                        falsename: res.result.falsename, // 读音
                        nickname: res.result.nickname,  // 昵称
                        sex: res.result.sex,       // 性别
                        year: res.result.year,      // 生日年份
                        month: res.result.month,     // 生日月份
                        day: res.result.day,       // 生日天
                        address: res.result.address,   // 住所
                        email: res.result.email      // 邮箱
                    }
                }
            })
        },
        // 增加邀请记录
        insertInvitationLog: function () {
            let that = this
            let data = {
                roomId: that.roomId,
                userId: that.userId
            }
            insertInvitationLog(data).then((res) => {
                if (res) {
                    // console.log(res)
                }
            })
        },
        getRoomDetailByUid: function (homeowner, code) {
            let that = this
            getRoomDetailByUid(homeowner, code).then((res) => {
                if (res) {
                    if (res.code == 200 && res.result) {
                        // let code = res.result.url.split('/')[3]
                        let code = res.result.url
                        let url = that.isUrl + '://yiju.lanyanceshi.com/cardgame/#/room/info?invite=' + res.result.homeownerId + '&code=' + code
                        that.info = {
                            roomName: res.result.roomName,
                            homeownerName: res.result.homeownerName,
                            startTime: that.GLOBAL.timeFormat((res.result.startTime * 1000)),
                            theme: res.result.theme,
                            isCopy: res.result.isCopy == 1 ? 'あり' : 'なし',
                            isPublic: res.result.isPublic == 1 ? '公開' : '非公開',
                            peopleNumber: res.result.peopleNumber,
                            url: url,
                            password: res.result.password
                        }
                        // 房间ID
                        that.roomId = res.result.id
                        console.log(that.code)

                        if (localStorage.getItem('join_' + that.roomId)) {
                            that.isJoin = true
                        }
                        let roomStatus = 'uncommitted'
                        if (that.isJoin) {
                            switch (res.result.status) {
                                case 1:
                                    roomStatus = 'waiting'
                                case 2:
                                    roomStatus = 'ongoing'
                                    if (res.result.peopleNumber == 1) {
                                        that.$router.push({ path: '/game/index1', query: { roomId: res.result.id } })
                                    } else if (res.result.peopleNumber == 2) {
                                        that.$router.push({ path: '/game/index2', query: { roomId: res.result.id } })
                                    } else if (res.result.peopleNumber == 3) {
                                        that.$router.push({ path: '/game/index3', query: { roomId: res.result.id } })
                                    } else if (res.result.peopleNumber == 4) {
                                        that.$router.push({ path: '/game/index', query: { roomId: res.result.id } })
                                    }
                                case 3:
                                    roomStatus = 'ending'
                            }
                            that.$options.methods.createSocket(that.code, that.userEmail)
                        }
                        that.status = roomStatus
                        that.code = code
                    }
                }
            })
        },
        getroominfo() {
            let that = this, roomId = that.roomId
            getRoomDetail(roomId).then((res) => {
                if (res) {
                    if (res.code == 200 && res.result) {
                        // let code = res.result.url.split('/')[3]
                        let code = res.result.url
                        let url = that.isUrl + '://online.engagement-card.com/#/room/info?invite=' + res.result.homeownerId + '&code=' + code
                        that.info = {
                            roomName: res.result.roomName,
                            homeownerName: res.result.homeownerName,
                            startTime: that.GLOBAL.timeFormat((res.result.startTime * 1000)),
                            theme: res.result.theme,
                            isCopy: res.result.isCopy == 1 ? 'あり' : 'なし',
                            isPublic: res.result.isPublic == 1 ? '公開' : '非公開',
                            peopleNumber: res.result.peopleNumber,
                            url: url,
                            password: res.result.password
                        }
                        if (localStorage.getItem('join_' + that.roomId)) {
                            that.isJoin = true
                        }
                        console.log(that.isJoin)
                        let roomStatus = 'uncommitted'
                        if (that.isJoin) {
                            switch (res.result.status) {
                                case 1:
                                    roomStatus = 'waiting'
                                case 2:
                                    roomStatus = 'ongoing'
                                case 3:
                                    roomStatus = 'ending'
                            }
                        }
                        that.status = roomStatus
                        that.code = code
                    }
                }
            })
        },
        getRoomDetail: function () {
            let that = this, roomId = that.roomId
            getRoomDetail(roomId).then((res) => {
                if (res) {
                    if (res.code == 200 && res.result) {
                        // let code = res.result.url.split('/')[3]
                        let code = res.result.url
                        let url = that.isUrl + '://online.engagement-card.com/#/room/info?invite=' + res.result.homeownerId + '&code=' + code
                        that.info = {
                            roomName: res.result.roomName,
                            homeownerName: res.result.homeownerName,
                            startTime: that.GLOBAL.timeFormat((res.result.startTime * 1000)),
                            theme: res.result.theme,
                            isCopy: res.result.isCopy == 1 ? 'あり' : 'なし',
                            isPublic: res.result.isPublic == 1 ? '公開' : '非公開',
                            peopleNumber: res.result.peopleNumber,
                            url: url,
                            password: res.result.password
                        }
                        if (localStorage.getItem('join_' + that.roomId)) {
                            that.isJoin = true
                        }
                        console.log(that.isJoin)
                        let roomStatus = 'uncommitted'
                        that.$options.methods.createSocket(code, that.userEmail)
                        switch (res.result.status) {
                            case 1:
                                if (that.home == 1) {
                                    roomStatus = 'waiting'
                                    that.$router.push({ path: '/waiting', query: { roomId: res.result.id } })
                                }
                                break
                            case 2:
                                roomStatus = 'ongoing'
                                console.log("re", res)
                                if (res.result.peopleNumber == 1) {
                                    that.$router.push({ path: '/game/index1', query: { roomId: res.result.id } })
                                } else if (res.result.peopleNumber == 2) {
                                    that.$router.push({ path: '/game/index2', query: { roomId: res.result.id } })
                                } else if (res.result.peopleNumber == 3) {
                                    that.$router.push({ path: '/game/index3', query: { roomId: res.result.id } })
                                } else if (res.result.peopleNumber == 4) {
                                    that.$router.push({ path: '/game/index', query: { roomId: res.result.id } })
                                }
                                break
                            case 3:
                                roomStatus = 'ending'
                                break
                        }
                        that.status = roomStatus
                        that.code = code
                    }
                }
            })
        },
        doJoin: function () {
            this.dialogFormVisible = true
        },
        doSub: function (formName) {
            let that = this
            let data = that.passForm
            that.$refs[formName].validate((valid) => {
                if (valid) {
                    let formData = {
                        password: data.password
                    };
                    that.status = 'waiting'
                    localStorage.setItem('join_' + that.roomId, true)
                    console.log('房间ID：', that.roomId)
                    console.log('url：', that.code)
                    console.log('email：', that.userEmail)
                    that.$options.methods.createSocket(that.code, that.userEmail)
                    that.dialogFormVisible = false
                    localStorage.setItem('join_' + that.roomId, true)
                    that.isJoin = true
                    // this.$router.push('/my')
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        createSocket: function (code, userEmail) {
            let that = this
            let wsUrl = ''
            let ishttps = ('https:' == document.location.protocol) ? true : false
            // 正式环境
            // if(ishttps) wsUrl = 'wss://cardgame.unismart.dev/jeecg-boot/websocket/' + code + '/' + userEmail
            // else  wsUrl = 'ws://cardgame.unismart.dev/jeecg-boot/websocket/' + code + '/' + userEmail
            // 测试环境
            if (ishttps)
                wsUrl =
                    "wss://online.engagement-card.com/jeecg-boot/websocket/" +
                    code +
                    "/" +
                    userEmail;
            else
                wsUrl =
                    "ws://online.engagement-card.com/jeecg-boot/websocket/" +
                    code +
                    "/" +
                    userEmail;
            createSocket(wsUrl)
        },
    }
}
</script>

<style scoped>
@import '../../assets/css/base.css';

.join-btn {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #63AEB0;
    font-weight: 700 !important;
    font-size: 14px;
    /* width: 100%; */
}

.join-btn {
    border: 1.5px solid #63AEB0;
    box-sizing: border-box;
    border-radius: 4px;
    color: #63AEB0;
    font-weight: 700 !important;
    font-size: 14px;
    /* width: 100%; */
    margin: 0 0 12px !important;
}


.join-btn span {
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

.join-btn span {
    color: #1E7578;
}

.waiting-room-in-box {
    width: 772px;
    height: 540px;
    margin: 0 auto;
    margin-top: 10%;
    background: #FFF;
    box-shadow: 0 10px 20px #63aeb0;
    border-radius: 24px;
    padding: 38px 42px;
    padding-bottom: 0
}

.waiting-room-in-box .waiting-header {
    height: 60px;
    line-height: 23px
}

.waiting-room-in-box .waiting-header .el-icon-arrow-left {
    line-height: 25px;
    color: #83c3c5;
    font-weight: 900
}

.waiting-room-in-box .waiting-header .title {
    font-weight: 700;
    font-size: 20px;
    color: #83c3c5;
    flex: 1;
    text-align: center
}

.waiting-room-in-box .waiting-header .des {
    font-weight: 400;
    font-size: 12px;
    line-height: 20px;
    color: #cacaca
}

.waiting-room-in-box .waiting-info-box {
    margin-bottom: 20px;
}

.waiting-room-in-box .waiting-info-box .item {
    font-size: 16px;
    line-height: 42px
}

.waiting-room-in-box .waiting-info-box .item .label {
    flex: 2;
    color: #686868
}

.waiting-room-in-box .waiting-info-box .item .text {
    flex: 3;
    color: #bebebe
}

/*  按钮  */
.waiting-room-in-box .btns-box {
    margin-top: 10px
}

.waiting-room-in-box .btns-box button {
    width: 196px;
    height: 39px;
    line-height: 9px;
    border-radius: 8px;
    font-size: 14px;
    margin: 7px 0 !important
}

.waiting-room-in-box .btns-box .save-btn {
    background: #83c3c5;
    border: 1.5px solid #83c3c5;
    color: #fff;
}

/* 等待状态 */
.waiting-des-box {
    width: 449px;
    height: 97px;
    margin: 0 auto;
    background: #ECECEC;
    border-radius: 18px;
    font-size: 16px;
    line-height: 23px;
    text-align: center;
    color: #686868;
}

@media (max-width: 460px) {
    .waiting-room-in-box {
        width: 90%;
        min-width: 305px;
        height: auto;
        margin-bottom: 3rem;
        padding: 1rem .5rem;
    }

    .waiting-room-in-box .waiting-info-box {
        font-size: 1.2rem;
    }

    .waiting-room-in-box .waiting-info-box .item {
        font-size: .8rem
    }
}
</style>

<style>
/*  弹窗  */
.waiting-room-in-box .el-dialog {
    width: 25rem;
    border-radius: 12px;
}

.waiting-room-in-box .el-dialog .el-dialog__header {
    text-align: center;
}

.waiting-room-in-box .el-dialog .el-dialog__header .el-dialog__title {
    font-weight: 700;
    font-size: 18px;
    line-height: 21px;
    color: #686868 !important;
}

.waiting-room-in-box .el-dialog .el-dialog__body {
    padding: 10px 30px;
}

.waiting-room-in-box .el-dialog .el-dialog__body .el-form-item {
    margin-right: 0
}

.waiting-room-in-box .el-dialog .el-dialog__body .el-form-item .el-input__inner {
    height: 48px;
    background: rgba(189, 189, 189, 0.18);
    border-radius: 8px;
}

.waiting-room-in-box .el-dialog .el-dialog__body .el-form-item .el-input__suffix {
    margin: 0;
}

.waiting-room-in-box .el-dialog .el-dialog__footer {
    padding: 0 20px 20px;
}

.waiting-room-in-box .el-dialog .el-dialog__footer .save-btn {
    width: 100%;
    height: 46px;
    background: #63AEB0;
}

.waiting-room-in-box .el-dialog .el-dialog__footer .save-btn span {
    font-weight: 700;
    font-size: 16px;
    line-height: 19px;
    color: #fff;
}

@media (max-width: 460px) {

    /*  弹窗  */
    .waiting-room-in-box .el-dialog {
        width: 90%;
        border-radius: 1rem;
    }
}
</style>
