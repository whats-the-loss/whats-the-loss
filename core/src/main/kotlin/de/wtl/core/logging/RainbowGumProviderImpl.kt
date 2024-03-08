package de.wtl.core.logging

import com.google.auto.service.AutoService
import io.jstach.rainbowgum.LogConfig
import io.jstach.rainbowgum.LogOutput
import io.jstach.rainbowgum.RainbowGum
import io.jstach.rainbowgum.pattern.format.PatternEncoderBuilder
import io.jstach.rainbowgum.spi.RainbowGumServiceProvider.RainbowGumProvider
import java.lang.System.Logger.Level
import java.util.*

@AutoService(RainbowGumProvider::class)
class RainbowGumProviderImpl : RainbowGumProvider {
    override fun provide(config: LogConfig): Optional<RainbowGum> = RainbowGum.builder(config)
        .route { builder ->
            builder.level(Level.INFO)
            builder.appender("console") { appender ->
                val encoder = PatternEncoderBuilder("console")
                    .pattern("%cyan(%d{YYYY-MM-dd HH:mm:ss.SSS}) [%yellow(%thread)] %highlight(%-5level) %logger{36}: %msg%n")
                    .fromProperties(config.properties())
                    .build()

                appender.encoder(encoder)
                appender.output(LogOutput.ofStandardOut())
            }
        }
        .optional()
}
