<template>
  <div class="kms-view">
    <div class="kms-header">
      <div class="kms-header-title">{{ kmsData.title }}</div>
      <!--<div class="kms-header-summary" v-show="kmsData.summary!=null&&kmsData.summary!=''">{{kmsData.summary}}</div>-->
      <div class="kms-header-create-info">
        <div class="kms-header-author">作者：{{ kmsData.createBy }}</div>
        <div class="kms-header-time">创建时间：{{ kmsData.createTime }}</div>
      </div>
    </div>
    <div class="kms-content" v-show="kmsData.summary!=null&&kmsData.summary!=''">
      <div v-html="kmsData.summary"></div>
    </div>
    <div class="kms-content" v-show="kmsData.mainContent!=null&&kmsData.mainContent!=''">
      <div v-html="kmsData.mainContent"></div>
    </div>
    <div class="list-panel" v-show="previewFile.length>0">
      <!--<div class="list-title"><i class="iconfont ic-xiangguanziliao-copy"></i><span class="">附件列表</span></div>-->
      <div class="list-title">附件列表</div>
      <ul class="preview-ul">
        <li v-for="item in previewFile" @click="openFile(item.path,item.state)" :key="item.path">
          <i class="iconfont" :class="item.type | fileIcon"></i>{{ item.name }}.{{ item.type }}
        </li>
      </ul>
    </div>

    <!--版本列表-->
    <div class="list-panel" v-show="versionList.length>0">
      <div class="list-title">版本列表</div>
      <ul class="version-ul">
        <li v-for="item in versionList" @click="wait" :key="item.id">
          <div class="label">{{ item.title }}</div>
          <span></span>
          <div class="time"> 发布时间：{{ item.publishDate }}</div>
        </li>
      </ul>
    </div>

    <!--审批-->
<!--    <activiti-approve v-if="true" :list="activitData" :historyData="historyData"-->
<!--                      :rejectApprovalNode="rejectApprovalNode" @submit="submitApprove"></activiti-approve>-->
  </div>
</template>

<script>
import {getMain, previewAttFile, listVersion} from "@/api/kms/main";
import {findTask, findHistory} from "@/api/activiti/template"
import {fileIconStyle} from '../../utils/fileType'
import ActivitiApprove from "../activiti/components/activiti-approve";
import {completeTask} from '@/api/activiti/template';
import {mapGetters} from "vuex";

export default {
  name: "view.vue",
  components: {
    ActivitiApprove
  },
  data() {
    return {
      id: this.$route.query.id,
      kmsData: {},
      previewFile: [],
      versionList: [],
      rejectApprovalNode: [],
      activitData: [],
      processInstanceId: '',
      historyData: [],
      summary: null
    }
  },
  computed: {
    ...mapGetters(['name'])
  },
  filters: {
    fileIcon(v) {
      return fileIconStyle(v)
    }
  },
  created() {
    this.initView();
  },
  methods: {
    /** 查询知识列表 */
    initView() {
      this.loading = true;
      getMain(this.id).then(response => {
        this.kmsData = response.data;
        this.loading = false;
        this.processInstanceId = this.kmsData.processInstanceId;
        console.log(this.processInstanceId)
        findTask(this.processInstanceId, this.name).then(response => {
          const findTask = response.data;
          this.activitData = findTask
          console.log('findTask=========:', findTask)
        })
        findHistory(this.processInstanceId).then(response => {
          const findHistory = response.data;
          this.historyData = findHistory
          console.log('findHistory=========:', findHistory)
        })
      })
      previewAttFile(this.id).then(response => {
        const previewData = response.data;
        //console.log('previewAttFile=========,previewData:',previewData)
        this.previewFile = []
        for (let i in previewData) {
          this.previewFile.push({
            name: previewData[i].fileName,
            path: previewData[i].converted,
            state: previewData[i].state,
            type: previewData[i].fileType
          })
        }
      })
      listVersion(this.id).then(response => {
        const verList = response.data;
        console.log('listVersion=========,verList:', verList)
        this.versionList = verList
      })
      //console.log(fileIconStyle('.doc'))
    },
    //打开文件
    openFile(path, state) {
      //console.log('openFile=========,state:',state)
      if (state === 2) {
        let baseUrl = process.env.VUE_APP_BASE_API //"http://localhost:9090"  //window.origin
        window.open(baseUrl + path, '_blank')
      } else {
        this.$modal.msgWarning("该附件还未转换完成或转换失败！");
      }
    },
    wait() {
      window.alert('期待你的加入')
    },
    //查询历史审批节点详情
    listRejectApprovalNode() {

    },
    //流程审批提交
    submitApprove(comment, approvalNodeId) {
      const data = {
        processInstanceId: this.processInstanceId,
        assignee: this.name,
        comment: comment
      }
      completeTask(data).then(response => {
        if (response?.message) {
          this.$modal.msgError(response.message)
        } else {
          this.$modal.msgSuccess("审批成功");
          //关闭本页签
          setTimeout(function () {
            this.$tab.closePage();
          }, 1500)

        }
      })
    }
  }
}
</script>

<style scoped>
/*@import url("http://at.alicdn.com/t/font_2602024_czfyrrtt7tk.css");*/
.iconfont {
  font-family: hufont !important;
  font-style: normal;
  margin-right: 5px;
  color: #00afff;

  speak: none;
  font-weight: 400;
  font-variant: normal;
  text-transform: none;
  line-height: 1;
  vertical-align: baseline;
  display: inline-block;
}

.ic-xiangguanziliao-copy {
  font-size: 13px;
}

.list-title {
  /*border-bottom: solid 1px #eee;
  padding: 0 1px 8px 1px;*/
  color: #00afff;
}

.kms-view {
  background: #FFFFFF;
  margin: 10px auto;
}

.kms-header {
  background: #FFFFFF;
  box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
  border-radius: 4px 4px 4px 4px;
  margin: 15px auto;
  width: 90%;
}

.kms-header-title {
  text-align: center;
  padding: 30px 0px;
}

.kms-header-summary {
  text-align: center;
  color: #5d5d5d;
  padding: 5px 10px 30px;
}

.kms-header-create-info {
  color: #5d5d5d;
  font-size: 13px;
  padding: 5px 10px 30px;
  display: flex;
  justify-content: space-around;
  width: 30%;
  margin: 0 auto;
}

.kms-content {
  background: #FFFFFF;
  box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
  border-radius: 4px 4px 4px 4px;
  margin: 15px auto;
  width: 90%;
  padding: 30px;
}

.list-panel {
  background: #FFFFFF;
  box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
  border-radius: 4px 4px 4px 4px;
  margin: 15px auto;
  width: 90%;
  padding: 15px 20px 30px;
}

.preview-ul {
  list-style: none;
  width: 95%;
}

.preview-ul li {
  padding: 5px 10px;
  cursor: pointer;
  font-size: 15px;
}

.preview-ul li:hover {
  color: #409eff;
}

.version-ul {
  list-style: none;
  width: 95%;
}

.version-ul li {
  display: flex;
  height: 50px;
  align-items: center;
  border-bottom: solid 1px #eee;
  padding-left: 15px;
  cursor: pointer;
}

.version-ul li:hover {
  color: #409eff;
}

.version-ul li span {
  margin: 0 15px 0 35px;
  color: #ddd;
}

.label {

}

.time {
  color: #bbb;
  font-size: 12px;
  padding-top: 5px;
}
</style>
