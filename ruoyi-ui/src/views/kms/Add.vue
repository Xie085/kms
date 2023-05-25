<template>
  <div class="kg-set">
    <breadcrumb-header :breadcrumbList="breadcrumbList" background="#ffffff" exist-back router-name="kgList">
      <template slot="right">
        <el-button type="primary"
                   size="small"
                   @click="save">
          保存
        </el-button>
      </template>
    </breadcrumb-header>
    <div class="kg-set-main">
      <div class="kg-set-bg">
        <!--基本信息-->
        <!--<base-info ref="base" :checkBase.sync="checkForm"></base-info>-->
        <!--知识正文-->
        <text-editor ref="editor"></text-editor>
      </div>
    </div>
  </div>
</template>

<script>
import {listDept, getDept, delDept, addKms, updateDept, listDeptExcludeChild} from "@/api/kms/kms";
import BreadcrumbHeader from './components/breadcrumb-header'
//import BaseInfo from './BaseInfo'
import TextEditor from './TextEditor'

export default {
  name: 'Add',
  components: {BreadcrumbHeader, TextEditor},
  data() {
    return {
      breadcrumbList: [
        {
          name: '知识管理',
          to: {name: 'kmsList'}
        },
        {
          name: '新增',
          to: {name: 'kmsAdd'}
        }
      ],
      set: this.$route.params.set,
      checkForm: false,
    }
  },
  computed: {},
  created() {
  },
  methods: {
    // 保存知识
    save(type) {
      this.$refs.base.checkForm('save')
      this.$refs.editor.checkData()
      if (this.checkForm) {
        const form = this.$refs.base.form
        const mainContent = this.$refs.editor.content
        const basic = {
          title: form.title.replace(/^\s*|\s*$/g, ''),
          summary: form.summary ? form.summary.replace(/^\s*|\s*$/g, '') : form.summary,
          author: form.author.type === 'Internal' ? form.author.inside.map(({id}) => id) : [],
          orgId: form.dept.data.id,
        }
        //console.log(basic)
        const data = {
          title: form.title.replace(/^\s*|\s*$/g, ''),
          summary: form.summary ? form.summary.replace(/^\s*|\s*$/g, '') : form.summary,
          mainContent
        }
        console.log(data)
        addKms(data).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      }
    }
  },
  destroyed() {
  }
}
</script>

<style scoped lang="scss">
.kg-set {
  width: 100%;
  height: 100%;

  .kg-set-main {
    padding: 16px 40px 40px;
    overflow: overlay; // 滚动条不占空间的样式，保留，勿删
    overflow: auto;
    height: calc(100% - 45px);

    .kg-set-bg {
      box-shadow: 0 2px 18px rgba(0, 0, 0, 0.08);
      background: #fff;
      border-radius: 4px;

      .kg-set-step {
        background: #fff;
        box-shadow: 0 2px 18px rgba(0, 0, 0, 0.08);
        border-top-left-radius: 4px;
        border-top-right-radius: 4px;
      }
    }

  }
}
</style>
