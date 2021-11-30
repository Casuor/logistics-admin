<template>
  <div>
    <!--start button-->
    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogFormVisibleOfInsert = true" icon="el-icon-circle-plus-outline">新增
        </el-button>
      </el-form-item>
      <el-divider direction="vertical"></el-divider>
      <el-form-item>
        <el-input
            placeholder="请输入产品名称..."
            v-model="searchForm.productName"
            clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchProduct(searchForm.productName)">查找</el-button>
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
          <el-button type="text" @click="editForm(scope.row.roleId)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="confirmToDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    end table-->


  </div>
</template>

<script>
export default {
  name: "product",
  data() {
    return {
      dialogFormVisibleOfInsert: false,
      delStatus: true,
      noSelected: true,
      searchForm: {
        productName: '',
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  }
}
</script>

<style scoped>
.el-divider {
  height: 40px;
}
</style>