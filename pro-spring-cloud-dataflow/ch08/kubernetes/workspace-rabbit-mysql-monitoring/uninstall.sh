#!/bin/sh
echo 'Uninstalling Spring Cloud Data Flow...'
kubectl delete -f server/server-deployment.yaml
kubectl delete -f server/server-svc.yaml
kubectl delete -f server/server-config.yaml
kubectl delete -f skipper/skipper-svc.yaml
kubectl delete -f skipper/skipper-deployment.yaml
kubectl delete -f skipper/skipper-config-rabbit.yaml
kubectl delete -f server/service-account.yaml
kubectl delete -f server/server-rolebinding.yaml
kubectl delete -f server/server-roles.yaml

kubectl delete -f grafana/
kubectl delete -f prometheus/prometheus-service.yaml
kubectl delete -f prometheus/prometheus-deployment.yaml
kubectl delete -f prometheus/prometheus-configmap.yaml
kubectl delete -f prometheus-proxy/
kubectl delete -f prometheus/prometheus-serviceaccount.yaml
kubectl delete -f prometheus/prometheus-clusterrolebinding.yaml
kubectl delete -f prometheus/prometheus-clusterroles.yaml

kubectl delete -f mysql/	
kubectl delete -f rabbitmq/
echo 'Done.'