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

import io.bootique.jetty.connector.PortFactory
import io.bootique.jetty.server.ServerFactory
import io.bootique.kotlin.config.ScriptingBQConfigurationScript
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JettyModuleTest {
    @Test
    fun `single http connector`() {
        val script = createBQConfigurationScript().apply {
            jetty {
                httpConnector {
                    port = PortFactory("4242")
                    host = "192.168.0.1"
                    responseHeaderSize = 42
                    requestHeaderSize = 13
                }
            }
        }
        val obj = script.get<ServerFactory>("jetty").connectors[0]

        assertEquals(4242, obj.port.resolve("192.168.0.1"))
        assertEquals("192.168.0.1", obj.host)
        assertEquals(42, obj.responseHeaderSize)
        assertEquals(13, obj.requestHeaderSize)
    }

    @Test
    fun `jetty config`() {
        val script = createBQConfigurationScript().apply {
            jetty {

            }
        }

        assertNotNull(script.get<ServerFactory>("jetty"))
    }

    private fun createBQConfigurationScript(): ScriptingBQConfigurationScript {
        return object : ScriptingBQConfigurationScript() {}
    }
}
