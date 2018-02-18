package io.bootique.kotlin.config.jetty

import io.bootique.jetty.connector.HttpConnectorFactory
import io.bootique.jetty.connector.HttpsConnectorFactory
import io.bootique.jetty.server.ServerFactory
import io.bootique.kotlin.config.modules.BootiqueConfiguration
import io.bootique.kotlin.config.modules.FactoryDSL

/**
 * Configuration DSL for JettyModule.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
inline fun BootiqueConfiguration.jetty(body: (@FactoryDSL ServerFactory).() -> Unit) {
    this.addConfig("jetty" to ServerFactory().also { factory ->
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
