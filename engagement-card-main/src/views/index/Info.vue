<template>
  <div class="info-box column">
    <div class="info-header row">
      <i class="el-icon-arrow-left" @click="toMy"></i>
      <div class="title">個人情報</div>
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
              :disabled="true"
              placeholder="お名前を入力"
            ></el-input>
          </el-form-item>
          <el-form-item label="フリガナ" prop="falsename">
            <el-input
              v-model="ruleForm.falsename"
              :disabled="true"
              placeholder="フリガナを入力"
            ></el-input>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="ニックネーム" prop="nickname">
            <el-input
              v-model="ruleForm.nickname"
              placeholder="ニックネームを入力"
              maxlength="8"
            ></el-input>
          </el-form-item>
          <el-form-item label="性別" prop="sex" class="sex-box m-row">
            <el-radio-group v-model="ruleForm.sex" class="m-row">
              <el-radio-button
                v-for="(item, index) in sexBox"
                :key="index"
                :label="index"
                :value="index"
                v-show="index == ruleForm.sex"
                name="sex"
                >{{ item }}</el-radio-button
              >
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <div class="birthday-box column">
            <label class="el-form-item__label" style="width: 100px"
              >生年月日</label
            >
            <div class="row m-row">
              <el-form-item prop="year">
                <el-select
                  v-model="ruleForm.year"
                  :disabled="true"
                  placeholder="请选择年份"
                  class="birthday-year"
                >
                  <el-option label="年" value=""></el-option>
                  <el-option label="1990" value="1990"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="month">
                <el-select
                  v-model="ruleForm.month"
                  :disabled="true"
                  placeholder="请选择月份"
                  class="birthday-month"
                >
                  <el-option label="月" value=""></el-option>
                  <el-option label="1" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="day">
                <el-select
                  v-model="ruleForm.day"
                  :disabled="true"
                  placeholder="请选择日"
                  class="birthday-day"
                >
                  <el-option label="日" value=""></el-option>
                  <el-option label="01" value="01"></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
          <el-form-item label="住所" prop="region">
            <el-select
              v-model="ruleForm.address"
              :disabled="true"
              placeholder="都道府県を選択"
              class="select-region"
            >
              <el-option label="请选择" value=""></el-option>
              <el-option
                v-for="(item, index) in region"
                :key="index"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="メールアドレス" prop="email">
            <el-input
              v-model="ruleForm.email"
              :disabled="true"
              placeholder="メールアドレスを入力"
            ></el-input>
          </el-form-item>
          <el-form-item class="m-kong"> </el-form-item>
        </div>
        <div class="form-item row m-column">
          <el-form-item label="パスワード" class="password-btn-box column">
            <el-button class="change-password-btn" @click="changePass"
              >パスワードを変更</el-button
            >
          </el-form-item>
        </div>
      </div>
      <div class="btns-box row row-center">
        <el-button class="save-btn" @click="doSub('ruleForm')"
          >保存する</el-button
        >
      </div>
    </el-form>
    <el-dialog
      title="パスワードを変更"
      :visible.sync="dialogFormVisible"
      class="change-box"
    >
      <el-form :model="passForm" :rules="passRules" ref="passForm">
        <el-form-item label="元のパスワード" prop="oldpassword">
          <el-input
            v-model="passForm.oldpassword"
            show-password
            autocomplete="off"
            placeholder="元のパスワードを入力"
          ></el-input>
        </el-form-item>
        <el-form-item label="新しいパスワード" prop="password">
          <el-input
            v-model="passForm.password"
            show-password
            autocomplete="off"
            placeholder="新しいパスワードを入力"
          ></el-input>
        </el-form-item>
        <el-form-item label="新しいパスワード" prop="confirmpassword">
          <el-input
            v-model="passForm.confirmpassword"
            show-password
            autocomplete="off"
            placeholder="確認のため新しいパスワードを再入力"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer btns-box row row-center">
        <el-button class="save-btn" @click="doChangePass('passForm')"
          >変更する</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getUserInfo, changPassword, editUserName } from "../../api/api.js";
