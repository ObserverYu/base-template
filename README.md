# springcloud通用项目模板
从最近项目抽取的通用springcloud项目模板, 包含最近项目常用的组件并通过配置文件开关, 新开服务时方便新建项目.

## 包含组件:
### dao层
- druid
- mybatis-plus
- mybatis-plus多数据源
- sharding-jdbc分库分表
- redisson(单机,哨兵,集群)

### 微服务
- nacos(服务发现和配置中心)
- ribbon
- feign
- sentinel

### 工具
- leaf(分布式id生成)
- xxl(分布式定时任务)
- log4j2
- protobuf
- fastjson
- okhttp
- hutool
- protobuf(google字节型序列化工具)

## 其他
- Dockerfile:from的是自己打包的oracle-java-8u202(最后一个bsd许可) + skywalking-agent镜像, [镜像](https://hub.docker.com/repository/docker/wanyuyichen/skywalking-agent-java)
- docker-java-project.sh:容器内启动脚本,可简单配置jvm参数(垃圾回收和heap大小,是否使用skywalking监控等)
- deploy-docker.sh:k8s更新脚本,包含操作:打包镜像,推送到harbor,修改deployment的镜像版本
- jenkins-project.sh:直接从虚拟机启动的脚本,jenkins用




