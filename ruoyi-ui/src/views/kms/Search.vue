<template>
  <section class="wrapper">
    <div class="content" style="padding-top: 60px;">
      <div class="mainwidth">
        <div class="logoimg" style=" margin-bottom: 0px;">
          <div
            style="float:left;margin-left:30%;margin-right:0px; font-size: 60px;color:#4b71aa; font-weight: bold;font-style:oblique; line-height: 60px;">
            <a href="http://www.ilas.com.cn/" target="_balnk" style="text-decoration:none;color:#4b71aa;">KMS</a>
          </div>
          <div style="float:left;color:#4b71aa; margin-left: 20px;">
            <div
              style="font-size: 25px;color:#4b71aa; font-weight: bold; margin-left: 102px; line-height: 25px;margin-top: 10px; ">
              知 识 平 台 检 索
            </div>
            <div style="color:#4b71aa;font-style:oblique;  margin-top: 0px; padding-top: 0px;">
              Total branch retrieval knowledge platform
            </div>
          </div>
          <div style="clear: both;"></div>
        </div>
        <!-- logo  end-->

        <!--mian    Start-->
        <div style="margin: 0 auto;width: 75%;">
          <el-form ref="form" :model="form" label-width="90px">
            <div class="literature_index">
              <div>
                <div class="searchbox">
                  <el-form-item label="检索条件">
                    <el-col :span="6">
                      <el-select v-model="form.searchKey" placeholder="请选择" size="small">
                        <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-col>
                    <el-col :span="13">
                      <el-input v-model="form.searchValue" placeholder="请输入关键字" style="width:96%"
                                size="small"></el-input>
                    </el-col>
                    <el-col :span="5">
                      <input value="检索" class="vipsearch" type="button" @click="searchList">
                      <input type="button" onclick="java:alert('还未建设，你有兴趣参与吗')" class="vipsearch"
                             value="高级检索 ">
                    </el-col>
                  </el-form-item>
                </div>

                <div class="searchbox">
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item label="每页显示">
                        <el-select v-model="form.pageSize" placeholder="请选择" size="small">
                          <el-option
                            v-for="item in pageOpt"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="排序选项">
                        <el-select v-model="form.sortBy" placeholder="请选择" size="small">
                          <el-option
                            v-for="item in sortByOpt"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="排序方式">
                        <el-select v-model="form.sortType" placeholder="请选择" size="small">
                          <el-option
                            v-for="item in sortTypeOpt"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>

                <div class="searchbox">
                  <el-form-item label="检索方式">
                    <el-col :span="6">
                      <el-select v-model="form.searchBy" placeholder="请选择" size="small">
                        <el-option
                          v-for="item in searchByOpt"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-col>
                  </el-form-item>
                </div>

              </div>
            </div>
          </el-form>

          <div class="index_showinfo" v-if="mainList.length===0">
            <div class="MoveRight" style="border:1px solid #dbf1fb;width: 650px; padding: 10px;">
              <div class="title1"><i class="t-ico1;"></i><em>热搜词&nbsp;&nbsp;</em></div>
              <!--<div class="tagcloud-all" ref="tagcloudall">
                <a v-for="item in tagList" :href="item.url"
                   class="tagc1" :title="item.key"
                   style="left: 229.18px; top: 122.161px; z-index: 239; opacity: 4.80967;">
                  <font color="#1f95df">{{item.key}}({{item.recno}})</font>
                </a>
              </div>-->
              <div class="floatTxt" id="tagscloud">
                <div ref="tagcloudall">
                  <a v-for="item in tagList" :href="item.url"
                     class="tagc1" :title="item.key"
                     style="left: 0px; top: 0px; z-index: 239; opacity: 4.80967;">
                    <font color="#1f95df">{{ item.key }}({{ item.recno }})</font>
                  </a>
                </div>
              </div>
            </div>
            <div class="sssss"></div>
            <div class="checktips"
                 style="background:#e6f5fc;border:1px solid #dbf1fb;width: 650px; margin-left:0px; padding: 10px;border-radius:4px;">
              <span class="tipstitle">温馨提示：</span>
              <ul class="tipslist">
                <li><span>仿ILAS知识平台检索，若涉及侵权，联系我删除</span>
                </li>
              </ul>
            </div>
          </div>

          <list :list="mainList" v-if="mainList"></list>
        </div>
        <!-- mian  end-->
      </div>
    </div>
  </section>
</template>

<script>
import {listForEs} from "@/api/kms/main";
import List from "./List";

