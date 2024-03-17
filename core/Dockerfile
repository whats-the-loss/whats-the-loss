# build has to be invoked from the repositorys root to copy over api spec for code auto generation via
# `docker buildx build -t whats-the-loss/core:latest -f core/Dockerfile . --load`

ARG JVM_VERSION="21"

FROM container-registry.oracle.com/graalvm/native-image:$JVM_VERSION AS builder

ARG VERSION="dev"
RUN microdnf install --nodocs -y tar gzip findutils
RUN mkdir -p /opt/gradle/.gradle
ENV GRADLE_USER_HOME=/opt/gradle/.gradle
COPY core/ /workspace/app/
COPY doc/ /workspace/doc/
WORKDIR /workspace/app

RUN --mount=type=cache,target=${GRADLE_USER_HOME}/caches \
--mount=type=cache,target=${GRADLE_USER_HOME}/wrapper/dists \
./gradlew --no-watch-fs --no-daemon -Pci "-Dorg.gradle.project.version=$VERSION" nativeCompile


FROM cgr.dev/chainguard/glibc-dynamic:latest

ARG VERSION="dev"
LABEL org.opencontainers.image.authors="cmdjulian,jeyrunner" \
      org.opencontainers.image.description="backend for whats the loss?" \
      org.opencontainers.image.documentation="https://github.com/whats-the-loss/whats-the-loss/blob/main/core/README.md" \
      org.opencontainers.image.source="https://github.com/whats-the-loss/whats-the-loss" \
      org.opencontainers.image.title="whats-the-loss" \
      org.opencontainers.image.url="https://whats-the-loss.com/api" \
      org.opencontainers.image.vendor="whats-the-loss" \
      org.opencontainers.image.version="$VERSION"
EXPOSE 8080/tcp
USER 65532:65532
WORKDIR /home/nonroot

# adjust "app" to whatever your applications name is
COPY --link --from=builder --chown=0:0 --chmod=755 /workspace/app/build/native/nativeCompile/de.wtl.core /usr/bin/wtl
ENTRYPOINT ["wtl", "-XX:+ExitOnOutOfMemoryError"]
