# flink-sample

本项目包含学习总结flink的相关案例

# 涉及框架及技术
- jdk-1.8
- gradle(kts)
- flink-1.14
- kafka
- docker

# 模块介绍

## docker
包含本项目需要用到的组件docker-compose文件，可以直接用命令行一键启动，详情说明请看[README.md](./docker/README.md)

## buildSrc
自定义的插件，仅仅是简单的使用，包含java相关的基础依赖

## common
通用模块的代码

## jobs
包含flink的各种任务

## web
可以通过FlinkApplication入口直接启动的一个springboot项目，主要用于接受请求生成jobs模块不同任务所需要的数据

# 构建
执行如下命令，可以构建整个项目，在jobs下的各个模块中的build/libs目录会生成以-all.jar结尾的文件，可以提交flink执行
```
./gradlew build
```

