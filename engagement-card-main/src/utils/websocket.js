let Socket = ''
let setIntervalWesocketPush = null
let status = localStorage.getItem("gamestatus")
var wsUrl = ''
/**
 * 建立websocket连接
 * @param {string} url ws地址
 */
export const createSocket = (url) => {
    wsUrl = url
    Socket && Socket.close()
    console.log("Socketcon",Socket)
    if (!Socket) {
        console.log('websocket create')
        Socket = new WebSocket(url)
        Socket.onopen    = onopenWS
        Socket.onmessage = onmessageWS
        Socket.onerror   = onerrorWS
        Socket.onclose   = oncloseWS
    } else {
        console.log('websocket created')
    }
}


/**打开WS之后发送心跳 */
const onopenWS = () => {
    // sendPing()
    console.log("socketOpen")
}

/**连接失败重连 */
const onerrorWS = (error) => {
    // Socket.close()
    console.log("error",error)
    clearInterval(setIntervalWesocketPush)
    console.log('Connection failed reconnecting')
    // if (Socket.readyState !== 3) {
    //     Socket = null
    //     createSocket(wsUrl)
    //     // console.log("")
    // }
}

/**WS数据接收统一处理 */
const onmessageWS = e => {
    // console.log(e.data)
    window.dispatchEvent(new CustomEvent('onmessageWS', {
        detail: {
            data: e.data
        }
    }))
}

/**
 * 发送数据但连接未建立时进行处理等待重发
 * @param {any} message 需要发送的数据
 */
const connecting = message => {
    setTimeout(() => {
        if (Socket.readyState === 0) {
            connecting(message)
        }

        // else {
        //     Socket.send(JSON.stringify(message))
        // }
    }, 1000)
}

/**
 * 发送数据
 * @param {any} message 需要发送的数据
 */
export const sendWSPush = message => {
    console.log("SocketStatus",Socket.readyState)
    if (Socket !== null && Socket.readyState === 3) {
        // Socket.close()
        // createSocket()
    } else if (Socket.readyState === 1) {
        Socket.send(JSON.stringify(message))
    } else if (Socket.readyState === 0) {
        connecting(message)
    }
}
// socket断开
export const WSClose = message => {
    // Socket.close()
    console.log("主动断开socket")
    Socket = null
}

// 重连
// export const WSreconnect = message => {
//     // Socket.close()
// 	if (Socket.readyState !== 2) {
// 	    Socket = null
// 	    createSocket(wsUrl)
// 	}
// }

/**断开重连 */
const oncloseWS = (e) => {
  console.log("socket断开了")
  console.log(e)
  // Socket = null
	// Socket.close()
   clearInterval(setIntervalWesocketPush)
    console.log('Websocket disconnected....Attempting to reconnect')
    console.log(Socket.readyState)
    if (Socket.readyState !== 2) {
        Socket = null
        createSocket(wsUrl)
        // window.history.go(0)
    }
}
/**发送心跳
 * @param {number} time 心跳间隔毫秒 默认5000
 * @param {string} ping 心跳名称 默认字符串ping
 */
export const sendPing = (time = 5000, ping = 'ping') => {
    clearInterval(setIntervalWesocketPush)
    setIntervalWesocketPush = setInterval(() => {
        Socket.send(ping)
    }, time)
}
