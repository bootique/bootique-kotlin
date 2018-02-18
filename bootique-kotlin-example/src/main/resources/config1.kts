
import io.bootique.kotlin.config.jetty.httpConnector
import io.bootique.kotlin.config.jetty.jetty
import io.bootique.kotlin.config.modules.config

config {
    jetty {
        httpConnector {
            port = 4242
            host = "0.0.0.0"
        }
    }
}