export default {
  name: "app",
  data() {
    var repasswordValidator = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("確認のためパスワードを再入力"));
      } else if (value !== this.passForm.password) {
        callback(new Error("パスワードが一致していません!"));
      } else {
        callback();
      }
    };
    return {
      region: this.GLOBAL.region,
      sexBox: this.GLOBAL.sexBox,
      dialogFormVisible: false,
      // 表单规则
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
      },
      rules: {
        nickname: [
          { required: true, message: "ニックネームを入力", trigger: "blur" },
        ],
      },
      passForm: {
        oldpassword: "", // 旧密码
        password: "", // 新密码
        confirmpassword: "", // 确认新密码
      },
      passRules: {
        oldpassword: [
          { required: true, message: "元のパスワード", trigger: "blur" },
        ],
        password: [
          { required: true, message: "新しいパスワード", trigger: "blur" },
        ],
        confirmpassword: [
          {
            required: true,
            validator: repasswordValidator,
            message: "確認のためパスワードを再入力",
            trigger: "blur",
          },
        ],
      },
      id: "",
    };
  },
  components: {},
  mounted() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo: function () {
      let that = this,
        token = localStorage.getItem("Authorization");
      getUserInfo(token).then((res) => {
        if (res) {
          that.id = res.result.id;
          that.ruleForm = {
            realname: res.result.realname, // 姓名
            falsename: res.result.falsename, // 读音
            nickname: res.result.nickname, // 昵称
            sex: res.result.sex, // 性别
            year: res.result.year, // 生日年份
            month: res.result.month, // 生日月份
            day: res.result.day, // 生日天
            address: res.result.address, // 住所
            email: res.result.email, // 邮箱
          };
        }
      });
    },
    changePass: function () {
      this.dialogFormVisible = true;
    },
    doChangePass: function (formName) {
      let that = this;
      let data = that.passForm;
      that.$refs[formName].validate((valid) => {
        if (valid) {
          let formData = {
            oldpassword: data.oldpassword,
            password: data.password,
            confirmpassword: data.confirmpassword,
          };
          changPassword(formData).then((res) => {
            console.log(res);
            if (res) {
              that.$message({
                message: "パスワードを再設定しました",
                type: "success",
                onClose: function () {
                  that.dialogFormVisible = false;
                },
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    toMy: function () {
      this.$router.push("/my");
    },
    doSub: function () {
      let that = this;
      let ob = {
        nickname: that.ruleForm.nickname,
        id: that.id,
      };

      editUserName(ob).then((res) => {
        if (res) {
          console.log(111, res);
          that.$message({
            message: "データは正しく保存されました",
            type: "success",
          });
          that.$router.push("/my");
        }
      });
    },
  },
};
</script>

<style lang="css" scoped>
@import "../../assets/css/base.css";
.info-box {
  width: 696px;
  height: 650px;
  margin: 0 auto;
  margin-top: 5%;
  background: #fff;
  box-shadow: 0 10px 20px #63aeb0;
  border-radius: 24px;
  padding: 38px 42px;
  padding-bottom: 0;
}
.info-box .info-header {
  height: 45px;
  line-height: 23px;
}
.info-box .info-header .el-icon-arrow-left {
  line-height: 25px;
  color: #83c3c5;
  font-weight: 900;
}
.info-box .info-header .title {
  font-weight: 700;
  font-size: 20px;
  color: #83c3c5;
}
.info-box .form-box {
  width: 100%;
  margin: 0 auto;
  color: #686868;
}
.info-box .form-item {
  justify-content: space-between;
}
.info-box .el-form-item {
  flex: 1;
  display: -webkit-flex;
  display: flex;
  flex-direction: column;
}
.info-box .el-form-item:nth-child(odd) {
  margin-right: 20px;
}
.info-box .el-form-item__label {
  text-align: left;
  line-height: 20px;
  width: auto !important;
}
.info-box
  .el-form-item.is-required:not(.is-no-asterisk)
  .el-form-item__label-wrap
  > .el-form-item__label:before,
.info-box
  .el-form-item.is-required:not(.is-no-asterisk)
  > .el-form-item__label:before {
  content: "";
}
.el-form-item__content {
  margin-left: 0 !important;
}
.info-box .password-btn-box .el-form-item__label {
  flex: 2;
}
.info-box .password-btn-box .el-form-item__content {
  flex: 3;
}
.info-box .password-btn-box .change-password-btn {
  background: #83c3c5;
  border-radius: 8px;
  color: #fff;
  height: 48px;
}
/*  按钮  */
.info-box .btns-box {
  margin-top: 10px;
}
.info-box .btns-box button {
  width: 196px;
  height: 39px;
  line-height: 9px;
  border-radius: 8px;
  font-size: 14px;
  margin: 7px 0 !important;
}
.info-box .btns-box .save-btn {
  background: #83c3c5;
  border: 1.5px solid #83c3c5;
  color: #fff;
}

@media (max-width: 460px) {
  .info-box {
    width: 90%;
    min-width: 305px;
    height: auto;
    margin-bottom: 3rem;
    padding: 1rem 0.5rem;
  }
  .info-box .form-box {
    width: 100%;
    margin: 0 auto;
    color: #686868;
  }
  .info-box .form-box div {
    width: 100%;
  }
}
</style>
<style>
.info-box .el-form-item__label {
  text-align: left;
  line-height: 20px;
  width: auto !important;
}
.info-box .form-box .el-input__inner {
  height: 43px;
  margin: 8px auto;
  margin-bottom: 3px;
  border: 0;
  background: rgba(189, 189, 189, 0.18);
  border-radius: 8px;
  color: #686868;
}
.info-box .form-box .el-input.is-disabled .el-input__inner {
  background: rgba(77, 77, 77, 0.18);
  border-color: #e4e7ed;
  color: #686868;
  cursor: not-allowed;
}
.info-box .form-box .sex-box .el-radio-group {
  height: 43px;
  margin: 8px auto;
}
.info-box .form-box .sex-box .el-radio-button__inner {
  border-radius: 8px !important;
  background: rgba(189, 189, 189, 0.18);
  margin-left: 30px;
  width: 88px;
  height: 43px;
  line-height: 20px;
  border: 0;
  color: #9c9c9c;
}
.info-box .form-box .sex-box .el-radio-button__inner:hover {
  color: #83c3c5;
}
.info-box
  .form-box
  .sex-box
  .el-radio-button:first-child
  .el-radio-button__inner {
  margin-left: 0;
  border-left: none;
}
.info-box
  .form-box
  .sex-box
  .el-radio-button__orig-radio:checked
  + .el-radio-button__inner {
  color: #686868;
  background: #4d4d4d2e;
  border-color: #4d4d4d2e;
  -webkit-box-shadow: -1px 0 0 0 #4d4d4d2e;
  box-shadow: -1px 0 0 0 #4d4d4d2e;
}
.info-box .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #83c3c5;
  border-color: #83c3c5;
}
.info-box .el-checkbox__input.is-checked .el-checkbox__inner:hover {
  border-color: #83c3c5;
}
.info-box .el-input__suffix {
  height: 43px;
  margin: 8px 0;
  margin-bottom: 3px;
}
.info-box .select-region {
  width: 100%;
}
.info-box .birthday-box .el-form-item {
  margin-right: 0;
}
.info-box .birthday-year {
  width: 113px;
}
.info-box .birthday-month,
.birthday-day {
  width: 82px;
  margin-left: 25px;
}
.info-box .birthday-day {
  margin-right: 30px;
}

/*  弹窗  */
.info-box .el-dialog {
  width: 20rem;
  border-radius: 24px;
}
.info-box .el-dialog .el-dialog__header {
  text-align: center;
}
.info-box .el-dialog .el-dialog__header .el-dialog__title {
  font-weight: 700;
  font-size: 20px;
  line-height: 23px;
  color: #83c3c5 !important;
}
.info-box .el-dialog .el-dialog__body {
  padding: 10px 30px;
}
.info-box .el-dialog .el-dialog__body .el-form-item {
  margin-right: 0;
}
.info-box .el-dialog .el-dialog__body .el-form-item .el-input__suffix {
  margin: 0;
}

@media (max-width: 460px) {
  .info-box {
    width: 90%;
    min-width: 305px;
    height: auto;
    margin-bottom: 3rem;
    padding: 1rem 0.5rem;
  }
  .info-box .form-box {
    width: 100%;
    margin: 0 auto;
    color: #686868;
  }
  .info-box .form-box div {
    width: 100%;
  }
  .info-box .form-item {
    justify-content: space-between;
  }
  .info-box .form-item .el-form-item {
    margin-right: 0;
  }
  .info-box .form-box .el-input--suffix .el-input__inner {
    padding-right: 0;
  }
  .info-box .el-form-item {
    margin-bottom: 10px;
  }
  /*  性别选择  */
  .info-box .form-box .el-input__inner {
    margin: 0.5rem auto;
    margin-bottom: 3px;
  }
  .info-box .form-box .sex-box .el-radio-group {
    height: 43px;
    margin: 0.5rem auto;
  }
  .info-box .form-box .sex-box .el-radio-group .el-radio-button {
    flex: 1;
  }
  .info-box .form-box .sex-box .el-radio-button__inner {
    margin: 0;
  }
  .info-box .birthday-box .el-form-item {
    flex: 1;
  }
  .info-box .birthday-box .el-form-item .el-form-item__content {
    width: 95%;
  }
  .info-box .birthday-box .birthday-year {
  }
  .info-box .birthday-box .birthday-month,
  .birthday-box .birthday-day {
    margin-left: 0;
  }
  .info-box .btns-box .save-btn {
    margin: 1rem auto !important;
  }
  /*  弹窗  */
  .info-box .el-dialog {
    width: 90%;
    border-radius: 1rem;
  }
}
</style>
