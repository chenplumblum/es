#!/bin/bash

cd `dirname $0`

echo "移除elasticsearch集群"
docker-compose -f docker-compose-ha.yml down -v
echo "移除elasticsearch集群成功"
sleep 10