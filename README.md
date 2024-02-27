# What's the Loss?

Do simple ML experiment logging and visualize your data with dashboards.
This project aims to provide a monitoring framework similar to the one provided
by [TensorBoard](https://www.tensorflow.org/tensorboard). It consists out of three main components:

- A [server (core)](core) that can be used to collect and store the data
- A [web interface (UI)](ui) that can be used to visualize the data
- [Open API documentation](api) that models the interaction between the server and the clients
- A [python sdk](python-sdk) that can be used to log data to the server

## Getting Started

To get started, just run the [docker-compose](docker-compose.yml) file from the repositories root.
This will start the server and the web interface on port `8080`. The server is accessible on `:8080/api` whereas the ui
is on just `:8080`.

## Backend

The backend service is written in kotlin and employs the [ktor](https://ktor.io) framework. It uses
[MongoDB](https://www.mongodb.com) as a persistence storage. It is implemented heavily employing Kotlin's coroutines to
provide best in class performance.  
Additionally it uses [GraalVM](https://www.graalvm.org/) to compile the application to a native image to benefit from
near instant startup time and reduced memory footprint.

## UI

The UI is written in [Angular](https://angular.io) and uses [Angular Material](https://material.angular.io).

## Python Client SDK

...
