<template>
  <div class="main">
    <div class="home-btn" @click="goPage('/')" style="cursor: pointer">マイページへ</div>
    <div class="pay-main-warp column">
      <div class="title-box">
        <div class="title">料金プランを確認</div>
      </div>
      <div class="lists-box row m-column">
        <div class="lists-item column">
          <div class="title" v-text="info.name"></div>
          <div class="price-box row">
            <div class="price" v-text="info.price"></div>
          </div>
          <div class="des" v-text="info.describe">
            お試しプランでまずチームメンバーとゲームしよう！
          </div>
          <div class="item-list" v-for="(item, index) in info.jurisdiction">
            <i class="el-icon-success"></i>{{ item }}
          </div>

          <div class="btns-box row row-center">
            <div class="join-btn" style="font-weight: 700 !important;text-align: center;box-sizing: border-box;padding: 8px 0;"
              >利用中</div>
          </div>
        </div>
        <!-- 右侧按钮 -->
        <div class="edits m-column">
          <div class="over-Time">契約期間：{{ startTime }} - {{ endTime }}</div>
          <el-button class="editbtn" @click="goPage('/pay/index')"
            >別のプランを確認</el-button
          >
          <el-button v-if="plan != '1on1プラン' && plan != '基本プラン'" class="editbtn" @click="goPage('/pay/seturl?price=' + info.priceCount + '&name=' + info.name)">固定URLの設定</el-button>
          <div class="release" @click="showMask">解約する</div>
        </div>
      </div>
    </div>
    <el-dialog
      style="border-radius: 15px"
      title="基本プラン"
      :visible.sync="centerDialogVisible"
      custom-class="confirmContent"
      top="30vh"
      width="378px"
      center
    >
      <span>お試しプランでまずチームメンバーとゲームしよう！</span>
      <div class="colbtns">
        <el-button class="join-btn" style="font-weight: 700 !important"
          >1ヶ月プラン</el-button
        >
        <el-button class="join-btn" style="font-weight: 700 !important"
          >1年プラン</el-button
        >
      </div>
    </el-dialog>

    <el-dialog
      style="border-radius: 15px"
      title="解約しますか"
      :visible.sync="dialogSettingVisible"
      custom-class="confirmContent"
      top="30vh"
      width="378px"
      center
    >
      <span>お試しプランでまずチームメンバーとゲームしよう！</span>
      <div class="colbtns">
        <el-button
          class="join-btn"
          style="font-weight: 700 !important"
          @click="rescind()"
          >解約する</el-button
        >
        <el-button
          class="join-btn"
          style="font-weight: 700 !important"
          @click="toClose()"
          >キャンセル</el-button
        >
      </div>
    </el-dialog>

    <!-- <el-dialog title="退会しますか" top="250px" :visible.sync="dialogSettingVisible" class="setting-box" center>
		<div slot="footer" class="dialog-footer btns-box column row-center">
			<el-button class="finish-btn" @click="rescind()">退会する</el-button>
			<el-button class="skip-btn" @click="toClose()">キャンセル</el-button>
		</div>
	</el-dialog> -->
  </div>
</template>

