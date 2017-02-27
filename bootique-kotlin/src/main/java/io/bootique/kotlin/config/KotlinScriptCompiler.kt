package io.bootique.kotlin.config

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmDaemonLocalEvalScriptEngineFactory
import java.net.URL

/**
 * TODO. Describe and test.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
interface KotlinScriptCompiler {
    fun <T> execute(url: URL): T
}

class DefaultKotlinScriptCompiler : KotlinScriptCompiler {
    private val factory = KotlinJsr223JvmDaemonLocalEvalScriptEngineFactory()
    private val scriptEngine = factory.scriptEngine

    @Suppress("UNCHECKED_CAST")
    override fun <T> execute(url: URL): T {
        return scriptEngine.eval(url.openStream().reader()) as T
    }
}