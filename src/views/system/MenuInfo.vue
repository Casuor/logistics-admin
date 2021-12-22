<template>
  <div>
    <!--start button-->
    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisible = true;selectStatus=false;dialogTitle='新增菜单'"
                   icon="el-icon-circle-plus-outline">新增
        </el-button>
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
        default-expand-all
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
          prop="name"
          label="名称" width="200px">
      </el-table-column>

      <el-table-column
          prop="premiss"
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
          prop="path"
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

    <!--    start insert and edit dialog-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="100px">

        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="addForm.parentId" placeholder="请选择上级菜单" :disabled="selectStatus">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>
              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ "- " + child.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="premiss">
          <el-input v-model="addForm.premiss"></el-input>
        </el-form-item>

        <el-form-item label="图标" prop="icon">
          <el-input v-model="addForm.icon"></el-input>
        </el-form-item>

        <el-form-item label="Url" prop="path">
          <el-input v-model="addForm.path"></el-input>
        </el-form-item>

        <el-form-item label="Component" prop="component">
          <el-input v-model="addForm.component"></el-input>
        </el-form-item>


        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="addForm.type">
            <el-radio :label=0>目录</el-radio>
            <el-radio :label=1>菜单</el-radio>
            <el-radio :label=2>按钮</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="addForm.status">
            <el-radio :label=0>禁用</el-radio>
            <el-radio :label=1>启用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="addForm.sort" @change="handleChange" :min="1" label="排序号"></el-input-number>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitForm('addForm')">提交</el-button>
      </div>
    </el-dialog>
    <!--    end insert and edit dialog-->
  </div>
</template>

<script>
export default {
  name: "MenuInfo",
  data() {
    return {
      selectStatus: false,
      dialogTitle: "",
      dialogFormVisible: false,
      addForm: {},
      tableData: [],
      addFormRules: {
        parentId: [
          {required: true, message: '请选择上级菜单', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入菜单名称', trigger: 'blur'},
        ],
        premiss: [
          {required: true, message: '请填写权限编码', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请至少选择一个类型', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请至少选择一个状态', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '请选择排序', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交成功
          this.$axios.post('/sys/menu/' + (this.addForm.id ? 'update' : 'insert '), this.addForm)
              .then(() => {
                this.$message({
                  message: '操作成功！！！',
                  type: 'success',
                  onClose: () => {
                    //更新表中数据
                    this.initTables();
                  }
                });
              })
          //添加成功后隐藏dialogForm
          // this.dialogFormVisible = false;
          this.handleClose();
        } else {
          console.log('error submit!!');
          this.$refs[formName].resetFields();
          return false;
        }
      });
    },
    handleClose() {
      this.resetForm('addForm');
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogFormVisible = false;
      this.addForm = {};
    },
    handleChange(value) {
      console.log(value);
    },
    confirmToDelete(id) {
      this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发起del请求
        this.$axios.post('/sys/menu/delete/' + id).then((res) => {
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
      this.$axios.get('/sys/menu/list').then(res => {
        this.tableData = res.data.data;
      })
    },
    //编辑菜单
    editForm(id) {
      this.$axios.get('/sys/menu/info/' + id).then(res => {
        this.addForm = res.data.data;

        if (res.data.data.parentId === 0) {
          console.log("parentId:", res.data.data.parentId);
          this.selectStatus = true
        }
        this.dialogTitle = "编辑菜单"
        this.dialogFormVisible = true
      })
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
