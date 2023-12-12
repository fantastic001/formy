#!/bin/sh

FRONTEND_DIR="$(readlink -f "$1")"

docker run -it --rm \
	--name formy_frontend \
	--volume "$FRONTEND_DIR/dist":/usr/share/nginx/html/frontend/dist \
	--volume "$FRONTEND_DIR/index.html":/usr/share/nginx/html/frontend/index.html \
	--volume "$FRONTEND_DIR/../config/":/etc/nginx/ \
	--publish 300:80 \
	nginx
	# --link formy_backend \
