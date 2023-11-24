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

package io.bootique.kotlin.config.jetty

import io.bootique.jetty.connector.HttpConnectorFactory
import io.bootique.jetty.connector.HttpsConnectorFactory
import io.bootique.jetty.server.ServerFactory
import io.bootique.kotlin.config.ScriptingBQConfigurationScript
import io.bootique.kotlin.config.modules.FactoryDSL

/**
 * Configuration DSL for JettyModule.
 * @deprecated No longer supported. Kotlin users should be able to use "regular" Java Bootique API
 */
@Deprecated(message = "deprecated since 3.0. Won't be a part of 4.0")
inline fun ScriptingBQConfigurationScript.jetty(body: (@FactoryDSL ServerFactory).() -> Unit) {
    this.addConfig("jetty", ServerFactory().also { factory ->
        body(factory)
    })
}

inline fun ServerFactory.httpConnector(body: (@FactoryDSL HttpConnectorFactory).() -> Unit) {
    this.connectors = this.connectors.orEmpty() + HttpConnectorFactory().also { factory ->
        body(factory)
    }
}

inline fun ServerFactory.httpsConnector(body: (@FactoryDSL HttpsConnectorFactory).() -> Unit) {
    this.connectors = this.connectors.orEmpty() + HttpsConnectorFactory().also { factory ->
        body(factory)
    }
}
