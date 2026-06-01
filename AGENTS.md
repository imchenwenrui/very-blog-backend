# 项目技术栈

本项目技术栈：

- Java 17
- Spring Boot 3.2.12
- Spring Cloud 2023.0.5
- Spring Cloud Alibaba 2023.0.3.3
- Nacos 2.4.3
- OpenFeign
- MyBatis Plus 3.5.12
- MySQL
- Redis 7.4
- Kafka
- Maven
- Elasticsearch 8.17.x
- Hutool 5.8.x
- Sa-Token 1.44

生成代码时必须兼容以上版本。

---

# 核心原则

## 优先复用

修改代码前：

1. 优先阅读现有实现
2. 优先复用已有代码
3. 优先复用已有工具类
4. 优先复用已有服务
5. 优先复用已有公共模块

禁止重复实现已有功能。

---

## 最小修改原则

仅修改与需求相关代码。

禁止：

- 大规模重构
- 调整包结构
- 修改无关代码
- 修改无关格式

---

# 微服务结构

标准结构：

- very-common
  - very-common-core
  - very-common-web
  - very-common-mybatis
- very-gateway-service

- very-user-service
  - very-user-api
  - very-user-server
- very-content-service
  - very-content-api
  - very-content-server
- very-interaction-service
  - very-interaction-api
  - very-interaction-server
- very-file-service
  - very-file-api
  - very-file-server

---

# api模块规范

仅允许存放：

- DTO
- VO
- Enum
- Constant
- FeignClient

禁止：

- Entity
- Mapper
- Service
- 业务逻辑

---

# Controller规范

## 入参

统一使用DTO。

示例：

UserCreateDTO

UserUpdateDTO

UserQueryDTO

禁止：

- Entity
- Map
- JSONObject

---

## 返回值

统一返回：

Result<T>

示例：

Result<UserVO>

Result<PageResult<UserVO>>

禁止：

- 返回Entity
- 返回Map
- 返回Object

---

# DTO规范

请求对象：

XXXDTO

示例：

UserCreateDTO

UserUpdateDTO

UserQueryDTO

BatchDeleteDTO

---

# VO规范

响应对象：

XXXVO

示例：

UserVO

UserDetailVO

RoleVO

---

# Entity规范

数据库实体：

XXXEntity

示例：

UserEntity

RoleEntity

OrderEntity

Entity仅用于数据持久化。

禁止返回给前端。

---

# Service规范

接口：

UserService

OrderService

RoleService

实现：

UserServiceImpl

OrderServiceImpl

RoleServiceImpl

---

# Mapper规范

简单查询：

优先使用LambdaQueryWrapper

复杂查询：

使用XML

以下情况必须使用XML：

- 多表关联
- 分组统计
- 动态复杂查询
- 报表查询

禁止在Java代码中拼接复杂SQL。

---

# 对象转换规范

统一使用Convert类。

示例：

UserConvert

RoleConvert

OrderConvert

禁止：

业务代码中大量BeanUtils.copyProperties

禁止：

Controller进行对象转换

---

# Feign规范

服务间调用统一使用Feign。

示例：

@FeignClient(value = "user-service")

public interface UserFeignClient

Feign接口统一放在：

xxx-api

模块。

禁止直接调用HTTP接口。

---

# 事务规范

事务必须放在Service层。

示例：

@Transactional(rollbackFor = Exception.class)

禁止：

Controller层开启事务。

---

# Redis规范

Redis Key统一管理。

示例：

RedisKeyConstants

禁止：

业务代码中硬编码Key。

---

# Kafka规范

Topic统一定义：

KafkaTopicConstants

消费者：

consumer

生产者：

producer

目录分离。

禁止硬编码Topic名称。

---

# 日志规范

统一使用：

@Slf4j

参数化日志：

log.info("创建用户成功,userId={}", userId);

禁止：

log.info("userId=" + userId);

---

# 异常处理规范

统一使用：

GlobalExceptionHandler

禁止：

try-catch后仅打印日志。

禁止吞异常。

---

# 接口规范

新增：

createUser

修改：

updateUser

删除：

deleteUser

批量删除：

batchDeleteUsers

详情：

getUserDetail

分页：

pageUsers

列表：

listUsers

---

# AI助手执行规范

收到需求后：

1. 分析现有代码结构
2. 优先复用已有实现
3. 保持现有代码风格
4. 不新增重复DTO
5. 不新增重复VO
6. 不新增重复Service
7. 不进行无关重构

输出前检查：

- Java17兼容
- SpringBoot2.7兼容
- DTO规范
- VO规范
- Feign规范
- 事务规范
- 日志规范
- Mapper规范

修改完成后必须说明：

修改文件：

- UserController.java
- UserService.java
- UserServiceImpl.java

修改内容：

1. 新增用户分页查询
2. 增加状态筛选
3. 补充参数校验