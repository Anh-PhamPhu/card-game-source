<template style="zoom: 0.85 !important;">
  <div class="game-warp" v-loading="loading">
    <div class="game-box row">
      <!--  左侧用户  -->
      <div style="width: 15%; height: 50rem"></div>
      <!--  中间  -->
      <div class="game-box-min column column-center row-flex">
        <!--  中间用户  -->
        <div
          style="position: relative; left: -16%; width: 100%"
          class="user-box row row-center"
          :style="user2.sort == playUser ? 'border:4px solid #FF8660' : ''"
          :data-id="user2.id"
          :data-email="user2.email"
          :data-username="user2.username"
          :data-realname="user2.realname"
          :data-lineStatus="user2.lineStatus"
          :data-isSkip="user2.isSkip"
          :data-sort="user2.sort"
        >
          <div
            class="username usernamesc"
            style="
              word-wrap: break-word;
              writing-mode: vertical-tb;
              overflow: hidden;
              font-size: 14px
            "
          >
            {{ user2.nickname }}

          </div>
          <!--  -->
          <div class="user-cards-box row row-center column-center">
            <el-image
              v-if="info.isPublic || mode == 'watch'"
              v-for="(item, index) in user2.cards"
              :key="index"
              class="card2 cardh"
              :class="item.checked == true ? 'active' : ''"
              :src="item.url"
              :data-id="item.id"
              fit="contain"
              @click="
                changeOtherCard(item, index, $event, 2, user2.cards, 'user2')
              "
            >
            </el-image>
            <el-image
              v-if="!info.isPublic && mode != 'watch'"
              v-for="(item, index) in user2.cards"
              :key="index"
              class="card2"
              :src="cardsBack"
              :data-id="item.id"
              fit="contain"
            >
            </el-image>

            <!-- <div class="card-box" v-if="!info.isPublic && mode != 'watch'" v-for="(item, index) in user2.cards" :data-id="item.id" style="border: none;background:#fff">
							<div class="card-box2 column column-center row-center" style="border: none;background:#fff">
								<el-image class="card" :src="cardsBack" fit="contain">
								</el-image>
							</div>
						</div> -->
          </div>
          <div class="mask" v-show="user2.isSkip">
            <div
              class="release-skip"
              v-if="isHomewer"
              @click="cancelSkip(user2.username, user2.id, 2)"
            >
              スキップ解除
            </div>
            スキップ状態
          </div>
        </div>
        <!--  中间游戏区  -->
        <div class="game-cards-box row-flex row">
          <!--  左侧弃牌区  -->
          <div class="left column column-center row-center">
            <div class="cards-lists" style="display: none">
              <div
                class="cards-scroll"
                ref="location4"
                @mousedown="dragscroll($event)"
              >
                <el-image
                  v-for="(item, index) in user4.discard"
                  :key="index"
                  class="card"
                  :class="item.checked == true ? 'active' : ''"
                  @click.stop="
                    extractCardFromDiscards(
                      item,
                      index,
                      4,
                      $event,
                      user4.discard,
                      'user4'
                    )
                  "
                  :src="item.url"
                  fit="contain"
                >
                </el-image>
              </div>
            </div>
          </div>
          <div class="min column">
            <!--  中间顶部弃牌区  -->
            <div class="min-top column column-center row-center">
              <div class="cards-lists">
                <div
                  class="cards-scroll row column-center"
                  ref="location2"
                  @mousedown="dragscroll($event)"
                >
                  <el-image
                    v-for="(item, index) in user2.discard"
                    :key="index"
                    class="card cardm"
                    :src="item.url"
                    :class="item.checked == true ? 'active' : ''"
                    @click.stop="
                      extractCardFromDiscards(
                        item,
                        index,
                        2,
                        $event,
                        user2.discard,
                        'user2'
                      )
                    "
                    fit="contain"
                  >
                  </el-image>
                </div>
              </div>
            </div>
            <!--  中间卡牌堆  -->
            <div
              class="min-cards-box row-flex"
              style="position: relative; left: 15%"
            >
              <div class="rest">残り枚数: {{ cards.length }}枚</div>
              <div class="cards-lists" ref="gamecardslists">
                <div
                  class="card-box"
                  v-for="(item, index) in cards"
                  :key="index"
                  :class="'card' + item.id"
                  :style="item.style"
                >
                  <div class="card-box2 column column-center row-center">
                    <el-image
                      class="card"
                      :src="cardsBack"
                      @click="extractCard(item, index)"
                      fit="contain"
                    >
                    </el-image>
                  </div>
                </div>
              </div>
              <div class="card-top" v-if="extracting">
                <el-image
                  class="card cardm"
                  style="z-index: 1002 !important"
                  :id="extractCardInfo.id"
                  :src="extractCardInfo.url"
                  fit="contain"
                >
                </el-image>
              </div>
            </div>
            <!--  中间底部弃牌区  -->
            <div class="min-bottom column column-center row-center">
              <div class="cards-lists" @click="discardCard(1, $event)">
                <div
                  class="cards-scroll row column-center"
                  ref="location1"
                  @mousedown="dragscroll($event)"
                  :style="{ width: scrollWidth }"
                >
                  <el-image
                    v-for="(item, index) in user1.discard"
                    :key="index"
                    class="card cardm"
                    :src="item.url"
                    :class="item.checked == true ? 'active' : ''"
                    @click.stop="
                      extractCardFromDiscards(
                        item,
                        index,
                        1,
                        $event,
                        user1.discard,
                        'user1'
                      )
                    "
                    fit="contain"
                  >
                  </el-image>
                </div>
              </div>
            </div>
          </div>
          <!--  右侧弃牌区  -->
          <div
            style="visibility: none"
            class="right column column-center row-center"
          >
            <div class="cards-lists" style="display: none">
              <div
                class="cards-scroll"
                ref="location2"
                @mousedown="dragscroll($event)"
              >
                <el-image
                  v-for="(item, index) in user3.discard"
                  :key="index"
                  class="card"
                  :class="item.checked == true ? 'active' : ''"
                  @click.stop="
                    extractCardFromDiscards(
                      item,
                      index,
                      2,
                      $event,
                      user2.discard,
                      'user2'
                    )
                  "
                  :src="item.url"
                  fit="contain"
                >
                </el-image>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--  右侧用户  -->
      <div
        style="display: none"
        class="game-box-right column"
        :style="user3.sort == playUser ? 'border:3px solid #FF8660' : ''"
        :data-id="user3.id"
        :data-email="user3.email"
        :data-username="user3.username"
        :data-realname="user3.realname"
        :data-lineStatus="user3.lineStatus"
        :data-isSkip="user3.isSkip"
        :data-sort="user3.sort"
      >
        <div class="username">{{ user3.nickname }}</div>
        <div class="user-cards-box column column-center">
          <el-image
            v-if="info.isPublic || mode == 'watch'"
            v-for="(item, index) in user3.cards"
            :key="index"
            class="card2"
            :src="item.url"
            :data-id="item.id"
            fit="contain"
          >
          </el-image>
          <div
            class="card-box"
            v-if="!info.isPublic && mode != 'watch'"
            v-for="(item, index) in user3.cards"
            :data-id="item.id"
          >
            <div class="card-box2 column column-center row-center">
              <el-image class="card" :src="cardsBack" fit="contain"> </el-image>
            </div>
          </div>
        </div>
        <div class="mask" v-show="user2.isSkip">
          <div
            class="release-skip"
            v-if="isHomewer"
            @click="cancelSkip(user3.username, user3.id, 3)"
          >
            スキップ解除
          </div>
          スキップ状態
        </div>
      </div>
    </div>
    <!--  用户  -->
    <div class="game-user-self row">
      <div
        class="user-box"
        :data-id="user1.id"
        :data-email="user1.email"
        :data-username="user1.username"
        :data-realname="user1.realname"
        :data-lineStatus="user1.lineStatus"
        :data-isSkip="user1.isSkip"
        :data-sort="user1.sort"
      >
        <div class="myself">{{ user1.nickname }}</div>
        <!-- <div class="myself">就是这样的八个字</div> -->
        <div
          class="user-cards-box row row-center column-center"
          :style="user1.sort == playUser ? 'border:4px solid #FF8660' : ''"
        >
          <el-image
            v-for="(item, index) in user1.cards"
            :key="index"
            class="card cardh"
            :class="{ cardchecked: mode == 'play' }"
            :src="item.url"
            :data-id="item.id"
            @click="changeSelfCard(item, index, $event, 1)"
            fit="contain"
          >
          </el-image>
          <div class="mask" v-show="user1.isSkip">
            <div
              class="release-skip"
              v-if="isHomewer"
              @click="cancelSkip(user1.username, user1.id, 1)"
            >
              スキップ解除
            </div>
            スキップ状態
          </div>
        </div>
      </div>
      <!--  房间信息  -->
      <div class="room-info row-flex column column-center">
        <div class="btns-box row" v-if="mode == 'play' || isHomewer">
          <div
            class="item row-flex column column-center row-center"
            v-if="isHomewer"
            @click="doTimeOut()"
          >
            <img src="../../assets/images/time-out.png" />
          </div>

          <!-- <div
            class="item row-flex column column-center row-center"
            v-if="isHomewer && !play"
            @click="doPlay()"
          >
            <img src="../../assets/images/time-out.png" />
          </div> -->
          <div
            class="item row-flex column column-center row-center"
            v-show="mode != 'watch'"
            @click="doSelfSkip()"
          >
            <span style="font-size: 14px; color: #63aeb0; font-weight: bold"
              >Pass</span
            >
          </div>
          <div
            class="item row-flex column column-center row-center"
            v-show="isHomewer"
            @click="doSet()"
          >
            <img src="../../assets/images/setting.png" />
          </div>
        </div>
        <div class="item countdown" v-if="mode == 'watch'">
          <div class="counts">観戦者</div>
          <div class="txt">身份</div>
        </div>
        <div class="item countdown">
          <div class="counts">{{ userTimes }}<span>s</span></div>
          <div class="txt">時間</div>
        </div>
        <div class="item times">
          <div class="counts">{{ gameTimes.str }}</div>
          <div class="txt">トータル時間</div>
        </div>
        <div class="item peopleNumber">
           <div class="counts">{{ (totalPerson * 1 - gemePeople * 1) < 0 ? 0 : totalPerson * 1 - gemePeople * 1}}人</div>
          <div class="txt">観戦者数</div>
        </div>
        <div
          class="item countdown"
          @click.stop="goPage('/')"
          v-if="mode == 'watch' && isHomewer"
        >
          <div class="counts">マイページへ</div>
        </div>
      </div>
    </div>
    <!--  弹窗  -->
    <el-dialog
      title="設定"
      top="250px"
      :visible.sync="dialogSettingVisible"
      class="setting-box"
      center
    >
      <div slot="footer" class="dialog-footer btns-box column row-center">
        <el-button class="finish-btn" @click="toFinish()">終了</el-button>
        <el-button class="skip-btn" @click="toSkip()">スキップ</el-button>
      </div>
    </el-dialog>
    <!-- 暂停弹窗 -->
    <el-dialog
      :close-on-click-modal="false"
      :show-close="false"
      title="中断中"
      top="250px"
      :visible.sync="dialogPauseVisible && cards.length > 0"
      class="setting-box"
      center
    >
      <div slot="footer" class="dialog-footer btns-box column row-center">
        <el-button v-if="isHomewer" class="skip-btn" @click.stop="reStart()"
          >ゲームを再開する</el-button
        >
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      title="ゲームを終了しますか？"
      top="250px"
      :visible.sync="dialogFinishVisible"
      class="setting-box"
      center
    >
      <div slot="footer" class="dialog-footer btns-box column row-center">
        <el-button class="finish-btn" @click="doFinish()">終了する</el-button>
        <el-button class="skip-btn" @click="toGame()">続ける</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="スキップさせるユーザーを選択"
      top="250px"
      :visible.sync="dialogSkipVisible"
      class="skip-box"
      center
    >
      <div class="personnel-box row">
        <div
          class="card column column-center row-center"
          v-for="(item, index) in users2"
          v-if="!item.isHomewer"
          @click="checkUser(item, index, item.checked)"
          :class="{ checked: item.checked }"
          :key="index"
        >
          <div class="text-box">
            <div class="name">{{ item.nickname }}</div>
            <div class="tag">{{ item.tag }}</div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer btns-box">
        <el-button class="save-btn" @click="doSkip()">スキップ</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createSocket, sendWSPush, WSClose } from "../../utils/websocket.js";
