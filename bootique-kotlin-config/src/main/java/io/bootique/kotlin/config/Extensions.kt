/**
 *    Licensed to the ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.kotlin.config

import io.bootique.BQCoreModule
import io.bootique.Bootique
import io.bootique.kotlin.config.modules.KotlinConfigModule
import io.bootique.kotlin.core.KotlinBootiqueInterface

/**
 * Extensions for config bootstrapping.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
fun Bootique.withKotlinConfig(): Bootique {
    return this.override(BQCoreModule::class.java)
        .with(KotlinConfigModule::class.java)
}

fun KotlinBootiqueInterface.withKotlinConfig(): KotlinBootiqueInterface {
    return this.override(BQCoreModule::class).with(KotlinConfigModule::class)
}
