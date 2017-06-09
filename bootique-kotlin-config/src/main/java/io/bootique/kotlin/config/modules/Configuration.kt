package io.bootique.kotlin.config.modules

/**
 * Top-Level
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class BootiqueConfiguration(private val configs: MutableMap<String, Any> = mutableMapOf()) {
    fun addConfig(pair: Pair<String, Any>) = this.configs.put(pair.first, pair.second)
    fun getConfigs(): Map<String, Any> = configs
}

fun config(body: (@FactoryDSL BootiqueConfiguration).() -> Unit): Map<String, Any> {
    val bootiqueConfiguration = BootiqueConfiguration()
    body(bootiqueConfiguration)
    return bootiqueConfiguration.getConfigs()
}