import {
  getUserInfo,
  getUserInfoById,
  getRoomDetail,
  gamesOperate,
  noticeUserOperate,
  getSame,
  addGameRecord,
} from "../../api/api.js";
import { mapMutations } from "vuex";
export default {
  data() {
    return {
      isUrl: "https", // 网络请求协议
      loading: false, // 加载
      tags: this.GLOBAL.tags, // 顺序标签
      mode: "play", // 游戏模式：play 参赛模式 watch 观战模式
      imgUrl: "://online.engagement-card.com/img/", // 图片地址
      cardsBack: require("@/assets/images/card-back.png"), // 卡片背面
      // 横向弃牌堆宽度
      scrollWidth: "900px",
      UID: "",
      // 用户信息
      userId: "1505124724596801538", // 用户 ID
      userEmail: "2856678408@qq.com", // 用户 email
      userName: "", // 用户名
      allCards: [], // 全部卡牌
      cards: [], // 卡片堆
      cardsNum: 89, // 卡片总数
      isfinish: 0,
      // 初始分配用户
      users: [
        /* { email: "2856678408@qq.com", id:"1505124724596801538", lineStatus:"online", realname:"戸田勇大1", username:"2856678408@qq.com", sort: 1, isSkip: false },
							{ email: "2856678409@qq.com", id:"1505124724596801539", lineStatus:"online", realname:"戸田勇大2", username:"2856678409@qq.com", sort: 2, isSkip: false },
							{ email: "2856678410@qq.com", id:"1505124724596801540", lineStatus:"online", realname:"戸田勇大3", username:"2856678410@qq.com", sort: 3, isSkip: false },
							{ email: "2856678411@qq.com", id:"1505124724596801541", lineStatus:"online", realname:"戸田勇大4", username:"2856678411@qq.com", sort: 4, isSkip: false } */
      ],
      // 保留用户记录
      users2: [
        /* { email: "2856678408@qq.com", id:"1505124724596801538", lineStatus:"online", realname:"戸田勇大1", username:"2856678408@qq.com", sort: 1, tag: '房主',   isHomewer: true,  isSkip: false, checked: false },
							{ email: "2856678409@qq.com", id:"1505124724596801539", lineStatus:"online", realname:"戸田勇大2", username:"2856678409@qq.com", sort: 2, tag: '二番手', isHomewer: false, isSkip: false, checked: false },
							{ email: "2856678410@qq.com", id:"1505124724596801540", lineStatus:"online", realname:"戸田勇大3", username:"2856678410@qq.com", sort: 3, tag: '三番手', isHomewer: false, isSkip: false, checked: false },
							{ email: "2856678411@qq.com", id:"1505124724596801541", lineStatus:"online", realname:"戸田勇大4", username:"2856678411@qq.com", sort: 4, tag: '四番手', isHomewer: false, isSkip: false, checked: false } */
      ],
      // 房间信息
      roomId: "", // 房间ID
      play: true, // 正在进行中
      isHomewer: false, // 是否是房主
      // 房间信息
      info: {
        homeowner: "",
        roomName: "",
        homeownerName: "",
        startTime: "",
        theme: "",
        isCopy: true,
        isPublic: false,
        peopleNumber: 4,
        url: "",
      },

      // 用户信息
      userInfo: {},

      // 游戏数据
      user1: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      user2: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      user3: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      user4: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      user5: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      user6: {
        email: "",
        id: "",
        username: "",
        realname: "",
        lineStatus: "",
        sort: 0,
        location: 0,
        isSkip: false,
        cards: [],
        discard: [],
      },
      // user6: { email: "", id:"", realname:"", lineStatus:"", sort: 0, location: 0, isSkip: false, cards: [], discard: [] },

      // 用户手牌
      userCards: [],
      // 抽牌
      extracting: false, // 是否抽取中
      extractCardInfo: {}, // 抽取的牌
      // 弃牌
      discardCardInfo: {},
      // 复制卡牌的信息
      copiedcard: {},
      // 当前操作用户
      playUser: 1,
      gemePeople: 0,
      // 弹窗状态
      dialogSettingVisible: false, // 设置弹窗
      dialogFinishVisible: false, // 结束弹窗
      dialogSkipVisible: false, // 跳过弹窗
      dialogPauseVisible: false, //暂停弹窗
      // 计时部分
      userTimes: 0,
      totalPerson: 0,
      countsNumber: 0,
      gameTimes: {
        str: "00:00",
        hour: 0,
        minute: 0,
        second: 0,
        ms: 0,
      },
      watchCount: 0, // 观战人数
    };
  },
  watch: {
    user1: {
      // 立即监听
      immediate: true,
      // 深度监听
      deep: true,
      handler(val) {
        // console.log('监听', val);
        if (val.hasOwnProperty("discard")) {
          let el = this.$refs.location1,
            discardCount = val.discard.length,
            clientWidth = 0;
          if (discardCount > 7) {
            clientWidth = (el.children[0].clientWidth + 25) * discardCount;
            el.style["width"] = clientWidth + "px";
            // console.log(el.children[0].clientWidth * discardCount)
          }
        }
      },
    },
    user3: {
      // 立即监听
      immediate: true,
      // 深度监听
      deep: true,
      handler(val) {
        // console.log('监听', val);
        if (val.hasOwnProperty("discard")) {
          let el = this.$refs.location3,
            discardCount = val.discard.length,
            clientWidth = 0;
          if (discardCount > 7) {
            clientWidth = (el.children[0].clientWidth + 25) * discardCount;
            el.style["width"] = clientWidth + "px";
            // console.log(el.children[0].clientWidth * discardCount)
          }
        }
      },
    },
    users2: {
      // 立即监听
      immediate: true,
      // 深度监听
      deep: true,
      handler(val) {
        // console.log('监听', val)
      },
    },
    cards: {
      // 立即监听
      immediate: true,
      // 深度监听
      deep: true,
      handler(val) {
        console.log("监听卡牌", val);
      },
    },
    /* cards: function(){//materielId为需要监听的data
					this.$nextTick(function(){
						console.log(222)
					});
				}, */
  },
  components: {},
  created() {
    this.isUrl = this.GLOBAL.getHttps();
    this.updateFooter(false); // 更新底部状态
    this.roomId = this.$route.query.roomId; // 获取房间信息
    this.userId = localStorage.getItem("homeowner"); // 当前用户ID
    this.userEmail = localStorage.getItem("userEmail"); // 当前用户email
    this.getRoomDetail(); // 获取房间信息
    // this.getSame()
  },
  mounted() {
    this.users = JSON.parse(localStorage.getItem("room_" + this.roomId));
    // this.giveLocation() // 分配位置
    // this.shuffle()   // 洗牌
    this.getUserInfo();
    this.goTimes(); // 计时
    this.countDown(); // 倒计时
    window.addEventListener("onmessageWS", this.getsocketData);
  },
  methods: {
    ...mapMutations(["updateFooter"]),
    // 跳转页面
    goPage: function (page) {
      this.$router.push(page);
    },
    // 初始化游戏者
    // 链接ws
    createSocket: function (code, userEmail) {
      let that = this;
      let wsUrl = "";
      let ishttps = "https:" == document.location.protocol ? true : false;
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
      createSocket(wsUrl);
    },
    csh() {
      let that = this;
      that.discardCardInfo = {};
      that.copiedcard = {};
      for (let i = 0; i < that.user1.cards.length; i++) {
        that.user1.cards[i].checked = false;
      }
      for (let i = 0; i < that.user1.discard.length; i++) {
        that.user1.discard[i].checked = false;
      }
      for (let i = 0; i < that.user2.cards.length; i++) {
        that.user2.cards[i].checked = false;
      }
      for (let i = 0; i < that.user2.discard.length; i++) {
        that.user2.discard[i].checked = false;
      }
      for (let i = 0; i < that.user3.cards.length; i++) {
        that.user3.cards[i].checked = false;
      }
      for (let i = 0; i < that.user3.discard.length; i++) {
        that.user3.discard[i].checked = false;
      }
      for (let i = 0; i < that.user4.cards.length; i++) {
        that.user4.cards[i].checked = false;
      }
      for (let i = 0; i < that.user4.discard.length; i++) {
        that.user4.discard[i].checked = false;
      }
      for (let i = 0; i < that.user5.cards.length; i++) {
        that.user5.cards[i].checked = false;
      }
      for (let i = 0; i < that.user5.discard.length; i++) {
        that.user5.discard[i].checked = false;
      }
      for (let i = 0; i < that.user6.cards.length; i++) {
        that.user6.cards[i].checked = false;
      }
      for (let i = 0; i < that.user6.discard.length; i++) {
        that.user6.discard[i].checked = false;
      }
    },
    getSame() {
      let that = this;
      getSame(that.roomId).then((res) => {
        console.log("same", res);
        if (res.result) {
          if (res.result.customize) {
            let samedata = JSON.parse(res.result.customize);
            console.log("re", res.result.customize);
            let re = samedata[0];

            setTimeout(function () {
              for (let i = 0; i < re.temp.length; i++) {
                if (that.user1.sort == re.temp[i].sort) {
                  that.user1 = re.temp[i];
                }
                if (that.user2.sort == re.temp[i].sort) {
                  that.user2 = re.temp[i];
                }
                if (that.user3.sort == re.temp[i].sort) {
                  that.user3 = re.temp[i];
                }
                if (that.user4.sort == re.temp[i].sort) {
                  that.user4 = re.temp[i];
                }
              }
              that.cards = re.pk;
              that.userTimes = re.countdown;
              that.gameTimes = re.gameTime;
              that.playUser = re.usersort;
              that.UID = re.UID;
              // that.users2 = re.users2
            }, 1000);
          }
        }
      });
    },
    getsocketData: function (e) {
      let that = this;
      if (e.detail.data == "发生连接错误") return false;
      let data = JSON.parse(e.detail.data),
        newUser = {},
        isNew = true;
      if (data.onlineNumber > 0) {
        that.totalPerson = data.onlineNumber;
        that.getSame();
      }
      if (data.operateMsg == "从卡堆抽牌") {
        that.cards.forEach((item, index) => {
          if (item.id == data.number) {
            that.extractCardInfo = {
              index: index,
              id: data.number,
              url: item.url,
            };
            that.extracting = true;
            return true;
          }
        });
      }
      if (data.operateMsg == "从弃卡堆里抽牌") {
        let newCard = {},
          location = 0;
        that.allCards.forEach((card, key) => {
          if (card.id == data.number) {
            newCard = card;
          }
        });
        // console.log(newCard)
        // 弃牌信息
        that.user1.discard.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 1;
            newCard.index = index;
          }
        });
        that.user2.discard.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 2;
            newCard.index = index;
          }
        });
        that.user3.discard.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 3;
            newCard.index = index;
          }
        });
        that.user4.discard.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 4;
            newCard.index = index;
          }
        });
        // console.log('WS选中弃牌信息:', newCard)
        that.discardCardInfo = newCard;
      }
      if (data.operateMsg == "替换手牌") {
        console.log("收到替换手牌的推送");
        that.users2.forEach((item, index) => {
          // console.log(item)
          if (data.userId == item.id) {
            let location = item.location,
              userCards = [];
            switch (location) {
              case 1:
                userCards = that.user1.cards;
                break;
              case 2:
                userCards = that.user2.cards;
                break;
              case 3:
                userCards = that.user3.cards;
                break;
              case 4:
                userCards = that.user4.cards;
                break;
              case 5:
                userCards = that.user5.cards;
                break;
              case 6:
                userCards = that.user6.cards;
                break;
            }
            if (that.extractCardInfo.id) {
              let newCard = that.extractCardInfo,
                oldCard = {};
              userCards.forEach((card, key) => {
                if (card.id == data.number) {
                  oldCard = userCards[key];
                  switch (location) {
                    case 1:
                      that.user1.cards[key] = newCard; // 替换手牌
                      break;
                    case 2:
                      that.user2.cards[key] = newCard;
                      break;
                    case 3:
                      that.user3.cards[key] = newCard;
                      break;
                    case 4:
                      that.user4.cards[key] = newCard;
                      break;
                    case 5:
                      that.user5.cards[key] = newCard;
                      break;
                    case 6:
                      that.user6.cards[key] = newCard;
                      break;
                  }
                  console.log("that.userInfo.sort", that.userInfo.sort);
                  console.log("that.playUser", that.playUser);
                  if (that.userInfo.sort == that.playUser) {
                    console.log("执行替换手牌操作1");
                    that.$options.methods.addDiscardCard(
                      oldCard,
                      location,
                      that
                    );
                  }
                  that.cards.splice(newCard.index, 1);
                  that.extracting = false;
                  that.extractCardInfo = {};
                  that.discardCardInfo = {};
                }
              });
            } else if (that.discardCardInfo.id) {
              // 从弃牌堆里替换手牌
              //console.log('弃牌堆抽取')
              let newCard = that.discardCardInfo,
                oldCard = {};
              console.log("新手牌信息", newCard);
              console.log("替换手牌ID", data.number);
              // console.log("card",card)
              userCards.forEach((card, key) => {
                // console.log(card.id, data.number)
                if (card.id == data.number) {
                  oldCard = userCards[key];
                  switch (location) {
                    case 1:
                      that.user1.cards[key] = newCard; // 替换手牌
                      break;
                    case 2:
                      that.user2.cards[key] = newCard;
                      break;
                    case 3:
                      that.user3.cards[key] = newCard;
                      break;
                    case 4:
                      that.user4.cards[key] = newCard;
                      break;
                    case 5:
                      that.user5.cards[key] = newCard;
                      break;
                    case 6:
                      that.user6.cards[key] = newCard;
                      break;
                  }
                  if (that.userInfo.sort == that.playUser) {
                    console.log("执行替换手牌操作2");
                    that.$options.methods.addDiscardCard(
                      oldCard,
                      newCard.location,
                      that
                    );
                  }
                  // if (that.userInfo.sort == that.playUser) {
                  // 	that.$options.methods.addDiscardCard(oldCard, 1, that)
                  // } else {
                  // 	that.$options.methods.addDiscardCard(oldCard, newCard
                  // 		.location, that)
                  // }
                  //console.log('删除弃牌的方位', newCard.location)
                  that.$options.methods.delDiscardCard(
                    newCard,
                    newCard.location,
                    that
                  );
                  // that.extracting = false
                  // that.extractCardInfo = {}
                  // that.discardCardInfo = {}
                  // that.playUser = (that.userInfo.sort < that.info.peopleNumber) ? (that.playUser + 1) : 1
                }
              });
            } else if (that.copiedcard.id) {
              let newCard = that.copiedcard,
                oldCard = {};
              // newCard.id = that.copiedcard
              // newCard.url = "http://yiju.lanyanceshi.com/cards/card_"+that.copiedcard < 0?'0'+that.copiedcard:that.copiedcard +".png"
              // console.log(newCard.url)
              // newCard.style = "transform: translate3d(3.625px, 3.625px, 3.625px);"
              //console.log('新手牌信息', newCard)
              //console.log('替换手牌ID', data.number)
              userCards.forEach((card, key) => {
                // console.log(card.id, data.number)
                if (card.id == data.number) {
                  oldCard = userCards[key];
                  switch (location) {
                    case 1:
                      that.user1.cards[key] = newCard; // 替换手牌
                      break;
                    case 2:
                      that.user2.cards[key] = newCard;
                      break;
                    case 3:
                      that.user3.cards[key] = newCard;
                      break;
                    case 4:
                      that.user4.cards[key] = newCard;
                      break;
                    case 5:
                      that.user5.cards[key] = newCard;
                      break;
                    case 6:
                      that.user6.cards[key] = newCard;
                      break;
                  }
                  if (that.userInfo.sort == that.playUser) {
                    console.log("执行替换手牌操作3");
                    that.$options.methods.addDiscardCard(
                      oldCard,
                      newCard.location,
                      that
                    );
                  }
                  //console.log('删除弃牌的方位', newCard.location)
                  // that.$options.methods.delDiscardCard(
                  // 	newCard,
                  // 	newCard.location,
                  // 	that
                  // );
                  that.copiedcard = {};
                  that.extracting = false;
                  that.extractCardInfo = {};
                  that.discardCardInfo = {};
                  // that.playUser = (that.userInfo.sort < that.info.peopleNumber) ? (that.playUser + 1) : 1
                }
              });
            }
          }
        });
      }
      if (data.operateMsg == "弃牌") {
        console.log("已经弃牌");
        if (that.extractCardInfo.id) {
          // 判断是牌堆替换
          let newCard = that.extractCardInfo;
          newCard.checked = false;
          console.log(that.users2);
          that.users2.forEach((item, index) => {
            let location = item.location;
            if (item.id == data.userId) {
              console.log(item.id, data.userId);
              that.cards.splice(newCard.index, 1);
              console.log("newCard", newCard);
              console.log("discard", that.user1.discard);
              console.log(that.user1.discard.indexOf(newCard));
              switch (location) {
                case 1:
                  if (that.user1.discard.indexOf(newCard) == -1) {
                    that.user1.discard.splice(0, 0, newCard);
                  }
                  break;
                case 2:
                  if (that.user2.discard.indexOf(newCard) == -1) {
                    that.user2.discard.splice(0, 0, newCard);
                  }
                  break;
                case 3:
                  if (that.user3.discard.indexOf(newCard) == -1) {
                    that.user3.discard.splice(0, 0, newCard);
                  }
                  break;
                case 4:
                  if (that.user4.discard.indexOf(newCard) == -1) {
                    that.user4.discard.splice(0, 0, newCard);
                  }
                  break;
                case 5:
                  if (that.user5.discard.indexOf(newCard) == -1) {
                    that.user5.discard.splice(0, 0, newCard);
                  }
                  break;
                case 6:
                  if (that.user6.discard.indexOf(newCard) == -1) {
                    that.user6.discard.splice(0, 0, newCard);
                  }
                  break;
              }
              // that.playUser = (that.userInfo.sort < that.info.peopleNumber) ? (that.playUser + 1) : 1
            }
          });
        } else if (that.discardCardInfo.id) {
          // 判断是弃牌堆替换
          //console.log('替换弃牌堆')
          let newCard = that.discardCardInfo;
          that.users2.forEach((item, index) => {
            if (item.id == data.userId) {
              let location = item.location,
                userCards = [];
              // console.log('方位', location)
              let oldCard = {};
              that.allCards.forEach((card, key) => {
                if (card.id == data.foldNumber) {
                  oldCard = card;
                  oldCard.checked = false;
                }
              });
              switch (location) {
                case 1:
                  if (that.user1.discard.indexOf(oldCard) == -1) {
                    that.user1.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 2:
                  if (that.user2.discard.indexOf(oldCard) == -1) {
                    that.user2.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 3:
                  if (that.user3.discard.indexOf(oldCard) == -1) {
                    that.user3.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 4:
                  if (that.user4.discard.indexOf(oldCard) == -1) {
                    that.user4.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 5:
                  if (that.user5.discard.indexOf(oldCard) == -1) {
                    that.user5.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 6:
                  if (that.user6.discard.indexOf(oldCard) == -1) {
                    that.user6.discard.splice(0, 0, oldCard);
                  }
                  break;
              }
              // that.$options.methods.delDiscardCard(newCard, location, that)
            }
          });
        } else {
          that.users2.forEach((item, index) => {
            // console.log(data.userId)
            if (item.id == data.userId) {
              let location = item.location;
              let oldCard = {};
              that.allCards.forEach((card, key) => {
                if (card.id == data.foldNumber) {
                  oldCard = card;
                  oldCard.checked = false;
                }
              });
              switch (location) {
                case 1:
                  if (that.user1.discard.indexOf(oldCard) == -1) {
                    that.user1.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 2:
                  if (that.user2.discard.indexOf(oldCard) == -1) {
                    that.user2.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 3:
                  if (that.user3.discard.indexOf(oldCard) == -1) {
                    that.user3.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 4:
                  if (that.user4.discard.indexOf(oldCard) == -1) {
                    that.user4.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 5:
                  if (that.user5.discard.indexOf(oldCard) == -1) {
                    that.user5.discard.splice(0, 0, oldCard);
                  }
                  break;
                case 6:
                  if (that.user6.discard.indexOf(oldCard) == -1) {
                    that.user6.discard.splice(0, 0, oldCard);
                  }
                  break;
              }
              // that.$options.methods.delDiscardCard(newCard, location, that)
            }
          });
        }
        that.extracting = false;
        that.extractCardInfo = {};
        that.discardCardInfo = {};
      }
      if (data.operateMsg == "下一用户操作") {
        if (
          that.isHomewer == true &&
          that.cards.length == 0 &&
          that.isfinish == 0
        ) {
          that.isfinish = 1;
          that.noDoTimeOut();
          // return false;
        }

        that.UID = data.userId;
        if (data.userId == that.userId) {
          that.$notify({
            title: "通知",
            message: "あなたの番です",
          });
        }
        for (let i = 0; i < that.user1.cards.length; i++) {
          that.user1.cards[i].checked = false;
        }
        for (let i = 0; i < that.user2.cards.length; i++) {
          that.user2.cards[i].checked = false;
        }
        for (let i = 0; i < that.user3.cards.length; i++) {
          that.user3.cards[i].checked = false;
        }
        for (let i = 0; i < that.user4.cards.length; i++) {
          that.user4.cards[i].checked = false;
        }
        let ids = [];
        that.userTimes = that.countsNumber;
        that.$options.methods.countDown();
        that.extracting = false;
        that.extractCardInfo = {};
        that.discardCardInfo = {};
        that.users2.forEach((item, index) => {
          if (item.id == data.userId) {
            that.playUser = item.sort;
          }
          if (item.checked == true) {
            ids.push(item.id);
          }
        });
        let arr = [];
        let syc = {};
        let temp = [];
        temp.push(that.user1);
        temp.push(that.user2);
        temp.push(that.user3);
        temp.push(that.user4);
        syc.temp = temp;
        syc.countdown = that.userTimes;
        syc.gameTime = that.gameTimes;
        syc.pk = that.cards;
        syc.users2 = that.users2;
        syc.usersort = that.playUser;
        syc.UId = that.UID;
        arr.push(syc);
        let next = {
          operateType: 13,
          userId: that.userId,
          roomId: that.roomId,
          username: that.userEmail,
          userIds: ids,
          customize: arr,
          url: that.info.url,
        };
        sendWSPush(next);

        // if (that.cards.length > 0) {

        // }
      }
      if (data.operateMsg == "游戏暂停") {
        that.play = false;
        that.dialogPauseVisible = true;
        clearInterval(that.timer);
        clearInterval(that.timer2);
      }

      if (data.operateMsg == "继续游戏") {
        that.play = true;
        that.dialogPauseVisible = false;
        that.countDown();
        that.goTimes();
      }

      if (data.operateMsg == "等待房主操作") {
        // if (that.isHomewer == true) {

        // } else {

        // }
        that.play = false;
        // that.dialogPauseVisible = true
        clearInterval(that.timer);
        clearInterval(that.timer2);
      }

      if (data.operateMsg == "跳过用户") {
        let cur = "";
        let cname = "";
        that.users2.forEach((item, index) => {
          if (that.playUser == item.sort) {
            cur = item.id;
            cname = item.username;
          }
          if (data.userIds.indexOf(item.id) != -1) {
            item.checked = true;
            item.isSkip = true;
            that.users2[index] = item;
            // 判断用户方位
            switch (item.location) {
              case 1:
                that.user1.isSkip = true;
                break;
              case 2:
                that.user2.isSkip = true;
                break;
              case 3:
                that.user3.isSkip = true;
                break;
              case 4:
                that.user4.isSkip = true;
                break;
              case 5:
                that.user5.isSkip = true;
                break;
              case 6:
                that.user6.isSkip = true;
                break;
            }
          }
        });

        if (data.nextFlag) {
          let arr = [];
          let syc = {};
          let temp = [];
          temp.push(that.user1);
          temp.push(that.user2);
          temp.push(that.user3);
          temp.push(that.user4);
          syc.temp = temp;
          syc.countdown = that.userTimes;
          syc.gameTime = that.gameTimes;
          syc.pk = that.cards;
          syc.users2 = that.users2;
          arr.push(syc);
          let ids = [];
          that.users2.forEach((item, index) => {
            if (item.checked == true) {
              ids.push(item.id);
            }
          });
          let next = {
            operateType: 12,
            userId: cur,
            roomId: that.roomId,
            username: cname,
            userIds: ids,
            customize: arr,
            url: that.info.url,
          };
          sendWSPush(next);
        }
      }
      if (data.operateMsg == "解除跳过用户") {
        console.log("users2", that.users2);
        that.users2.forEach((item, index) => {
          console.log("item", item);
          console.log("sdata", data);
          if (data.userIds.indexOf(item.id) != -1) {
            console.log("sa");
            item.checked = false;
            item.isSkip = false;
            that.users2[index] = item;
            // 判断用户方位
            switch (item.location) {
              case 1:
                that.user1.isSkip = false;
                break;
              case 2:
                that.user2.isSkip = false;
                break;
              case 3:
                that.user3.isSkip = false;
                break;
              case 4:
                that.user4.isSkip = false;
                break;
              case 5:
                that.user5.isSkip = false;
                break;
              case 6:
                that.user6.isSkip = false;
                break;
            }
          }
        });
      }

      if (data.operateMsg == "复制其他用户的牌") {
        let newCard = {},
          location = 0;
        that.allCards.forEach((card, key) => {
          if (card.id == data.number) {
            newCard = card;
          }
        });
        // console.log(newCard)
        // 弃牌信息
        that.user1.cards.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 1;
            newCard.index = index;
          }
        });
        that.user2.cards.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 2;
            newCard.index = index;
          }
        });
        that.user3.cards.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 3;
            newCard.index = index;
          }
        });
        that.user4.cards.forEach((item, index) => {
          if (item.id == data.number) {
            newCard.location = 4;
            newCard.index = index;
          }
        });
        // console.log('WS选中弃牌信息:', newCard)
        that.copiedcard = newCard;
      }

      if (data.operateMsg == "游戏结束") {
        let result = {
          minute: that.gameTimes.minute,
          second: that.gameTimes.second,
        };
        let users = [];
        localStorage.removeItem("hasStart");
        if (that.user1.id) users.push(that.user1);
        if (that.user2.id) users.push(that.user2);
        if (that.user3.id) users.push(that.user3);
        if (that.user4.id) users.push(that.user4);
        if (that.user5.id) users.push(that.user5);
        if (that.user6.id) users.push(that.user6);
        result.users = users;
        let addResultParamList = [];
        if (that.user1.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user1.cards.length; i++) {
            if (that.user1.cards[i].id < 10) {
              temp.push("0" + that.user1.cards[i].id);
            } else {
              temp.push(that.user1.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user1.nickname;
          obj.userRole = that.user1.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        if (that.user2.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user2.cards.length; i++) {
            if (that.user2.cards[i].id < 10) {
              temp.push("0" + that.user2.cards[i].id);
            } else {
              temp.push(that.user2.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user2.nickname;
          obj.userRole = that.user2.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        if (that.user3.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user3.cards.length; i++) {
            if (that.user3.cards[i].id < 10) {
              temp.push("0" + that.user3.cards[i].id);
            } else {
              temp.push(that.user3.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user3.nickname;
          obj.userRole = that.user3.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        if (that.user4.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user4.cards.length; i++) {
            if (that.user4.cards[i].id < 10) {
              temp.push("0" + that.user4.cards[i].id);
            } else {
              temp.push(that.user4.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user4.nickname;
          obj.userRole = that.user4.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        if (that.user5.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user5.cards.length; i++) {
            if (that.user5.cards[i].id < 10) {
              temp.push("0" + that.user5.cards[i].id);
            } else {
              temp.push(that.user5.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user5.nickname;
          obj.userRole = that.user5.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        if (that.user6.id) {
          let temp = [];
          let obj = {};
          for (let i = 0; i < that.user6.cards.length; i++) {
            if (that.user6.cards[i].id < 10) {
              temp.push("0" + that.user6.cards[i].id);
            } else {
              temp.push(that.user6.cards[i].id);
            }
          }
          obj.userCard = JSON.stringify(temp);
          obj.userName = that.user6.nickname;
          obj.userRole = that.user6.tag == "房主" ? 1 : 0;
          obj.roomId = that.roomId;
          addResultParamList.push(obj);
        }
        // console.log("result", addResultParamList);
        localStorage.setItem(
          "room_result_" + that.roomId,
          JSON.stringify(result)
        );
        localStorage.setItem(
          "addResultParamList",
          JSON.stringify(addResultParamList)
        );
        that.$router.push({
          path: "/game/result",
          query: {
            roomId: that.roomId,
          },
        });
      }
    },
    // 分配位置
    giveLocation: function (code, email) {
      let that = this,
        giveNum = 1,
        isPlayer = false,
        players = [];
      // 手牌
      let userCards = localStorage.getItem("room_usercards" + that.roomId)
        ? JSON.parse(localStorage.getItem("room_usercards" + that.roomId))
        : "";
      // console.log("userCards", userCards)
      if (!userCards) {
        that.$message({
          message: "カード情報に誤りがあるため、ゲームを再起動してください",
          type: "error",
        });
      }
      let playerIds = [];
      let playerIdsBox = Object.keys(userCards);
      // console.log("playerIdsBox", playerIdsBox)
      playerIdsBox.forEach(function (key, index) {
        // console.log(key)
        if (key == "userId" || key == "email" || key == "username") {
          return true;
        }
        playerIds.push(key);
      });
      console.log("玩家ID顺序", playerIds);
      that.UID = playerIds[0];
      let playersCount = playerIds.length,
        num = 1;
      playerIds.forEach(function (key, index) {
        console.log(key);
        if (key == "userId" || key == "email" || key == "username") {
          return true;
        }
        getUserInfoById(key).then((res) => {
          console.log("getUserInfoById", res);
          if (res.code == 200 && res.result) {
            let sort = index + 1,
              tag = that.tags[index],
              isHomewer = false;
            if (key == that.info.homeowner) {
              tag = "房主";
              isHomewer = true;
            }
            let userInfo = {
              email: res.result.email,
              id: res.result.id,
              realname: res.result.realname,
              nickname: res.result.nickname,
              username: res.result.username,
              sort: index + 1,
              tag: tag,
              isHomewer: isHomewer,
              isSkip: false,
              checked: false,
            };
            that.users2.push(userInfo);
            players.push(userInfo);
            console.log(players, "sss");
            console.log("num", num);
            // console.log("players",players[0])
            if (num == playersCount) {
              // 分配玩家
              players.forEach((item, index) => {
                if (that.userId == item.id) {
                  that.userInfo = item;
                  that.userInfo.isPlayer = isPlayer = true; // 是游戏参加者
                  giveNum = that.$options.methods.giveLocationData(
                    item,
                    index,
                    that.user1,
                    1,
                    giveNum,
                    that
                  );
                  players.splice(index, 1);
                }
              });
              if (!isPlayer) {
                // 如果不是游戏参加者
                players.forEach((item, index) => {
                  if (1 == item.sort) {
                    giveNum = that.$options.methods.giveLocationData(
                      item,
                      index,
                      that.user1,
                      1,
                      giveNum,
                      that
                    );
                    players.splice(index, 1);
                  }
                });
                that.mode = "watch"; // 开启观战模式
                // that.mode = 'watch' // 开启观战模式
              }
              players.forEach((item, index) => {
                if (giveNum == item.sort) {
                  giveNum = that.$options.methods.giveLocationData(
                    item,
                    index,
                    that.user2,
                    2,
                    giveNum,
                    that
                  );
                  players.splice(index, 1);
                }
              });
              players.forEach((item, index) => {
                if (giveNum == item.sort) {
                  giveNum = that.$options.methods.giveLocationData(
                    item,
                    index,
                    that.user3,
                    3,
                    giveNum,
                    that
                  );
                  players.splice(index, 1);
                }
              });
              players.forEach((item, index) => {
                if (giveNum == item.sort) {
                  giveNum = that.$options.methods.giveLocationData(
                    item,
                    index,
                    that.user4,
                    4,
                    giveNum,
                    that
                  );
                  players.splice(index, 1);
                }
              });
              console.log("玩家", players);

              let cards = [],
                cardsNum = that.cardsNum;
              for (var i = 1; i <= cardsNum; i++) {
                let url = "",
                  style = "";
                if (i < 10)
                  url = that.isUrl + that.imgUrl + "card_0" + i + ".png";
                else url = that.isUrl + that.imgUrl + "card_" + i + ".png";
                let z = (that.cardsNum - i) / 8;
                // console.log(cardObj)
                style =
                  "transform: translate3d(" +
                  z +
                  "px, " +
                  z +
                  "px, " +
                  z +
                  "px);";
                let card = {
                  id: i,
                  url: url,
                  style: style,
                };
                // cards.push(card)
                cards[i] = card;
              }
              that.allCards = cards;
              // 随机给用户分配手牌
              that.user1.cards = [];
              that.user2.cards = [];
              that.user3.cards = [];
              that.user4.cards = [];

              // 手牌
              let userCards = localStorage.getItem(
                "room_usercards" + that.roomId
              )
                ? JSON.parse(
                    localStorage.getItem("room_usercards" + that.roomId)
                  )
                : "";
              if (!userCards) {
                that.$message({
                  message:
                    "カード情報に誤りがあるため、ゲームを再起動してください",
                  type: "error",
                });
              }
              // console.log(userCards)
              let newCards = [],
                noCards = [];
              playerIds.forEach(function (key) {
                let cardLists = userCards[key],
                  userCard = [];
                cardLists.forEach((item, index) => {
                  noCards.push(item);
                });
                //console.log(cardLists)
                cards.forEach((item, index) => {
                  if (cardLists.indexOf(item.id) >= 0) {
                    userCard.push(item);
                  }
                });
                if (key == that.user1.id) that.user1.cards = userCard;
                if (key == that.user2.id) that.user2.cards = userCard;
                if (key == that.user3.id) that.user3.cards = userCard;
                if (key == that.user4.id) that.user4.cards = userCard;
                if (key == that.user5.id) that.user5.cards = userCard;
                if (key == that.user6.id) that.user6.cards = userCard;
              });

              // let nn = 1
              cards.forEach((item, index) => {
                // if(nn>5) return true;
                if (noCards.indexOf(item.id) < 0) {
                  // nn++
                  newCards.push(item);
                }
              });
              // console.log(newCards)
              that.cards = newCards;
            }
            num++;
          }
        });
      });
      that.$options.methods.createSocket(code, email);
    },
    // 分配位置公共方法
    giveLocationData: function (item, index, obj, location, giveNum, e) {
      if (item.sort < e.info.peopleNumber) {
        giveNum = item.sort + 1;
      } else {
        giveNum = 1;
      }
      obj.email = item.email;
      obj.id = item.id;
      obj.username = item.username;
      obj.nickname = item.nickname;
      obj.realname = item.realname;
      obj.lineStatus = item.lineStatus;
      obj.sort = item.sort;
      obj.tag = item.tag;
      obj.location = location;
      obj.isSkip = item.isSkip;
      e.users2.forEach((item2, index2) => {
        if (item2.id == item.id) {
          e.users2[index2].location = location;
        }
      });
      return giveNum;
    },
    // 初始化公共卡牌
    getCards: function () {
      let cards = [],
        that = this,
        cardsNum = that.cardsNum;
      for (var i = 1; i <= cardsNum; i++) {
        let url = "";
        if (i < 10) url = that.imgUrl + "card_0" + i + ".png";
        else url = that.imgUrl + "card_" + i + ".png";
        let card = {
          id: i,
          url: url,
        };
        // cards.push(card)
        cards[i] = card;
      }

      // 随机给用户分配手牌
      that.user1.cards = [];
      that.user2.cards = [];
      that.user3.cards = [];
      that.user4.cards = [];

      // 手牌
      let userCards = localStorage.getItem("room_usercards" + that.roomId)
        ? JSON.parse(localStorage.getItem("room_usercards" + that.roomId))
        : "";
      if (!userCards) {
        that.$message({
          message: "カード情報に誤りがあるため、ゲームを再起動してください",
          type: "error",
        });
      }
      // console.log(userCards)
      let newCards = [];
      Object.keys(userCards).forEach(function (key) {
        let cardLists = userCards[key],
          userCard = [];
        cards.forEach((item, index) => {
          if (cardLists.indexOf(item.id) >= 0) {
            userCard.push(item);
          } else {
            newCards.push(item);
          }
        });
        if (key == that.user1.id) that.user1.cards = userCard;
        if (key == that.user2.id) that.user2.cards = userCard;
        if (key == that.user3.id) that.user3.cards = userCard;
        if (key == that.user4.id) that.user4.cards = userCard;
        if (key == that.user5.id) that.user5.cards = userCard;
        if (key == that.user6.id) that.user6.cards = userCard;
      });

      newCards.sort(() => Math.random() - 0.5);
      that.cards = newCards;
    },
    // 初始化卡牌堆
    shuffle: function () {
      let that = this;
      let cards = that.cards;
      //console.log(that.$refs.gamecardslists.children)
      let cardBoxObj = that.$refs.gamecardslists,
        cardLists = that.$refs.gamecardslists.children;
      cards.sort(() => Math.random() - 0.5);
      cards.forEach((item, index) => {
        let cardObj = cardLists[index],
          z = (that.cardsNum - index) / 8;
        // console.log(cardObj)
        cardObj.style["transform"] =
          "translate3d(" + z + "px, " + z + "px, -" + z + "px)";
      });
    },
    translate: function (a, b, c) {
      c = c || 0;
      if (has3d) {
        return "translate3d(" + a + ", " + b + ", " + c + ")";
      } else {
        return "translate(" + a + ", " + b + ")";
      }
    },
    // 抽取卡牌
    extractCard: function (item, index) {
      let that = this;
      that.csh();
      //console.log(that.playUser)
      console.log("that.userInfo", that.userInfo);
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      if (that.extracting == true) {
        console.log("操作を禁止しています1");
        return false;
      }
      if (that.userInfo.sort != that.playUser) {
        console.log(that.userInfo);
        console.log(that.userInfo.sort);
        console.log(that.playUser);
        console.log("操作を禁止しています2");
        return false;
      }
      if (that.play == false) {
        that.$message({
          message: "ゲームが中断中です",
          type: "error",
        });
      }

      // that.$refs.location1.children.$el.removeClass("open")
      // console.log(that.$refs.location1.children)
      // 清空要替换的弃牌堆信息
      that.discardCardInfo = {};

      // 获取牌堆ID
      let noCreatNum = [];
      that.cards.forEach((card, id) => {
        // if(card.id != item.id) noCreatNum.push(card.id)
        noCreatNum.push(card.id);
      });
      console.log(noCreatNum);
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      // 抽取推送
      let data = {
        foldNumber: "",
        noCreatNum: noCreatNum,
        // number:   item.id,
        operateType: 1,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        roomId: that.roomId,
        customize: JSON.stringify(temp),
      };
      sendWSPush(data);
      // console.log(data)
      // gamesOperate(data).then((res) => {
      //   // console.log(res)
      //   if (res && res.code == 200) {
      //     // if(that.cards.length == 0){
      //     //   that.doFinish()
      //     // }
      //   }
      // })
    },
    // 从弃牌堆中抽取
    extractCardFromDiscards: function (
      item,
      index,
      location,
      event,
      discard,
      username
    ) {
      let that = this;
      that.discardCardInfo = {};
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      if (that.userInfo.sort != that.playUser) {
        console.log("操作を禁止しています");
        return false;
      }
      if (that.extracting == true) {
        that.$message({
          message: "手札の入れ替えを選択してください",
          type: "error",
        });
        return false;
      }
      if (that.play == false) {
        that.$message({
          message: "ゲームが中断中です",
          type: "error",
        });
        return false;
      }
	   that.csh()
      let user1id = [];
      for (let i = 0; i < that.user1.cards.length; i++) {
        user1id.push(that.user1.cards[i].id);
      }
      if (user1id.indexOf(item.id) != -1) {
        that.$message({
          message: "既にこのカードを持っています",
          type: "error",
        });
        return;
      }
      let array = discard;
      for (let i = 0; i < array.length; i++) {
        if (i == index) {
          if (array[i].checked == false) {
            array[i].checked = true;
            that.discardCardInfo = {
              location: location,
              index: index,
              id: item.id,
              url: item.url,
            };
          } else {
            array[i].checked = false;
            that.discardCardInfo = {};
          }
        } else {
          array[i].checked = false;
        }
      }
      console.log(array);
      if (username == "user1") {
        that.user1.discard = array;
        console.log("s", that.user1.discard);
      }
      let newCard = that.discardCardInfo;
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      // console.log(noCreatNum)
      // 抽取推送
      let data = {
        foldNumber: "",
        number: newCard.id,
        operateType: 3,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        roomId: that.roomId,
        customize: temp,
      };
      sendWSPush(data);
      // 请求
    },

    // 复制对面卡牌
    changeOtherCard(item, index, event, location, cards, player) {
      let that = this;
      // 判断手牌中没有重复的牌
      let user1id = [];
      for (let i = 0; i < that.user1.cards.length; i++) {
        user1id.push(that.user1.cards[i].id);
      }
      if (user1id.indexOf(item.id) != -1) {
        that.$message({
          message: "今はその操作はできません",
          type: "error",
        });
        return;
      }
      if(that.extracting == true){
         return false;
      }
	  that.csh()
      if (player == "user2") {
        for (let i = 0; i < cards.length; i++) {
          console.log(i);
          if (index == i) {
            if (cards[i].checked == true) {
              cards[i].checked = false;
            } else {
              cards[i].checked = true;
            }
          } else {
            cards[i].checked = false;
          }
        }
        that.user2.cards = cards;
      } else if (player == "user3") {
        for (let i = 0; i < cards.length; i++) {
          console.log(i);
          if (index == i) {
            if (cards[i].checked == true) {
              cards[i].checked = false;
            } else {
              cards[i].checked = true;
            }
          } else {
            cards[i].checked = false;
          }
        }
        that.user3.cards = cards;
      } else if (player == "user4") {
        for (let i = 0; i < cards.length; i++) {
          console.log(i);
          if (index == i) {
            if (cards[i].checked == true) {
              cards[i].checked = false;
            } else {
              cards[i].checked = true;
            }
          } else {
            cards[i].checked = false;
          }
        }
        that.user4.cards = cards;
      }
      if (that.info.isCopy == "あり") {
        let data = {
          number: item.id,
          operateType: 2,
          url: that.info.url,
          userId: that.userId,
          username: that.userEmail,
          roomId: that.roomId,
        };
        sendWSPush(data);
      } else {
        that.$message({
          message: "今はその操作はできません",
          type: "error",
        });
      }
    },

    // 替换手牌
    changeSelfCard: function (item, index, event, location) {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        that.$message({
          message: "観戦中",
          type: "error",
        });
        return false;
      }
      if (that.userInfo.sort != that.playUser) {
        console.log("操作を禁止しています");
        that.$message({
          message: "今はその操作はできません",
          type: "error",
        });
        return false;
      }
      if (that.play == false) {
        that.$message({
          message: "ゲームが中断中です",
          type: "error",
        });
      }

      let newCard = {},
        oldCard = that.user1.cards[index],
        discardType = 0;
      // console.log(that.copiedcard.id)
      if (that.extractCardInfo.id) {
        discardType = 1;
      } else if (that.discardCardInfo.id) {
        discardType = 2;
      } else if (that.copiedcard.id) {
        discardType = 3;
      }
      if (discardType == 0) {
        console.log("操作を禁止しています");
        that.$message({
          message: "今はその操作はできません",
          type: "error",
        });
        return false;
      }

      if (discardType == 3) {
        // 确认换牌
        if (location == 1) {
          newCard = that.copiedcard;
          let temp = [];
          temp.push(that.user1);
          temp.push(that.user2);
          temp.push(that.user3);
          temp.push(that.user4);
          let data = {
            number: item.id,
            operateType: 10,
            url: that.info.url,
            userId: that.userId,
            username: that.userEmail,
          };
          sendWSPush(data);
        }
      } else {
        // 确认换牌
        if (location == 1) {
          newCard = that.extractCardInfo;
          let temp = [];
          temp.push(that.user1);
          temp.push(that.user2);
          temp.push(that.user3);
          temp.push(that.user4);
          let data = {
            number: item.id,
            operateType: 10,
            url: that.info.url,
            userId: that.userId,
            username: that.userEmail,
          };
          sendWSPush(data);
        }
      }
    },
    // 直接弃牌
    discardCard: function (location, event) {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      if (that.extracting == false) {
        console.log("操作を禁止しています");
        return false;
      }
      if (that.userInfo.sort != that.playUser) {
        console.log("操作を禁止しています");
        return false;
      }
      if (that.play == false) {
        that.$message({
          message: "ゲームが中断中です",
          type: "error",
        });
      }
      let newCard = that.extractCardInfo;
      that.$options.methods.addDiscardCard(newCard, location, that);
      if (that.cards.length == 0) {
        // that.doFinish()
        that.dialogFinishVisible = true;
      }
    },
    // 添加弃牌
    addDiscardCard: function (card, location, e) {
      let that = this;
      console.log("roomid", e.roomId);
      console.log(e);
      let arr = [];
      let syc = {};
      let temp = [];
      temp.push(e.user1);
      temp.push(e.user2);
      temp.push(e.user3);
      temp.push(e.user4);
      syc.temp = temp;
      syc.countdown = e.userTimes;
      syc.gameTime = e.gameTimes;
      syc.pk = e.cards;
      syc.users2 = e.users2;
      arr.push(syc);
      let ids = [];
      e.users2.forEach((item, index) => {
        if (item.checked == true) {
          ids.push(item.id);
        }
      });
      let next = {
        operateType: 12,
        userId: e.userId,
        roomId: e.roomId,
        username: e.userEmail,
        userIds: ids,
        customize: arr,
        url: e.info.url,
      };
      // 弃牌请求
      let data = {
        foldNumber: card.id,
        operateType: 4,
        url: e.info.url,
        userId: e.userId,
        username: e.userEmail,
        roomId: e.roomId,
        customize: JSON.stringify(temp),
      };
      sendWSPush(data);
      sendWSPush(next);
    },
    // 移除弃牌
    delDiscardCard: function (card, location, e) {
      switch (location) {
        case 1:
          e.user1.discard.splice(card.index, 1);
          break;
        case 2:
          e.user2.discard.splice(card.index, 1);
          break;
        case 3:
          e.user3.discard.splice(card.index, 1);
          break;
        case 4:
          e.user4.discard.splice(card.index, 1);
          break;
        case 5:
          e.user5.discard.splice(card.index, 1);
          break;
        case 6:
          e.user6.discard.splice(card.index, 1);
          break;
      }
    },
    // 复制手牌
    copyCard: function () {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      if (that.play == false) {
        that.$message({
          message: "ゲームが中断中です",
          type: "error",
        });
      }
      if ((that.extracting = true)) {
        that.$message({
          message: "已抽牌，请选择替换手牌",
          type: "error",
        });
      }
    },
    // 暂停之后再开始
    reStart() {
      let that = this;
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      let data = {
        operateType: 11,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        roomId: that.roomId,
        customize: temp,
      };
      sendWSPush(data);
    },
    // 卡牌拖拽
    dragscroll: function (e) {
      // console.log(e.currentTarget.parentElement)
      let el = e.currentTarget.parentElement; // 当前对象父元素
      // 当鼠标事件发生时（不管是onclick，还是omousemove，onmouseover等），鼠标相对于浏览器（这里说的是浏览器的有效区域）x轴的位置；
      let disX = e.clientX;
      // 当鼠标事件发生时，鼠标相对于浏览器（这里说的是浏览器的有效区域）y轴的位置；
      let disY = e.clientY;
      let originalScrollLeft = el.scrollLeft; // 网页被卷去的左
      let originalScrollTop = el.scrollTop; // 网页被卷去的高
      let originalScrollBehavior = el.style["scroll-behavior"]; // auto 滚动条立即滚动  smooth 窗口平稳滚动
      let originalPointerEvents = el.style["pointer-events"]; // 设置元素是否对指针事件做出反应
      el.style["scroll-behavior"] = "auto"; // 滚动条立即滚动
      // 鼠标移动事件是监听的整个document，这样可以使鼠标能够在元素外部移动的时候也能实现拖动
      document.onmousemove = function (e) {
        e.preventDefault(); // 防止链接打开 URL
        let distanceX = e.clientX - disX;
        let distanceY = e.clientY - disY;
        el.scrollTo(
          originalScrollLeft - distanceX,
          originalScrollTop - distanceY
        );
        // 由于我们的图片本身有点击效果，所以需要在鼠标拖动的时候将点击事件屏蔽掉
        el.style["pointer-events"] = "none"; // 不对指针事件做出反应
      };
      document.onmouseup = function () {
        document.onmousemove = null;
        document.onmouseup = null;
        el.style["scroll-behavior"] = originalScrollBehavior;
        el.style["pointer-events"] = originalPointerEvents;
      };
    },
    // 获取房间信息
    getRoomDetail: function () {
      let that = this,
        roomId = that.roomId;
      getRoomDetail(roomId).then((res) => {
        if (res) {
          if (res.code == 200 && res.result) {
            that.info = {
              homeowner: res.result.homeownerId,
              roomName: res.result.roomName,
              homeownerName: res.result.homeownerName,
              startTime: that.GLOBAL.timeFormat(res.result.startTime * 1000),
              theme: res.result.theme,
              isCopy: res.result.isCopy == 1 ? "あり" : "なし",
              isPublic: res.result.isPublic,
              peopleNumber: res.result.peopleNumber,
              url: res.result.url,
            };
            that.userTimes = res.result.turnTime;
            that.countsNumber = res.result.turnTime;
            that.gemePeople = res.result.peopleNumber;
            let pb = res.result.isPublic == 1 ? "公開" : "非公開";
            let copyInfo =
              res.result.homeownerName +
              "さんからの招待です。\n \n 以下から参加できます。\n URL: " +
              res.result.url +
              " \n \n パスワード：" +
              res.result.password +
              " \n \n ・ゲーム情報 \n ホスト名：" +
              res.result.homeownerName +
              "\n ルーム名：" +
              res.result.roomName +
              "\n 開始時刻：" +
              res.result.startTime +
              "\n \n ・ゲーム設定" +
              "\n 主题：" +
              res.result.theme +
              "\n 人数：" +
              res.result.peopleNumber +
              "\n 手札公開：" +
              pb +
              "\n 复制功能：" +
              res.result.isCopy;
            that.copyInfo = copyInfo;

            if (that.userId == res.result.homeownerId) that.isHomewer = true;
            // let code = res.result.url.split('/')[3]
            let code = res.result.url;
            // console.log(that.userEmail)
            // 链接
            that.giveLocation(code, that.userEmail);
          }
        }
      });
    },
    // 获取用户信息
    getUserInfo: function () {
      let that = this,
        token = localStorage.getItem("Authorization");
      getUserInfo(token).then((res) => {
        console.log("userinfo", res.result);
        if (res) {
          that.userInfo = res.result;
        }
      });
    },
    // 暂停计时
    doTimeOut: function () {
      let that = this;
      // if (that.mode == "watch") {
      //   console.log("観戦中");
      //   return false;
      // }
      that
        .$confirm("操作を続けますか？", "通知", {
          confirmButtonText: "OK",
          cancelButtonText: "キャンセル",
          type: "warning",
        })
        .then(() => {
          let temp = [];
          temp.push(that.user1);
          temp.push(that.user2);
          temp.push(that.user3);
          temp.push(that.user4);
          let data = {
            operateType: 5,
            url: that.info.url,
            userId: that.userId,
            username: that.userEmail,
            customize: temp,
            roomId: that.roomId,
          };
          sendWSPush(data);
          // 请求
          // gamesOperate(data).then((res) => {
          //     if (res && res.code == 200) {}
          // })
        })
        .catch(() => {});
    },

    //没有手牌的暂停
    noDoTimeOut: function () {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      that.dialogFinishVisible = true;
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      let data = {
        operateType: 5,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        customize: temp,
        roomId: that.roomId,
      };
      sendWSPush(data);
      // 请求
      // gamesOperate(data).then((res) => {
      //     if (res && res.code == 200) {}
      // })
    },

    // 次回合跳过
    doSelfSkip: function () {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      if (that.userInfo.sort != that.playUser) {
        console.log("操作を禁止しています");
        return false;
      }
      that
        .$confirm("操作を続けますか？", "通知", {
          confirmButtonText: "OK",
          cancelButtonText: "キャンセル",
          type: "warning",
        })
        .then(() => {
          let arr = [];
          let syc = {};
          let temp = [];
          let ids = [];
          temp.push(that.user1);
          temp.push(that.user2);
          temp.push(that.user3);
          temp.push(that.user4);
          syc.temp = temp;
          syc.countdown = that.userTimes;
          syc.gameTime = that.gameTimes;
          syc.pk = that.cards;
          syc.users2 = that.users2;
          arr.push(syc);
          that.users2.forEach((item, index) => {
            if (item.checked == true) {
              ids.push(item.id);
            }
          });
          let next = {
            operateType: 12,
            userId: that.userId,
            roomId: that.roomId,
            username: that.userEmail,
            userIds: ids,
            customize: arr,
            url: that.info.url,
          };
          sendWSPush(next);
        })
        .catch(() => {});
    },
    // 继续计时
    doPlay: function () {
      let that = this;
      if (that.mode == "watch") {
        console.log("観戦中");
        return false;
      }
      that.play = true;
    },
    // 设置
    doSet: function () {
      this.dialogSettingVisible = true;
    },
    // 终止
    toFinish: function () {
      this.dialogFinishVisible = true;
    },
    // 确认结束
    doFinish: function () {
      let that = this;
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      let data = {
        operateType: 8,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        customize: JSON.stringify(temp),
        roomId: that.roomId,
        gameTime:that.gameTimes.str
      };
      // 请求
      gamesOperate(data).then((res) => {
        if (res && res.code == 200) {
          let result = {
            minute: that.gameTimes.minute,
            second: that.gameTimes.second,
          };
          let users = [];
          if (that.user1.id) users.push(that.user1);
          if (that.user2.id) users.push(that.user2);
          if (that.user3.id) users.push(that.user3);
          if (that.user4.id) users.push(that.user4);
          if (that.user5.id) users.push(that.user5);
          if (that.user6.id) users.push(that.user6);
          result.users = users;

          // localStorage.setItem('room_result_' + that.roomId, JSON.stringify(result))
          // that.$router.push({
          //   path: '/game/result',
          //   query: {
          //     roomId: that.roomId
          //   }
          // })
        }
      });
    },
    // 继续游戏
    toGame: function () {
      this.dialogFinishVisible = false;
      this.dialogSettingVisible = false;
      this.reStart();
    },
    // 设置跳过
    toSkip: function () {
      this.dialogSkipVisible = true;
      this.dialogSettingVisible = false;
    },
    // 选择跳过用户
    // 选择游戏者
    checkUser: function (item, index, checked) {
      let that = this;
      if (checked) {
        that.users2[index].checked = false;
      } else {
        that.users2[index].checked = true;
      }
    },
    doSkip: function () {
      let that = this;
      let ids = [];
      let names = [];
      let cur = "";
      that.users2.forEach((item, index) => {
        if (item.checked == true) {
          console.log(item);
          ids.push(item.id);
          names.push(item.username);
        }
        if (item.sort == that.playUser) {
          console.log(item);
          cur = item.id;
        }
      });
      let data = {
        operateType: 6,
        url: that.info.url,
        userId: cur,
        username: names[0],
        userIds: ids,
        usernames: names,
        currentUserId: cur,
      };
      sendWSPush(data);
      that.dialogSkipVisible = false;
    },
    // キャンセル跳过设置
    cancelSkip: function (name, id, location) {
      let that = this;
      let s = [];
      let n = [];
      let temp = [];
      temp.push(that.user1);
      temp.push(that.user2);
      temp.push(that.user3);
      temp.push(that.user4);
      s.push(id);
      n.push(name);
      let data = {
        operateType: 7,
        url: that.info.url,
        userId: that.userId,
        username: that.userEmail,
        userIds: s,
        usernames: n,
      };
      sendWSPush(data);
    },
    // 倒计时
    countDown: function () {
      let that = this,
        times = that.userTimes;
      that.timer = setInterval(() => {
        that.userTimes--;
      }, 1000);
    },
    // 计时器
    goTimes: function () {
      let that = this;
      that.timer2 = setInterval(() => {
        that.gameTimes.second++;
        if (that.gameTimes.second == 60) {
          that.gameTimes.second = 0;
          that.gameTimes.minute++;
        }
        that.gameTimes.str =
          that.$options.methods.toDub(that.gameTimes.minute) +
          ":" +
          that.$options.methods.toDub(that.gameTimes.second);
      }, 1000);
    },
    // 补 0
    toDub: function (n) {
      if (n < 10) {
        return "0" + n;
      } else {
        return "" + n;
      }
    },
  },
};
</script>

<style scoped>
@import "../../assets/css/base.css";

#app {
}

html,
body,
#app,
.main {
  width: 100%;
  min-width: 320px;
  height: 100%;
  padding: 0;
  margin: 0;
  background-color: red !important;
}

.game-warp {
  width: 65%;
  min-width: 1200px;
  height: 80%;
  margin: 5% auto;
}

.game-warp .game-box {
  width: 100%;
  height: 100%;
}

.mask {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  border-radius: 21.5735px;
  text-align: center;
  font-weight: 700;
  font-size: 20px;
  color: #ffffff;
  z-index: 999;
}

.mask .release-skip {
  width: 114px;
  height: 42px;
  border: 2px solid #ffffff;
  box-sizing: border-box;
  border-radius: 8px;
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #ffffff;
  position: absolute;
  top: 20px;
  right: 20px;
}

.mask::before,
.mask .release-skip::before {
  display: inline-block;
  content: "";
  height: 100%;
  vertical-align: middle;
}

.game-warp .game-box .game-box-left,
.game-warp .game-box .game-box-right {
  width: 15%;
  height: 50rem;
  background: #ffffff;
  box-shadow: 0px 5px 10px #63aeb0;
  border-radius: 21.5735px;
  padding: 20px 0;
  position: relative;
}

.game-warp .game-box .game-box-left .username,
.game-box-min .user-box .username,
.game-warp .game-box .game-box-right .username {
  width: 136.63px;
  height: 35.96px;
  line-height: 35.96px;
  background: #63aeb0;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  text-align: center;
  font-weight: 700;
  font-size: 19.1765px;
  color: #fff;
}

.game-warp .game-box .game-box-left .user-cards-box,
.game-warp .game-box .game-box-right .user-cards-box {
  flex: 1;
  justify-content: space-around;
}

.game-warp .game-box .game-box-left .user-cards-box .card-box,
.game-warp .game-box .game-box-right .user-cards-box .card-box {
  width: 65%;
  padding: 3px;
  background: #ffffff;
  border: 0.261801px solid #b0b0b0;
  box-sizing: border-box;
  border-radius: 3.66522px;
}

.game-warp .game-box .game-box-left .user-cards-box .card-box .card-box2,
.game-warp .game-box .game-box-right .user-cards-box .card-box .card-box2 {
  background: #6dc5c6;
  border-radius: 2.58986px;
  text-align: center;
}

.game-warp .game-box .game-box-left .user-cards-box .card-box .card-box2 .card,
.game-warp
  .game-box
  .game-box-right
  .user-cards-box
  .card-box
  .card-box2
  .card {
  height: 100%;
  transform: rotate(90deg);
}

.game-warp .game-box .game-box-left .user-cards-box .card2,
.game-warp .game-box .game-box-right .user-cards-box .card2 {
  width: 43%;
  transform: rotate(90deg);
}

.myself {
  background: #1e7578;
  width: 140px;
  height: 46px;
  color: #fff;
  text-align: center;
  font-size: 14px;
  line-height: 46px;
  border-radius: 11px;
  position: absolute;
  left: -150px;
  top: 21px;
}

.rest {
  text-align: center;
  font-size: 14px;
  color: #fff;
  position: relative;
  top: 40px;
  left: -15%;
}

.game-warp .game-box .game-box-min {
  width: 70%;
}

.game-warp .game-box .game-box-min .user-box {
  width: 88%;
  height: 163px;
  background: #fff;
  box-shadow: 0px 5px 10px #63aeb0;
  border-radius: 21.5735px;
  padding: 0 20px;
  position: relative !important;
}

.game-warp .game-box .game-box-min .user-box .username {
  height: 90.63px;
  width: 35.96px;
  border-top-right-radius: 0;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  font-size: 19.1765px;
  line-height: 22px;
  padding: 23px 0;
}

.game-warp .game-box .game-box-min .user-box .user-cards-box {
  flex: 1;
  justify-content: space-around;
}

.game-warp .game-box .game-box-min .user-box .user-cards-box .card-box {
  height: 65%;
  padding: 3px;
  background: #ffffff;
  border: 0.261801px solid #b0b0b0;
  box-sizing: border-box;
  border-radius: 3.66522px;
}

.game-warp
  .game-box
  .game-box-min
  .user-box
  .user-cards-box
  .card-box
  .card-box2 {
  height: 100%;
  padding: 0 0.3rem;
  background: #6dc5c6;
  border-radius: 2.58986px;
  text-align: center;
}

.game-warp
  .game-box
  .game-box-min
  .user-box
  .user-cards-box
  .card-box
  .card-box2
  .card {
}

.game-warp .game-box .game-box-min .user-box .user-cards-box .card2 {
  width: 125px;
  height: 155px;
  /* z-index: 98 !important; */
}

.game-cards-box {
  width: 95%;
  padding: 40px 0 0 0;
  position: relative;
  left: -16%;
}

.cardm:hover {
  transform: scale(1.4) !important;
  z-index: 999 !important;
}

.cardh:hover {
  transform: scale(2) !important;
  z-index: 1002 !important;
  /* border: 3px solid #1e7578; */
  box-sizing: border-box;
  border-radius: 6px;
}

.cardh,
.cardm {
  transition: all 0.2s linear;
}

.game-cards-box .left,
.game-cards-box .right {
  width: 12%;
}

.game-cards-box .left .cards-lists,
.game-cards-box .right .cards-lists {
  width: 75%;
  height: 367px;
  background: #ffffff;
  border-radius: 10.8529px;
  overflow-y: hidden;
  padding: 20px 16px;
}

.card {
  flex-shrink: 0 !important;
}

.game-cards-box .left .cards-lists .card,
.game-cards-box .right .cards-lists .card {
  height: auto;
  transform: rotate(90deg);
  margin-top: -18px;
}

.game-cards-box .min {
  width: 76%;
}

.game-cards-box .min .min-top,
.game-cards-box .min .min-bottom {
}

.game-cards-box .min .min-top .cards-lists,
.game-cards-box .min .min-bottom .cards-lists {
  width: 90%;
  height: 133px;
  background: #ffffff;
  border-radius: 10.8529px;
  overflow-x: hidden;
  padding: 0 10px;
}

.game-cards-box .min .min-top .cards-lists .cards-scroll,
.game-cards-box .min .min-bottom .cards-lists .cards-scroll {
  min-width: 1900px;
  height: 100%;
}

.game-cards-box .min .min-top .cards-lists .cards-scroll .card,
.game-cards-box .min .min-bottom .cards-lists .cards-scroll .card {
  width: 67.38px;
  height: 84px;
  margin: 0 10px;
  z-index: 999 !important;
  flex-shrink: 0;
}

.game-cards-box .min {
  justify-content: space-between;
}

.game-cards-box .min .min-cards-box {
  overflow: hidden;
}

.game-cards-box .min .min-cards-box .cards-lists {
  position: relative;
  top: 3rem;
  left: 14rem;
  /* transform:translate(14rem,6rem) */
}

.game-cards-box .min .min-cards-box .cards-lists .card-box {
  width: 140.24px;
  height: 174.81px;
  padding: 3px;
  background: #ffffff;
  border: 0.480257px solid #b0b0b0;
  box-sizing: border-box;
  border-radius: 6.7236px;
  position: absolute;
  top: 0;
}

.game-cards-box .min .min-cards-box .cards-lists .card-box .card-box2 {
  width: 132.82px;
  height: 166.03px;
  background: #6dc5c6;
  border-radius: 2.58986px;
  text-align: center;
}

.game-cards-box .min .min-cards-box .cards-lists .card-box .card-box2 .card {
  width: 120px;
  height: 151.5px;
}

.game-cards-box .min .min-cards-box .card-top {
  width: 138.98px;
  height: 173.25px;
  position: relative;
  top: 5rem;
  left: 16.8rem;
  z-index: 999;
  /* transform:translate(16.8rem,5.8rem) */
}

.game-cards-box .right {
}

.game-cards-box .right .cards-lists .card {
  transform: rotate(-90deg);
}

.game-warp .game-user-self {
  justify-content: space-between;
  position: relative;
}

.game-warp .game-user-self .user-box {
  width: 85%;
}

.game-warp .game-user-self .user-box .user-cards-box {
  width: 100%;
  height: 210px;
  background: #ffffff;
  box-shadow: 0px 5px 10px #63aeb0;
  border-radius: 18px;
  position: relative;
  top: 20px;
  justify-content: space-around;
  position: relative;
}

.game-warp .game-user-self .user-box .user-cards-box .mask {
  border-radius: 17.5735px;
}

.game-warp .game-user-self .user-box .user-cards-box .card {
  width: 125.07px;
  height: 155.91px;
}

.game-warp .game-user-self .room-info {
  justify-content: space-around;
  padding: 10px 0 0 10px;
  position: relative;
}

.game-warp .game-user-self .room-info .item {
  width: 100%;
  height: 44px;
  background: #ffffff;
  box-shadow: 0px 4px 6px rgba(99, 174, 176, 0.8);
  border-radius: 8px;
  text-align: center;
  margin-top: 5px;
}

.usernamesc {
  width: 120px !important;
  height: 35.96px !important;
  border-radius: 10px !important;
  padding: 0 !important;
  line-height: 35.96px !important;
  position: absolute;
  top: -38px;
  left: 0;
  background: #63aeb0 !important;
  /* line-height: 22px; */
  padding: 0 23px !important;
}

.game-warp .game-user-self .room-info .item .counts {
  font-family: "DIN Alternate";
  font-style: normal;
  font-weight: 700;
  font-size: 24px;
  line-height: 28px;
  letter-spacing: 0.16em;
  color: #686868;
}

.game-warp .game-user-self .room-info .item .txt {
  font-style: normal;
  font-weight: 700;
  font-size: 10px;
  line-height: 14px;
  letter-spacing: 0.16em;
  color: #d7d7d7;
}

.game-warp .game-user-self .room-info .btns-box {
  width: 100%;
  justify-content: space-between;
}

.game-warp .game-user-self .room-info .btns-box .item {
  width: auto;
  height: 58px;
  background: #fff;
  box-shadow: 0px 4px 6px rgba(99, 174, 176, 0.8);
  border-radius: 8px;
  text-align: center;
}

.game-warp .game-user-self .room-info .btns-box .item:nth-last-child(3) {
  margin-right: 10px;
}

.game-warp .game-user-self .room-info .btns-box .item:nth-last-child(1) {
  margin-left: 10px;
}

.game-cards-box
  .min
  .min-cards-box
  .cards-lists
  .card-box:nth-last-child(1)
  .card-box2:hover {
  border: 3px solid #EFFF00;
  box-sizing: border-box;
  border-radius: 8px;
}

.cardchecked:hover,
.cardchecked:active {
  /* border: 3px solid #1e7578; */
  /* box-sizing: border-box; */
  border-radius: 8px;
  /* transform: scale(1.1) !important; */
  z-index: 999 !important;
}
</style>

<style>
.cards-scroll .card .active {
  border: 2px solid #EFFF00;
  box-sizing: border-box;
  border-radius: 8px;
}

/*  弹窗  */
.game-warp .setting-box .el-dialog {
  width: 25rem;
  border-radius: 12px;
}

.game-warp .setting-box .el-dialog .el-dialog__header {
  text-align: center;
}

.game-warp .setting-box .el-dialog .el-dialog__header .el-dialog__title {
  font-weight: 700;
  font-size: 18px;
  line-height: 21px;
  color: #686868 !important;
}

.game-warp .setting-box .el-dialog .el-dialog__body {
  display: none;
}

.game-warp .setting-box .el-dialog .el-dialog__footer {
  padding: 0 20px 20px;
}

.game-warp .setting-box .el-dialog .el-dialog__footer .finish-btn {
  width: 100%;
  height: 46px;
  border: 2px solid #63aeb0;
  box-sizing: border-box;
  border-radius: 5px;
  margin-bottom: 10px;
}

.game-warp .setting-box .el-dialog .el-dialog__footer .finish-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #63aeb0;
}

.game-warp .setting-box .el-dialog .el-dialog__footer .skip-btn {
  width: 100%;
  height: 46px;
  background: #63aeb0;
  margin-left: 0;
}

.game-warp .setting-box .el-dialog .el-dialog__footer .skip-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #fff;
}

.game-warp .skip-box .el-dialog {
  width: 42rem;
  border-radius: 12px;
}

.game-warp .skip-box .el-dialog .el-dialog__header {
  text-align: center;
}

.game-warp .skip-box .el-dialog .el-dialog__header .el-dialog__title {
  font-weight: 700;
  font-size: 18px;
  line-height: 21px;
  color: #686868 !important;
}

.game-warp .skip-box .el-dialog .el-dialog__body {
}

.game-warp .skip-box .el-dialog .el-dialog__body .personnel-box {
  flex-wrap: wrap;
}

.game-warp .skip-box .el-dialog .el-dialog__body .personnel-box .card {
  width: 20%;
  height: 79px;
  border: 2px solid #eaeaea;
  box-sizing: border-box;
  border-radius: 8px;
  position: relative;
  margin: 10px 15px;
}

.game-warp .skip-box .el-dialog .el-dialog__body .personnel-box .card.checked {
  border: 2px solid #63aeb0;
}

.game-warp
  .skip-box
  .el-dialog
  .el-dialog__body
  .personnel-box
  .card.checked
  .name {
  font-weight: 700;
  color: #63aeb0;
}

.game-warp
  .skip-box
  .el-dialog
  .el-dialog__body
  .personnel-box
  .card
  .text-box {
  text-align: center;
  font-weight: 500;
  font-size: 14px;
  line-height: 20px;
  color: #bebebe;
}

.game-warp .skip-box .el-dialog .el-dialog__body .personnel-box .card .tag {
  font-size: 12px;
  line-height: 17px;
  color: #cacaca;
}

.game-warp
  .skip-box
  .el-dialog
  .el-dialog__body
  .personnel-box
  .card
  .el-badge {
  position: absolute;
  top: 0;
  right: 8px;
}

.game-warp
  .skip-box
  .el-dialog
  .el-dialog__body
  .personnel-box
  .card
  .el-badge__content--primary {
  background-color: #63aeb0;
}

.game-warp .skip-box .el-dialog .el-dialog__footer {
  padding: 0 20px 20px;
}

.game-warp .skip-box .el-dialog .el-dialog__footer .save-btn {
  width: 60%;
  height: 46px;
  background: #63aeb0;
  margin: 0 auto;
  margin-left: 0;
}

.game-warp .skip-box .el-dialog .el-dialog__footer .save-btn span {
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  color: #fff;
}
</style>
