<template>
  <div class="register-box column">
    <div class="register-header column">
      <div class="title-box row">
        <img src="../../assets/images/logo.png" />
        <div class="title">アカウント作成</div>
      </div>
      <div class="des">もっとワクワークする世の中を</div>
    </div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="form-box column"
    >
      <div class="column">
        <div class="form-item row m-column">
          <el-form-item label="お名前" prop="realname">
            <el-input
              v-model="ruleForm.realname"
              placeholder="お名前を入力"
            ></el-input>
          </el-form-item>
          <el-form-item label="フリガナ" prop="falsename">
            <el-input
              v-model="ruleForm.falsename"
              placeholder="フリガナを入力"
            ></el-input>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="ニックネーム" prop="nickname">
            <el-input
              v-model="ruleForm.nickname"
              maxlength="8"
              placeholder="ニックネームを入力"
            ></el-input>
          </el-form-item>
          <el-form-item label="性別" prop="sex" class="sex-box m-row">
            <el-radio-group v-model="ruleForm.sex" class="m-row">
              <el-radio-button
                v-for="(item, index) in sexBox"
                :key="index"
                :label="index"
                :value="index"
                name="sex"
                >{{ item }}</el-radio-button
              >
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <div class="birthday-box column">
            <!-- <label class="el-form-item__label" style="width: 100px;">生年月日</label> -->
            <div class="row m-row">
              <el-form-item label="生年月日" prop="year">
                <el-select
                  v-model="ruleForm.year"
                  placeholder="年を選択してください"
                  class="birthday-year"
                  @change="monthChange"
                >
                  <el-option label="年" value=""></el-option>
                  <el-option
                    v-for="index in 2022"
                    v-if="index >= 1900"
                    :key="index"
                    :label="index"
                    :value="index"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label=" " prop="month">
                <el-select
                  v-model="ruleForm.month"
                  placeholder="月を選択してください"
                  class="birthday-month"
                  @change="monthChange"
                >
                  <el-option label="月" value=""></el-option>
                  <el-option
                    v-for="index in 12"
                    v-if="index >= 1"
                    :key="index"
                    :label="index"
                    :value="index"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label=" " prop="day">
                <el-select
                  v-model="ruleForm.day"
                  placeholder="日を選択してください"
                  class="birthday-day"
                >
                  <el-option label="日" value=""></el-option>
                  <el-option
                    v-for="index in choosedate"
                    v-if="index >= 1"
                    :key="index"
                    :label="index"
                    :value="index"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <el-form-item label="住所" prop="address">
            <el-select
              v-model="ruleForm.address"
              placeholder="都道府県を選択"
              class="select-region"
            >
              <el-option label="選択してください" value=""></el-option>
              <el-option
                v-for="(item, index) in region"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="メールアドレス" prop="email">
            <el-input
              v-model="ruleForm.email"
              placeholder="メールアドレスを入力"
            ></el-input>
          </el-form-item>
          <el-form-item label="メールアドレスを再入力" prop="reemail">
            <el-input
              v-model="ruleForm.reemail"
              placeholder="確認のためメールアドレスを再入力"
            ></el-input>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="パスワード" prop="password">
            <el-input
              v-model="ruleForm.password"
              show-password
              placeholder="パスワードを入力"
            ></el-input>
          </el-form-item>
          <el-form-item label="パスワードを再入力" prop="repassword">
            <el-input
              v-model="ruleForm.repassword"
              show-password
              placeholder="確認のためパスワードを再入力"
            ></el-input>
          </el-form-item>
        </div>
      </div>
      <div class="agree-box">
        <el-form-item label="" prop="agree">
          <el-checkbox
            v-model="ruleForm.agree"
            label=""
            name="agree"
            class="agree-check"
          ></el-checkbox>
          <el-link type="primary">利用規約</el-link>に同意する
        </el-form-item>
      </div>
      <div
        class="btns-box row row-center m-column"
        style="width: 80%; margin: 0 auto"
      >
        <el-button class="register-btn" @click="doReg('ruleForm')"
          >アカウントを作成する</el-button
        >
        <el-button class="login-btn" @click="toLogin"
          >アカウントをお持ちの方はこちらへ</el-button
        >
        <!-- <router-link to="/login"><el-button class="login-btn">ログインへ</el-button></router-link> -->
      </div>
    </el-form>
  </div>