<script>
import { getUserInfo, checkSubscribe, canceld } from "../../api/api.js";
export default {
  name: "app",
  data() {
    return {
      links: [],
      centerDialogVisible: false,
      info: {},
      startTime: "1980-01-01",
      endTime: "1980-01-01",
      customerId: "",
      dialogSettingVisible: false,
      plan:localStorage.getItem("plan"),
    };
  },
  created() {},
  components: {},
  mounted() {
    this.getUserInfo();
  },
  methods: {
    goPage: function (page) {
      this.$router.push(page);
    },
    showMask() {
      let that = this;
      that.dialogSettingVisible = true;
    },
    toClose() {
      let that = this;
      that.dialogSettingVisible = false;
    },
    tobuy() {
      let that = this;
      that.centerDialogVisible = true;
    },
    goPage: function (page) {
      this.$router.push(page);
    },
    rescind() {
      let that = this;
      let data = {
        customerId: that.customerId,
      };
      canceld(data).then((res) => {
        console.log(res);
        if (res.code == 200) {
          // that.$message({
          //   message: '操作を禁止しています',
          //   type: 'warn'
          // })
          that.goPage("/");
        }
      });
    },
    check(id) {
      let that = this;
      let data = {};
      data.customerId = id;
      checkSubscribe(id).then((res) => {
        console.log(111, res);
        if (res.code == 200) {
          that.info = res.result.productListVO;
          that.startTime = res.result.startTime;
          that.endTime = res.result.endTime;
          if(res){
              if (res.code == 200) {
              if (res.result) {
                that.su = 1
                localStorage.setItem("plan",res.result.productListVO.name)
                that.plan = res.result.productListVO.name
              } else {
                that.su = 0
                localStorage.setItem("plan","1on1プラン")
                that.plan = "1on1プラン"
              }
            }
          }
        }
      });
    },

    getUserInfo: function () {
      let that = this,
        token = localStorage.getItem("Authorization");
      getUserInfo(token).then((res) => {
        if (res) {
          localStorage.setItem("customerId", res.result.customerId);
          that.realname = res.result.realname; // 姓名
          that.nickname = res.result.nickname; // 昵称
          that.customerId = res.result.customerId;
          that.check(res.result.customerId);
        }
      });
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/base.css";

.pay-main-warp {
  width: 60%;
  height: 692px;
  margin: 0 auto;
  margin-top: 3%;
  padding: 38px 42px;
  padding-bottom: 0;
}

.edits {
  width: 370px;
}

.release {
  color: #fff;
  opacity: 0.64;
  text-decoration: underline;
  cursor: pointer;
  text-align: center;
  font-size: 12px;
}

.colbtns {
  margin-top: 40px;
}

.confirmContent {
  border-radius: 20px;
}

.home-btn {
  position: absolute;
  top: 6rem;
  left: 24rem;
  border: 1.5px solid #fff;
  box-sizing: border-box;
  border-radius: 6px;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  color: #fff;
  padding: 5px 15px;
}

.pay-main-warp .title-box {
  text-align: center;
  margin-bottom: 72px;
}

.editbtn {
  width: 370px;
  height: 70px;
  background: #fff;
  box-shadow: 0px 10px 20px #63aeb0;
  border-radius: 12px;
  display: block;
  color: #1e7578;
  margin: 0 0 22px !important;
}

.editbtn:hover {
  background: #1e7578;
  color: #fff;
}

.pay-main-warp .title-box .title {
  font-weight: 700;
  font-size: 30px;
  line-height: 35px;
  color: #fff;
}

.pay-main-warp .lists-box {
  justify-content: center;
  align-items: center;
}

.pay-main-warp .lists-box .lists-item {
  justify-content: flex-start;
  align-items: flex-start;
  width: 243px;
  position: relative;
  height: 433px;
  box-shadow: 0px 10px 20px #63aeb0;
  border: none;
  border-radius: 12px;
  background: #fff;
  margin-right: 87px;
  padding: 20px;
}

.pay-main-warp .lists-box .lists-item {
  border: 4px solid #1e7578;
  cursor: pointer;
}

.pay-main-warp .lists-box .lists-item:last-child {
  margin: 0;
}

.pay-main-warp .lists-box .lists-item div.title {
  font-weight: 500;
  font-size: 12px;
  line-height: 20px;
  color: #686868;
}

.pay-main-warp .lists-box .lists-item div.price-box {
}

.pay-main-warp .lists-box .lists-item div.price-box .price {
  font-weight: 700;
  font-size: 22px;
  line-height: 32px;
  color: #6d6d6d;
  margin-right: 5px;
}

.pay-main-warp .lists-box .lists-item div.price-box .price-unit {
  font-weight: 500;
  font-size: 12px;
  line-height: 38px;
  color: #bebebe;
}

.pay-main-warp .lists-box .lists-item div.des {
  font-size: 12px;
  line-height: 17px;
  color: #9f9b9b;
  padding: 10px 0;
}

.pay-main-warp .lists-box .lists-item div.item-list {
  font-weight: 500;
  font-size: 12px;
  line-height: 27px;
  color: #686868;
}

.pay-main-warp .lists-box .lists-item .btns-box {
  width: calc(100% - 40px);
  position: absolute;
  bottom: 23px;
}

.pay-main-warp .lists-box .lists-item .btns-box .join-btn {
  border: 1.5px solid #63aeb0;
  box-sizing: border-box;
  border-radius: 4px;
  color: #63aeb0;
  font-weight: 700 !important;
  font-size: 14px;
  width: 100%;
}

.colbtns .join-btn {
  border: 1.5px solid #63aeb0;
  box-sizing: border-box;
  border-radius: 4px;
  color: #63aeb0;
  font-weight: 700 !important;
  font-size: 14px;
  width: 100%;
  margin: 0 0 12px !important;
}

.colbtns .join-btn:last-child {
  border: 1.5px solid #63aeb0;
  box-sizing: border-box;
  border-radius: 4px;
  color: #fff;
  font-weight: 700 !important;
  font-size: 14px;
  width: 100%;
  background: #63aeb0;
  margin: 0 0 12px !important;
}

.pay-main-warp .lists-box .lists-item .btns-box .join-btn span {
  font-weight: bolder !important;
  font-size: 14px;
}

.pay-main-warp .lists-box .checked {
  background: #1e7578;
  box-shadow: 0px 10px 20px #63aeb0;
}

.pay-main-warp .lists-box .checked div {
  color: #fff !important;
}

.pay-main-warp .lists-box .checked .btns-box .join-btn span {
  color: #1e7578;
}

/*  弹窗  */
.setting-box .el-dialog {
  width: 30% !important;
  border-radius: 12px;
}

.setting-box .el-dialog .el-dialog__header {
  text-align: center;
}

.setting-box .el-dialog .el-dialog__header .el-dialog__title {
  font-weight: 700;
  font-size: 18px;
  line-height: 21px;
  color: #686868 !important;
}

.setting-box .el-dialog .el-dialog__body {
  display: none;
}

.setting-box .el-dialog .el-dialog__footer {
  padding: 0 20px 20px;
}

.setting-box .el-dialog .el-dialog__footer .finish-btn {
  width: 100%;
  height: 46px;
  border: 2px solid #63aeb0;
  box-sizing: border-box;
  border-radius: 5px;
  margin-bottom: 10px;
}

.setting-box .el-dialog .el-dialog__footer .finish-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #63aeb0;
}

.setting-box .el-dialog .el-dialog__footer .skip-btn {
  width: 100%;
  height: 46px;
  background: #63aeb0;
  margin-left: 0;
}

.setting-box .el-dialog .el-dialog__footer .skip-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #fff;
}

.skip-box .el-dialog {
  width: 42rem;
  border-radius: 12px;
}

.skip-box .el-dialog .el-dialog__header {
  text-align: center;
}

.skip-box .el-dialog .el-dialog__header .el-dialog__title {
  font-weight: 700;
  font-size: 18px;
  line-height: 21px;
  color: #686868 !important;
}

.skip-box .el-dialog .el-dialog__body {
}

.skip-box .el-dialog .el-dialog__body .personnel-box {
  flex-wrap: wrap;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card {
  width: 20%;
  height: 79px;
  border: 2px solid #eaeaea;
  box-sizing: border-box;
  border-radius: 8px;
  position: relative;
  margin: 10px 15px;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card.checked {
  border: 2px solid #63aeb0;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card.checked .name {
  font-weight: 700;
  color: #63aeb0;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card .text-box {
  text-align: center;
  font-weight: 500;
  font-size: 14px;
  line-height: 20px;
  color: #bebebe;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card .tag {
  font-size: 12px;
  line-height: 17px;
  color: #cacaca;
}

.skip-box .el-dialog .el-dialog__body .personnel-box .card .el-badge {
  position: absolute;
  top: 0;
  right: 8px;
}

.skip-box
  .el-dialog
  .el-dialog__body
  .personnel-box
  .card
  .el-badge__content--primary {
  background-color: #63aeb0;
}

.skip-box .el-dialog .el-dialog__footer {
  padding: 0 20px 20px;
}

.skip-box .el-dialog .el-dialog__footer .save-btn {
  width: 60%;
  height: 46px;
  background: #63aeb0;
  margin: 0 auto;
  margin-left: 0;
}

.skip-box .el-dialog .el-dialog__footer .save-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #fff;
}

@media (max-width: 460px) {
  .pay-main-warp {
    width: 90%;
    padding: 5%;
    height: auto;
    margin-bottom: 3rem;
    margin-top: 3rem;
  }

  .home-btn {
    position: absolute;
    top: 0.5rem;
    left: 1rem;
  }

  .pay-main-warp .lists-box .lists-item {
    margin: 0;
    margin-bottom: 1.5rem;
    flex: none;
  }
}
.over-Time {
  color: #fff;
  font-weight: 700;
  font-size: 17px;
  margin-bottom: 20px;
}
</style>
