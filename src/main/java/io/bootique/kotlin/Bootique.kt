package io.bootique.kotlin

import io.bootique.Bootique

/**
 * Micro-DSL for bootstraping application.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
fun bootique(args: Array<String>, initializer: Bootique.() -> Unit) {
    Bootique.app(*args).apply {
        initializer(this)
        run()
    }
}
