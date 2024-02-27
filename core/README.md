# Backend

The backend service is written in kotlin and employs the [ktor](https://ktor.io) framework. It uses
[MongoDB](https://www.mongodb.com) as a persistence storage. It is implemented heavily employing Kotlin's coroutines to
provide best in class performance.   
Additionally, it uses [GraalVM](https://www.graalvm.org/) to compile the application to a native image to benefit from
near instant startup time and reduced memory footprint.

##  