</template>

<script>
import { doReg, doLogin } from "../../api/api.js";
import { mapMutations } from "vuex";
export default {
  name: "register",
  data() {
    var reemailValidator = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("確認のためメールアドレスを再入力"));
      } else if (value !== this.ruleForm.email) {
        callback(new Error("メールアドレスが一致していません"));
      } else {
        callback();
      }
    };
    var repasswordValidator = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("確認のためパスワードを再入力"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("パスワードが一致していません!"));
      } else {
        callback();
      }
    };
    return {
      invite: "",
      code: "",
      region: this.GLOBAL.region,
      sexBox: this.GLOBAL.sexBox,
      choosedate: 30,
      /* ruleForm: {
				    realname: '测试5',  // 姓名
				    falsename: 'ceshi', // 读音
				    nickname: '测试昵称',  // 昵称
				    sex: 0,        // 性别
				    year: '1990',      // 生日年份
				    month: '1',     // 生日月份
				    day: '01',       // 生日天
				    birthday: '',  // 生日
				    address: '北海道',   // 住所
				    email: '12345@126.com',     // 邮箱
				    reemail: '12345@126.com',   // 确认邮箱
				    password: '123456',  // 密码
				    repassword: '123456',// 确认密码
				    agree: true      // 同意
				}, */
      ruleForm: {
        realname: "", // 姓名
        falsename: "", // 读音
        nickname: "", // 昵称
        sex: 0, // 性别
        year: "", // 生日年份
        month: "", // 生日月份
        day: "", // 生日天
        birthday: "", // 生日
        address: "", // 住所
        email: "", // 邮箱
        reemail: "", // 确认邮箱
        password: "", // 密码
        repassword: "", // 确认密码
        agree: false, // 同意
      },
      rules: {
        realname: [
          {
            required: true,
            message: "お名前を入力",
            trigger: "blur",
          },
        ],
        falsename: [
          {
            required: true,
            message: "フリガナを入力",
            trigger: "blur",
          },
          {
            validator: function (rule, value, callback) {
              //校验中文的正则：/^[\u4e00-\u9fa5]{0,}$/
              if (
                /^[\u30A1-\u30F6\u30FC]+$/.test(value) == true ||
                /^ [\uFF66-\uFF9F]+$/.test(value) == true
              ) {
                callback();
              } else {
                //校验通过
                return callback(new Error("カタカナを入力してください"));
              }
            },
            trigger: "blur",
          },
        ],
        nickname: [
          {
            required: true,
            message: "ニックネームを入力",
            trigger: "blur",
          },
        ],
        sex: [
          {
            required: true,
            message: "性別を選択してください",
            trigger: "change",
          },
        ],
        year: [
          {
            required: true,
            message: "年を選択してください",
            trigger: "change",
          },
        ],
        month: [
          {
            required: true,
            message: "月を選択してください",
            trigger: "change",
          },
        ],
        day: [
          {
            required: true,
            message: "日を選択してください",
            trigger: "change",
          },
        ],
        address: [
          {
            required: true,
            message: "都道府県を選択",
            trigger: "change",
          },
        ],
        email: [
          {
            type: "email",
            required: true,
            message: "メールアドレスを入力",
            trigger: "blur",
          },
        ],
        reemail: [
          {
            type: "email",
            required: true,
            validator: reemailValidator,
            message: "確認のためメールアドレスを再入力",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "パスワードを入力",
            trigger: "blur",
          },
        ],
        repassword: [
          {
            required: true,
            validator: repasswordValidator,
            message: "確認のためパスワードを再入力",
            trigger: "blur",
          },
        ],
      },
    };
  },
  components: {},
  created() {
    // 接受参数
    this.invite = this.$route.query.invite;
    this.code = this.$route.query.code;
  },
  methods: {
    ...mapMutations(["changeLogin"]),
    toLogin: function () {
      this.$router.push("/login");
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
    doLogin() {
      let that = this;
      let data = this.ruleForm;
      let sendData = {};
      sendData.username = data.email;
      sendData.password = data.password;
      // that.$router.push('/my')
      doLogin(sendData).then((res) => {
        if (res) {
          console.log(res.result.token);

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
                code: that.code,
              },
            });
          } else {
            that.$router.push("/my");
          }
        }
      });
    },
    doReg: function (formName) {
      let that = this;
      let data = that.ruleForm;
      let email = data.email;
      console.log("www");
      var reg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
      if (reg.test(email)) {
        console.log("验证成功");
      } else {
        console.log("邮箱验证失败");
        return false;
      }
      console.log(that);
      that.$refs[formName].validate((valid) => {
        console.log("valid", valid);
        if (valid) {
          if (!data.agree) {
            that.$message({
              message: "利用規約をご覧ください",
              type: "warning",
            });
            return false;
          }
          let formData = {
            realname: data.realname, // 姓名
            falsename: data.falsename, // 读音
            nickname: data.nickname, // 昵称
            sex: data.sex, // 性别
            birthday: data.year + "-" + data.month + "-" + data.day, // 生日
            address: data.address, // 住所
            email: data.email, // 邮箱
            password: data.password, // 密码
          };
          let ret = doReg(formData).then((res) => {
            console.log(res);
            if (res.code == 200) {
              that.$message({
                message: "登録しました",
                type: "success",
              });
              console.log(that);
              that.doLogin();
            }else{
              that.$message({
                message:res.message,
                type: "error",
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/base.css";

.register-box {
  width: 696px;
  height: 692px;
  margin: 0 auto;
  margin-top: 5%;
  background: #fff;
  box-shadow: 0 10px 20px #63aeb0;
  border-radius: 24px;
  padding: 38px 42px;
  padding-bottom: 0;
}

.register-box .register-header {
  height: 76px;
}

.register-box .register-header img {
  width: 30.35px;
  height: 29.11px;
}

.register-box .register-header .title-box .title {
  font-weight: 700;
  font-size: 20px;
  line-height: 23px;
  color: #83c3c5;
}

.register-box .register-header .des {
  font-weight: 400;
  font-size: 12px;
  line-height: 20px;
  color: #cacaca;
}

.register-box .form-box {
  width: 100%;
  margin: 0 auto;
  color: #686868;
}

.register-box .select-region {
  width: 100%;
}

.register-box .birthday-box .el-form-item {
  margin-right: 0;
}

.register-box .birthday-year {
  width: 113px;
}

.register-box .birthday-month,
.birthday-day {
  width: 82px;
  margin-left: 25px;
}

.register-box .birthday-day {
  margin-right: 30px;
}

.register-box .btns-box {
  margin-top: 10px;
}

.register-box .btns-box button {
  width: 196px;
  height: 39px;
  line-height: 9px;
  border-radius: 8px;
  font-size: 14px;
  margin: 7px 0 !important;
}

.register-box .btns-box .register-btn {
  background: #83c3c5;
  border: 1.5px solid #83c3c5;
  color: #fff;
  margin-right: 10px !important;
  flex: 1;
}

.register-box .btns-box a {
}

.register-box .btns-box .login-btn {
  border: 1.5px solid #83c3c5;
  box-sizing: border-box;
  color: #83c3c5;
  margin-left: 10px !important;
  flex: 1;
}

@media (max-width: 460px) {
  .register-box {
    width: 90%;
    height: auto;
    margin: 0 auto;
    margin-top: 3rem;
    padding: 1rem 0.5rem;
  }

  .register-box .form-box {
    width: 100%;
    margin: 0 auto;
    color: #686868;
  }

  .register-box .form-box div {
    width: 100%;
  }

  /*  性别选择  */
  .register-box .birthday-box .birthday-year {
  }

  .register-box .birthday-box .birthday-month,
  .birthday-box .birthday-day {
    margin-left: 0;
  }

  .register-box .birthday-box .birthday-day {
    margin-right: 0;
  }

  .register-box .btns-box .register-btn {
    margin: 0 auto !important;
  }

  .register-box .btns-box .login-btn {
    margin: 0 auto !important;
    margin-top: 0.5rem !important;
  }
}
</style>
<style>
.register-box .form-item {
  justify-content: space-between;
}

.register-box .el-form-item {
  flex: 1;
  display: -webkit-flex;
  display: flex;
  flex-direction: column;
}

.register-box .el-form-item:nth-child(odd) {
  margin-right: 20px;
}

.register-box .el-form-item__label {
  text-align: left;
  line-height: 20px;
  width: auto !important;
}

.register-box
  .el-form-item.is-required:not(.is-no-asterisk)
  .register-box
  .el-form-item__label-wrap
  > .el-form-item__label:before,
.el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label:before {
  content: "";
}

.el-form-item__content {
  margin-left: 0 !important;
}

.register-box .form-box .el-input__inner {
  height: 43px;
  margin: 8px auto;
  margin-bottom: 3px;
  border: 0;
  background: rgba(189, 189, 189, 0.18);
  border-radius: 8px;
  color: #686868;
}

.register-box .form-box .sex-box .el-radio-group {
  height: 43px;
  margin: 8px auto;
}

.register-box .form-box .sex-box .el-radio-button__inner {
  border-radius: 8px !important;
  background: rgba(189, 189, 189, 0.18);
  margin-left: 30px;
  width: 88px;
  height: 43px;
  line-height: 20px;
  border: 0;
  color: #9c9c9c;
}

.register-box .form-box .sex-box .el-radio-button__inner:hover {
  color: #83c3c5;
}

.register-box
  .form-box
  .sex-box
  .el-radio-button:first-child
  .el-radio-button__inner {
  margin-left: 0;
  border-left: none;
}

.register-box
  .form-box
  .sex-box
  .el-radio-button__orig-radio:checked
  + .el-radio-button__inner {
  color: #fff;
  background-color: #83c3c5;
  border-color: #83c3c5;
  -webkit-box-shadow: -1px 0 0 0 #83c3c5;
  box-shadow: -1px 0 0 0 #83c3c5;
}

.register-box .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #83c3c5;
  border-color: #83c3c5;
}

.register-box .el-checkbox__input.is-checked .el-checkbox__inner:hover {
  border-color: #83c3c5;
}

.register-box .el-input__suffix {
  height: 43px;
  margin: 8px 0;
  margin-bottom: 3px;
}

.register-box .agree-box .el-form-item {
  margin-bottom: 0;
}

.register-box .agree-box .el-form-item__content {
  display: -webkit-flex;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.register-box .agree-box .agree-check .el-checkbox__input .el-checkbox__inner {
  width: 15px;
  height: 15px;
  margin-right: 5px;
}

.register-box .agree-box .el-link.el-link--primary {
  color: #83c3c5;
}

@media (max-width: 460px) {
  .register-box .form-item {
    justify-content: space-between;
  }

  .register-box .form-item .el-form-item {
    margin-right: 0;
  }

  .register-box .form-box .el-input--suffix .el-input__inner {
    padding-right: 0;
  }

  /*  性别选择  */
  .register-box .form-box .el-input__inner {
    margin: 0.5rem auto;
    margin-bottom: 3px;
  }

  .register-box .form-box .sex-box .el-radio-group {
    height: 43px;
    margin: 0.5rem auto;
  }

  .register-box .form-box .sex-box .el-radio-group .el-radio-button {
    flex: 1;
  }

  .register-box .form-box .sex-box .el-radio-button__inner {
    margin: 0;
  }

  .register-box .birthday-box .el-form-item {
    flex: 1;
  }

  .register-box .birthday-box .el-form-item .el-form-item__content {
    width: 95%;
  }
}
</style>
