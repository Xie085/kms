<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="知识标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入知识标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select
          v-model="queryParams.state"
          placeholder="知识状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.kms_main_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--<el-form-item label="所属目录" prop="catalogId">
        <el-input
          v-model="queryParams.catalogId"
          placeholder="请输入所属目录"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <!--<el-form-item label="所属部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入所属部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <!--<el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布时间" prop="publishDate">
        <el-date-picker clearable
          v-model="queryParams.publishDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="阅读量" prop="readCount">
        <el-input
          v-model="queryParams.readCount"
          placeholder="请输入阅读量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label="创建者" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--<el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['kms:main:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['kms:main:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['kms:main:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['kms:main:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mainList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键ID" align="center" prop="id" width="60"/>
      <el-table-column label="知识标题" align="center" prop="title"/>
      <el-table-column label="作者" align="center" prop="author" width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.extAuthor.join('、') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state" width="60">
        <template slot-scope="scope">
          <span>{{ scope.row.state | stateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="版本" align="center" prop="version" width="50">
        <template slot-scope="scope">
          <span>{{ scope.row.version | versionFilter }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="所属目录" align="center" prop="catalogId" />-->
      <!-- <el-table-column label="所属部门" align="center" prop="deptId" />-->
      <!--<el-table-column label="知识正文" align="center" prop="mainContent" />-->
      <!--<el-table-column label="知识摘要" align="center" prop="summary" />
      <el-table-column label="关键字" align="center" prop="keyword" />
      <el-table-column label="知识封面" align="center" prop="coverImg" />-->
      <!--<el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="发布时间" align="center" prop="publishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="阅读量" align="center" prop="readCount" />-->
      <el-table-column label="创建者" align="center" prop="createBy" width="80"/>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <!--<el-table-column label="更新人" align="center" prop="updateBy" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['kms:main:edit']"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleNewVersion(scope.row)"
            v-hasPermi="['kms:main:edit']"
            v-if="scope.row.state==='30'"
          >新版本
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['kms:main:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-preview"
            @click="handlePreviewAttFile(scope.row)"
            v-if="scope.row.fileName!=null"
          >预览附件
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-preview"
            @click="handleView(scope.row)"
          >查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改知识对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="知识标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入知识标题"/>
        </el-form-item>
        <el-form-item label="所属目录" prop="catalogId">
          <!--<el-input v-model="form.catalogId" placeholder="请输入所属目录" />-->
          <treeselect v-model="form.catalogId" :options="catalogOptions" :normalizer="normalizer"
                      placeholder="选择目录"/>
        </el-form-item>
        <!--<el-form-item label="所属部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入所属部门" />
        </el-form-item>-->
        <el-form-item label="作者" prop="author">
          <input-tag
            :data.sync="form.author.external"
            placeholder="输入作者名称，点击enter键添加"
            :check="form.author.check"
            :check-text="form.author.checkText"></input-tag>
        </el-form-item>
        <el-form-item label="知识摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="知识正文">
          <editor v-model="form.mainContent" :min-height="192"/>
          <!--知识正文-->
          <!--<ty-editor ref="editor" :content="form.mainContent"></ty-editor>-->
          <!--<vue-tinymce
            v-model="form.mainContent"
            :setting="setting">
          </vue-tinymce>-->
        </el-form-item>
        <el-form-item label="上传附件">
          <file-upload @input="getFileList" :value="form.filePath"></file-upload>
          <!--知识正文-->
          <!--<ty-editor ref="editor" :content="form.mainContent"></ty-editor>-->
          <!--<vue-tinymce
            v-model="form.mainContent"
            :setting="setting">
          </vue-tinymce>-->
        </el-form-item>
        <!-- <el-form-item label="关键字" prop="keyword">
           <el-input v-model="form.keyword" type="textarea" placeholder="请输入内容" />
         </el-form-item>-->
        <!--<el-form-item label="知识封面" prop="coverImg">
          <el-input v-model="form.coverImg" placeholder="请输入知识封面" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入状态" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishDate">
          <el-date-picker clearable
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="阅读量" prop="readCount">
          <el-input v-model="form.readCount" placeholder="请输入阅读量" />
        </el-form-item>-->
        <!--<el-form-item label="创建者" prop="createBy">
          <el-input v-model="form.createUser" placeholder="请输入创建者" />
        </el-form-item>-->
        <!--<el-form-item label="更新人" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入更新人" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(1)">提交</el-button>
        <el-button @click="submitForm(0)" v-if="form.state!=30">暂存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 附件预览对话框 -->
    <el-dialog :title="titlePreview" :visible.sync="openPreview" width="800px" append-to-body>
      <ul class="preview-ul">
        <li v-for="item in previewFile" @click="openFile(item.path,item.state)">
          {{ item.name }}
        </li>
      </ul>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelPreview">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 新版本-添加或修改知识对话框 -->
    <el-dialog :title="title" :visible.sync="openNew" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="知识标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入知识标题"/>
        </el-form-item>
        <el-form-item label="所属目录" prop="catalogId">
          <!--<el-input v-model="form.catalogId" placeholder="请输入所属目录" />-->
          <treeselect v-model="form.catalogId" :options="catalogOptions" :normalizer="normalizer"
                      placeholder="选择目录"/>
        </el-form-item>
        <!--<el-form-item label="所属部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入所属部门" />
        </el-form-item>-->
        <el-form-item label="作者" prop="author">
          <input-tag
            :data.sync="form.author.external"
            placeholder="输入作者名称，点击enter键添加"
            :check="form.author.check"
            :check-text="form.author.checkText"></input-tag>
        </el-form-item>
        <el-form-item label="知识摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="知识正文">
          <editor v-model="form.mainContent" :min-height="192"/>
          <!--知识正文-->
          <!--<ty-editor ref="editor" :content="form.mainContent"></ty-editor>-->
          <!--<vue-tinymce
            v-model="form.mainContent"
            :setting="setting">
          </vue-tinymce>-->
        </el-form-item>
        <el-form-item label="上传附件">
          <file-upload @input="getFileList" :value="form.filePath"></file-upload>
          <!--知识正文-->
          <!--<ty-editor ref="editor" :content="form.mainContent"></ty-editor>-->
          <!--<vue-tinymce
            v-model="form.mainContent"
            :setting="setting">
          </vue-tinymce>-->
        </el-form-item>
        <!-- <el-form-item label="关键字" prop="keyword">
           <el-input v-model="form.keyword" type="textarea" placeholder="请输入内容" />
         </el-form-item>-->
        <!--<el-form-item label="知识封面" prop="coverImg">
          <el-input v-model="form.coverImg" placeholder="请输入知识封面" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入状态" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishDate">
          <el-date-picker clearable
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="阅读量" prop="readCount">
          <el-input v-model="form.readCount" placeholder="请输入阅读量" />
        </el-form-item>-->
        <!--<el-form-item label="创建者" prop="createBy">
          <el-input v-model="form.createUser" placeholder="请输入创建者" />
        </el-form-item>-->
        <!--<el-form-item label="更新人" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入更新人" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(3)">提交</el-button>
        <el-button @click="submitForm(2)">暂存</el-button>
        <el-button @click="cancel('new')">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listMain, getMain, delMain, addMain, updateMain, previewAttFile} from "@/api/kms/main";
import {listCatalogForTree, listCatalogExcludeChild} from "@/api/kms/catalog";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import TyEditor from "./TyEditor";
import FileUpload from '../../components/FileUpload'
import InputTag from './components/form/input-tag'

export default {
  name: "Main",
  dicts: ['kms_main_state'],
  components: {TyEditor, FileUpload, Treeselect, InputTag},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 知识表格数据
      mainList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openNew: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        catalogId: null,
        deptId: null,
        mainContent: null,
        summary: null,
        keyword: null,
        coverImg: null,
        version: null,
        state: null,
        publishDate: null,
        readCount: null,
        createUser: null,
        updateUser: null,
      },
      // 表单参数
      form: {
        mainContent: '',
        author: {
          type: 'Internal',
          inside: [],
          external: [],
          check: false,
          checkText: ''
        },
      },
      // 表单校验
      rules: {
        title: [
          {required: true, message: '请输入知识标题', trigger: 'blur'},
          {min: 5, max: 30, message: '长度在 5 到 30 个字符', trigger: 'blur'}
        ],
        catalogId: [
          {required: true, message: '请选择目录', trigger: 'blur'},
        ],
        author: [
          {required: true, message: '请输入作者', trigger: 'blur'},
        ],
        summary: [
          {required: true, message: '请输入知识摘要', trigger: 'blur'},
          {min: 10, max: 100, message: '长度在 10 到 100 个字符', trigger: 'blur'}
        ]
      },
      setting: {
        menubar: false,
        // 工具栏
        toolbar: 'formatselect | fontselect | fontsizeselect | bold italic underline strikethrough | bullist numlist | alignleft aligncenter alignright alignjustify | outdent indent blockquote | undo redo | image media table link | code preview',
        toolbar_drawer: 'sliding',
        quickbars_selection_toolbar: 'removeformat | bold italic underline strikethrough | fontsizeselect forecolor backcolor',
        plugins: 'link image media table lists fullscreen quickbars advlist code preview media',//选择需加载的插件 ['','']
        language_url: '/tinymce/langs/zh_CN.js',//设置中文
        language: 'zh_CN',
        height: 352,
        images_upload_url: '/demo/upimg.php',
        toolbar_mode: 'wrap',// 宽度过小时，工具不隐藏
      },
      titlePreview: '',
      // 是否显示弹出层
      openPreview: false,
      previewFile: [],
      // 目录树选项
      catalogOptions: []
    };
  },
  filters: {
    stateFilter(s) {
      switch (s) {
        case '10':
          return '草稿'
        case '20':
          return '待审'
        case '30':
          return '发布'
      }
    },
    versionFilter(v) {
      if (v) {
        return v + ".0"
      }
      return '-'
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询知识列表 */
    getList() {
      this.loading = true;
      listMain(this.queryParams).then(response => {
        this.mainList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel(v) {
      if (v === 'new') {
        this.openNew = false;
        this.reset();
      } else {
        this.open = false;
        this.reset();
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        catalogId: null,
        deptId: null,
        mainContent: null,
        summary: null,
        keyword: null,
        coverImg: null,
        version: null,
        state: null,
        publishDate: null,
        readCount: null,
        createUser: null,
        createTime: null,
        updateUser: null,
        updateTime: null,
        author: {
          type: 'Internal',
          inside: [],
          external: [],
          check: false,
          checkText: ''
        }
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加知识";
      listCatalogForTree().then(response => {
        this.catalogOptions = this.handleTree(response.data, "id");
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMain(id).then(response => {
        console.log(response, 'handleUpdate')
        this.form = response.data;
        this.form.author = {
          type: 'Internal',
          inside: [],
          external: response.data.extAuthor,
          check: false,
          checkText: ''
        }
        const isNewVersion = this.form.isNewVersion
        const version = this.form.version
        if (isNewVersion === 0 && version > 1) {
          this.openNew = true;
          this.title = "修改知识新版本";
        } else {
          this.open = true;
          this.title = "修改知识";
        }
        listCatalogExcludeChild(row.id).then(response => {
          this.catalogOptions = this.handleTree(response.data, "id");
        });
      });
    },
    /** 新版本按钮操作 */
    handleNewVersion(row) {
      this.reset();
      const id = row.id || this.ids
      getMain(id).then(response => {
        this.form = response.data;
        this.form.author = {
          type: 'Internal',
          inside: [],
          external: response.data.extAuthor,
          check: false,
          checkText: ''
        }
        this.openNew = true;
        this.title = "创建知识新版本";
        this.form.currentVersionId = this.form.id
        this.form.id = null
        listCatalogExcludeChild(row.id).then(response => {
          this.catalogOptions = this.handleTree(response.data, "id");
        });
      });
    },
    /** 提交按钮 */
    submitForm(oper) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.extAuthor = this.form.author.external || []
          this.form.state = oper === 1 || oper === 3 ? '30' : '10'
          this.form.operation = oper
          this.form.publishDate = null
          this.form.updateTime = null
          this.form.updateBy = null
          this.form.updateUserId = null
          if (this.form.id != null) {
            updateMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.openNew = false;
              this.getList();
            });
          } else {
            addMain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.openNew = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除知识编号为"' + ids + '"的数据项？').then(function () {
        return delMain(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('kms/main/export', {
        ...this.queryParams
      }, `main_${new Date().getTime()}.xlsx`)
    },
    //获取附件列表
    getFileList(list, names, attMainIds) {
      //console.log('getFileList(list)=======list:',list)
      this.form.filePath = list
      this.form.fileName = names
      this.form.attMainIds = attMainIds
    },
    /** 预览附件操作 */
    handlePreview(row) {
      this.reset();
      const id = row.id || this.ids
      getMain(id).then(response => {
        const previewData = response.data;
        const paths = previewData.filePath.split(",")
        if (paths.length == 1) {
          this.openFile(previewData.filePath)
        } else {
          const names = previewData.fileName.split(",")
          this.previewFile = []
          for (let i in names) {
            this.previewFile.push({
              name: names[i],
              path: paths[i]
            })
          }
          this.openPreview = true;
          this.titlePreview = "附件预览";
        }
      });
    },
    /** 预览附件操作 new */
    handlePreviewAttFile(row) {
      this.reset();
      const id = row.id || this.ids
      previewAttFile(id).then(response => {
        const previewData = response.data;
        //console.log('previewAttFile=========,previewData:',previewData)
        if (previewData.length == 1) {
          this.openFile(previewData[0].converted, previewData[0].state)
        } else {
          this.previewFile = []
          for (let i in previewData) {
            this.previewFile.push({
              name: previewData[i].fileName,
              path: previewData[i].converted,
              state: previewData[i].state
            })
          }
          this.openPreview = true;
          this.titlePreview = "附件预览";
        }
      });
    },
    // 取消按钮
    cancelPreview() {
      this.openPreview = false;
      this.reset();
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
    /** 查看详情操作 */
    handleView: function (row) {
      const id = row.id;
      //this.$router.push("/kms/view");
      this.$router.push({path: '/kms/view', query: {id: id}})
    },
    /** 转换目录数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.title,
        children: node.children
      };
    },
  }
};
</script>

<style>
.preview-ul li {
  padding: 5px 10px;
  cursor: pointer;
  font-size: 15px;
  font-weight: 700;
}

.preview-ul li:hover {
  color: #409eff;
}

</style>
