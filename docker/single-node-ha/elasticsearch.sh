#!/bin/bash

function redis_cluster_status {
    echo "检查elasticsearch集群状态"
    container_is_exist $@
    if [[ ! $? -eq 0 ]]
    then
        exit 1
    fi

    container_is_start $@
    if [[ ! $? -eq 0 ]]
    then
        exit 1
    fi
    echo "elasticsearch集群正在运行中"
}

function container_is_exist {
    for name in $@
    do
        is_exist ${name}
        if [[ ! $? -eq 0 ]]
        then
            return 1
        fi
    done

    return 0;
}

function is_exist {
    echo "检查组件的容器($1)是否存在"
    docker ps -a | grep "$1" &> /dev/null
    if [[ ! $? -eq 0 ]]
    then
        echo "组件容器($1)不存在"
        return 1
    fi
    return 0;
}

function container_is_start {
    for name in $@
    do
        is_start ${name}
        if [[ ! $? -eq 0 ]]
        then
            return 1
        fi
    done

    return 0;
}

function is_start {
    echo "检查组件的容器($1)是否能正常访问"
    docker exec -it $1 ls -l &> /dev/null
    if [[ ! $? -eq 0 ]]
    then
        echo "组件容器($1)无法正常访问"
        return 1
    fi
    return 0;
}

function port_is_useable {
    #  检查netstat是否安装
    netstat -V &> /dev/null
    if [[ ! $? -eq 0 ]]
    then
        yum -y install net-tools
    fi
    for port in $@
    do
        is_useable ${port}
        if [[ ! $? -eq 0 ]]
        then
            exit 1
        fi
    done
}

function is_useable {
    echo "检查$1端口是否可用"
    netstat -tunlp | grep $1 &> /dev/null
    if [[ $? -eq 0 ]]
    then
        echo "启动elasticsearch集群失败，$1端口已被占用"
        return 1
    fi
    return 0;
}