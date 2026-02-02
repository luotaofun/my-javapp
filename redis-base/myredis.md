## 概述

* redis: remote dictionary server (远程字典服务器)
* 用c编写的基于key-value键值对的内存型数据库
* 大部分的业务操作80%都是查询，剩下的是写入，我们在做查询时先去redis缓存命中返回给你，如果redis找不到再去访问mysql，如果mysql有了就将redis缺失的数据回写到redis，在第三次访问的时候可以从redis拿到，这样，对于传统的关系型数据库RDBMS进行减负，比如mysql的负担被大大减轻。比如说微信朋友圈点赞，每次去点赞加一如果都去update，这样多次IO会导致mysql性能急剧下降。
* redis数据操作主要在内存，mysql主要存储在磁盘
* redis支持自身数据持久化到磁盘，如果断电关机后可以从磁盘恢复到内存数据库

## 