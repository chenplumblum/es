#!/bin/bash

cd `dirname $0`


echo "停止elasticsearch集群"
docker-compose -f docker-compose-ha.yml stop
echo "停止elasticsearch集群成功"