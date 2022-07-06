<template>
	<div class="waiting-box column">
		<div class="waiting-header column">
			<div class="title-box row">
				<i class="el-icon-arrow-left" @click="goBack()" style="cursor: pointer"></i>
				<div class="title">待機室</div>
				<el-button @click="deleteRoom" class="join-btn" style="font-weight: 700 !important">ルームを削除
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
				<div class="label">開始時刻</div>
				<div class="text">{{ info.startTime }}</div>
			</div>
			<div class="item row">
				<div class="label">テーマ</div>
				<div class="text">{{ info.theme }}</div>
			</div>
			<div class="item row">
				<div class="label">カードコピー機能</div>
				<div class="text" v-if="info.isCopy">あり</div>
				<div class="text" v-else>なし</div>
			</div>
			<div class="item row">
				<div class="label">手札公開</div>
				<div class="text" v-if="info.isPublic">公開</div>
				<div class="text" v-else>非公開</div>
			</div>
			<div class="item row">
				<div class="label">人数</div>
				<div class="text">{{ info.peopleNumber }}人</div>
			</div>
			<div class="item row m-column url-box">
				<div class="label">招待URL</div>
				<div class="text row">
					<div class="url-text">{{ info.url }}</div>
					<el-button class="copy-btn" id="copy-btn" v-clipboard:copy="copyInfo" v-clipboard:success="onCopy"
						v-clipboard:error="onError">
						コピーする
					</el-button>
				</div>
			</div>
		</div>
		<div class="waiting-info-box column">
			<h5>参加人員(プレイヤー設定)</h5>
			<div class="personnel-box row">
				<div class="card column column-center row-center" style="cursor: pointer" v-for="(item, index) in lists" :key="index"
					@click="checkUser(item, index, item.checked)" :class="{ checked: item.checked }">
					<i class="el-icon-setting" v-if="info.homeowner == item.id"></i>
					<el-badge :value="item.sort" class="item" type="primary" v-show="item.checked"></el-badge>
					<div class="text-box">
						<div class="name">{{ item.username }}</div>
						<div class="tag" v-if="info.homeowner == item.id">ファシリテーター</div>
						<div class="tag" v-else>{{ item.tag }}</div>
					</div>
				</div>
			</div>
			<div class="btns-box row row-center">
				<el-button class="save-btn" @click="startGame()">スタート</el-button>
			</div>
		</div>
	</div>
</template>

