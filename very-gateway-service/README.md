# very-gateway-service

统一网关服务，是后台管理系统和前台博客系统访问后端的唯一入口。

主要场景：

- 后台 `/admin/**` 请求路由与权限拦截
- 前台 `/blog/**` 请求路由与游客访问放行
- 跨域、限流、登录态校验、请求日志等网关能力

该服务基于 Spring Cloud Gateway，不拆分 `api/server` 子模块。
