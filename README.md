1. 功能需求规格
1.1 用户模块 (User Module)

- 注册：用户输入手机号，系统通过第三方服务（短信宝）发送验证码，用户凭验证码完成注册。
- 登录：已注册用户通过手机号+验证码的方式登录。
- 用户信息：用户可以设置和修改自己的昵称、头像。

1.2 频道模块 (Channel Module)
- 创建频道：登录用户可以创建新的聊天频道。
- 创建者即管理员：创建频道的用户默认为该频道的管理员。
- 订阅/加入频道：用户可以发现并加入公开频 道，或通过邀请加入私有频道。
- 消息收发：频道内成员可以发送和接收实时消息（文本、图片等）。

1.3 权限管理模块 (Permission Module)

- 管理员指定：频道创建者可以将其他成员设置或取消管理员权限。
- 禁言：管理员可以对频道内的某个成员进行禁言操作（在一定时间内或永久）。
- 踢出成员：管理员可以将成员从频道中移除。

1.4 机器人模块 (Bot Module) - 核心亮点
- 每个频道有唯一的bot
- Token生成：每个机器人生成一个唯一的认证token，用于API调用。
- 智能分析：
  - 消息总结：机器人可以被@调用后选择指令，对指定时间段内的频道聊天记录进行总结，并生成摘要。
  - 聊天：机器人可以被@调用，在不选择指令的情况下，bot将对该条聊天内容回复
- 机器人SDK：提供一个简单易用的SDK，方便开发者（或用户）基于token快速开发自定义机器人功能。

2. 系统架构设计
graph TD
    subgraph 用户端
        A[前端应用 (Web/Mobile)]
    end

    subgraph 后端服务
        B[API网关 (Gateway)]
        C[用户服务 (User Service)]
        D[频道服务 (Channel Service)]
        E[消息服务 (Message Service with WebSocket)]
        F[机器人服务 (Bot Service)]
        G[短信发送服务 (SMS Service)]
    end

    subgraph 基础设施
        H[消息队列 (Message Queue - 如RabbitMQ/Kafka)]
        I[数据库 (Database - 如PostgreSQL/MongoDB)]
        J[缓存 (Cache - 如Redis)]
        K[短信宝API (smsbao.com)]
    end

    A --> B
    B --> C
    B --> D
    B --> E
    B --> F
    C --> I
    C --> J
    D --> I
    E --> I
    F --> I
    E -- 实时消息 --> A

    subgraph 短信异步发送流程
        C -- 注册/登录请求 --> H
        G -- 消费请求 --> H
        G -- 发送短信 --> K
    end

3. 技术选型 (Tech Stack)

- 前端：Vue.js
- 后端：Springboot
- 数据库：Mysql
- 实时通信：WebSocket
- 消息队列：RabbitMQ
- 缓存：Redis (存储Session, 验证码)
- 部署：Docker

4. 数据库设计
- users表
  - id BIGINT UNSIGNED
  - user_type ENUM('human', 'bot')
  - phone_number VARCHAR
  - nickname VARCHAR
  - avatar_url VARCHAR
  - create_time DATETIME
  - update_time DATETIME
- bots表
  - id BIGINT UNSIGNED
  - bot_user_id  --对应 users 表中机器人的ID
  - token VARCHAR  -- 唯一的认证Token
  - create_time
  - FOREIGN KEY (bot_user_id) REFERENCES users(id)
- channels表
  - id BIGINT UNSIGNEDD
  - name VARCHAR
  - creator_id（foreign key users.id)
  - is_public TINYINT
  - create_time
  - update_time
- channel_members表
  - id
  - channel_id
  - user_id
  - role ENUM('creator', 'admin', 'member')
  - is_muted TINYINT
  - muted_until DATETIME
  - create_time
- messages表
  - id
  - channel_id (foreign key channels.id)
  - sender_id (foreign key users.id/bots.id)
  - content_type ENUM('text', 'image', 'ai_summary')
  - content TEXT
  - metadata JSON(ai调用的元数据)
  - create_time

