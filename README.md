# Very Blog Backend

Very Blog 后端微服务父工程，负责统一管理 Maven 模块、依赖版本和构建配置。

当前阶段采用一套后端微服务体系，同时支撑后台管理系统和前台博客系统：

- 后台管理：文章管理、栏目/分类管理、标签管理、评论审核、文件管理、用户管理等。
- 前台博客：文章浏览、分类/标签浏览、归档、评论、点赞、收藏、留言等。

## 模块结构

```text
very-blog-backend
  very-common
    very-common-core
    very-common-web
    very-common-mybatis

  very-gateway-service

  very-user-service
    very-user-api
    very-user-server

  very-content-service
    very-content-api
    very-content-server

  very-interaction-service
    very-interaction-api
    very-interaction-server

  very-file-service
    very-file-api
    very-file-server
```

## 设计原则

- 父工程只管理版本、插件和模块，不承载业务代码。
- `api` 模块只放 DTO、VO、枚举、常量和 FeignClient。
- `server` 模块放 Controller、Service、Entity、Mapper 和业务实现。
- `common` 模块只沉淀真正跨服务复用的公共能力。