export default {
  name: "Search",
  components: {List},
  data() {
    return {
      radius: 140,
      d: 360,
      dtr: Math.PI / 180,
      sa: 0,
      ca: 0,
      sb: 0,
      cb: 0,
      sc: 0,
      cc: 0,
      mcList: [],
      lasta: 1,
      lastb: 1,
      distr: true,
      tspeed: 8,
      size: 250,
      mouseX: 0,
      mouseY: 10,
      howElliptical: 1,

      tagList: [],
      oList: null,
      oA: null,

      form: {
        searchKey: 'title',
        searchValue: '',
        pageSize: 10,
        sortBy: 'createTime',
        sortType: 1,
        searchBy: 1
      },
      searchKey: '',
      options: [
        {
          value: 'title',
          label: '知识标题'
        },
        {
          value: 'mainContent',
          label: '正文'
        },
        {
          value: 'author',
          label: '作者'
        }
      ],
      pageOpt: [
        {
          value: 10,
          label: '10'
        },
        {
          value: 20,
          label: '20'
        },
        {
          value: 30,
          label: '30'
        },
        {
          value: 50,
          label: '50'
        }
      ],
      sortByOpt: [
        {
          value: 'publishDate',
          label: '发布时间'
        },
        {
          value: 'createTime',
          label: '创建时间'
        },
        /*{
            value: '30',
            label: '30'
          },
          {
            value: '50',
            label: '50'
          }*/
      ],
      sortTypeOpt: [
        {
          value: 1,
          label: '降序排列'
        },
        {
          value: 2,
          label: '升序排列'
        }
      ],
      searchByOpt: [
        {
          value: 1,
          label: '精确查询'
        },
        {
          value: 2,
          label: '模糊匹配'
        }
      ],
      mainList: [],
      total: 0,
      loading: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.query();
    })
  },
  methods: {
    // 生成标签云
    onReady() {
      this.$nextTick(() => {
        this.oList = this.$refs.tagcloudall;
        this.oA = this.oList.getElementsByTagName('a')
        var oTag = null;
        for (var i = 0; i < this.oA.length; i++) {
          oTag = {};
          this.oA[i].onmouseover = (function (obj) {
            return function () {
              obj.on = true;
              this.style.zIndex = 9999;
              this.style.color = '#fff';
              this.style.padding = '5px 5px';
              this.style.filter = "alpha(opacity=100)";
              this.style.opacity = 1;
            }
          })(oTag)
          this.oA[i].onmouseout = (function (obj) {
            return function () {
              obj.on = false;
              this.style.zIndex = obj.zIndex;
              this.style.color = '#fff';
              this.style.padding = '5px';
              this.style.filter = "alpha(opacity="
                + 100 * obj.alpha + ")";
              this.style.opacity = obj.alpha;
              this.style.zIndex = obj.zIndex;
            }
          })(oTag)
          oTag.offsetWidth = this.oA[i].offsetWidth;
          oTag.offsetHeight = this.oA[i].offsetHeight;
          this.mcList.push(oTag);
        }
        this.sineCosine(0, 0, 0);
        this.positionAll();
        setInterval(() => {
          this.update()
        }, 40);            // 定时器执行 不能写setInterval(this.update(), 30)
      })
    },
    // 网络请求 拿到tagList
    query() {
      // 假装从接口拿回来的数据
      let tagListOrg = [
        {
          "recno": 112307,
          "calss": "1001",
          "type": "text",
          "key": "人",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106609,
          "calss": "1001",
          "type": "callno",
          "key": "D6",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83401,
          "calss": "1001",
          "type": "publish",
          "key": "人民美术出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69625,
          "calss": "1001",
          "type": "callno",
          "key": "I",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61560,
          "calss": "1001",
          "type": "text",
          "key": "公司",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43414,
          "calss": "1001",
          "type": "callno",
          "key": "TS972",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42176,
          "calss": "1001",
          "type": "callno",
          "key": "D5",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 16553,
          "calss": "1001",
          "type": "text",
          "key": "家族",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 16521,
          "calss": "1001",
          "type": "text",
          "key": "%E4%BA%BA",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 15908,
          "calss": "1001",
          "type": "callno",
          "key": "H125.4",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 14093,
          "calss": "1001",
          "type": "text",
          "key": "出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 13789,
          "calss": "1001",
          "type": "publish",
          "key": "机械工业出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 13607,
          "calss": "1001",
          "type": "callno",
          "key": "R2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 9986,
          "calss": "1001",
          "type": "author",
          "key": "新华通讯社",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 8871,
          "calss": "1001",
          "type": "text",
          "key": "%E5%85%AC%E5%8F%B8",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 8652,
          "calss": "1001",
          "type": "subject",
          "key": "长篇小说",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 3590,
          "calss": "1001",
          "type": "name",
          "key": "中国烹饪",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 3363,
          "calss": "1001",
          "type": "text",
          "key": "机械工业出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 3323,
          "calss": "1001",
          "type": "text",
          "key": "南海出版公司",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 2851,
          "calss": "1001",
          "type": "publish",
          "key": "北京出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 2701,
          "calss": "1001",
          "type": "text",
          "key": "山西人民出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 2599,
          "calss": "1001",
          "type": "name",
          "key": "天文爱好者",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 2491,
          "calss": "1001",
          "type": "text",
          "key": "%E5%AE%B6%E6%97%8F",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 2117,
          "calss": "1001",
          "type": "name",
          "key": "爱",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1939,
          "calss": "1001",
          "type": "text",
          "key": "F299.233",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1876,
          "calss": "1001",
          "type": "text",
          "key": "F830.59-49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1678,
          "calss": "1001",
          "type": "text",
          "key": "当代世界出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1525,
          "calss": "1001",
          "type": "callno",
          "key": "I287.7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1511,
          "calss": "1001",
          "type": "isbn",
          "key": "7",
          "curdate": "20220507",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1420,
          "calss": "1001",
          "type": "name",
          "key": "人",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1220,
          "calss": "1001",
          "type": "text",
          "key": "I",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1216,
          "calss": "1001",
          "type": "text",
          "key": "安徽师范大学出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1198,
          "calss": "1001",
          "type": "text",
          "key": "H125.4",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 1097,
          "calss": "1001",
          "type": "name",
          "key": "汉语",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 980,
          "calss": "1001",
          "type": "callno",
          "key": "F71",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 972,
          "calss": "1001",
          "type": "name",
          "key": "三国演义",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 940,
          "calss": "1001",
          "type": "text",
          "key": "F069.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 861,
          "calss": "1001",
          "type": "text",
          "key": "汉语",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 858,
          "calss": "1001",
          "type": "text",
          "key": "国际贸易",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 849,
          "calss": "1001",
          "type": "text",
          "key": "H",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 774,
          "calss": "1001",
          "type": "classno",
          "key": "*.*",
          "curdate": "20220531",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 772,
          "calss": "1001",
          "type": "callno",
          "key": "I712.45",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 737,
          "calss": "1001",
          "type": "isbn",
          "key": "10027335",
          "curdate": "20220503",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 712,
          "calss": "1001",
          "type": "text",
          "key": "爱",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 679,
          "calss": "1001",
          "type": "name",
          "key": "史记",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 660,
          "calss": "1001",
          "type": "text",
          "key": "I247.57",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 654,
          "calss": "1001",
          "type": "isbn",
          "key": "04932285",
          "curdate": "20220504",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 630,
          "calss": "1001",
          "type": "name",
          "key": "昆虫记",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 600,
          "calss": "1001",
          "type": "text",
          "key": "经济理论",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 591,
          "calss": "1001",
          "type": "text",
          "key": "经济危机",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 582,
          "calss": "1001",
          "type": "text",
          "key": "对外经济贸易大学出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 577,
          "calss": "1001",
          "type": "text",
          "key": "中国人口出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 561,
          "calss": "1001",
          "type": "name",
          "key": "米小圈上学记",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 557,
          "calss": "1001",
          "type": "callno",
          "key": "I247.5",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 551,
          "calss": "1001",
          "type": "name",
          "key": "心理学",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 544,
          "calss": "1001",
          "type": "text",
          "key": "I712.45",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 544,
          "calss": "1001",
          "type": "callno",
          "key": "I2",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 526,
          "calss": "1001",
          "type": "text",
          "key": "城市经济",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 502,
          "calss": "1001",
          "type": "text",
          "key": "%E5%8D%97%E6%B5%B7%E5%87%BA%E7%89%88%E5%85%AC%E5%8F%B8",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 484,
          "calss": "1001",
          "type": "name",
          "key": "童年",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 473,
          "calss": "1001",
          "type": "text",
          "key": "女性",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 470,
          "calss": "1001",
          "type": "author",
          "key": "世界知识出版社主办",
          "curdate": "20220809",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 457,
          "calss": "1001",
          "type": "name",
          "key": "木偶奇遇记",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 452,
          "calss": "1001",
          "type": "name",
          "key": "漫画",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 442,
          "calss": "1001",
          "type": "text",
          "key": "I267",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 423,
          "calss": "1001",
          "type": "name",
          "key": "西游记",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 411,
          "calss": "1001",
          "type": "text",
          "key": "黄蓓佳著",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 410,
          "calss": "1001",
          "type": "publish",
          "key": "北京联合出版公司",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 403,
          "calss": "1001",
          "type": "text",
          "key": "%E5%B1%B1%E8%A5%BF%E4%BA%BA%E6%B0%91%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 397,
          "calss": "1001",
          "type": "callno",
          "key": "B848.4-49",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 397,
          "calss": "1001",
          "type": "text",
          "key": "中国纺织出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 391,
          "calss": "1001",
          "type": "callno",
          "key": "I247.59",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 368,
          "calss": "1001",
          "type": "name",
          "key": "斗罗大陆",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 366,
          "calss": "1001",
          "type": "subject",
          "key": "童话",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 360,
          "calss": "1001",
          "type": "callno",
          "key": "P7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 351,
          "calss": "1001",
          "type": "text",
          "key": "I287.7",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 351,
          "calss": "1001",
          "type": "text",
          "key": "政治经济学：社会主义部分",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 351,
          "calss": "1001",
          "type": "name",
          "key": "女性",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 330,
          "calss": "1001",
          "type": "text",
          "key": "(1959",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 325,
          "calss": "1001",
          "type": "text",
          "key": "土地规划",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 320,
          "calss": "1001",
          "type": "name",
          "key": "英语",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 318,
          "calss": "1001",
          "type": "callno",
          "key": "I247.7",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 315,
          "calss": "1001",
          "type": "text",
          "key": "长篇小说",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 314,
          "calss": "1001",
          "type": "publish",
          "key": "上海科学技术出版社",
          "curdate": "20220716",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 313,
          "calss": "1001",
          "type": "name",
          "key": "寻宝记",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 305,
          "calss": "1001",
          "type": "text",
          "key": "I247.59",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 296,
          "calss": "1001",
          "type": "name",
          "key": "中国文学",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 291,
          "calss": "1001",
          "type": "text",
          "key": "Z228.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 271,
          "calss": "1001",
          "type": "text",
          "key": "心理学",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 270,
          "calss": "1001",
          "type": "text",
          "key": "P7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 270,
          "calss": "1001",
          "type": "publish",
          "key": "北京科学技术出版社",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 269,
          "calss": "1001",
          "type": "callno",
          "key": "I267",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 268,
          "calss": "1001",
          "type": "text",
          "key": "B82",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 267,
          "calss": "1001",
          "type": "text",
          "key": "史记",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 265,
          "calss": "1001",
          "type": "text",
          "key": "%E6%9C%BA%E6%A2%B0%E5%B7%A5%E4%B8%9A%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 264,
          "calss": "1001",
          "type": "callno",
          "key": "H",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 264,
          "calss": "1001",
          "type": "text",
          "key": "I247.5",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 259,
          "calss": "1001",
          "type": "name",
          "key": "查理九世",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 257,
          "calss": "1001",
          "type": "text",
          "key": "本书编写组编",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 245,
          "calss": "1001",
          "type": "text",
          "key": "(1949",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 241,
          "calss": "1001",
          "type": "text",
          "key": "F112.7",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 238,
          "calss": "1001",
          "type": "text",
          "key": "K827=6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 236,
          "calss": "1001",
          "type": "text",
          "key": "北京联合出版公司",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 233,
          "calss": "1001",
          "type": "text",
          "key": "7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 231,
          "calss": "1001",
          "type": "text",
          "key": "B848.4-49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 231,
          "calss": "1001",
          "type": "text",
          "key": "TS976.31",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 230,
          "calss": "1001",
          "type": "callno",
          "key": "F471.266",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 229,
          "calss": "1001",
          "type": "publish",
          "key": "湖南少年儿童出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 228,
          "calss": "1001",
          "type": "text",
          "key": "%E5%BD%93%E4%BB%A3%E4%B8%96%E7%95%8C%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 222,
          "calss": "1001",
          "type": "author",
          "key": "(1949",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 220,
          "calss": "1001",
          "type": "text",
          "key": "F740",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 219,
          "calss": "1001",
          "type": "text",
          "key": "教育",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 216,
          "calss": "1001",
          "type": "name",
          "key": "哈利波特",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 215,
          "calss": "1001",
          "type": "text",
          "key": "R",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 213,
          "calss": "1001",
          "type": "text",
          "key": "科学技术出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 212,
          "calss": "1001",
          "type": "callno",
          "key": "I565",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 209,
          "calss": "1001",
          "type": "text",
          "key": "K825.46",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 208,
          "calss": "1001",
          "type": "text",
          "key": "科学知识",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 207,
          "calss": "1001",
          "type": "text",
          "key": "青海人民出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 201,
          "calss": "1001",
          "type": "text",
          "key": "R212",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 201,
          "calss": "1001",
          "type": "callno",
          "key": "C912.1",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 196,
          "calss": "1001",
          "type": "name",
          "key": "植物大战僵尸",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 194,
          "calss": "1001",
          "type": "text",
          "key": "J228.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 193,
          "calss": "1001",
          "type": "text",
          "key": "珠海出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 192,
          "calss": "1001",
          "type": "callno",
          "key": "I247.57",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 192,
          "calss": "1001",
          "type": "publish",
          "key": "二十一世纪出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 189,
          "calss": "1001",
          "type": "callno",
          "key": "N1",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 189,
          "calss": "1001",
          "type": "text",
          "key": "二战",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 187,
          "calss": "1001",
          "type": "classno",
          "key": "I712.45",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 186,
          "calss": "1001",
          "type": "text",
          "key": "轻工业出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 185,
          "calss": "1001",
          "type": "author",
          "key": "(195",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 184,
          "calss": "1001",
          "type": "text",
          "key": "会议",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 183,
          "calss": "1001",
          "type": "text",
          "key": "[不详]",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 182,
          "calss": "1001",
          "type": "text",
          "key": "F743",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 181,
          "calss": "1001",
          "type": "text",
          "key": "王俊",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 180,
          "calss": "1001",
          "type": "text",
          "key": "木偶奇遇记",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 180,
          "calss": "1001",
          "type": "text",
          "key": "青岛出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 178,
          "calss": "1001",
          "type": "text",
          "key": "南京大学出版社",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 178,
          "calss": "1001",
          "type": "author",
          "key": "(196",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 177,
          "calss": "1001",
          "type": "text",
          "key": "王洪",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 176,
          "calss": "1001",
          "type": "text",
          "key": "克里斯蒂",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 172,
          "calss": "1001",
          "type": "text",
          "key": "知识产权出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 171,
          "calss": "1001",
          "type": "text",
          "key": "东方",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 171,
          "calss": "1001",
          "type": "text",
          "key": "西苑出版社",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 170,
          "calss": "1001",
          "type": "text",
          "key": "武器",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 170,
          "calss": "1001",
          "type": "text",
          "key": "未来出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 170,
          "calss": "1001",
          "type": "text",
          "key": "9787515342238",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 170,
          "calss": "1001",
          "type": "name",
          "key": "我的世界",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 169,
          "calss": "1001",
          "type": "text",
          "key": "%E5%AE%89%E5%BE%BD%E5%B8%88%E8%8C%83%E5%A4%A7%E5%AD%A6%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 168,
          "calss": "1001",
          "type": "name",
          "key": "老人与海",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 168,
          "calss": "1001",
          "type": "callno",
          "key": "K815",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 166,
          "calss": "1001",
          "type": "text",
          "key": "洞天",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 164,
          "calss": "1001",
          "type": "name",
          "key": "海底两万里",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 164,
          "calss": "1001",
          "type": "text",
          "key": "国防工业出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 163,
          "calss": "1001",
          "type": "text",
          "key": "国学",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 162,
          "calss": "1001",
          "type": "text",
          "key": "世界观",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 161,
          "calss": "1001",
          "type": "publish",
          "key": "中国社会科学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 159,
          "calss": "1001",
          "type": "text",
          "key": "F420",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 158,
          "calss": "1001",
          "type": "callno",
          "key": "H1",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 158,
          "calss": "1001",
          "type": "text",
          "key": "亲亲我的妈妈",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 158,
          "calss": "1001",
          "type": "text",
          "key": "法律",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 157,
          "calss": "1001",
          "type": "text",
          "key": "优雅",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 156,
          "calss": "1001",
          "type": "text",
          "key": "B821-49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 156,
          "calss": "1001",
          "type": "text",
          "key": "长春出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 155,
          "calss": "1001",
          "type": "text",
          "key": "Z",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 154,
          "calss": "1001",
          "type": "text",
          "key": "鹭江出版社",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 154,
          "calss": "1001",
          "type": "publish",
          "key": "浙江教育出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 154,
          "calss": "1001",
          "type": "text",
          "key": "太白文艺出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 153,
          "calss": "1001",
          "type": "text",
          "key": "G782",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 150,
          "calss": "1001",
          "type": "text",
          "key": "I247.8",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 149,
          "calss": "1001",
          "type": "text",
          "key": "(1953",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 149,
          "calss": "1001",
          "type": "name",
          "key": "米小圈",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 146,
          "calss": "1001",
          "type": "text",
          "key": "长征出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 146,
          "calss": "1001",
          "type": "text",
          "key": "杭州出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 146,
          "calss": "1001",
          "type": "text",
          "key": "广东人民出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 146,
          "calss": "1001",
          "type": "text",
          "key": "F115",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 146,
          "calss": "1001",
          "type": "callno",
          "key": "R",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 145,
          "calss": "1001",
          "type": "text",
          "key": "本书编",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 142,
          "calss": "1001",
          "type": "text",
          "key": "上海远东出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 142,
          "calss": "1001",
          "type": "text",
          "key": "(196",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 141,
          "calss": "1001",
          "type": "name",
          "key": "水浒传",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 141,
          "calss": "1001",
          "type": "text",
          "key": "9787550279568",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 140,
          "calss": "1001",
          "type": "text",
          "key": "CNY1.75",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 140,
          "calss": "1001",
          "type": "text",
          "key": "漫画",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 138,
          "calss": "1001",
          "type": "text",
          "key": "摩根",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 138,
          "calss": "1001",
          "type": "text",
          "key": "水利电力出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 138,
          "calss": "1001",
          "type": "name",
          "key": "二战",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 137,
          "calss": "1001",
          "type": "text",
          "key": "K825.2",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 137,
          "calss": "1001",
          "type": "text",
          "key": "TS972.12",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 135,
          "calss": "1001",
          "type": "text",
          "key": "(198",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 135,
          "calss": "1001",
          "type": "text",
          "key": "9787532588244",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 134,
          "calss": "1001",
          "type": "callno",
          "key": "TP393",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 134,
          "calss": "1001",
          "type": "text",
          "key": "B821",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 134,
          "calss": "1001",
          "type": "text",
          "key": "R247.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 133,
          "calss": "1001",
          "type": "name",
          "key": "三体",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 133,
          "calss": "1001",
          "type": "text",
          "key": "G634.333",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 133,
          "calss": "1001",
          "type": "text",
          "key": "斗罗大陆",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 133,
          "calss": "1001",
          "type": "text",
          "key": "平",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 132,
          "calss": "1001",
          "type": "text",
          "key": "K928.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 132,
          "calss": "1001",
          "type": "text",
          "key": "今天我是升旗手",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 131,
          "calss": "1001",
          "type": "name",
          "key": "奥特曼",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 131,
          "calss": "1001",
          "type": "text",
          "key": "广西科学技术出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 131,
          "calss": "1001",
          "type": "subject",
          "key": "人生哲学",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 131,
          "calss": "1001",
          "type": "text",
          "key": "见证",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 130,
          "calss": "1001",
          "type": "text",
          "key": "F832.755-54",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 130,
          "calss": "1001",
          "type": "text",
          "key": "F127.554",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 130,
          "calss": "1001",
          "type": "text",
          "key": "B848.4",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 128,
          "calss": "1001",
          "type": "text",
          "key": "J238.2",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 128,
          "calss": "1001",
          "type": "text",
          "key": "I16",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 128,
          "calss": "1001",
          "type": "text",
          "key": "上海大学出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 126,
          "calss": "1001",
          "type": "text",
          "key": "东南大学出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 126,
          "calss": "1001",
          "type": "callno",
          "key": "C93",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 126,
          "calss": "1001",
          "type": "text",
          "key": "吉",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 125,
          "calss": "1001",
          "type": "text",
          "key": "昆虫记",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 123,
          "calss": "1001",
          "type": "text",
          "key": "9787213054303",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 122,
          "calss": "1001",
          "type": "classno",
          "key": "I247.57",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "text",
          "key": "K827=73",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "callno",
          "key": "K837.125.",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "name",
          "key": "稻草人",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "text",
          "key": "%E5%9B%BD%E9%99%85%E8%B4%B8%E6%98%93",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "callno",
          "key": "J6",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "text",
          "key": "湖南文艺出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "publish",
          "key": "人民文学出版社",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 121,
          "calss": "1001",
          "type": "text",
          "key": "汉语大词典出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 120,
          "calss": "1001",
          "type": "text",
          "key": "(英)特德·休斯著；(英)雷蒙德·布里格斯绘",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 120,
          "calss": "1001",
          "type": "text",
          "key": "R289.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 120,
          "calss": "1001",
          "type": "text",
          "key": "陕西旅游出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 119,
          "calss": "1001",
          "type": "text",
          "key": "K825.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 119,
          "calss": "1001",
          "type": "text",
          "key": "发动机",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 119,
          "calss": "1001",
          "type": "author",
          "key": "(198",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 119,
          "calss": "1001",
          "type": "text",
          "key": "阿姆斯特朗",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 118,
          "calss": "1001",
          "type": "text",
          "key": "中国文史出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 117,
          "calss": "1001",
          "type": "text",
          "key": "Z121.7",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 117,
          "calss": "1001",
          "type": "text",
          "key": "小书虫读经典工作室编著",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 116,
          "calss": "1001",
          "type": "text",
          "key": "(1964",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 116,
          "calss": "1001",
          "type": "name",
          "key": "盗墓笔记",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 115,
          "calss": "1001",
          "type": "text",
          "key": "B842.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 115,
          "calss": "1001",
          "type": "text",
          "key": "(197",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 115,
          "calss": "1001",
          "type": "text",
          "key": "K825.78=76",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 115,
          "calss": "1001",
          "type": "text",
          "key": "北京科学技术出版社",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 114,
          "calss": "1001",
          "type": "callno",
          "key": "R247.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 114,
          "calss": "1001",
          "type": "text",
          "key": "花生",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 114,
          "calss": "1001",
          "type": "publish",
          "key": "中华书局",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 114,
          "calss": "1001",
          "type": "text",
          "key": "北京美术摄影出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 113,
          "calss": "1001",
          "type": "subject",
          "key": "中国特色社会主义",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 113,
          "calss": "1001",
          "type": "text",
          "key": "西游记",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 113,
          "calss": "1001",
          "type": "text",
          "key": "S",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 112,
          "calss": "1001",
          "type": "callno",
          "key": "G782",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 112,
          "calss": "1001",
          "type": "text",
          "key": "F23",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 112,
          "calss": "1001",
          "type": "callno",
          "key": "F2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 111,
          "calss": "1001",
          "type": "text",
          "key": "K203",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 109,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.38",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "publish",
          "key": "江苏文艺出版社",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "text",
          "key": "N",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "text",
          "key": "海燕出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "callno",
          "key": "I247.56",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "callno",
          "key": "B825",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 108,
          "calss": "1001",
          "type": "text",
          "key": "%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 107,
          "calss": "1001",
          "type": "text",
          "key": "新星出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 107,
          "calss": "1001",
          "type": "callno",
          "key": "R28",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 107,
          "calss": "1001",
          "type": "text",
          "key": "K837.127=6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 107,
          "calss": "1001",
          "type": "name",
          "key": "青铜葵花",
          "curdate": "20220716",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "text",
          "key": "李杨著",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "name",
          "key": "活着",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "text",
          "key": "I561.44",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "text",
          "key": "作家出版社有限公司",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "publish",
          "key": "百花文艺出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 106,
          "calss": "1001",
          "type": "text",
          "key": "(199",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 105,
          "calss": "1001",
          "type": "text",
          "key": "R151.3",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 105,
          "calss": "1001",
          "type": "name",
          "key": "装在口袋里的爸爸",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 105,
          "calss": "1001",
          "type": "publish",
          "key": "浙江文艺出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 105,
          "calss": "1001",
          "type": "text",
          "key": "K835.615.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 105,
          "calss": "1001",
          "type": "text",
          "key": "长江出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 104,
          "calss": "1001",
          "type": "text",
          "key": "(1962",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 104,
          "calss": "1001",
          "type": "callno",
          "key": "Q96-49",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 104,
          "calss": "1001",
          "type": "callno",
          "key": "TN929.53",
          "curdate": "20220614",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 104,
          "calss": "1001",
          "type": "text",
          "key": "I512.45",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 104,
          "calss": "1001",
          "type": "text",
          "key": "G634.33",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 103,
          "calss": "1001",
          "type": "text",
          "key": "跨越雄关：长征中的重大战役",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 103,
          "calss": "1001",
          "type": "text",
          "key": "H152.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 103,
          "calss": "1001",
          "type": "text",
          "key": "%E4%B8%AD%E5%9B%BD%E4%BA%BA%E5%8F%A3%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 103,
          "calss": "1001",
          "type": "callno",
          "key": "I207.4",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 103,
          "calss": "1001",
          "type": "text",
          "key": "刘春",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 102,
          "calss": "1001",
          "type": "text",
          "key": "K820.7-49",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 102,
          "calss": "1001",
          "type": "text",
          "key": "I206.7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 101,
          "calss": "1001",
          "type": "text",
          "key": "%E7%BB%8F%E6%B5%8E%E5%8D%B1%E6%9C%BA",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 100,
          "calss": "1001",
          "type": "text",
          "key": "(1988",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 100,
          "calss": "1001",
          "type": "name",
          "key": "山海经",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 100,
          "calss": "1001",
          "type": "text",
          "key": "B933",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 100,
          "calss": "1001",
          "type": "text",
          "key": "Z228.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 100,
          "calss": "1001",
          "type": "text",
          "key": "Q95-49",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "callno",
          "key": "K835",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "text",
          "key": "9787010084237",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "callno",
          "key": "I287",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "text",
          "key": "K825.72",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "author",
          "key": "崔钟雷",
          "curdate": "20220720",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "callno",
          "key": "G61",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 99,
          "calss": "1001",
          "type": "publish",
          "key": "陕西师范大学出版社",
          "curdate": "20220810",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 98,
          "calss": "1001",
          "type": "text",
          "key": "TU755.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 98,
          "calss": "1001",
          "type": "text",
          "key": "9787113231439",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 98,
          "calss": "1001",
          "type": "text",
          "key": "H319.4",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 98,
          "calss": "1001",
          "type": "text",
          "key": "I287.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 98,
          "calss": "1001",
          "type": "text",
          "key": "K825.38=76",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 97,
          "calss": "1001",
          "type": "text",
          "key": "K827",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 97,
          "calss": "1001",
          "type": "subject",
          "key": "纵横家",
          "curdate": "20220812",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 97,
          "calss": "1001",
          "type": "callno",
          "key": "I561.4",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 97,
          "calss": "1001",
          "type": "callno",
          "key": "K928.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 95,
          "calss": "1001",
          "type": "callno",
          "key": "K828.5",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 95,
          "calss": "1001",
          "type": "text",
          "key": "(1958",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 94,
          "calss": "1001",
          "type": "publish",
          "key": "化学工业出版社",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 94,
          "calss": "1001",
          "type": "text",
          "key": "D033",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 94,
          "calss": "1001",
          "type": "text",
          "key": "D2-0",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 94,
          "calss": "1001",
          "type": "text",
          "key": "(1957.4-)冰波著",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "name",
          "key": "法律",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "name",
          "key": "曾有一个人爱我如生命",
          "curdate": "20220506",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "author",
          "key": "(197",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "text",
          "key": "李杰主编",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "text",
          "key": "F121",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "text",
          "key": "中国文学",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 93,
          "calss": "1001",
          "type": "text",
          "key": "张春",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "text",
          "key": "K820.9",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "text",
          "key": "古都",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "callno",
          "key": "N19",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "text",
          "key": "女心",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "callno",
          "key": "G62",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "text",
          "key": "G78",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 92,
          "calss": "1001",
          "type": "text",
          "key": "新华出版社",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "callno",
          "key": "TS21",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "name",
          "key": "人世间",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "text",
          "key": "明",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "callno",
          "key": "B844",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "callno",
          "key": "K82",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "text",
          "key": "R2",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "text",
          "key": "报告文学",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 91,
          "calss": "1001",
          "type": "text",
          "key": "旅游心理学",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "publish",
          "key": "长江文艺出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "name",
          "key": "红楼梦",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "callno",
          "key": "S9",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "text",
          "key": "中国友谊出版公司",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "text",
          "key": "瓦尔登湖",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 90,
          "calss": "1001",
          "type": "text",
          "key": "9787509790748",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 89,
          "calss": "1001",
          "type": "text",
          "key": "中华书局",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 88,
          "calss": "1001",
          "type": "text",
          "key": "晨光出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 88,
          "calss": "1001",
          "type": "text",
          "key": "小鹿．上",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 88,
          "calss": "1001",
          "type": "callno",
          "key": "I247.4",
          "curdate": "20220711",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "callno",
          "key": "D0",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "author",
          "key": "杨鹏",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "callno",
          "key": "K928.7",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "text",
          "key": "计划生育",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "callno",
          "key": "O4",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "text",
          "key": "I251",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "text",
          "key": "上海文艺出版",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 87,
          "calss": "1001",
          "type": "text",
          "key": "马克思恩格斯全集",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "text",
          "key": "I216.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "text",
          "key": "寻宝记",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "publish",
          "key": "人民邮电出版社",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "publish",
          "key": "广东人民出版社",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "author",
          "key": "伍美珍",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 86,
          "calss": "1001",
          "type": "name",
          "key": "足",
          "curdate": "20220815",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "publish",
          "key": "中国纺织出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "callno",
          "key": "B84-49",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "text",
          "key": "G624.243",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "text",
          "key": "谣言背后的健康真相",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "text",
          "key": "民族心理",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 85,
          "calss": "1001",
          "type": "text",
          "key": "苏州大学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "text",
          "key": "D0",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "callno",
          "key": "D9",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "text",
          "key": "军事科学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "callno",
          "key": "TS97",
          "curdate": "20220718",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "publish",
          "key": "春风文艺出版社",
          "curdate": "20220731",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "callno",
          "key": "I26",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "text",
          "key": "三国演义",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 84,
          "calss": "1001",
          "type": "text",
          "key": "沐丞著",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "name",
          "key": "中国象棋",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "callno",
          "key": "TP3",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "text",
          "key": "周国平[著]",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "text",
          "key": "(1969",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "callno",
          "key": "I247.58",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "callno",
          "key": "K24",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 83,
          "calss": "1001",
          "type": "text",
          "key": "F812.423",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "text",
          "key": "人口调查",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "text",
          "key": "C924.255.54",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "text",
          "key": "R245.9",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "callno",
          "key": "H319.4",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "text",
          "key": "北京",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "name",
          "key": "俗世奇人",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "name",
          "key": "福尔摩斯",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "name",
          "key": "十宗罪",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 82,
          "calss": "1001",
          "type": "text",
          "key": "K825.6=76",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "callno",
          "key": "B821-49",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "text",
          "key": "八路",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "text",
          "key": "F326.12",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "text",
          "key": "(195",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "callno",
          "key": "Z228.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "text",
          "key": "章回小说",
          "curdate": "20220811",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "publish",
          "key": "清华大学出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "callno",
          "key": "TS971",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "name",
          "key": "大中华寻宝记",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "callno",
          "key": "K10",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "callno",
          "key": "G634",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 81,
          "calss": "1001",
          "type": "text",
          "key": "%E5%AF%B9%E5%A4%96%E7%BB%8F%E6%B5%8E%E8%B4%B8%E6%98%93%E5%A4%A7%E5%AD%A6%E5%87%BA%E7%89%88%E7%A4%BE",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "callno",
          "key": "J52",
          "curdate": "20220706",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "publish",
          "key": "浙江大学出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "text",
          "key": "%E7%BB%8F%E6%B5%8E%E7%90%86%E8%AE%BA",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "text",
          "key": "K827=76",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "callno",
          "key": "I313.45",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 80,
          "calss": "1001",
          "type": "callno",
          "key": "TS972.12",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "callno",
          "key": "F32",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "callno",
          "key": "Z228",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "text",
          "key": "F231.5",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "text",
          "key": "F719",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "callno",
          "key": "I565.45",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "text",
          "key": "H019",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "callno",
          "key": "TS974.1",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "text",
          "key": "人民文学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "author",
          "key": "(1958",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 79,
          "calss": "1001",
          "type": "callno",
          "key": "H136.3",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "callno",
          "key": "N",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "text",
          "key": "北京燕山出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "subject",
          "key": "女性",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "callno",
          "key": "I227",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "text",
          "key": "红旗出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "name",
          "key": "雾",
          "curdate": "20220807",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "text",
          "key": "崔钟雷",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 78,
          "calss": "1001",
          "type": "text",
          "key": "K209",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 77,
          "calss": "1001",
          "type": "text",
          "key": "远方出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 77,
          "calss": "1001",
          "type": "text",
          "key": "TS971",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 77,
          "calss": "1001",
          "type": "callno",
          "key": "S",
          "curdate": "20220718",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "text",
          "key": "I247.52",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "text",
          "key": "I217.62",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "name",
          "key": "笨狼的故事",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "publish",
          "key": "中国友谊出版公司",
          "curdate": "20220722",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "text",
          "key": "K827=7",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 76,
          "calss": "1001",
          "type": "text",
          "key": "B84-49",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "D922.2",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "TS",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "text",
          "key": "I712.85",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "R151",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "text",
          "key": "英语",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "text",
          "key": "%E6%94%BF%E6%B2%BB%E7%BB%8F%E6%B5%8E%E5%AD%A6%EF%BC%9A%E7%A4%BE%E4%BC%9A%E4%B8%BB%E4%B9%89%E9%83%A8%E5%88%86",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "K825.38",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "text",
          "key": "I287.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "K892",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "callno",
          "key": "I247.8",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 75,
          "calss": "1001",
          "type": "publish",
          "key": "天津人民出版社",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "R395.6",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "text",
          "key": "K820.2",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "I217.62",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "B82",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "text",
          "key": "F719.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "B848.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "K820.855",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "text",
          "key": "I207.23",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "text",
          "key": "F114.4",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "I14",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 74,
          "calss": "1001",
          "type": "callno",
          "key": "I1",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "%E9%BB%84%E8%93%93%E4%BD%B3%E8%91%97",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "I227",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "callno",
          "key": "K20",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "企业创新",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "callno",
          "key": "I287.8",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "%E5%9F%8E%E5%B8%82%E7%BB%8F%E6%B5%8E",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "author",
          "key": "余华",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "战后帝国主义经济",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "callno",
          "key": "K295.5",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "I565.45",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "callno",
          "key": "I28",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 73,
          "calss": "1001",
          "type": "text",
          "key": "叶秀山著",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 71,
          "calss": "1001",
          "type": "text",
          "key": "王安忆著",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 71,
          "calss": "1001",
          "type": "callno",
          "key": "I16",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 71,
          "calss": "1001",
          "type": "callno",
          "key": "B502.232",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 71,
          "calss": "1001",
          "type": "callno",
          "key": "K295.53",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 71,
          "calss": "1001",
          "type": "callno",
          "key": "D69",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "H319.9",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "text",
          "key": "C",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "name",
          "key": "脑筋急转弯",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "publish",
          "key": "山东画报出版社",
          "curdate": "20220628",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "B942",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "text",
          "key": "9787521702118",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "R25",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "C913",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "G634.34",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "callno",
          "key": "R749.055",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 70,
          "calss": "1001",
          "type": "publish",
          "key": "湖南人民出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "text",
          "key": "G613",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "text",
          "key": "H319.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "text",
          "key": "K835.127",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "text",
          "key": "K825.38",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "callno",
          "key": "J212",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "callno",
          "key": "I25",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "callno",
          "key": "I561.45",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 69,
          "calss": "1001",
          "type": "publish",
          "key": "北京",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "callno",
          "key": "C913.1-49",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "text",
          "key": "岭南美术出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "callno",
          "key": "H0",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "publish",
          "key": "电子工业出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "text",
          "key": "人性的弱点",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "callno",
          "key": "F279",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 68,
          "calss": "1001",
          "type": "text",
          "key": "I206.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "text",
          "key": "I253",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "name",
          "key": "游戏",
          "curdate": "20220809",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "TM",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "R5",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "R247",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "Z2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "name",
          "key": "屁屁侦探",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "text",
          "key": "F272",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "name",
          "key": "旅游",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "text",
          "key": "K820.7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "R15",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "name",
          "key": "特种兵学校",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "Z",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 67,
          "calss": "1001",
          "type": "callno",
          "key": "I565.8",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "name",
          "key": "爆笑校园",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "text",
          "key": "TS972.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "text",
          "key": "中国妇女出版",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "text",
          "key": "内蒙古人民出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "text",
          "key": "中国象棋",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 66,
          "calss": "1001",
          "type": "callno",
          "key": "Q",
          "curdate": "20220807",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "text",
          "key": "国家图书馆出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "text",
          "key": "成都地图出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "publish",
          "key": "中国社会出版社",
          "curdate": "20220520",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "text",
          "key": "查理九世",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "name",
          "key": "百年孤独",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "callno",
          "key": "I22",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "text",
          "key": "N1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 65,
          "calss": "1001",
          "type": "text",
          "key": "C52",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "K837.125.3",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "publish",
          "key": "北京大学出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "author",
          "key": "沈石溪",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "text",
          "key": "四川文艺出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "I277",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "F83",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "I712.88",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "J652",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "publish",
          "key": "人民日报出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "text",
          "key": "K825.6=75",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "callno",
          "key": "K835.6",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 64,
          "calss": "1001",
          "type": "text",
          "key": "G634.7",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "人生哲学",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "callno",
          "key": "K248",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "I313.45",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "author",
          "key": "(199",
          "curdate": "20220624",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "G894",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "callno",
          "key": "D922",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "王振",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "name",
          "key": "小英雄雨来",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "H194.5",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "publish",
          "key": "中国农业大学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "author",
          "key": "南派三叔",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "text",
          "key": "创新",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 63,
          "calss": "1001",
          "type": "name",
          "key": "美术",
          "curdate": "20220811",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "callno",
          "key": "C913.13",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "text",
          "key": "TS976",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "publish",
          "key": "上海文化出版社",
          "curdate": "20220719",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "callno",
          "key": "H15",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "text",
          "key": "K827=36",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "text",
          "key": "Q959.7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "text",
          "key": "I287.8",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 62,
          "calss": "1001",
          "type": "text",
          "key": "广州出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "I253",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "G624.243",
          "curdate": "20220813",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "author",
          "key": "杨红樱",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "author",
          "key": "(1969",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "text",
          "key": "I266",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "name",
          "key": "平凡的世界",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "S96",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "H019",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "Z4",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "callno",
          "key": "S831",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 61,
          "calss": "1001",
          "type": "classno",
          "key": "I287.5",
          "curdate": "20220618",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "callno",
          "key": "I712.65",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "callno",
          "key": "TM925",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "B222.2",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "穿越",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "G634",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "name",
          "key": "名侦探柯南",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "中国和平出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "云南人民出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "9787556102266",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 60,
          "calss": "1001",
          "type": "text",
          "key": "陈宝英孕产育儿研究中心编著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "publish",
          "key": "四川文艺出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "text",
          "key": "尾鱼",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "text",
          "key": "F299.2",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "callno",
          "key": "R151.4",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "callno",
          "key": "H194.1",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "name",
          "key": "笑猫日记",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "text",
          "key": "精装",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "publish",
          "key": "上海人民出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "callno",
          "key": "I247.52",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "callno",
          "key": "R9",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "text",
          "key": "人民卫生出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "subject",
          "key": "世界史",
          "curdate": "20220721",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "callno",
          "key": "D916",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 59,
          "calss": "1001",
          "type": "text",
          "key": "语文",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "R174",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "name",
          "key": "如果历史是一群喵",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "I12",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "name",
          "key": "宇宙",
          "curdate": "20220505",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "幸福",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "G633.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "J212.27",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "纵横家",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "subject",
          "key": "儿童文学",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 58,
          "calss": "1001",
          "type": "text",
          "key": "K828.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "K827.7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "F124",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "J409.1",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "配筋",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "O4",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "TS974.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "callno",
          "key": "B845.1",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "B825",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "I287.4",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "callno",
          "key": "C",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "S646",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "中国国际经济交流中心编",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "text",
          "key": "I247.56",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 57,
          "calss": "1001",
          "type": "callno",
          "key": "C912.11",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "杨东著",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "callno",
          "key": "B244",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "classno",
          "key": "K825.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "9787551139342",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "城市狩猎",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "publish",
          "key": "企业管理出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "callno",
          "key": "R163",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "publish",
          "key": "百花洲文艺出版社",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "callno",
          "key": "I242.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "publish",
          "key": "浙江人民出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "K827=72",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "文新编著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "subject",
          "key": "数学",
          "curdate": "20220807",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "callno",
          "key": "D035",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "Q",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 56,
          "calss": "1001",
          "type": "text",
          "key": "文学",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "F830.59",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "F279.382.3",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "水浒传",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "K811",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "K826.2",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "subject",
          "key": "网络营销",
          "curdate": "20220620",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "帝国主义国家",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "author",
          "key": "(1964",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 55,
          "calss": "1001",
          "type": "text",
          "key": "J221",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "author",
          "key": "万象文画编写组编",
          "curdate": "20220503",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "text",
          "key": "黄河",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "publish",
          "key": "中国华侨出版社",
          "curdate": "20220728",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.78",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "callno",
          "key": "I247.53",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "callno",
          "key": "H152",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "text",
          "key": "K837.125",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "text",
          "key": "超人",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 54,
          "calss": "1001",
          "type": "publish",
          "key": "广西师范大学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "I712.65",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "C912.1",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "F127",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "S858",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "TS972.13",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "publish",
          "key": "浙江少年儿童出版社",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "R681.5",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "H136.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "K827=52",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "中国时代经济出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "J06",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "G256",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "H033",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "text",
          "key": "K812",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 53,
          "calss": "1001",
          "type": "callno",
          "key": "F830.9",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "callno",
          "key": "R711",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "name",
          "key": "决斗",
          "curdate": "20220504",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "N19",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "publish",
          "key": "江西人民出版社",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "人造卫星",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "subject",
          "key": "科学知识",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "publish",
          "key": "首都师范大学出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "publish",
          "key": "上海音乐出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "callno",
          "key": "N33-49",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "蔡骏著",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "E19",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "callno",
          "key": "K87",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "subject",
          "key": "心理",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "浮躁",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "何",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "publish",
          "key": "社会科学文献出版社",
          "curdate": "20220521",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "text",
          "key": "9787537262835",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 52,
          "calss": "1001",
          "type": "subject",
          "key": "安全教育",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "text",
          "key": "H313.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "text",
          "key": "古龙著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "K203",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "I207",
          "curdate": "20220728",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "J228.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "S6",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "R16",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 51,
          "calss": "1001",
          "type": "callno",
          "key": "J228.2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "I251",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "B222",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "text",
          "key": "I253.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "name",
          "key": "十万个为什么",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "D63",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "text",
          "key": "甘肃人民出版社",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "name",
          "key": "瓦尔登湖",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "F832",
          "curdate": "20220722",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "O12",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "text",
          "key": "古文观止",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "D669.3",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "B99",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "J05",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "D771.2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "name",
          "key": "爱的教育",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "text",
          "key": "冠诚编著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "text",
          "key": "商业管理",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 50,
          "calss": "1001",
          "type": "callno",
          "key": "H125",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "callno",
          "key": "I712.84",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "王芳著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "B948",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "G613.3",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "浙江沿海及海岛综合开发战略研究：浙江沿海及海岛地区能源与电力发展研究：能源电力卷",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "callno",
          "key": "I206",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "F713.5",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "J238.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "publish",
          "key": "外文出版社",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "G212.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "callno",
          "key": "S965.8",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "author",
          "key": "(1959",
          "curdate": "20220705",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "I277.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "安",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "C913",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "(1940-)周孝信主编",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "中国唱片总公司",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "TS976.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "name",
          "key": "短篇小说",
          "curdate": "20220624",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "I242.4",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "callno",
          "key": "P159",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "callno",
          "key": "K837.125",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 49,
          "calss": "1001",
          "type": "text",
          "key": "私营企业",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "Z8",
          "curdate": "20220711",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "R441",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "朱自清著",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "S83",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "植物大战僵尸",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "D771.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "(古罗马)",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "G831",
          "curdate": "20220622",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "%E5%9C%9F%E5%9C%B0%E8%A7%84%E5%88%92",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "藤萍",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "G891",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "author",
          "key": "李飞著",
          "curdate": "20220506",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "Z89",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "G63",
          "curdate": "20220801",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "publish",
          "key": "中国文史出版社",
          "curdate": "20220610",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "TN949.12",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "K250.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "I712",
          "curdate": "20220721",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "TP393",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "R212",
          "curdate": "20220719",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "I516.45",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "callno",
          "key": "I266",
          "curdate": "20220704",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "纺织工业出版社",
          "curdate": "20220718",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 48,
          "calss": "1001",
          "type": "text",
          "key": "Z88",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "G633.4",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "P18",
          "curdate": "20220703",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "X4",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "南方十六所大学编",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "中国宇航出版社",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "D929",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "I512.44",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "publish",
          "key": "黑龙江美术出版社",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "R245.9",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "publish",
          "key": "中国文联出版社",
          "curdate": "20220720",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "J525.1",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "C91",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "I561.45",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "P183-49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "R395.6",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "I206",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "TM3",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "publish",
          "key": "复旦大学出版社",
          "curdate": "20220520",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "name",
          "key": "米小圈上学记：我是小学生",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "半导体器件",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "C933",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "J222.7",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "callno",
          "key": "S51",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 47,
          "calss": "1001",
          "type": "text",
          "key": "E",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "callno",
          "key": "I222.7",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "足",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "K928.6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "callno",
          "key": "I287.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "TS972.162",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "publish",
          "key": "广西人民出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "callno",
          "key": "S646",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "callno",
          "key": "J42",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "K295.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "publish",
          "key": "北京时代华文书局",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "R161.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "C934",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "name",
          "key": "法医秦明",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "name",
          "key": "故宫里的大怪兽",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "O1-49",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "I207.419",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 46,
          "calss": "1001",
          "type": "text",
          "key": "I14",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "S66",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "G781",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "K837.127=5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "漓江出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "subject",
          "key": "报告文学",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "name",
          "key": "骆驼祥子",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "X321.2",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "B946",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "I25",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "F552.1",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "K837.127=41",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "G633",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "R24",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "K828",
          "curdate": "20220718",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "H194.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "I56",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "B2",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "I222.7",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "name",
          "key": "墨多多",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "TU241",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "name",
          "key": "中国民间故事",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "text",
          "key": "J528",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "K29",
          "curdate": "20220728",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "callno",
          "key": "B933",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "publish",
          "key": "上海译文出版社",
          "curdate": "20220509",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 45,
          "calss": "1001",
          "type": "classno",
          "key": "J238.2",
          "curdate": "20220811",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "会计报表",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "Q94",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "R56",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "publish",
          "key": "中国轻工业出版社",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "Z228",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "D66",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "老人",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "R749.055",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "D691.9",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "B823.1",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "name",
          "key": "汤小团",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "K220.9",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "TN",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "subject",
          "key": "动物",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "C913.1",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "name",
          "key": "钢铁是怎样炼成的",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "D60",
          "curdate": "20220728",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "author",
          "key": "张秋生",
          "curdate": "20220723",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "写给你的诗，孩子",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "callno",
          "key": "Z12",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "name",
          "key": "红岩",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "区(城市)",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "E297.5",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 44,
          "calss": "1001",
          "type": "text",
          "key": "汉语拼音",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "%E7%88%B1",
          "curdate": "20220808",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "name",
          "key": "朝花夕拾",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "S68",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "F12-53",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "subject",
          "key": "职业选择",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "陈润著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "李洁著",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "name",
          "key": "科学实验王",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "D925.105",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "TM6",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "classno",
          "key": "F272.92",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "K825.7",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "J292.1",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "publish",
          "key": "少年儿童出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "D035",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "舟山",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "F299.2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "K827=49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "K835.127",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "J64",
          "curdate": "20220704",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "B221.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "text",
          "key": "傲慢与偏见",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 43,
          "calss": "1001",
          "type": "callno",
          "key": "Z228.2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "9787210041849",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "G831.3",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "K826.1",
          "curdate": "20220728",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "I207.6",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "I313.4",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "J212.052",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "Q98",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "Q94",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "D922.291.914",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "publish",
          "key": "福建人民出版社",
          "curdate": "20220601",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "J524",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "K891.26",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "subject",
          "key": "智力开发",
          "curdate": "20220801",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "K103",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "洁岛著",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "S68",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "P159-49",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "TS972.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "C913.1-49",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "publish",
          "key": "中国经济出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "R161",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "K825.42",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "D60",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "callno",
          "key": "D092",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 42,
          "calss": "1001",
          "type": "text",
          "key": "原来圣保罗不悲伤",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "Q96-49",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "name",
          "key": "人间失格",
          "curdate": "20220715",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "K835.65",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "K826.1",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "C913.2",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "G898.2",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "J221.8",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "I22",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.38=6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "B844.1",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "道路交通安全",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "I247.53",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "TP39",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "K928.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "S27",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "F830.4",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "H12",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "publish",
          "key": "中国书籍出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "name",
          "key": "舟山",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "callno",
          "key": "J657.41",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "subject",
          "key": "漫画",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "TP393.4",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "text",
          "key": "F832.5",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 41,
          "calss": "1001",
          "type": "author",
          "key": "东野圭吾",
          "curdate": "20220810",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "F127",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "I267.5",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "暨南大学出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "G632.46",
          "curdate": "20220815",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "I210.4",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "H19",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "TU984",
          "curdate": "20220704",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "上下五千年",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "F272.91",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "9787213096242",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.76",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "K827.7",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "图解心理学：人人都需要了解的学科",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "李贯通",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "author",
          "key": "金波",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "J642",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "I516.45",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "publish",
          "key": "山东人民出版社",
          "curdate": "20220509",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "F713.8",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "F7",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "(1907-",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "K825.6",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "B81",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "装饰美术",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "TS972",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "callno",
          "key": "K249.09",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "publish",
          "key": "中信出版社",
          "curdate": "20220730",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "name",
          "key": "聊斋志异",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "author",
          "key": "王小波",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "author",
          "key": "刘慈欣",
          "curdate": "20220817",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 40,
          "calss": "1001",
          "type": "text",
          "key": "I26",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "B949.2",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "一本女人写给女人的书-我们的身体和我们的心理",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "subject",
          "key": "成功心理学",
          "curdate": "20220810",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "K825.6=6",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "name",
          "key": "被讨厌的勇气",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "publish",
          "key": "江苏人民出版社",
          "curdate": "20220726",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "K837.125.34",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "F84",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "publish",
          "key": "九州出版社",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "F0",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "J292",
          "curdate": "20220627",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "F0",
          "curdate": "20220624",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "G61",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "D26",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "name",
          "key": "撒野",
          "curdate": "20220810",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "Q95",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "H136",
          "curdate": "20220717",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "author",
          "key": "八路",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "F299.21",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "publish",
          "key": "中国致公出版社",
          "curdate": "20220812",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "name",
          "key": "小兵张嘎",
          "curdate": "20220824",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "I267.4",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "Q111",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "B84",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "name",
          "key": "半小时漫画",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "I267.5",
          "curdate": "20220821",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "中国智库经济观察．2015．2015",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "D920.4",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "一个陌生女人的来信",
          "curdate": "20220816",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "B844.1",
          "curdate": "20220818",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "I561.44",
          "curdate": "20220722",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "K92",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "C913.3",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "author",
          "key": "凯叔",
          "curdate": "20220825",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "D648",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "K248",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "name",
          "key": "半小时漫画中国史",
          "curdate": "20220814",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "G624.233",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "publish",
          "key": "新华出版社",
          "curdate": "20220819",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "G831.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "I712.4",
          "curdate": "20220823",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "B844",
          "curdate": "20220822",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "callno",
          "key": "I247.55",
          "curdate": "20220820",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "高等教育出版社",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "K250.6",
          "curdate": "20220827",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        },
        {
          "recno": 39,
          "calss": "1001",
          "type": "text",
          "key": "K825.3",
          "curdate": "20220826",
          "int1": 0,
          "bibrecno": 0,
          "rdrrecno": 0
        }
      ];
      this.tagList = tagListOrg.slice(0, 50);
      this.onReady();
    },
    // 生成随机数
    getRandomNum() {
      return Math.floor(Math.random() * (255 + 1));
    },
    doPosition() {
      this.$nextTick(() => {            // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
        var l = this.oList.offsetWidth / 2;
        var t = this.oList.offsetHeight / 2;
        for (var i = 0; i < this.mcList.length; i++) {
          if (this.mcList[i].on) {
            continue;
          }
          var aAs = this.oA[i].style;
          if (this.mcList[i].alpha > 0.1) {
            if (aAs.display != '')
              aAs.display = '';
          } else {
            if (aAs.display != 'none')
              aAs.display = 'none';
            continue;
          }
          this.oA[i].style.left = this.mcList[i].cx + l - this.mcList[i].offsetWidth / 2 + 'px';
          this.oA[i].style.top = this.mcList[i].cy + t - this.mcList[i].offsetHeight / 2 + 120 + 'px';
          this.oA[i].filter = "alpha(opacity=" + 100 * this.mcList[i].alpha + ")";
          this.oA[i].zIndex = this.mcList[i].zIndex;

          this.oA[i].style.opacity = this.mcList[i].alpha;
        }

      })
    },
    update() {

      this.$nextTick(() => {           // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
        var a, b, c = 0;

        a = (Math.min(Math.max(-this.mouseY, -this.size), this.size) / this.radius) * this.tspeed;
        b = (-Math.min(Math.max(-this.mouseX, -this.size), this.size) / this.radius) * this.tspeed;

        this.lasta = a;
        this.lastb = b;
        if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
          return
        }
        this.sineCosine(a, b, c);
        for (var j = 0; j < this.mcList.length; j++) {
          if (this.mcList[j].on) {
            continue;
          }
          var rx1 = this.mcList[j].cx;
          var ry1 = this.mcList[j].cy * this.ca + this.mcList[j].cz * (-this.sa);
          var rz1 = this.mcList[j].cy * this.sa + this.mcList[j].cz * this.ca;
          var rx2 = rx1 * this.cb + rz1 * this.sb;
          var ry2 = ry1;
          var rz2 = rx1 * (-this.sb) + rz1 * this.cb;
          var rx3 = rx2 * this.cc + ry2 * (-this.sc);
          var ry3 = rx2 * this.sc + ry2 * this.cc;
          var rz3 = rz2;
          this.mcList[j].cx = rx3;
          this.mcList[j].cy = ry3;
          this.mcList[j].cz = rz3;
          var per = this.d / (this.d + rz3);
          this.mcList[j].x = (this.howElliptical * rx3 * per) - (this.howElliptical * 2);
          this.mcList[j].y = ry3 * per;
          this.mcList[j].scale = per;

          //this.mcList[j].alpha = per;
          //this.mcList[j].alpha = (this.mcList[j].alpha - 0.6) * (10 / 6);

          var alpha = per;
          alpha = (alpha - 0.6) * (10 / 6);
          this.mcList[j].alpha = alpha * alpha * alpha - 0.2;
          this.mcList[j].zIndex = Math.ceil(100 - Math.floor(this.mcList[j].cz));
        }
        this.doPosition();
      })
    },
    positionAll() {

      this.$nextTick(() => {      // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
        var phi = 0;
        var theta = 0;
        var max = this.mcList.length;
        var aTmp = [];
        var oFragment = document.createDocumentFragment();
        // 随机排序
        for (let i = 0; i < this.tagList.length; i++) {
          aTmp.push(this.oA[i]);
        }
        aTmp.sort(() => {
          return Math.random() < 0.5 ? 1 : -1;
        });
        for (let i = 0; i < aTmp.length; i++) {
          oFragment.appendChild(aTmp[i]);
        }
        this.oList.appendChild(oFragment);

        for (var i = 0; i < max; i++) {
          if (this.distr) {
            phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
            theta = Math.sqrt(max * Math.PI) * phi;
          } else {
            phi = Math.random() * (Math.PI);
            theta = Math.random() * (2 * Math.PI);
          }

          //坐标变换
          this.mcList[i].cx = this.radius * Math.cos(theta) * Math.sin(phi);
          this.mcList[i].cy = this.radius * Math.sin(theta) * Math.sin(phi);
          this.mcList[i].cz = this.radius * Math.cos(phi);

          this.oA[i].style.left = this.mcList[i].cx + this.oList.offsetWidth / 2
            - this.mcList[i].offsetWidth / 2 + 'px';
          this.oA[i].style.top = this.mcList[i].cy + this.oList.offsetHeight / 2
            - this.mcList[i].offsetHeight / 2 + 'px';
        }
      })
    },
    sineCosine(a, b, c) {
      this.sa = Math.sin(a * this.dtr);
      this.ca = Math.cos(a * this.dtr);
      this.sb = Math.sin(b * this.dtr);
      this.cb = Math.cos(b * this.dtr);
      this.sc = Math.sin(c * this.dtr);
      this.cc = Math.cos(c * this.dtr);
    },

    /** 查询知识列表 */
    searchList() {
      this.loading = true;
      listForEs(this.form).then(response => {
        this.mainList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
}
</script>

<style scoped>
@import "../../assets/kms/styles/style.css";

/* 机器人样式 */
.iyunwen_js_class {
  top: -100px;
  position: fixed !important;
}

.MoveRight {
  width: 766px;
  float: left;
}

.title1 em {
  font-size: 16px;
  color: #1f95df;
  border-bottom: solid 2px #EAEAEA;
  text-align: left;
}

.floatTxt {
  position: relative;
  font-size: 16px;
  margin-top: 4px;
  height: 100px;
  margin-bottom: 20px;
}

.floatTxt a {
  display: inline-block;
  color: #333;
  position: absolute;
  z-index: 1;
}

.floatTxt a:hover {
  color: #000
}

.floatTxtNoSupport {
  position: relative;
  font-size: 16px;
  margin-top: 15px;
  height: 180px;
}

.floatTxtNoSupport a {
  display: inline-block;
  color: #222;
}

/* tagscloud */
#tagscloud {
  width: 500px;
  height: 260px;
  position: relative;
  font-size: 14px;
  color: #333;
  margin: 20px auto 0;
  text-align: center;
}

#tagscloud a {
  position: absolute;
  top: 0px;
  left: 0px;
  color: #333;
  font-family: Arial;
  text-decoration: none;
  margin: 0 10px 15px 0;
  line-height: 22px;
  text-align: center;
  font-size: 14px;
  padding: 1px 5px;
  display: inline-block;
  border-radius: 3px;
}

