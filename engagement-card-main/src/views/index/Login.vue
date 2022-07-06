<template>
	<div class="login-box column">
		<div class="login-logo column row-center">
			<img src="../../assets/images/logo.png" />
			<div class="title">ログイン</div>
			<div class="des">もっとワクワークする世の中を</div>
		</div>
		<div class="login-form column column-center">
			<el-input placeholder="メールアドレス" id="username" v-model="form.username"></el-input>
			<el-input placeholder="パスワード" id="password" v-model="form.password" show-password></el-input>
			<div class="forget" @click="shows = true"><a href="#">パスワードをお忘れの方</a> </div>
		</div>
		<div class="btns-box column column-center">
			<el-button class="login-btn" @keyup.enter.native="ss()" @click="doLogin()">ログイン</el-button>
			<el-button class="register-btn" @click="toReg()">新規登録</el-button>
			<el-button class="facebook-btn" @click="faceLogin()"><span class="iconfont">&#xeb8d</span> Facebookでログイン
			</el-button>
			<!-- <el-button class="google-btn" @click="googleLogin()"></el-button> -->
			<div class="google-btn" @click="googleLogin()">
				<img style="width: 20px;height: 20px;border-radius: 50%;vertical-align: middle;margin-right: ;" src="../../assets/images/google.png" alt=""/> <span>Googleでログイン</span> 
			</div>
		</div>
		<!-- 忘记密码 -->
		<div class="poup" v-if="shows">
			<div class="mask" @click="hidemask"></div>
			<div class="center">
				<div class="title">パスワードお忘れの方</div>
				<div class="bboox">
					<div class="name">メールアドレス</div>
					<el-input placeholder="メールアドレスを入力" v-model="phone"></el-input>
					<div class="name">認証コード</div>
					<div class="fll">
						<el-input placeholder="認証コードを入力" v-model="yzm"></el-input>
						<el-button v-if="timeShow" :disabled="true">{{time}}s</el-button>
						<el-button v-if="!timeShow" class="login-btn" @click.stop="sendXin()">認証コードを送信</el-button>
					</div>
					<div class="name">新しいパスワード</div>
					<el-input placeholder="新しいパスワードを再入力" v-model="newPass"></el-input>
					<div class="name">新しいパスワードを確認</div>
					<el-input placeholder="確認のため新しいパスワードを再入力" v-model="aginPass"></el-input>
					<div class="bbt">
						<el-button class="login-btn" @click.stop="edit()">変更する</el-button>
					</div>
				</div>

			</div>
		</div>
	</div>
</template>

