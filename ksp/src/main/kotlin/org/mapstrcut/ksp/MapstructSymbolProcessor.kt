package org.mapstrcut.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

class MapstructSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

//  private val annotationsKlass = listOf(Codec::class)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("Mapstruct processing ... ")
        val processed = emptyList<KSAnnotated>()
        logger.info(
            "Mapstruct processing ended for ${processed.size} class(es) (${
                processed.take(3).joinToString()
            }...)"
        )
        return emptyList() // TODO should return unprocessed KSAnnotated
    }
}
