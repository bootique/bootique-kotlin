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

package io.bootique.kotlin.config

import io.bootique.kotlin.config.modules.config
import org.junit.Assert.assertEquals
import org.junit.Test

internal data class Sample(val text: String)

class ConfigurationTest {

    @Test
    fun `single config`() {
        val config = config {
            addConfig("Yoda" to Sample("Jedi"))
        }

        assertEquals(Sample("Jedi"), config["Yoda"])
    }

    @Test
    fun `multiple configs`() {
        val config = config {
            addConfig("Yoda" to Sample("Jedi"))
            addConfig("Vader" to Sample("Sith"))
        }

        assertEquals(Sample("Jedi"), config["Yoda"])
        assertEquals(Sample("Sith"), config["Vader"])
    }
}
