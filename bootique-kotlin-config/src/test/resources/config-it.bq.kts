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

import io.bootique.jetty.connector.HttpConnectorFactory
import io.bootique.jetty.connector.PortFactory
import io.bootique.jetty.server.ServerFactory
import io.bootique.value.Bytes
import io.bootique.value.Duration
import io.bootique.value.Percent

// Jetty config sample
// We don't have DSL from jetty-kotlin module here, so will use direct instantiation to emulate same result as:
/*
jetty {
    minThreads = 1
    maxThreads = 3

    httpConnector {
        port = PortFactory("4242")
        host = "192.168.0.1"
    }
}
*/
val connector = HttpConnectorFactory()
connector.host = "192.168.0.1"
connector.port = PortFactory("4242")

val factory = ServerFactory()
factory.minThreads = 1
factory.maxThreads = 3
factory.connectors = factory.connectors.orEmpty() + connector

addConfig("jetty", factory)

// add some value objects to check that they are correctly serialized and deserialized
addConfig("test_p", Percent("50%"))
addConfig("test_d", Duration("1s"))
addConfig("test_b", Bytes("50kb"))
