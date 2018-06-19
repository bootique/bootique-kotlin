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

package io.bootique.kotlin.guice

import com.google.inject.Key
import com.google.inject.TypeLiteral

/**
 * Constructs a new [Key] using kotlin reified generics.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
inline fun <reified T> key() = object : Key<T>() {}

/**
 * Constructs a new [TypeLiteral] using kotlin reified generics.
 *
 * @author Ibragimov Ruslan
 * @since 0.25
 */
inline fun <reified T> typeLiteral() = object : TypeLiteral<T>() {}
