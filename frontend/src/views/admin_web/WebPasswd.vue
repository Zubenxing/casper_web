<template>
  <div class="card passwd-card">
    <div class="passwd-header">
      <el-input v-model="search" placeholder="搜索网站名/账号" clearable class="search-input" @keyup.enter="fetchData" />
      <el-button type="primary" class="add-btn" @click="openAdd">新增</el-button>
    </div>
    <el-table :data="tableData" stripe class="passwd-table">
      <el-table-column prop="site_name" label="网站名称" min-width="200" />
      <el-table-column prop="username" label="账号" min-width="200" />
      <el-table-column prop="password" label="密码" min-width="200">
        <template #default="scope">
          <span v-if="showPwd[scope.$index]">{{ scope.row.password }}</span>
          <span v-else>******</span>a
          <el-button size="small" text @click="togglePwd(scope.$index)">
            {{ showPwd[scope.$index] ? '隐藏' : '显示' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="160" />
      <el-table-column label="操作" min-width="120">
        <template #default="scope">
          <el-button size="small" class="op-btn" @click="openEdit(scope.row)">编辑</el-button>
          <el-button size="small" class="op-btn" type="danger" @click="confirmDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="table-footer">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        v-model:current-page="page"
        @current-change="fetchData"
      />
    </div>
    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogMode==='add'?'新增账号密码':'编辑账号密码'" width="400px" class="round-dialog">
      <el-form :model="form" label-width="80px" class="round-form">
        <el-form-item label="网站名称">
          <el-input v-model="form.site_name" maxlength="32" />
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form.username" maxlength="32" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" maxlength="32" show-password />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" maxlength="64" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
    <!-- 删除确认弹窗 -->
    <el-dialog v-model="deleteDialog" title="确认删除" width="320px" class="round-dialog">
      <div style="padding: 16px 0; text-align:center;">确定要删除该账号密码信息吗？</div>
      <template #footer>
        <el-button @click="deleteDialog=false">取消</el-button>
        <el-button type="danger" @click="doDelete">删除</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElTable, ElTableColumn, ElButton, ElInput, ElDialog, ElForm, ElFormItem, ElPagination, ElMessage } from 'element-plus'
import axios from 'axios'

const tableData = ref([])
const showPwd = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = 10
const search = ref('')

const dialogVisible = ref(false)
const dialogMode = ref('add') // add | edit
const form = ref({ id: null, site_name: '', username: '', password: '', remark: '' })
const deleteDialog = ref(false)
const deleteId = ref(null)

const fetchData = async () => {
  const params = { page: page.value-1, size: pageSize, search: search.value }
  const res = await axios.get('/api/web_passwd/list', { params })
  const arr = res.data.data?.content || []
  tableData.value = arr
  total.value = res.data.data?.totalElements || arr.length
  showPwd.value = arr.map(() => false)
}

const togglePwd = (idx) => {
  showPwd.value[idx] = !showPwd.value[idx]
}

const openAdd = () => {
  dialogMode.value = 'add'
  form.value = { id: null, site_name: '', username: '', password: '', remark: '' }
  dialogVisible.value = true
}

const openEdit = (row) => {
  dialogMode.value = 'edit'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (!form.value.site_name || !form.value.username || !form.value.password) {
    ElMessage.error('请填写完整信息')
    return
  }
  if (dialogMode.value === 'add') {
    await axios.post('/api/web_passwd', form.value)
    ElMessage.success('添加成功')
  } else {
    await axios.put(`/api/web_passwd/${form.value.id}`, form.value)
    ElMessage.success('更新成功')
  }
  dialogVisible.value = false
  fetchData()
}

const confirmDelete = (row) => {
  deleteId.value = row.id
  deleteDialog.value = true
}

const doDelete = async () => {
  await axios.delete(`/api/web_passwd/${deleteId.value}`)
  ElMessage.success('删除成功')
  deleteDialog.value = false
  fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.card.passwd-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.07);
  width: 100%;
  margin: 0 ;
  padding: 32px 2% 24px 2%;
  min-height: 600px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box; 
}
.passwd-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 18px;
  padding: 0 1%;
}
.search-input {
  width: 260px;
  border-radius: 18px;
}
.add-btn {
  border-radius: 18px;
  font-weight: bold;
}
.passwd-table {
  background: #fff;
  color: #222;
  width: 100% !important;
  margin-bottom: 18px;
  border-radius: 12px;
  overflow: hidden;
}
.op-btn {
  border-radius: 14px;
  margin-right: 6px;
}
.table-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 8px;
}
.round-dialog .el-dialog {
  border-radius: 18px !important;
}
.round-form .el-input {
  border-radius: 14px;
}
</style> 