<template style="zoom: 1 !important">
  <div class="game-result-warp column">
    <div class="game-result-header">
      <div class="my-btn" @click="toMy()">マイページへ</div>
      <div class="title-box column column-center">
        <div class="title">結果</div>
        <div class="des">プレイ時間：{{ timeStr }}</div>
      </div>
    </div>
    <div class="game-result-info-box column">
      <div class="item row m-column" v-for="(item, index) in users">
        <div class="title">
          <div class="tig">
            {{ item.tag == "房主" ? "ファシリテーター" : "プレイヤー" }}
          </div>
          <div class="name">{{ item.nickname }}</div>
          <!-- <div class="fenxi" @click="fenxi(item)">
						チャート分析をみる
					</div> -->
        </div>
        <div class="cards-lists row-flex row m-column">
          <el-image
            v-for="(item2, index2) in item.cards"
            :key="index2"
            class="card"
            :src="item2.url"
            fit="contain"
          >
          </el-image>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { addGameRecord, getLogDetail } from "../../api/api.js";
export default {
  name: "app",
  data() {
    return {
      isUrl: "https", // 网络请求协议
      roomId: "",
      timeStr: "",
      users: [],
    };
  },
  components: {},
  created() {
    this.roomId = this.$route.query.roomId; // 获取房间信息
    this.userId = localStorage.getItem("homeowner"); // 当前用户ID
    this.userEmail = localStorage.getItem("userEmail"); // 当前用户email
    // 初始化用户信息
    let data = JSON.parse(localStorage.getItem("room_result_" + this.roomId));
    if (data.minute > 0) this.timeStr = data.minute + "分";
    this.timeStr += data.second + "秒";
    this.users = data.users;
    
    this.addRe();
  },
  mounted() {
    window.addEventListener("beforeunload", (e) => this.beforeunloadHandler(e));
  },
  methods: {
    toRoom: function (id) {
      let that = this;
      that.$router.push({
        path: "/room/info",
        params: {
          roomId: id,
        },
      });
    },

    getLogDetail() {
      let that = this;
      let id = that.$route.query.roomId;
	  getLogDetail(id).then(res => {
		console.log(res)
	  })
    },

    addRe() {
      let addResultParamList = localStorage.getItem("addResultParamList");
      let resultInfo = JSON.parse(localStorage.getItem("room_result_" + this.roomId));
      let data = JSON.parse(addResultParamList);
      addGameRecord(data).then((res) => {
        console.log(res);
        if (res.code == 200) {
          console.log("提交成功");
          localStorage.removeItem(addResultParamList);
          localStorage.removeItem("room_result_" + this.roomId);
		  this.getLogDetail();
        }
      });
    },
    toMy: function () {
      this.$router.push("/my");
    },
    fenxi: function (e) {
      let str = "";
      for (let i = 0; i < e.cards.length; i++) {
        if (i == e.userCard.length - 1) {
          str += e.userCard[i];
        } else {
          str += e.userCard[i] + ",";
        }
      }
      console.log(str);
      this.$router.push({
        path: "/game/analysis",
        query: {
          id: str,
        },
      });
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/base.css";

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
  background-color: #ffffff;
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
  cursor: pointer;
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
  border: 2px solid #fff;
  box-sizing: border-box;
  border-radius: 6px;
  font-weight: 700;
  font-size: 14px;
  line-height: 23px;
  color: #fff;
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
  background: #fff;
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
  color: #c8c8c8;
  width: 130px;
  overflow: hidden;
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
  margin: 7px 0 !important;
}

.game-result-warp .btns-box .details-btn {
  background: #63aeb0;
  border: 1.5px solid #63aeb0;
  color: #fff;
}

@media (max-width: 460px) {
  .game-result-warp {
    width: 90%;
    min-width: 305px;
    height: auto;
    margin-bottom: 3rem;
    padding: 1rem 0.5rem;
  }

  .game-result-warp .game-result-info-box {
    font-size: 1.2rem;
  }

  .game-result-warp .game-result-info-box .item {
    font-size: 0.8rem;
  }
}
</style>
