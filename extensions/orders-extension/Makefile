RELEASE=0.0.2
APP=orders-extension
DOCKER_ACCOUNT?=TBD
CONTAINER_IMAGE=${DOCKER_ACCOUNT}/${APP}:${RELEASE}

.PHONY: build build-docker clean tag push

all: clean build build-docker tag

clean:
	./gradlew clean
build:
	./gradlew -Pversion=$(RELEASE) -Pname=$(APP) clean build

build-image: clean build
	cp build/libs/$(APP)-$(RELEASE).jar docker
	docker build -t $(CONTAINER_IMAGE) --no-cache --rm docker
	rm docker/$(APP)-*.jar

push-image: build-image
	docker push $(CONTAINER_IMAGE)