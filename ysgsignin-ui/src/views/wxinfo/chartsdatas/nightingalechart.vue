<<template>
  <div>
     <div ref="Dom" class="BOX" style="width: 400px; height: 300px"></div>
  </div>
</template>
<script>
import {getDatavdatavgetcalzzbanjisumInfo} from "@/api/wxinfo/echarts"; 
export default {
    name:'nightingalechart',
  data() {
    return {

    }
  },
  mounted(){
  this.optionEcharts();
  },
  methods:{
        optionEcharts(){
            //获取Dom节点
            let chartDom = this.$refs.Dom;
            //初始化echarts实例
            let myChart = this.$echarts.init(chartDom);
            //绘制图标
            let option;
            option = {
                    legend: {
                        top: 'bottom'
                    },
                    toolbox: {
                        show: true,
                        feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                        }
                    },
                      tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c}人 ({d}%)'
                        },
                    series: [
                        {
                        name: '   班级名称       各班级总人数',
                        type: 'pie',
                        radius: [20, 70],
                        center: ['50%', '40%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                     
                        data: [ ]
                        }
                    ]
                };

                       //   获取每一个具体班级签到的数据信息
                getDatavdatavgetcalzzbanjisumInfo().then((response) => {
                response.data.forEach((item) => {
                    option.series[0].data.push({ name:item.name,value: item.value }); //这个对象的写法
          
                });
                option && myChart.setOption(option);
                });

        }

  },
}
</script>
<style scoped>

</style>
