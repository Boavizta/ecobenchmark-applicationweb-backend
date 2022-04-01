build-runner:
	docker buildx build --output type=local,dest=$(shell pwd)/runner runner

