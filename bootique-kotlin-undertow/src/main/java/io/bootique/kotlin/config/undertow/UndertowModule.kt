package io.bootique.kotlin.config.undertow

import io.bootique.resource.FolderResourceFactory
import io.bootique.undertow.StaticResourceFactory
import io.bootique.undertow.UndertowFactory
import io.bootique.undertow.config.HttpListener
import io.bootique.undertow.config.HttpsListener

/**
 * Configuration DSL for UndertowModule.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
fun undertowFactory(
    httpListeners: List<HttpListener> = listOf(),
    httpsListeners: List<HttpsListener> = listOf(),
    staticFiles: List<StaticResourceFactory> = listOf(),
    bufferSize: Int? = null,
    ioThreads: Int? = null,
    workerThreads: Int? = null,
    directBuffers: Boolean? = null
) = UndertowFactory().also {
    it.httpListeners = httpListeners
    it.httpsListeners = httpsListeners
    it.staticFiles = staticFiles
    it.bufferSize = bufferSize
    it.ioThreads = ioThreads
    it.workerThreads = workerThreads
    it.directBuffers = directBuffers
}

fun httpListener(
    port: Int,
    host: String = "0.0.0.0"
) = HttpListener().also {
    it.port = port
    it.host = host
}

fun staticResource(
    path: FolderResourceFactory,
    url: String,
    isCache: Boolean = false
) = StaticResourceFactory().also {
    it.path = path
    it.url = url
    it.isCache = isCache
}
