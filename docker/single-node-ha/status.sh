#!/bin/bash

cd `dirname $0`

. $PWD/elasticsearch.sh

CONTAINER_NAME_PREFIX="comp-elasticsearch-deepexi"
CONTAINER_NAMES=("${CONTAINER_NAME_PREFIX}-cerebro" \
"${CONTAINER_NAME_PREFIX}-9200" "${CONTAINER_NAME_PREFIX}-9201" \
"${CONTAINER_NAME_PREFIX}-9202" )

redis_cluster_status ${CONTAINER_NAMES[*]}