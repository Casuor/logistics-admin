<template>
  <div>
    <!--start button-->
    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisible = true" icon="el-icon-circle-plus-outline">新增</el-button>
      </el-form-item>
    </el-form>
    <!--end button-->

    <!--    start table-->
    <el-table
        :data="tableData"
        style="width: 100%;"
        row-key="id"
        border
        stripe
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
          prop="name"
          label="名称">
      </el-table-column>

      <el-table-column
          prop="authority"
          label="权限编码"
          width="180">
      </el-table-column>

      <el-table-column
          prop="icon"
          label="图标">
      </el-table-column>

      <el-table-column
          prop="type"
          label="类型">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.type===0">目录</el-tag>
          <el-tag type="danger" size="small" v-else-if="scope.row.type===1">菜单</el-tag>
          <el-tag type="danger" size="small" v-else-if="scope.row.type===2">按钮</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="url"
          label="Url" width="180">
      </el-table-column>

      <el-table-column
          prop="component"
          label="Component" width="180">
      </el-table-column>

      <el-table-column
          prop="sort"
          label="排序"
          sortable>
      </el-table-column>

      <el-table-column
          prop="status"
          label="状态" width="180">
        <template slot-scope="scope">
          <el-tag type="success" size="small" v-if="scope.row.status===1">启用</el-tag>
          <el-tag type="danger" size="small" v-else-if="scope.row.status===0">禁用</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="operate"
          label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="editForm(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->

    <!--    start dialog-->
    <el-dialog title="新增菜单" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="addForm" :rules="rules" ref="addForm" label-width="100px">
        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="addForm.parentId" placeholder="请选择上级菜单">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id">
              </el-option>
              <template v-for="i in item.children">
                <el-option :label="i.name" :value="i.id">
                  <span>{{ '-' + ' ' + item.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="authority">
          <el-input v-model="addForm.authority"></el-input>
        </el-form-item>

        <el-form-item label="图标" prop="icon">
          <el-input v-model="addForm.icon"></el-input>
        </el-form-item>

        <el-form-item label="Url" prop="url">
          <el-input v-model="addForm.url"></el-input>
        </el-form-item>

        <el-form-item label="Component" prop="component">
          <el-input v-model="addForm.component"></el-input>
        </el-form-item>


        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="addForm.type">
            <el-radio label="目录"></el-radio>
            <el-radio label="菜单"></el-radio>
            <el-radio label="按钮"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="addForm.status">
            <el-radio label="启用"></el-radio>
            <el-radio label="禁用"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="排序" prop="sort">
          <template>
            <el-input-number v-model="sort" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('addForm')">提交</el-button>
      </div>
    </el-dialog>
    <!--    end dialog-->
  </div>
</template>

<script>
export default {
  name: "MenuInfo",
  data() {
    return {
      sort: 1,
      dialogFormVisible: false,
      addForm: {
        name: '',
        authority: '',
        icon: '',
        url: '',
        component: '',
        type: [],
        status: [],
        sort: ''
      },
      formLabelWidth: '120px',
      tableData: [],
      rules: {
        parentId: [
          {required: true, message: '请选择上级菜单', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入菜单名称', trigger: 'blur'},
          {min: 3, max: 6, message: '长度在 3 到 6 个字符', trigger: 'blur'}
        ],
        authority: [
          {required: true, message: '请选择权限类型', trigger: 'change'}
        ],
        type: [
          {type: 'array', required: true, message: '请至少选择一个类型', trigger: 'change'}
        ],
        status: [
          {type: 'array', required: true, message: '请至少选择一个状态', trigger: 'change'}
        ],
        sort: [
          {required: true, message: '请填写活动形式', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交成功
          this.$axios.post('/menuList/add' + (this.addForm.id ? 'edit' : 'add'), this.addForm).then(() => {
            this.$message({
              message: '添加成功！！！',
              type: 'success',
              onClose: () => {
                //更新表中数据
                this.initTables();
              }
            });
          })
          //添加成功后隐藏dialogForm
          this.dialogFormVisible = false;
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleClose(done) {
      this.resetForm('editForm');
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogFormVisible = false;
      this.addForm = {};
    }
    ,
    handleChange(value) {
      console.log(value);
    },
    confirmToDelete(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发起del请求
        this.$axios.post('/main/menuList/del' + id).then((res) => {
          this.initTables();
        })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    initTables() {
      this.$axios.get('/main/menuList').then(res => {
        this.tableData = res.data.data;
      })
    },
    editForm(id) {
      this.$axios.get('/main/menuList/edit' + id).then(res => {
        this.addForm = res.data.data;
      })
      this.dialogFormVisible = true
      // this.initTables();
    }
  },
  created() {
    //初始化菜单表格
    this.initTables();
  },
};
</script>

<style scoped>

</style>
