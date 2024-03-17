package de.wtl.core.utils

import io.ktor.server.routing.Routing

class Router(val routes: Routing.() -> Unit)

interface RouterFactory {
    fun router(): Router

    fun register(routing: Routing) {
        router().routes(routing)
    }
}
