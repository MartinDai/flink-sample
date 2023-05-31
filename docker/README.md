# Docker环境部署使用说明

## _进入当前目录，根据实际情况选择需要的容器组合命令执行_

目前配置的是适用于Apple M1芯片（arm64）版本的镜像，如果在其他操作系统上执行，需要根据实际情况替换镜像，版本最好保持跟配置中的一致，否则可能会不兼容

## Flink服务

```
docker-compose -f docker-compose-flink.yml up -d
```

- 此配置包含1个jobManager+2个taskManager服务节点，可以根据需要自行调整
- Flink Dashboard页面：http://localhost:8081

## Kafka服务

```
docker-compose -f docker-compose-kafka.yml up -d
```

- 该服务使用kafka的kraft模式启动，无需zookeeper
- 需要替换kafka/conf/server.properties文件内的172.16.0.152为本机局域网地址
- Kafka连接地址：http://localhost:9092