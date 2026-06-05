# 项目技术栈

该项目为个人博客网站，后端使用 Spring Boot + Spring Cloud Alibaba 微服务架构

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

- 优先保证代码可维护性
- 类、方法、变量都要有完整的 javadoc 注释，方法还需要有入参和出参的注释，包括方法体内，核心业务逻辑也需要有注释
- 不允许省略关键实现
- 优先使用 Lombok
- 无参构造和全参构造使用注解 @NoArgsConstructor 和 @AllArgsConstructor
- 禁止使用字段注入 @Autowired

## 优先复用

修改代码前：

1. 优先阅读现有实现
2. 优先复用已有代码
3. 优先复用已有工具类
4. 优先复用已有服务
5. 优先复用已有公共模块

禁止重复实现已有功能。

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

## 接口
- 只使用 POST 方法和 GET 方法，不要使用 DELETE 和 PUT 方法

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

# 枚举规范

DTO、VO、Entity 中如果字段取值来源于枚举类，必须在字段 Javadoc 中使用 `{@link}` 标明对应枚举类。

示例：

```java
/**
 * 文章状态，取值来源于 {@link ArticleStatusEnum}
 */
private Integer articleStatus;
```

禁止在字段注释中重复维护枚举值列表，避免枚举调整后注释不同步。

数据库字段注释只描述中文业务含义，不需要写 Java 枚举类名。

枚举类的 `code` 统一从 `0` 开始，后续值按 `1、2、3...` 递增。

枚举 `code` 一旦入库，不允许随意修改；新增枚举值优先追加到末尾，避免影响历史数据。

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
