# redis基本数据类型

### 1. String (字符串)

* 可以存储二进制安全的字符串、整数、浮点数。支持原子性的自增/自减 (`INCR`, `DECR`, `INCRBY`)。内部采用预分配冗余空间的方式来优化追加操作，减少内存重分配。最大可存储512MB。

* 缓存用户登录后的会话信息（Session ID -> User Data）。

* 缓存热点数据，比如某篇热门点评的内容，减少数据库查询。

* 用作计数器，如记录文章的阅读量 (`INCR article:123:views`)。

  ```sql
  set username luotao
  get username
  -- 插入多个kv
  mset username luotao age 18
  -- 获取多个v
  mget username age
  
  -- 自增自减
  incr age
  incrby age 步长
  decr age
  decrby age 步长
  ```

  

```shell
# 设置用户信息，有效期1小时
# 命令: SET user:1000:info "{\"name\":\"Alice\", \"email\":\"alice@example.com\"}" EX 3600
# 输出: OK

# 获取用户信息
# 命令: GET user:1000:info
# 输出: "{\"name\":\"Alice\", \"email\":\"alice@example.com\"}"

# 网站访问计数器
# 命令: INCR site:page_views
# 输出: (integer) 1
# 命令: INCR site:page_views
# 输出: (integer) 2

# 设置文章标题，仅当键不存在时
# 命令: SETNX article:123:title "My First Redis Article"
# 输出: (integer) 1
# 命令: SETNX article:123:title "Another Title"
# 输出: (integer) 0

# 追加内容到字符串
# 命令: APPEND mykey "Hello"
# 输出: (integer) 5
# 命令: APPEND mykey " World"
# 输出: (integer) 11
# 命令: GET mykey
# 输出: "Hello World"

# 删除键
# 命令: DEL mykey
# 输出: (integer) 1
```

### 2. Hash (哈希)

* **核心特性:** 存储键值对集合，适合存储对象。字段级别的原子性操作。内部采用渐进式rehash，避免服务阻塞。

* **作用:** 存储结构化数据，如用户信息、商品信息，避免了整个对象的序列化/反序列化开销。

* 存储用户的个人资料，如 `user:1001 -> {name: "张三", age: 30, city: "北京"}`。每个用户的属性（name, age, city）作为Hash的字段。这样修改单个属性很方便，也无需序列化整个对象。

  ```sql
  hset userInfo username luotao age 18
  hget userInfo username
  hmget userInfo username age
  hgetall userInfo
  -- 统计map的个数
  hlen userInfo
  -- 自增
  hincry userInfo age 步长
  ```

  

```shell
# 存储用户对象
# 命令: HSET user:1001 name "Bob" age 30 city "New York"
# 输出: (integer) 3

# 获取用户姓名
# 命令: HGET user:1001 name
# 输出: "Bob"

# 获取用户所有信息
# 命令: HGETALL user:1001
# 输出:
# 1) "name"
# 2) "Bob"
# 3) "age"
# 4) "30"
# 5) "city"
# 6) "New York"

# 增加用户年龄
# 命令: HINCRBY user:1001 age 1
# 输出: (integer) 31

# 检查字段是否存在
# 命令: HEXISTS user:1001 email
# 输出: (integer) 0

# 删除用户城市信息
# 命令: HDEL user:1001 city
# 输出: (integer) 1
```

### 3. List (列表)

*   **核心特性:** 一个有序的字符串元素集合，双向链表实现，两端操作（`LPUSH`, `RPUSH`, `LPOP`, `RPOP`）时间复杂度为O(1)。支持按索引范围获取元素 (`LRANGE`)。
*   实现用户动态Feed流：当用户发布新动态时，`LPUSH user:1001:feed "新动态ID"`，然后通过 `LRANGE user:1001:feed 0 9` 获取最新的10条动态。
*   简单的消息队列：生产者 `LPUSH tasks "task_data"`，消费者 `RPOP tasks`。

```sql
-- 左插和右插:neko3 neko2 neko1 neko4
lpush users neko1 neko2 neko3
rpush neko4
-- 左弹出和右弹出
lpop users 
rpop users
-- 统计：[0,1]
lrange users 0 1
```



```shell
# 作为消息队列，生产者向列表左端添加任务
# 命令: LPUSH tasks "task:image_processing:id123"
# 输出: (integer) 1
# 命令: LPUSH tasks "task:report_generation:id456"
# 输出: (integer) 2

# 查看当前任务
# 命令: LRANGE tasks 0 -1
# 输出:
# 1) "task:report_generation:id456"
# 2) "task:image_processing:id123"

# 消费者从列表右端获取任务
# 命令: RPOP tasks
# 输出: "task:image_processing:id123"
# 命令: RPOP tasks
# 输出: "task:report_generation:id456"

# 用户动态Feed流 (最新动态在最前)
# 命令: LPUSH user:bob:feed "post_id_3"
# 命令: LPUSH user:bob:feed "post_id_4"
# 输出: (integer) 2 (第二次LPUSH后列表的长度)

# 获取最新的2条动态
# 命令: LRANGE user:bob:feed 0 1
# 输出:
# 1) "post_id_4"
# 2) "post_id_3"
```

