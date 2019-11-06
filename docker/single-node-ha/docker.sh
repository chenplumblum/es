#!/bin/bash

function docker_check {
    echo "检查docker"
    docker -v &> /dev/null
    if [[ ! $? -eq 0 ]]
    then
        echo "安装docker环境"

        #  卸载docker残留模块
        yum remove -y docker docker-ce docker-ce-cli docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine
        yum install -y docker
    fi
    systemctl start docker
}

function docker_compose_check {
    echo "检查docker-compose"
    docker-compose -v &> /dev/null
    if [[ ! $? -eq 0 ]]
    then
        echo "安装docker-compose环境"
        pip -V
        if [[ ! $? -eq 0 ]]
        then
            yum -y install python-pip
            pip install --upgrade pip
        fi
        pip install docker-compose
    fi
}