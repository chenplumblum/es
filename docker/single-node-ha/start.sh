#!/bin/bash

cd `dirname $0`

. $PWD/elasticsearch.sh
. $PWD/docker.sh

# 检查docker-compose环境
docker_compose_check

# 检查docker环境
docker_check

PORTS=("9000" "9200" "9201" "9202" "9300" "9301" "9302")

echo "开始创建elasticsearch集群"

# 检查端口是否可用
port_is_useable ${PORTS[*]}

echo "启动elasticsearch容器"
docker-compose -f docker-compose-ha.yml up -d
echo "elasticsearch集群创建成功"