### 4. Set (集合)

*   **核心特性:** 无序、元素唯一。支持高效的成员检查 (`SISMEMBER`) 和集合运算（交、并、差集）。
*   **作用:** 去重、标签系统、共同好友/兴趣推荐、统计独立IP等。
*   存储用户关注的好友ID列表：`SADD user:1001:following "user_id_A"`。
*   计算共同好友：使用 `SINTER user:1001:following user:1002:following`。
*   存储文章标签，确保标签唯一性。

```sql
sadd users neko1 neko2 neko3
-- 列出所有元素
smembers users
-- 删除指定元素
srem users neko3
-- 随机弹出元素
spop users
-- 交集
SADD userA neko1 neko2 neko3
SADD userB neko4 neko2 nako5
sinter userA userB -- neko2
-- 差集
sdiff userA userB -- neko1 neko3
-- 并集
sunion userA userB

```



```shell
# 存储文章标签
# 命令: SADD article:1:tags "redis" "database" "nosql"
# 输出: (integer) 3

# 再次添加已存在的标签
# 命令: SADD article:1:tags "redis" "performance"
# 输出: (integer) 1

# 查看所有标签
# 命令: SMEMBERS article:1:tags
# 输出: (顺序不保证)
# 1) "redis"
# 2) "database"
# 3) "nosql"
# 4) "performance"

# 检查标签是否存在
# 命令: SISMEMBER article:1:tags "java"
# 输出: (integer) 0

# 用户A和用户B共同关注的人 (交集)
# 命令: SADD user:A:following "userB" "userC" "userD"
# 命令: SADD user:B:following "userC" "userE"
# 命令: SINTER user:A:following user:B:following
# 输出:
# 1) "userC"
```

### 5. Sorted Set (有序集合 / ZSet)

*   与Set类似，但每个元素都会关联一个浮点数类型的“分数”(score)，并根据分数进行排序。元素唯一，但分数可以重复。
*   **核心特性:** 元素唯一，每个元素关联一个分数，并按分数排序。支持按分数范围或排名范围获取元素。内部使用跳表（Skip List）和哈希表结合实现，保证了高效的插入、删除和查找。
*   **作用:** 排行榜、带权重的任务调度、范围查询。
*   实现用户积分排行榜：`ZADD leaderboard 1500 "user:1001"` (用户1001积分为1500)。通过 `ZREVRANGE leaderboard 0 9 WITHSCORES` 获取排名前10的用户及其积分。
*   带权重的任务队列，分数代表优先级。

```sql
zadd userRank 60 nekoC 100 nekoA 90 nekoB
-- 按score降序列出[60,100]的v
zrangebyscore userRank 60 100
-- 统计[60,100]的个数
zcount userRank 60 100
-- 按score降序
zrange userRank 0 2
-- 按score升序
zrange userRank 0 2
-- 获取下标
zrank userRank nekoA
-- 获取倒序下标
zrevrank userRank nekoA
-- 删除
zrem userRank nekoA
-- 集合的长度
zcard userRank



```



```shell
# 记录游戏玩家得分
# 命令: ZADD game:scores 1500 "player1" 2200 "player2" 1800 "player3"
# 输出: (integer) 3

# 增加 player1 的分数
# 命令: ZINCRBY game:scores 200 "player1"
# 输出: "1700"

# 获取得分最高的2位玩家
# 命令: ZREVRANGE game:scores 0 1 WITHSCORES
# 输出:
# 1) "player2"
# 2) "2200"
# 3) "player3"
# 4) "1800"

# 获取 player1 的排名 (从0开始，分数从高到低)
# 命令: ZREVRANK game:scores "player1"
# 输出: (integer) 2

# 获取分数在 1000 到 1800 之间的玩家
# 命令: ZRANGEBYSCORE game:scores 1000 1800 WITHSCORES
# 输出:
# 1) "player1"
# 2) "1700"
# 3) "player3"
# 4) "1800"
```

## Sorted Set的底层算法



## Sorted Set实现用户积分排行榜

1.  **初始化/用户获得积分:**
    *   当用户（例如`user_id_A`）完成某个操作获得积分（例如获得100分）时，应用会执行Redis命令：
        `ZADD user_scores 100 "user_id_A"`
    *   如果`user_id_A`已在排行榜中，此命令会更新其分数为100。如果不在，则会添加。
    *   如果用户后续又获得了50分，可以执行：
        `ZINCRBY user_scores 50 "user_id_A"`
        这样`user_id_A`的总分就变成了150。

2.  **展示排行榜 (Top N):**
    *   要获取积分最高的10位用户，可以执行（分数从高到低）：
        `ZREVRANGE user_scores 0 9 WITHSCORES`
    *   这将返回排名前10的用户名及其分数。

3.  **查询用户排名:**
    *   要查询特定用户`user_id_A`的排名（分数从高到低，排名从0开始）：
        `ZREVRANK user_scores "user_id_A"`

4.  **查询指定分数范围的用户:**
    *   要查询分数在1000到2000之间的用户：
        `ZRANGEBYSCORE user_scores 1000 2000 WITHSCORES`
