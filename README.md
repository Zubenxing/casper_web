# 项目名称
# casper_web
## 项目简介

本项目为单体多功能Web系统，采用前后端分离架构。前端使用Vue3，后端基于Java 17，数据库为MySQL 8，缓存使用Redis。后端支持JWT认证，所有API接口通过Swagger自动生成文档，便于开发和测试。

## 技术栈

- 前端：Vue3
- 后端：Java 17（Spring Boot）
- 数据库：MySQL 8
- 缓存：Redis
- API文档：Swagger（Springfox或Springdoc）
- 认证：JWT

## 目录结构设计

### 后端

后端代码采用**按功能模块分目录**的方式，每个功能独立一个目录，便于维护和扩展。例如：

```
backend/
│
├── web_passwd/    # 账号密码管理功能
│   ├── controller/
│   ├── service/
│   ├── repository/
│   └── ...
│
├── jwt_auth/      # JWT认证相关功能
│   ├── controller/
│   ├── service/
│   └── ...
│
├── common/        # 公共工具类、配置等
│
└── ...
```

### 前端

前端为单一Vue3项目，按功能模块分目录：

```
frontend/
├── src/
│   ├── views/
│   │   ├── auth_web/      # 登录相关页面
│   │   ├── admin_web/     # 后台管理相关页面
│   │   └── ...
│   ├── components/        # 公共组件
│   ├── api/               # 接口封装
│   ├── router/            # 路由配置
│   ├── store/             # 状态管理
│   ├── assets/            # 静态资源
│   └── App.vue
├── public/
└── package.json
```

- `views/auth_web/`：登录页面
- `views/admin_web/`：后台管理页面（如仪表盘、账号管理等）
- 通过路由和路由守卫实现登录与后台页面的切换

## 开发流程

1. **初始化后端项目**  
   使用Spring Boot搭建基础项目，集成MySQL、Redis、Swagger等依赖。
2. **开发基础功能模块**  
   例如：`web_passwd`（账号密码管理）、`jwt_auth`（JWT认证）。
3. **接口文档**  
   通过Swagger自动生成API文档，访问`/swagger-ui.html`即可查看所有接口。
4. **前端开发**  
   使用Vue3开发单一前端项目，通过API与后端交互，登录与后台管理页面通过路由切换。

## Swagger简介

Swagger是一套开放源代码的API文档生成工具，集成到Spring Boot后，可以自动扫描Controller生成接口文档，方便前后端联调和测试。

- 访问方式：`http://localhost:8080/swagger-ui.html`
- 常用依赖：`springfox-swagger2` 或 `springdoc-openapi-ui`

## 环境要求

- JDK 17
- Node.js 16+
- MySQL 8
- Redis

## 启动方式

1. 启动MySQL和Redis服务
2. 后端：`cd backend && ./mvnw spring-boot:run`
3. 前端：`cd frontend && npm install && npm run dev` 