<script>
	import {
		mapMutations
	} from "vuex";
	import {
		doLogin,
		otherLogin,
		sendCode,
		changepwd
	} from "../../api/api.js";
	export default {
		name: "login",
		data() {
			return {
				form: {
					// username: 'test@work.com',
					// password: 'lod010513',
					username: "",
					password: "",
				},
				time: 60,
				timeShow: false,
				aginPass: "",
				newPass: "",
				yzm: "",
				phone: "",
				shows: false,
				invite: "",
				code: "",
			};
		},
		components: {},
		beforeCreate() {
			// console.log(123)
			// console.log(this.$route.query.token)
			let that = this;
			let token = that.$route.query.token;
			if (token) {
				otherLogin(token).then((res) => {
					if (res.code == 200) {
						that.changeLogin({
							Authorization: res.result.token,
							homeowner: res.result.userInfo.id,
							userEmail: res.result.userInfo.email,
							userName: res.result.userInfo.username,
						});
						that.$message({
							message: "ログインしました",
							type: "success",
							onClose: function() {
								if (that.invite && that.code) {
									that.$router.push({
										path: "/room/info",
										query: {
											invite: that.invite,
											code: that.code
										},
									});
								} else {
									that.$router.push("/my");
								}
							},
						});
					}
				});
			}
		},
		created() {
			// 接受参数
			this.invite = this.$route.query.invite;
			this.code = this.$route.query.code;
			document.addEventListener("keyup", this.ss);
		},
		beforeUnmount() {
			console.log('de;e')
			document.removeEventListener("keyup", this.ss);
		},
		methods: {
			...mapMutations(["changeLogin"]),
			ss(e) {
				let that = this;
				if (e.keyCode == 13) {
					that.doLogin();
					document.removeEventListener("keyup", this.ss);
				}
			},
      hidemask(){
        this.shows = false
      },
			doLogin: function() {
				let that = this;
				let data = this.form;
				if (!data.username) {
					that.$message({
						message: "メールアドレスを入力してください",
						type: "warning",
					});
					return false;
				}
				if (!data.password) {
					that.$message({
						message: "パスワードを入力してください",
						type: "warning",
					});
					return false;
				}
				// that.$router.push('/my')
				doLogin(data).then((res) => {
					if (res) {
						console.log(res.result.token);
						document.removeEventListener("keyup", this.ss);
						that.changeLogin({
							Authorization: res.result.token,
							homeowner: res.result.userInfo.id,
							userEmail: res.result.userInfo.email,
							userName: res.result.userInfo.username,
						});
						if (that.invite && that.code) {
							that.$router.push({
								path: "/room/info",
								query: {
									invite: that.invite,
									code: that.code
								},
							});
						} else {
							that.$router.push("/my");
						}
					}
				});
			},
			sendXin() {
				let that = this
				if (that.phone == "") {
					that.$message({
						message: res.message,
						type: "success",
					});
				} else {
					let data = {}
					data.to = that.phone
					sendCode(data).then(res => {
						console.log(res)
						if (res.code == 200) {
							that.$message({
								message: res.message,
								type: "success",
							});
							this.timeShow = true;
							let timer = setInterval(() => {
								this.time--;
								// console.log(this.time);
								if (this.time == 0) {
									clearInterval(timer);
									this.time = 60;
									this.timeShow = false;
								}
							}, 1000);
						}else{
							that.$message({
								message: res.message,
								type: "warning",
							});
						}
					})
				}
			},
			edit() {
				let that = this
				let aginPass = that.aginPass
				let newPass = that.newPass
				let yzm = that.yzm
				let phone = that.phone
				let data = {}
				data.code = yzm
				data.password = newPass
				data.email = phone
				if(aginPass != "" && newPass != "" && yzm != "" && phone != ""){
					if(aginPass == newPass){
						changepwd(data).then(res=>{
							console.log(res)
							if(res.code == 200){
								that.shows = false;
								that.$message({
									message: res.message,
									type: "success",
								});
							}else{
								that.$message({
									message: res.message,
									type: "warning",
								});
							}
						})
					}else{
						that.$message({
							message: "パスワードが間違っています",
							type: "warning",
						});

					}
				}
			},
			googleLogin() {
				let that = this;
				window.open(
					"https://online.engagement-card.com/dev/jeecg-boot/oauth/render/google"
				);
			},

			faceLogin() {
				let that = this;
				window.open(
					"https://online.engagement-card.com/dev/jeecg-boot/oauth/render/facebook"
				);
			},

			toReg: function() {
				let that = this;
				document.removeEventListener("keyup", this.ss);
				that.$router.push({
					path: "/register",
					query: {
						invite: that.invite,
						code: that.code
					},
				});
			},
		},
	};
</script>

