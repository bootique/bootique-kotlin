/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.kotlin.config.undertow

import io.bootique.Bootique
import io.bootique.undertow.UndertowFactory
import io.bootique.undertow.config.HttpListener
import io.bootique.undertow.config.HttpsListener

/**
 * Configuration DSL for UndertowModule.
 * @deprecated No longer supported. Kotlin users should be able to use "regular" Java Bootique API
 */
@Deprecated(message = "deprecated since 3.0. Won't be a part of 4.0")
fun undertowFactory(
    httpListeners: List<HttpListener> = listOf(),
    httpsListeners: List<HttpsListener> = listOf(),
    bufferSize: Int? = null,
    ioThreads: Int? = null,
    workerThreads: Int? = null,
    directBuffers: Boolean? = null
) = UndertowFactory().also {
    it.httpListeners = httpListeners
    it.httpsListeners = httpsListeners
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
