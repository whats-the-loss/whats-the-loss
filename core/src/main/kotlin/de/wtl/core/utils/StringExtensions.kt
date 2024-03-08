package de.wtl.core.utils

import org.bson.types.ObjectId

fun String.toObjectIdOrNull(): ObjectId? = runCatching(::ObjectId).getOrNull()

