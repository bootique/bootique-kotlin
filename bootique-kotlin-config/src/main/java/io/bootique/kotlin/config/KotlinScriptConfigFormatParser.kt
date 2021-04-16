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

import com.fasterxml.jackson.databind.JsonNode
import io.bootique.config.jackson.parser.ConfigurationFormatParser
import io.bootique.jackson.JacksonService
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

/**
 * @since 2.0.B1
 */
class KotlinScriptConfigFormatParser @Inject constructor(
        private val jacksonService: JacksonService,
        private val scriptHost: BQConfigurationScriptHost
) : ConfigurationFormatParser {

    override fun parse(stream: InputStream): JsonNode {
        val result = scriptHost.eval(stream)
        val newObjectMapper = jacksonService.newObjectMapper()
        return newObjectMapper.valueToTree(result.getAll())
    }

    override fun shouldParse(url: URL, contentType: String?): Boolean {
        return url.path.endsWith(".bq.kts")
    }
}