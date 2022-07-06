<template> </template>

<script>
  const wsBaseUrl = 'ws://cardgame.unismart.dev/jeecg-boot/websocket/';
  const region = [
      '北海道',
      '青森県',
      '岩手県',
      '宮城県',
      '秋田県',
      '山形県',
      '福島県',
      '茨城県',
      '栃木県',
      '群馬県',
      '埼玉県',
      '千葉県',
      '東京都',
      '神奈川県',
      '新潟県',
      '富山県',
      '石川県',
      '福井県',
      '山梨県',
      '長野県',
      '岐阜県',
      '静岡県',
      '愛知県',
      '三重県',
      '滋賀県',
      '京都府',
      '大阪府',
      '兵庫県',
      '奈良県',
      '和歌山県',
      '鳥取県',
      '島根県',
      '岡山県',
      '広島県',
      '山口県',
      '徳島県',
      '香川県',
      '愛媛県',
      '高知県',
      '福岡県',
      '佐賀県',
      '長崎県',
      '熊本県',
      '大分県',
      '宮崎県',
      '鹿児島県',
      '沖縄県'
  ]
  const sexBox = [
      '秘密',
      '男性',
      '女性'
  ]
  const tags = ['一番手', '二番手', '三番手', '四番手', '五番手', '六番手']
  // 随机字符串
  const randomString = (len) => {
      len = len || 6;
      /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
      var $chars = 'ABCDEFGHJKMNPQRSTWXYZ12345678';
      var maxLen = $chars.length;
      var str = '';
      for (var i = 0; i < len; i++) {
          str += $chars.charAt(Math.floor(Math.random() * maxLen));
      }
      return str;
  }
  // 转时间戳
  const strToTime = (time) => {
    let date = new Date(time)
    let newTime = Date.parse(date) / 1000
    return newTime
  }
  // 时间格式化
  const timeFormat = (value) => {
    if (value) {
      let add0 = (m) => {
        return m < 10 ? '0' + m : m
      }
      var time = new Date(value)
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      var h = time.getHours()
      var mm = time.getMinutes()
      var s = time.getSeconds()
      // return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s)
      return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm)
    } else {
      return ''
    }
  }

  const getHttps = () => {
      const ishttps = 'https:' == document.location.protocol ? true : false
      var isUrl = 'https'
      if(ishttps) {
        isUrl = 'https'
      } else {
        isUrl = 'http'
      }
      return isUrl
  }
export default
{
  wsBaseUrl,
  region,
  sexBox,
  tags,
  randomString,
  strToTime,
  timeFormat,
  getHttps
}
</script>
