
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.modules.httpConnector
import io.bootique.kotlin.config.modules.jetty

config {
    jetty {
        httpConnector {
            port = 4242
            host = "0.0.0.0"
        }
    }
}