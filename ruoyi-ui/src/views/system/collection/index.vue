<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="http方法" prop="httpMethod">
        <el-select v-model="queryParams.httpMethod" placeholder="请选择http执行方式" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_http_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="定时任务" prop="cronFlag">
        <el-select v-model="queryParams.cronFlag" placeholder="是否启动定时任务" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_support"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="定时配置" prop="cronParam">
        <el-input
          v-model="queryParams.cronParam"
          placeholder="请输入定时任务配置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持https" prop="httpsFlag">
        <el-select v-model="queryParams.httpsFlag" placeholder="是否支持https" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_support"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="代理支持" prop="proxyFlag">
        <el-select v-model="queryParams.proxyFlag" placeholder="请选择代理支持" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_support"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="adminId">
        <el-input
          v-model="queryParams.adminId"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['system:collection:add']"
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
          v-hasPermi="['system:collection:edit']"
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
          v-hasPermi="['system:collection:remove']"
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
          v-hasPermi="['system:collection:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="collectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--<el-table-column label="" align="center" prop="id" />-->
      <el-table-column label="执行url" align="center" prop="httpUrl" width="250"/>
      <el-table-column label="http方式" align="center" prop="httpMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_http_method" :value="scope.row.httpMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="参数配置" align="center" prop="httpParam" width="120"/>
      <el-table-column label="数据裁剪" align="center" prop="dataDeal" width="120"/>
      <el-table-column label="定时任务" align="center" prop="cronFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_support" :value="scope.row.cronFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="定时配置" align="center" prop="cronParam"/>
      <el-table-column label="https支持" align="center" prop="httpsFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_support" :value="scope.row.httpsFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="https证书" align="center" prop="httpsCertificate"/>
      <el-table-column label="代理支持" align="center" prop="proxyFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_support" :value="scope.row.proxyFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="代理参数" align="center" prop="proxyParam"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="adminId"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:collection:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:collection:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handleExecUrl(scope.row)"
            v-hasPermi="['system:collection:exec']"
          >执行
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

    <!-- 添加或修改http数据收集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="执行url" prop="httpUrl">
          <el-input v-model="form.httpUrl" type="textarea" placeholder="执行的URL"/>
        </el-form-item>
        <el-form-item label="http方式" prop="httpMethod">
          <el-select v-model="form.httpMethod" placeholder="http执行方式">
            <el-option
              v-for="dict in dict.type.sys_http_method"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参数配置" prop="httpParam">
          <el-input v-model="form.httpParam" type="textarea" placeholder="json格式,可以空或者写{}"/>
        </el-form-item>
        <el-form-item label="数据裁剪" prop="dataDeal">
          <el-input v-model="form.dataDeal" type="textarea" placeholder="json格式,可以空或者写{}"/>
        </el-form-item>
        <el-form-item label="定时任务" prop="cronFlag">
          <el-select v-model="form.cronFlag" placeholder="请选择是否启动定时任务">
            <el-option
              v-for="dict in dict.type.sys_support"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="定时配置" prop="cronParam">
          <el-input v-model="form.cronParam" placeholder="正常cron,如:0/5 * * * * ?"/>
        </el-form-item>
        <el-form-item label="https支持" prop="httpsFlag">
          <el-select v-model="form.httpsFlag" placeholder="请选择是否支持https">
            <el-option
              v-for="dict in dict.type.sys_support"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="https证书" prop="httpsCertificate">
          <el-input v-model="form.httpsCertificate" type="textarea" placeholder="本系统证书路径.cer文件,如:/data/server/http.cer"/>
        </el-form-item>
        <el-form-item label="代理支持" prop="proxyFlag">
          <el-select v-model="form.proxyFlag" placeholder="请选择代理支持">
            <el-option
              v-for="dict in dict.type.sys_support"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="代理参数" prop="proxyParam">
          <el-input v-model="form.proxyParam" type="textarea"
                    placeholder="json格式:{useName:xx,passWord:xx,hostName:xx,port:xx}"/>
        </el-form-item>
        <!--<el-form-item label="创建人" prop="adminId">
          <el-input v-model="form.adminId" placeholder="请输入创建人"/>
        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {
        addCollection,
        delCollection,
        execCollection,
        getCollection,
        listCollection,
        updateCollection
    } from "@/api/system/collection";

    export default {
        name: "Collection",
        dicts: ['sys_normal_disable', 'sys_http_method', 'sys_support'],
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
                // http数据收集表格数据
                collectionList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    httpUrl: null,
                    httpMethod: null,
                    httpParam: null,
                    dataDeal: null,
                    cronFlag: null,
                    cronParam: null,
                    httpsFlag: null,
                    httpsCertificate: null,
                    proxyFlag: null,
                    proxyParam: null,
                    adminId: null,
                    rm1: null,
                    rm2: null
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    httpUrl: [
                        {required: true, message: "执行url不能为空", trigger: "blur"}
                    ],
                    httpMethod: [
                        {required: true, message: "http执行方式不能为空", trigger: "change"}
                    ],
                    cronFlag: [
                        {required: true, message: "是否支持定时任务不能为空", trigger: "change"}
                    ],
                    httpsFlag: [
                        {required: true, message: "是否支持https不能为空", trigger: "change"}
                    ],
                    proxyFlag: [
                        {required: true, message: "代理支持不能为空", trigger: "change"}
                    ],
                }
            };
        },
        created() {
            this.getList();
        },
        methods: {
            /** 查询http数据收集列表 */
            getList() {
                this.loading = true;
                listCollection(this.queryParams).then(response => {
                    this.collectionList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                    id: null,
                    httpUrl: null,
                    httpMethod: null,
                    httpParam: null,
                    dataDeal: null,
                    cronFlag: null,
                    cronParam: null,
                    httpsFlag: null,
                    httpsCertificate: null,
                    proxyFlag: null,
                    proxyParam: null,
                    createTime: null,
                    updateTime: null,
                    adminId: null,
                    remark: null,
                    rm1: null,
                    rm2: null
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
                this.title = "添加http数据收集";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                const id = row.id || this.ids
                getCollection(id).then(response => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "修改http数据收集";
                });
            },
            /** 提交按钮 */
            submitForm() {
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        if (this.form.id != null) {
                            updateCollection(this.form).then(response => {
                                this.$modal.msgSuccess("修改成功");
                                this.open = false;
                                this.getList();
                            });
                        } else {
                            addCollection(this.form).then(response => {
                                this.$modal.msgSuccess("新增成功");
                                this.open = false;
                                this.getList();
                            });
                        }
                    }
                });
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const ids = row.id || this.ids;
                this.$modal.confirm('是否确认删除http数据收集编号为"' + ids + '"的数据项？').then(function () {
                    return delCollection(ids);
                }).then(() => {
                    this.getList();
                    this.$modal.msgSuccess("删除成功");
                }).catch(() => {
                });
            },
            /** 执行数据收集 */
            handleExecUrl(row) {
                const id = row.id || this.ids;
                execCollection(id).then(response => {
                    this.$modal.msgSuccess("执行成功");
                    this.open = false;
                    this.getList();
                });
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('system/collection/export', {
                    ...this.queryParams
                }, `collection_${new Date().getTime()}.xlsx`)
            }
        }
    };
</script>
