package io.bootique.kotlin.config.modules

import io.bootique.jetty.connector.HttpConnectorFactory
import io.bootique.jetty.connector.HttpsConnectorFactory
import io.bootique.jetty.server.ServerFactory

/**
 * Configuration DSL for JettyModule.
 * TODO. Describe and test.
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
