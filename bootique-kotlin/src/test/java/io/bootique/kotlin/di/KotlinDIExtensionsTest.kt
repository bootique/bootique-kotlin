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

package io.bootique.kotlin.di

import org.junit.Assert.assertEquals
import org.junit.Test

class KotlinDIExtensionsTest {

    @Test
    fun `test creation of DI key`() {
        val k = key<List<String>>()

        assertEquals(List::class.java, k.type.rawType)
        assertEquals("java.util.List[io.bootique.di.TypeLiteral\$WildcardMarker[java.lang.Object, java.lang.String]]"
            , k.type.toString())
    }

    @Test
    fun `test creation of DI TypeLiteral`() {
        val k = typeLiteral<List<String>>()

        assertEquals(List::class.java, k.rawType)
        assertEquals("java.util.List[io.bootique.di.TypeLiteral\$WildcardMarker[java.lang.Object, java.lang.String]]"
            , k.toString())
    }
}
