<template>
    <div>
       <dv-border-box-11 title="学生考勤数据报表"><br><br>


<el-row type="flex" class="row-bg" justify="space-around">

  <el-col :span="9"><div>      <!-- 基本轮播表 -->
        <dv-scroll-board :config="configdata"  style="width:500px;height:220px" > 
        </dv-scroll-board></div></el-col>

  <el-col :span="8"><div>    <!-- 单条滚动 -->
        <dv-scroll-ranking-board :config="gundoconfig" style="width:350px;height:250px" /></div></el-col>

  <el-col :span="7"><div>  
       <!-- 使用引入组件 -->
      <NightingaleChart></NightingaleChart></div></el-col>
</el-row>

<!-- 最下方的数据栏 -->
         <div ref="Dom" class="BOX" style="width: 1500px; height: 400px"></div>
        </dv-border-box-11>
    </div>
</template>
<script>
import {getEchartssigninCountSumStatusInfo ,getDatavEchartssigninCountSumStatusInfo,getDatavdatavgetcalzzbanjisumInfo} from "@/api/wxinfo/echarts"; 
import NightingaleChart from '@/views/wxinfo/chartsdatas/nightingalechart';
export default {
    name:'index',
    components:{ NightingaleChart },
    data() {
        return {
            // 滚动栏
                    header: ['班级名称', '签到类型', '签到数量'], //头部数据
                    configdata:{
                    data: [],
                    index: true,
                    columnWidth: [50],
                    align: ['center']
                    },
                    //  班级总数
                 gundoconfig: {
                data: [],
            }
        }
    },
    mounted() {
        this.optionEcharts();//单独初始化 最下方的可视化
                // 返回班级考勤的数据信息
        getDatavEchartssigninCountSumStatusInfo().then((response) => {
            this.configdata = { data: response.data,header:this.header,index: true,columnWidth: [50],align: ['center']}
                    console.log(response.data)
        })
                // 统计班级总人数进行排行
        getDatavdatavgetcalzzbanjisumInfo().then((response) => {
            this.gundoconfig = { data: response.data}
                    console.log(response.data)
        })
    },
    methods: {
          optionEcharts(){
          //获取Dom节点
            let chartDom = this.$refs.Dom;
            //初始化echarts实例
            let myChart = this.$echarts.init(chartDom);
            //绘制图标
            let option;
            //数据
            option = {
                   tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#999'
                        }
                        }
                    },
                    toolbox: {
                        feature: {
                        dataView: { show: true, readOnly: false },
                        magicType: { show: true, type: ['line', 'bar'] },
                        restore: { show: true },
                        saveAsImage: { show: true }
                        }
                    },
                    legend: {
                        data: []
                    },
                    xAxis: [
                        {
                        type: 'category',
                        data: [],
                        axisPointer: {
                            type: 'shadow'
                        }
                        }
                    ],
                    yAxis: [
                        {
                        type: 'value',
                        name: '',
                        min: 0,
                        max: 100,
                        interval: 50,
                        axisLabel: {
                            formatter: '{value} 人数'
                        }
                        },
                        {
                        type: 'value',
                        name: '',
                        min: 0,
                        max: 25,
                        interval: 5,
                        axisLabel: {
                            formatter: '{value} 人数'
                        }
                        }
                    ],
                    series: [
                        {
                        name: '签到类型',
                        type: 'bar',
                        barWidth: 80,  // 根据实际情况设置合适的值
                        barGap: 0,
                        barCategoryGap: 0,
                        tooltip: {
                            valueFormatter: function (value) {
                            return value;
                            }
                        },
                        data: [  ]
                        },
                      
                        {
                        name: '签到数据',
                        type: 'line',
                        yAxisIndex: 1,
                        tooltip: {
                            valueFormatter: function (value) {
                            return value + ' 人数';
                            }
                        },
                        data: []
                        }
                    ]
            },

                //   获取每一个具体班级签到的数据信息
                getEchartssigninCountSumStatusInfo().then((response) => {
                response.data.forEach((item) => {
                    option.legend.data.push(item.signin_info);
                    option.series[0].data.push(item.signin_info);
                    option.series[1].data.push(item.countsum); //数量
                    // option.series[1].data.push({ value: item.JF }); //这个对象的写法
                    option.xAxis[0].data.push(item.signin_clazzname); //时间
                });
                option && myChart.setOption(option);
                });
          },
    },
}
</script>
<style scoped>
 .dv-scroll-board {
    position: relative;
    padding: 3px;
    margin-left: 60px;
    margin-top:47px;
    color:  #FFFFFF !important;
} 
/*   滚动样式css */
.dv-scroll-ranking-board {
        position:relative !important;
    width: 100%;
    height: 100%;
    color: #3d6e53 !important;
       margin-top:47px;
     margin-left: 60px;
    overflow: hidden;
}

  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }


</style>