<script>
import {
	createSocket,
	WSClose
} from '../../utils/websocket.js'
import {
	getUserInfo,
	getUserInfoById,
	getRoomDetail,
	startGame,
	delRoom
} from '../../api/api.js';
export default {
	name: 'app',
	data() {
		return {
			isUrl: '', // 网络请求协议
			tags: this.GLOBAL.tags,
			// 房间ID
			roomId: 'c0e6b1e56ed0141b000650f2ec5232d9',
			userId: '1505124724596801538', // 用户 ID
			userEmail: '2856678408@qq.com', // 会员邮箱
			// 房间信息
			info: {
				homeowner: '1505124724596801538',
				roomName: '测试房间1',
				homeownerName: '戸田勇大1',
				startTime: '2022-4-6 10:00',
				theme: '仕事',
				isCopy: false,
				isPublic: false,
				peopleNumber: 4,
				url: 'http://online.engagement-card.com/#/room/info?invite=1505124724596801538&code=123456'
			},
			// 复制信息
			copyInfo: '',
			// 参加者列表
			lists: [
				/* { email: "2856678408@qq.com", id:"1505124724596801538", lineStatus:"online", onlineNumber:4, realname:"戸田勇大1", username:"2856678408@qq.com", tag: '観戦者', isHomewer: true,  checked: false, sort: 0 },
				{ email: "2856678409@qq.com", id:"1505124724596801539", lineStatus:"online", onlineNumber:4, realname:"戸田勇大2", username:"2856678409@qq.com", tag: '観戦者', isHomewer: false, checked: false, sort: 0 },
				{ email: "2856678410@qq.com", id:"1505124724596801540", lineStatus:"online", onlineNumber:4, realname:"戸田勇大3", username:"2856678410@qq.com", tag: '観戦者', isHomewer: false, checked: false, sort: 0 },
				{ email: "2856678411@qq.com", id:"1505124724596801541", lineStatus:"online", onlineNumber:4, realname:"戸田勇大4", username:"2856678411@qq.com", tag: '観戦者', isHomewer: false, checked: false, sort: 0 } */
			],
			// 选择列表
			checkLists: [],
			checkNum: 0, // 已选择数量
			// 开始游戏后手牌推送
			// userCards: []
		}
	},
	created() {
		this.isUrl = this.GLOBAL.getHttps()
		this.roomId = this.$route.query.roomId
		this.userId = localStorage.getItem('homeowner')
		this.userEmail = localStorage.getItem('userEmail')
		this.userName = localStorage.getItem('userName')
    // alert(this.roomId)
		this.lists = localStorage.getItem('room_' + this.roomId) ? JSON.parse(localStorage.getItem('room_' + this
			.roomId)) : [],
			this.checkNum = this.checkLists.length
		this.getRoomDetail()
	},
	/* watch: { }, */
	components: {},
	mounted() {
		window.addEventListener('onmessageWS', this.getsocketData)
	},
	methods: {
		// 获取用户
		// 链接ws
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
		deleteRoom() {
			let that = this
			delRoom(that.roomId).then(res => {
				console.log(res)
				if (res.code == 200) {
          localStorage.removeItem('room_' + this.roomId)
					that.$message({
						message: res.message,
						type: 'success'
					});
					setTimeout(function () {
						that.$router.push("/");
					}, 1000)
				}
			})
		},
		getsocketData: function (e) {
			// console.log(e.detail.data)
			if (e.detail.data == '发生连接错误') return false
			let data = JSON.parse(e.detail.data),
				newUser = {},
				isNew = true,
				that = this
			// console.log(data)
			// 处理发牌操作
			if (data.result && data.operateMsg == '游戏开始，向每位玩家分牌') {
				let userCards = JSON.parse(data.result)
				console.log("result", data.result)
				// console.log(userCards)
				let playerIds = Object.keys(userCards),
					players = [],
					count = playerIds.length
				console.log('房间ID', that.roomId)
				console.log('储存内容', JSON.stringify(data.result))
				localStorage.setItem('room_usercards' + that.roomId, data.result)
				if (that.checkNum == 1) {
					that.$router.push({
						path: '/game/index1',
						query: {
							roomId: that.roomId
						}
					})
				} else if (that.checkNum == 2) {
					that.$router.push({
						path: '/game/index2',
						query: {
							roomId: that.roomId
						}
					})
				} else if (that.checkNum == 3) {
					that.$router.push({
						path: '/game/index3',
						query: {
							roomId: that.roomId
						}
					})
				} else if (that.checkNum == 4) {
					that.$router.push({
						path: '/game/index',
						query: {
							roomId: that.roomId
						}
					})
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
					if (data.id == this.info.homeowner) tag = 'ファシリテーター'
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
					// console.log(newUser)
					this.lists.push(newUser)
				}
				// console.log('WS数据',JSON.parse(e.detail.data))
				// console.log(JSON.stringify(this.lists))
				localStorage.setItem('room_' + this.roomId, JSON.stringify(this.lists))
				// localStorage.removeItem('room_' + this.roomId)
			}
			// WSClose()
		},
		goBack: function () {
      localStorage.removeItem('room_' + this.roomId)
			this.$router.push({
				path: '/',
			})
		},
		// 复制功能
		onCopy: function (e) {
			let that = this
			that.$message({
				message: 'コピーしました',
				type: 'success'
			})
		},
		onError: function (e) {
			let that = this
			that.$message({
				message: 'コピーできませんでした',
				type: 'error'
			})
		},
		// 选择游戏者
		checkUser: function (item, index, checked) {
			let that = this
			if (checked) {
				// 取消选择
				let i = 0
				if (that.checkNum > 0) that.checkNum -= 1
				// 遍历
				// console.log(that.checkLists)
				that.checkLists.forEach((data, key) => {
					if (item.id == data.id) {
						that.checkLists.splice(key, 1)
					}
				})
				that.checkLists.forEach((data, key) => {
					i++
					data.sort = i
					that.lists.forEach((data2, key2) => {
						if (data.id == data2.id) {
							data2.sort = i
							data2.tag = that.tags[(i - 1)]
							// console.log(data2)
							that.lists[key2] = data2
						}
					})
					that.checkLists[key] = data
				})
				// console.log(that.checkLists)
				// console.log(that.lists)
				that.lists[index].checked = false
				that.lists[index].sort = 0
				that.lists[index].tag = '観戦者'
			} else {
				if (that.checkNum >= that.info.peopleNumber) {
					that.$message({
						message: that.info.peopleNumber + 'まで選択可能です',
						type: 'error'
					})
					return false
				}
				// 选择
				that.checkNum += 1
				let user = {
					id: item.id,
					email: item.email,
					lineStatus: item.lineStatus,
					onlineNumber: item.onlineNumber,
					realname: item.realname,
					username: item.username,
					sort: that.checkNum
				}
				that.lists[index].checked = true
				that.lists[index].sort = that.checkNum
				that.lists[index].tag = that.tags[(that.checkNum - 1)]
				that.checkLists.push(user)
			}
		},
		// 开始游戏
		startGame: function () {
			let that = this
			if (that.checkNum < that.info.peopleNumber) {
				that.$message({
					message: '参加者が足りないためお待ちください',
					type: 'error'
				})
				return false
			}
			let data = []
			that.checkLists.forEach((item, index) => {
				//console.log(item)
				data.push({
					roomId: that.roomId,
					sequence: item.sort,
					userId: item.id
				})
			})
			//console.log(data)
			startGame(data).then((res) => {
				//console.log(res)
				if (res) {
					if (res.code == 200 && res.result) {
						console.log("res", res)
						let createdNum = res.result.createdNum,
							noCreatNum = res.result.noCreatNum
						// WSClose()
					}
				}
			})
		},
		// 获取房间信息
		getRoomDetail: function () {
			let that = this,
				roomId = that.roomId
			// 遍历用户
			that.lists.forEach((item, index) => {
				if (item.checked) {
					that.checkLists.push(item)
				}
			})
			that.checkNum = that.checkLists.length

			getRoomDetail(roomId).then((res) => {
				if (res) {
					if (res.code == 200 && res.result) {
						// let code = res.result.url.split('/')[3]
						let code = res.result.url
						// let url = that.isUrl + '://yiju.lanyanceshi.com/cardgame/#/room/info?invite=' + res
						// 	.result.homeownerId + '&code=' + code
						// let url = that.isUrl + '://online.engagement-card.com/#/room/info?invite=' + res
						// 	.result.homeownerId + '&code=' + code
						let url = that.isUrl + '://online.engagement-card.com/#/room/info?invite=' + res
						.result.homeownerId + '&code=' + code
						that.info = {
							homeowner: res.result.homeownerId,
							roomName: res.result.roomName,
							homeownerName: res.result.homeownerName,
							startTime: that.GLOBAL.timeFormat((res.result.startTime * 1000)),
							theme: res.result.theme,
							isCopy: res.result.isCopy ,
							isPublic: res.result.isPublic ,
							peopleNumber: res.result.peopleNumber,
							url: url
						}
						let cpub = res.result.isPublic == 1 ? '公開' : '非公開'
						let jn = res.result.isCopy == 1 ? 'あり' : 'なし'
						let copyInfo = res.result.homeownerName +
							'さんからの招待です。\n以下から参加できます。（初めての方は新規登録をお願いします）\n URL: ' +
							url + ' \n \n 招待コード：' + res.result.password + ' \n \n ・ゲーム情報 \n ホスト名：' + res
								.result.homeownerName + '\n ルーム名：' + res.result.roomName + '\n 開始時刻：' + that
									.GLOBAL.timeFormat((res.result.startTime * 1000)) + '\n \n ・ゲーム設定' +
							'\n テーマ：' + res.result.theme + '\n 人数：' + res.result.peopleNumber +
							'\n 手札公開：' + cpub + '\n カードコピー機能：' + jn
						that.copyInfo = copyInfo
						// let code = res.result.url
						// console.log(that.userEmail)
						// 链接
						that.$options.methods.createSocket(code, that.userEmail)
					}
				}
			})
		}
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

.waiting-box {
	width: 696px;
	height: 720px;
	margin: 0 auto;
	margin-top: 3%;
	background: #FFF;
	box-shadow: 0 10px 20px #63aeb0;
	border-radius: 24px;
	padding: 38px 42px;
	padding-bottom: 0
}

.waiting-header {
	height: 60px;
	line-height: 23px
}

.waiting-header .el-icon-arrow-left {
	line-height: 25px;
	color: #83c3c5;
	font-weight: 900
}

.waiting-header .title {
	font-weight: 700;
	font-size: 20px;
	color: #83c3c5;
	flex: 1;
	text-align: center
}

.waiting-header .des {
	font-weight: 400;
	font-size: 12px;
	line-height: 20px;
	color: #cacaca
}

.waiting-box .waiting-info-box {
	margin-bottom: 20px;
}

.waiting-box .waiting-info-box .item {
	font-size: 16px;
	line-height: 42px
}

.waiting-box .waiting-info-box .item .label {
	flex: 2;
	color: #686868
}

.waiting-box .waiting-info-box .item .text {
	flex: 3;
	color: #bebebe
}

.waiting-box .waiting-info-box .url-box {}

.waiting-box .waiting-info-box .url-box .text {
	flex-direction: row
}

.waiting-box .waiting-info-box .url-box .text .url-text {
	width: 80%;
	font-size: 16px;
	line-height: 23px;
	color: #83C3C5;
}

.waiting-box .waiting-info-box .url-box .text .copy-btn {
	height: 46px;
	background: #83C3C5;
	border-radius: 8px;
	color: #fff;
}

.waiting-box .personnel-box {
	flex-wrap: wrap;
}

.waiting-box .personnel-box .card {
	width: 20%;
	height: 79px;
	border: 2px solid #eaeaea;
	box-sizing: border-box;
	border-radius: 8px;
	position: relative;
	margin: 10px 15px
}

.waiting-box .personnel-box .card.checked {
	border: 2px solid #63aeb0
}

.waiting-box .personnel-box .card.checked .name {
	font-weight: 700;
	color: #63aeb0
}

.waiting-box .personnel-box .card .el-icon-setting {
	font-size: 16px;
	color: #1e7578;
	font-weight: 800;
	position: absolute;
	top: 9px;
	left: 8px;
}

.waiting-box .personnel-box .card .el-icon-view {
	font-size: 16px;
	font-weight: 800;
	color: #EAEAEA;
	position: absolute;
	top: 9px;
	right: 8px;
}

.waiting-box .personnel-box .card .text-box {
	text-align: center;
	font-weight: 500;
	font-size: 14px;
	line-height: 20px;
	color: #bebebe
}

.waiting-box .personnel-box .card .tag {
	font-size: 12px;
	line-height: 17px;
	color: #cacaca
}

/*  按钮  */
.waiting-box .btns-box {
	margin-top: 10px
}

.waiting-box .btns-box button {
	width: 196px;
	height: 39px;
	line-height: 9px;
	border-radius: 8px;
	font-size: 14px;
	margin: 7px 0 !important
}

.waiting-box .btns-box .save-btn {
	background: #83c3c5;
	border: 1.5px solid #83c3c5;
	color: #fff;
}

@media (max-width: 460px) {
	.waiting-box {
		width: 90%;
		min-width: 305px;
		height: auto;
		margin-bottom: 3rem;
		padding: 1rem .5rem;
	}

	.waiting-box .waiting-info-box {
		font-size: 1.2rem;
	}

	.waiting-box .waiting-info-box .item {
		font-size: .8rem
	}

	.waiting-box .waiting-info-box .url-box .text .url-text {
		font-size: .8rem
	}

	.waiting-box .waiting-info-box .url-box .text .copy-btn {
		height: 2.4rem;
	}

	.waiting-box .personnel-box .card {
		width: 40%;
	}

	.waiting-box .personnel-box .card .el-icon-setting {
		font-size: 1rem;
	}
}
</style>
<style>
.waiting-box .personnel-box .card .el-badge {
	position: absolute;
	top: 0;
	right: 8px
}

.waiting-box .personnel-box .card .el-badge__content--primary {
	background: #63aeb0
}
</style>
