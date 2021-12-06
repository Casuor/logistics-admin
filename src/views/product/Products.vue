<template>
  <div>
    <!--start button-->
    <el-form :inline="true" :model="productForm">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true" icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-divider direction="vertical"></el-divider>
      <el-form-item>
        <el-input
            placeholder="请输入产品名称..."
            v-model="productForm.productName"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchProduct(productForm.productName)">查找</el-button>
      </el-form-item>
      <el-form-item>
        <el-select v-model="productStatus" placeholder="产品状态">
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
        <el-button type="danger" @click="delProduct" icon="el-icon-delete" :disabled="delStatus">批量删除
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
          prop="productId"
          label="产品ID">
      </el-table-column>
      <el-table-column
          prop="productName"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="productCategory"
          label="产品类别">
      </el-table-column>
      <el-table-column
          prop="productDetails"
          label="产品规格">
      </el-table-column>
      <el-table-column
          prop="signDate"
          sortable
          label="上架时间">
      </el-table-column>
      <el-table-column
          prop="productStatus"
          label="产品状态"
          show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tooltip :content="'产品状态: ' +scope.row.productStatus " placement="top">
            <el-switch
                v-model="scope.row.productStatus"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="上架"
                inactive-value="下架">
            </el-switch>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
          prop="operate"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editProduct">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->
    <!--    start Pagination-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :total="totalPages">
    </el-pagination>
    <!--    END Pagination-->

  </div>
</template>

<script>
export default {
  name: "Products",
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      delStatus: true,
      noSelected: true,
      currentPage: 1,
      pageSize: 10,
      totalPages: 200,
      productForm: {
        productId: '',
        productCategory: '',
        productName: '',
        productDetails: '',
        productStatus: '',
      },
      productStatus: '',
      options: [
        {
          value: 'normal',
          label: '上架'
        },
        {
          value: 'disable',
          label: '下架'
        }],
      tableData: [
        {
          productId: 1,
          productCategory: '水果',
          productName: '柚子',
          productDetails: '200克',
          productStatus: '上架',
        }
      ]
    }
  },
  methods: {
    searchProduct() {

    },
    delProduct() {

    },
    upProduct() {

    },
    downProduct() {

    },
    editProduct() {

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    confirmToDelete() {

    },
    //分页处理
    handleSizeChange() {

    },
    handleCurrentChange() {
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