#!/bin/sh

if type jq &> /dev/null; then
	echo "jq .... OK"
else
	echo "You need to have 'jq' installed: https://stedolan.github.io/jq/"
	exit 1 
fi

read -p "Is this a local (Minikube/Docker Desktop) deployment (y/n)? " answer

if [ "$answer" = "n" ]; then
	echo '\n> Cloud Installation'
else
	echo '\n> A Minikube/Docker Desktop Installation'
fi 

echo '> Installing Spring Cloud Data Flow Server...\n'
echo '> Installing RabbitMQ and MySQL...\n'
kubectl create -f rabbitmq/
kubectl create -f mysql/

echo '> Installing Server Roles and Skipper...\n'
kubectl create -f server/server-roles.yaml
kubectl create -f server/server-rolebinding.yaml
kubectl create -f server/service-account.yaml
kubectl create -f skipper/skipper-config-rabbit.yaml
kubectl create -f skipper/skipper-deployment.yaml

if [ "$answer" = "n" ]; then 
	kubectl create -f skipper/skipper-svc.yaml 
else 
	sed 's/LoadBalancer/NodePort/;s/80/7577/' skipper/skipper-svc.yaml | kubectl create -f- 
fi
echo '\n> Waiting a little bit for Skipper to start ...\n'
kubectl wait --for=condition=Ready pod -l app=skipper --timeout=180s
kubectl create -f server/server-config.yaml

if [ "$answer" = "n" ]; then 
	kubectl create -f server/server-svc.yaml
else
	sed 's/LoadBalancer/NodePort/;1,/80/s//8080/' server/server-svc.yaml | kubectl create -f- 
fi

kubectl create -f server/server-deployment.yaml
echo '\n> Waiting a little bit for Spring Cloud Data Flow Server to start ...\n'
kubectl wait --for=condition=Ready pod -l app=scdf-server --timeout=180s
echo '\n> Done.'

SCDF=$(kubectl cluster-info | awk 'NR==1 {print $NF}'| sed 's/\(.*\):/\1 /' | awk '{print $1}' | sed 's/https/http/' | sed -E "s/"$'\E'"\[([0-9]{1,3}((;[0-9]{1,3})*)?)?[m|K]//g" )
SVC=$(kubectl get svc -l app=scdf-server -o json)
PORT=8080
if [ "$answer" = "n" ]; then
	PORT=$(echo $SVC | jq '.items[0].spec.ports[0].port')
else
	PORT=$(echo $SVC | jq '.items[0].spec.ports[0].nodePort')
fi

URL="$SCDF:$PORT/dashboard"
echo "\nSpring Cloud Data Flow Server: $URL \n"