/* #tagscloud a.tagc1{color:#666;opacity: 0.68293;  padding: 5px;}
  #tagscloud a.tagc2{color:#F16E50;opacity: 0.68293; padding: 5px;}
  #tagscloud a.tagc3{color:#83c325;opacity: 0.68293;  padding: 5px;}
  #tagscloud a.tagc4{color:#0F6098;opacity: 0.68293;  padding: 5px;}
  #tagscloud a.tagc5{color:#006633;opacity: 0.68293;  padding: 5px;} */
#tagscloud a:hover {
  background: #fff;
  border: 1px solid #1f95df;
}

/* .label {
      display: inline-block;
      margin: 0 10px 0 0;
      min-width:60px;
      max-width:120px;
      text-align: right;
  } */

.literature_index {
  width: 100%;
}

.literature_index .searchbox .label, .literature_index .radiobox .label, .literature_index .time_publish .label, .literature_index .sortleft .label {
  display: inline-block;
  margin: 0 10px 0 0;
  width: 20%;
  text-align: right;
}

.literature_index .label_1 {
  width: 8% !important;
}

.searchbox .input {
  height: 30px;
  line-height: 30px;
  color: #333;
  border: 1px solid #809ec5;
  background: #f5fdff;
  font-size: 12px;
  width: 30%;
  padding: 0 15px;
  margin: 0 10px;
}

.index_showinfo {
  margin-left: 15%;
}

.content {
  padding-bottom: 0;
}

.footer {
  margin-top: 20px;
}


.tagcloud-all {
  position: relative;
}

.tagcloud-all a {
  position: absolute;
  top: 0px;
  left: 0px;
  color: #fff;
  font-weight: bold;
  text-decoration: none;
  padding: 3px 6px;
}

a:hover {
  color: #FF0000;
  letter-spacing: 2px;
}

</style>

<style scoped lang="scss">
.searchbox {
  ::v-deep .el-input--small {
    .el-input__inner {
      border: 1px solid #809ec5;
      background: #f5fdff;
    }
  }
}
</style>
