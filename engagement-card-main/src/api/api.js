import request from '../utils/request.js';
/**
 * 登录
 */
export const doLogin = (data) => {
    return request({
        url: '/jeecg-boot/sys/login',
        method: 'post',
        data: data
    })
}

/**
 * 发送邮箱
 */
export const getsendMail = (data) => {
    return request({
        url: '/jeecg-boot/games/email/sendMail',
        method: 'post',
        data: data
    })
}

// 查询邀请但没参加的房间数量

export const getUn = (id) => {
    return request({
        url: '/jeecg-boot/games/rooms/getInviteLog/'+id,
        method: 'get',
    })
}


// 获取记录详情
export const getLogDetail = (id) => {
    return request({
        url: '/jeecg-boot/games/rooms/getGamesRecordDetail?roomId='+id,
        method: 'get',
    })
}

// 添加游戏记录
export const addGameRecord = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/addGamesRecord',
        method: 'post',
		data:data
    })
}

// 游戏结果
export const getCharacter = (id) => {
    return request({
        url: '/jeecg-boot/games/result/getCharacter?cardNumber='+id,
        method: 'get'
    })
}

// 判断最多可选几人
export const chooseMember = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/createRoomPersonCount',
        method: 'post',
        data: data
    })
}

// 第三方登录
export const otherLogin = (token) => {
    return request({
        url: '/jeecg-boot/oauth/getLoginUser/'+token,
        method: 'get',
    })
}

// 取消订阅
export const canceld = (data) => {
    return request({
        url: '/jeecg-boot/v1/cancelSubscription',
        method: 'post',
        data: data
    })
}


// 同步游戏数据接口
export const getSame = (roomid) => {
    return request({
        url: '/jeecg-boot/games/rooms/synchronousData/'+roomid,
        method: 'get',
    })
}

/**
 * 注册
 */
export const doReg = (data) => {
    return request({
        url: '/jeecg-boot/sys/user/register',
        method: 'post',
        data: data
    })
}

/**
 *  修改用户名
 */
 export const editUserName = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/updateUserName',
        method: 'post',
        data: data
    })
}

/**
 * 获取用户信息
 */
export const getUserInfo = (token) => {
    return request({
        url: '/jeecg-boot/sys/user/getUserSectionInfoByToken',
        method: 'get',
        data: {token: token}
    })
}
/**
 * 获取用户信息
 */
export const getUserInfoById = (userId) => {
    return request({
        url: '/jeecg-boot/games/rooms/getUserInfoById/' + userId,
        method: 'get'
    })
}
/**
 * 密码变更
 */
export const changPassword = (data) => {
    return request({
        url: '/jeecg-boot/sys/user/changPassword',
        method: 'put',
        data: data
    })
}
/**
 * 创建房间
 */
export const createRoom = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/createRoom',
        method: 'post',
        data: data
    })
}
/**
 * 获取房间信息
 */
export const getRoomDetail = (roomId) => {
    return request({
        url: '/jeecg-boot/games/rooms/getRoomDetail/' + roomId,
        method: 'get'
    })
}
/**
 * 房间-根据房间url后缀和房主用户id查询房间信息
 */
export const getRoomDetailByUid = (homeowner, code) => {
    return request({
        url: '/jeecg-boot/games/rooms/getRoomDetailByUrlAndHomeowner/' + code + '/' + homeowner,
        method: 'get'
    })
}
/**
 * 获取邀请记录
 */
export const getInvitationRecord = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/getInvitationRecord',
        method: 'post',
        data: data
    })
}
/**
 * 增加邀请记录
 */
export const insertInvitationLog = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/insertInvitationLog',
        method: 'post',
        data: data
    })
}
/**
 * 获取游戏记录
 */
export const getGamesRecord = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/getGamesRecord',
        method: 'post',
        data: data
    })
}
/**
 * 开始游戏
 */
export const startGame = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/insertRoomUser',
        method: 'post',
        data: data
    })
}
/**
 * 游戏操作
 */
export const gamesOperate = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/gamesOperate',
        method: 'post',
        data: data
    })
}
/**
 * 用户结束当前回合
 */
export const noticeUserOperate = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/noticeUserOperate',
        method: 'post',
        data: data
    })
}

// 充值列表

export const plan = (data) => {
    return request({
        url: '/jeecg-boot/v1/productList',
        method: 'post',
        data: data
    })
}

// 创建价格id
export const createPrice = (data) => {
    return request({
        url: '/jeecg-boot/v1/createPrice',
        method: 'post',
        data: data
    })
}

// 创建客户id
export const createCustomer = (data) => {
    return request({
        url: '/jeecg-boot/v1/createCustomer',
        method: 'post',
        data: data
    })
}

// 创建session
export const createSession = (data) => {
    return request({
        url: '/jeecg-boot/v1/createSession',
        method: 'post',
        data: data
    })
}

// 查询订阅状态
export const checkSubscribe = (id) => {
    return request({
        url: '/jeecg-boot/v1/subscriptionSearch?customerId='+id,
        method: 'get',
    })
}

// 设置自定义URL
export const setUrl = (data) => {
    return request({
        url: '/jeecg-boot/sys/user/saveUrl',
        method: 'post',
        data: data
    })
}

// 邮箱验证码
export const sendCode = (data) => {
    return request({
        url: '/jeecg-boot/games/email/sendMailCode',
        method: 'post',
        data: data
    })
}

// 修改密码
export const changepwd = (data) => {
    return request({
        url: '/jeecg-boot/games/rooms/updatePassword',
        method: 'post',
        data: data
    })
}

// 固定URL回显
export const showUrl = (data) => {
    return request({
        url: '/jeecg-boot/sys/user/getUserUrl',
        method: 'get'
    })
}

// 删除房间

export const delRoom = (id) => {
    return request({
        url: '/jeecg-boot/games/rooms/deleteRoom/'+id,
        method: 'delete'
    })
}

