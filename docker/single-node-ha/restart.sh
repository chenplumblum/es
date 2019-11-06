#!/bin/bash

cd `dirname $0`


echo "重启elasticsearch集群"
docker-compose -f docker-compose-ha.yml restart
echo "重启elasticsearch集群成功"