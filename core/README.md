# Backend

The backend service is written in kotlin and employs the [ktor](https://ktor.io) framework. It uses
[MongoDB](https://www.mongodb.com) as a persistence storage. It is implemented heavily employing Kotlin's coroutines to
provide best in class performance.   
Additionally, it uses [GraalVM](https://www.graalvm.org/) to compile the application to a native image to benefit from
near instant startup time and reduced memory footprint.

## Development

To start developing, just run the [docker-compose](docker-compose.yml) file to bring up MongoDB.  
For development we use normal JVM mode. To start the server in development mode, just run the following command:

```bash
./gradlew run
```

This will start the server on port `8080`.

For production, we use GraalVM to compile the application to a native image. To do so, just run the following command:

```bash
./gradlew nativeImage
```

To mostly statically compile the application, simply run the following command:

```bash
./gradlew nativeCompile -Pci
```

This will create a native image in the `build/native/nativeCompile` directory. To start the server, just run the
following command:

```bash
./build/native/nativeCompile/de.wtl.core
```

Before deploying the application, make sure to run the tests:

```bash
./gradlew test nativeTest
```
