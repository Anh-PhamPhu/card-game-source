<template>
	<div :style="{ height: height, width: width }" :id="id" :userid="userid"></div>
</template>
<script>
	// 引入基本模板
	let echarts = require("echarts/lib/echarts");
	// 引入柱状图组件
	require("echarts/lib/chart/radar");
	require("echarts/lib/component/radar");
	// 引入提示框和title组件
	require("echarts/lib/component/tooltip");
	require("echarts/lib/component/title");
	// require("echarts/lib/component/axis");
	// 不引入这个会报错 xAxis "0" not found
	require("echarts/lib/component/grid");
	// import 'echarts/lib/component/grid';
	import {
		getCharacter
	} from '../api/api.js';
	export default {
		name: "echarts",
		props: {
			height: {
				type: String,
				default: "500px",
			},
			width: {
				type: String,
				default: "500px",
			},
			id: {
				type: String,
				default: "demo",
			},
			userid: {
				type: String,
			}
		},
		data() {
			return {};
		},
		mounted() {
			this.drawLine();
		},
		methods: {
			drawLine() {
				let that = this
				// 基于准备好的dom，初始化echarts实例
				console.log('userid', that.userid);
				getCharacter(that.userid).then((res) => {
					console.log(res)
					if (res.code == 0) {
						console.log("success")
						let myChart = echarts.init(document.getElementById(that.id));
						// 绘制图表
						myChart.setOption({
						  radar:[
						    {
						      triggerEvent: true,
						      // shape: 'circle',
						      radius: 150,//缩放
						      center: ['50%', '50%'],//位置
                  axisName: {
                    formatter: '{value}',
                    color: '#000'
                  },
						      indicator: [{
						          name: '専門',
						          max: 5,
						        },
						        {
						          name: '伝達',
						          max: 5
						        },
						        {
						          name: '自然体',
						          max: 5
						        },
						        {
						          name: '挑戦、問題解決',
						          max: 5
						        },
						        {
						          name: '社会貢献',
						          max: 5
						        },
						        {
						          name: '創造',
						          max: 5
						        },
						        {
						          name: '安心、安定',
						          max: 5
						        },
						        {
						          name: '独自',
						          max: 5
						        },
						        {
						          name: '組織',
						          max: 5
						        },
						      ]
						    },
						  ] ,
						  tooltip: {
						    trigger: 'axis',
						    triggerEvent: true,
						    axisPointer: { // 坐标轴指示器，坐标轴触发有效
						      type: 'line' // 默认为直线，可选为：'line' | 'shadow'
						    },
                textStyle: {
                  color: '#000',  //更改坐标轴文字颜色
                  fontSize : 14      //更改坐标轴文字大小
                }
						  },
						  splitLine: {
						    lineStyle: {
						      color: [
						        'rgba(52, 148, 136, 0.3)', 'rgba(52, 148, 136, 0.4)',
						        'rgba(52, 148, 136, 0.5)', 'rgba(52, 148, 136, 0.7)',
						        'rgba(52, 148, 136, 0.8)', 'rgba(52, 148, 136, 1)'
						      ].reverse()
						    }
						  },
						  series: [{
						    name: '',
						    type: 'radar',
                textStyle: {
                  color: '#000000',  //更改坐标轴文字颜色
                  fontSize : 14      //更改坐标轴文字大小
                },
						    data: [{
						        value: res.result.resultList,
						      }]
						  }]
						});
					}
				})
			},
		},
	};
</script>
