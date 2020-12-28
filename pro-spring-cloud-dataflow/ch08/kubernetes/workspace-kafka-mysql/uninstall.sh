#!/bin/sh
echo 'Uninstalling Spring Cloud Data Flow...'
kubectl delete -f server/server-deployment.yaml
kubectl delete -f server/server-svc.yaml
kubectl delete -f server/server-config.yaml
kubectl delete -f skipper/skipper-svc.yaml
kubectl delete -f skipper/skipper-deployment.yaml
kubectl delete -f skipper/skipper-config-kafka.yaml
kubectl delete -f server/service-account.yaml
kubectl delete -f server/server-rolebinding.yaml
kubectl delete -f server/server-roles.yaml
kubectl delete -f mysql/	
kubectl delete -f kafka/
echo 'Done.'