<style>
	@import "../../assets/css/base.css";

	.poup {
		position: fixed;
		left: 0;
		top: 0;
		z-index: 99;
		width: 100%;
		height: 100%;
	}

	.mask {
		position: fixed;
		left: 0;
		top: 0;
		z-index: 999;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.3);
	}

	.poup .center {
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 9999;
		transform: translate(-50%, -50%);
		width: 400px;
		height: 620px;
		background: #ffffff;
		border-radius: 24px;
	}

	.poup .center .title {
		text-align: center;
		font-weight: 700;
		font-size: 20px;
		color: #83c3c5;
		margin-top: 34px;
	}

	.bboox {
		width: 80%;
		margin: 5px auto;
	}

	.poup .center .name {
		font-weight: 400;
		font-size: 16px;
		color: #686868;
		margin-top: 22px;
		margin-bottom: 9px;
	}

	.poup .center .el-input .el-input__inner {
		background: rgba(189, 189, 189, 0.18);
		border-radius: 8px;
		height: 48px;
		border: 0;
	}

	.fll {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.fll .el-input .el-input__inner {
		width: 147px;
	}

	.fll .el-button {
		width: 161px;
		height: 48px;
		background: #83c3c5;
		border-radius: 8px;
		font-size: 14px;
		color: #fff;
	}

	.fll .el-button:active {
		background: #83c3c5 !important;
	}

	.bbt {
		margin-top: 27px;
		text-align: center;
	}

	.bbt .el-button {
		width: 196px;
		height: 39px;
		border: 0;
		background: #83c3c5;
		border-radius: 8px;
		font-weight: 700;
		font-size: 14px;
		color: #ffffff;
	}

	.login-form .el-input__inner {
		color: #4d4d4d !important;
	}

	.el-input .el-input__inner {
		color: #4d4d4d !important;
	}

	.login-box {
		width: 446px;
		height: 680px;
		margin: 0 auto;
		margin-top: 4%;
		background: #fff;
		box-shadow: 0 10px 20px #63aeb0;
		border-radius: 24px;
	}

	.login-logo {
		width: 40%;
		margin: 0 auto;
		text-align: center;
		margin-top: 40px;
	}

	.login-logo img {
		width: 50%;
		margin: 0 auto;
		margin-bottom: 15px;
	}

	.login-logo div {
		font-family: "Noto Sans JP";
		font-style: normal;
		font-weight: 700;
		font-size: 20px;
		line-height: 29px;
		color: #83c3c5;
		text-align: center;
	}

	.login-logo .title {
		color: #83c3c5;
	}

	.login-logo .des {
		font-weight: 400;
		font-size: 12px;
		line-height: 17px;
		color: #cacaca;
	}

	.login-form {
		width: 77%;
		margin: 0 auto;
		margin-top: 20px;
	}

	.login-form .forget {
		width: 100%;
		text-align: right;
	}

	.login-form .forget a {
		font-size: 12px;
		line-height: 17px;
		text-align: center;
		text-decoration-line: underline;
		color: #a3a3a3;
	}

	.btns-box {
		margin-top: 10px;
	}

	.btns-box button {
		width: 196px;
		height: 39px;
		line-height: 9px;
		border-radius: 8px;
		font-size: 14px;
		margin: 7px 0 !important;
	}

	.login-box .btns-box .login-btn {
		background: #83c3c5;
		border: 1.5px solid #83c3c5;
		color: #fff;
	}

	.login-box .btns-box .register-btn {
		border: 1.5px solid #83c3c5;
		box-sizing: border-box;
		color: #83c3c5;
	}

	.login-box .btns-box .facebook-btn {
		background: #3a5ca9;
		border: 1.5px solid #3a5ca9;
		color: #fff;
	}

	.login-box .btns-box .google-btn {
		background: #FF8660;
		border: 1.5px solid #FF8660;
		color: #fff;
		width: 196px;
    	height: 39px;
		border-radius: 8px;
		font-size: 14px;
		display: flex;
		align-items: center;
		justify-content: center;
		line-height: 39px;
		cursor: pointer;
	}

	.login-box .btns-box .google-btn img{
		margin-right: 5px;
	}

	.login-box .btns-box .google-btn:hover{
		background: #D4491C !important;
	}


	@media (max-width: 460px) {
		.login-box {
			width: 100%;
			min-width: 305px;
			margin-bottom: 3rem;
		}
	}
</style>
<style>
	.login-form .el-input__inner {
		height: 48px;
		margin: 12px auto;
		border: 0;
		background: rgba(131, 195, 197, 0.18);
		border-radius: 8px;
		color: #c0c4cc;
	}

	.login-form .el-input__suffix {
		height: 43px;
		margin: 16px 0;
		margin-bottom: 3px;
	}
</style>
