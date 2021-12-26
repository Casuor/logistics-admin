<template>
  <div>
    <!--start button-->
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true;dialogTitle='新增产品';"
                   icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-input
            placeholder="请输入产品名称..."
            v-model="searchForm.name"
            @clear="initProduct"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="initProduct(searchForm.name)">查找</el-button>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.status" clearable placeholder="产品状态"
                   @change="handleSelectChange(searchForm.status)" @clear="initProduct">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-divider direction="vertical"></el-divider>

      <el-form-item>
        <el-button type="danger" @click="deleteProduct(searchForm.id)" icon="el-icon-delete" :disabled="delBtnStatus"
                   plain>
          批量删除
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="upProduct" icon="el-icon-caret-top" :disabled="noSelected" plain>上架
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="downProduct" icon="el-icon-caret-bottom" :disabled="noSelected" plain>下架
        </el-button>
      </el-form-item>
    </el-form>
    <!--end button-->

    <!--    start table-->
    <el-table
        ref="multipleTable"
        :data="tableData"
        v-loading="loading"
        element-loading-text="拼命加载中..."
        element-loading-spinner="el-icon-loading"
        tooltip-effect="dark"
        style="width: 100%"
        stripe
        :border="true"
        :header-cell-style="{background:'#f8f8f9',color:'#282a36'}"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection">
      </el-table-column>
      <el-table-column
          prop="name"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="ptype"
          label="产品类别">
      </el-table-column>
      <el-table-column
          prop="quality"
          label="产品规格">
      </el-table-column>
      <el-table-column
          prop="price"
          label="产品单价">
      </el-table-column>
      <el-table-column
          prop="created"
          sortable
          label="上架时间">
      </el-table-column>
      <el-table-column
          prop="status"
          label="产品状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.status"
              @change="handleSwitchStatus(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0">
          </el-switch>
          <el-divider direction="vertical"></el-divider>
          <el-tag size="small" v-if="scope.row.status === 1" type="success">上架</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 0" type="danger">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="operate"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editProduct(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="deleteProduct(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->
    <!--    start Pagination-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :total="total">
    </el-pagination>
    <!--    END Pagination-->

    <!--    start insert and edit form dialog-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisibleOfInsert" :before-close="handleClose">
      <el-form :model="productForm" :rules="productFormRules" ref="productForm" label-width="100px">
        <el-form-item label="产品名称" prop="name">
          <el-input v-model="productForm.name"></el-input>
        </el-form-item>
        <el-form-item label="产品类型" prop="role">
          <el-select v-model="productForm.ptype" placeholder="请选择产品类型" @change="selectType">
            <el-option
                v-for="item in productTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品规格" prop="quality">
          <el-input-number v-model="productForm.quality" :precision="2" :step="0.1" :min="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="产品单价" prop="price">
          <el-input-number v-model="productForm.price" :precision="2" :step="0.1" :min="0.01"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleOfInsert = false;productForm={}">取 消</el-button>
        <el-button type="primary" @click="submitForm('productForm');productForm={}">确认</el-button>
      </div>
    </el-dialog>
    <!--    end insert and edit form dialog-->

  </div>
</template>

<script>
export default {
  name: "Products",
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      delBtnStatus: true,
      noSelected: true,
      loading: false,
      current: 1,
      size: 10,
      total: 0,
      dialogTitle: '',
      productForm: {},
      searchForm: {},
      multipleSelection: [],
      options: [
        {
          value: '1',
          label: '上架'
        },
        {
          value: '0',
          label: '下架'
        }],
      productTypes: [
        {
          value: '水果',
          label: '水果'
        },
        {
          value: '蔬菜',
          label: '蔬菜'
        }
      ],
      tableData: [],
      productFormRules: {}
    }
  },
  created() {
    this.initProduct()
  },
  methods: {
    initProduct() {

      this.loading = true;

      this.$axios.get('/sys/product/list', {
        params: {
          name: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false
        if (this.total === 0) {
          this.$notify({
            title: '提示',
            dangerouslyUseHTMLString: true,
            message: '<strong><i>查不到这个产品啊，亲！</i></strong>'
          });
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/product/' + (this.productForm.id ? 'update' : 'insert'), this.productForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功',
                  type: 'success',
                  onClose: () => {
                    this.initProduct()
                  }
                });
                this.dialogFormVisibleOfInsert = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    selectType() {
    },
    //删除产品
    deleteProduct(id) {
      this.$confirm('此操作将永久删除该产品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = []
        if (id) {
          ids.push(id)
        } else {
          this.multipleSelection.forEach(row => {
            ids.push(row.id)
          })
        }
        // 发起del请求
        this.$axios.post('/sys/product/delete/' + ids).then((res) => {
          this.initProduct();
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
    //上架
    upProduct() {
      this.multipleSelection.forEach(e => {
        e.status = 1;
        this.handleSwitchStatus(e);
      })
      this.$message({
        showClose: true,
        message: '上架成功！',
        type: 'success',
        onClose: () => {
          this.initProduct()
        }
      });
    },
    //下架
    downProduct() {
      this.multipleSelection.forEach(e => {
        e.status = 0;
        this.handleSwitchStatus(e);
      })
      this.$message({
        showClose: true,
        message: '下架成功！',
        type: 'success',
        onClose: () => {
          this.initProduct()
        }
      });
    },
    handleClose() {
      this.$refs['productForm'].resetFields();
      this.dialogFormVisibleOfInsert = false;
      this.productForm = {};
    },
    editProduct(id) {
      this.dialogTitle = "编辑产品"
      this.$axios.get('/sys/product/info/' + id).then(res => {
        this.productForm = res.data.data
        console.log("product:", this.productForm)
        this.dialogFormVisibleOfInsert = true
      })
    },
    //表格多选数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("数据：", this.multipleSelection);
      //多选multipleSelection删除
      this.delBtnStatus = val.length === 0;
      this.noSelected = val.length === 0;
    },
    //上下架商品
    handleSwitchStatus(data) {
      console.log("switch status:", data.id)
      this.$axios.post('/sys/product/' + (data.id ? 'update' : 'insert'), data)
          .then(res => {
                this.initProduct();
              }
          )
    },
    handleSelectChange(value) {
      console.log("select:", value)
      this.loading = true;
      this.$axios.get('/sys/product/search', {
        params: {
          status: this.searchForm.status,
          current: this.current,
          size: this.size
        }
      }).then((res) => {
        this.tableData = res.data.data.records;
        this.size = res.data.data.size;
        this.current = res.data.data.current;
        this.total = res.data.data.total;
        this.loading = false;
      })
    },
    //分页处理
    //分页选择每页条数改变
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.initProduct()
    },
    //处理当前页面改变
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.initProduct()
    }
  }
}
</script>

<style scoped>
.el-divider {
  height: 40px;
}

.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>