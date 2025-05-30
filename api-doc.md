# API文档

## 1. 账号密码管理（web_passwd）

### 1.1 新增账号密码
- 接口：`POST /api/web_passwd`
- 描述：新增一个网站账号密码信息
- 请求参数：
  - site_name（string）：网站名称
  - username（string）：账号
  - password（string）：密码
  - remark（string，可选）：备注
- 返回：
  - code（int）
  - message（string）
  - data（object）

### 1.2 查询账号密码列表
- 接口：`GET /api/web_passwd/list`
- 描述：获取所有账号密码信息
- 请求参数：
  - 无
- 返回：
  - code（int）
  - message（string）
  - data（array）

### 1.3 删除账号密码
- 接口：`DELETE /api/web_passwd/{id}`
- 描述：删除指定ID的账号密码信息
- 请求参数：
  - id（path参数，long）：账号密码ID
- 返回：
  - code（int）
  - message（string）


## 2. JWT认证（jwt_auth）

### 2.1 用户登录
- 接口：`POST /api/auth/login`
- 描述：用户登录，获取JWT Token
- 请求参数：
  - username（string）：用户名
  - password（string）：密码
- 返回：
  - code（int）
  - message（string）
  - data（object）
    - token（string）：JWT Token

### 2.2 校验Token
- 接口：`POST /api/auth/verify`
- 描述：校验JWT Token是否有效
- 请求参数：
  - token（string）：JWT Token
- 返回：
  - code（int）
  - message（string）
  - data（object）
    - valid（boolean）：是否有效


---

> 后续如有新功能，按模块继续补充接口文档。 