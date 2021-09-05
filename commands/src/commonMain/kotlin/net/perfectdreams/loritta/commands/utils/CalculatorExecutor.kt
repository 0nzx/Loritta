package net.perfectdreams.loritta.commands.utils

import net.perfectdreams.loritta.commands.utils.declarations.CalculatorCommand
import net.perfectdreams.loritta.common.commands.CommandArguments
import net.perfectdreams.loritta.common.commands.CommandContext
import net.perfectdreams.loritta.common.commands.CommandExecutor
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration
import net.perfectdreams.loritta.common.commands.options.CommandOptions
import net.perfectdreams.loritta.common.emotes.Emotes
import net.perfectdreams.loritta.common.utils.math.MathUtils

class CalculatorExecutor(val emotes: Emotes) : CommandExecutor() {
    companion object : CommandExecutorDeclaration(CalculatorExecutor::class) {
        object Options : CommandOptions() {
            val expression = string("expression", CalculatorCommand.I18N_PREFIX.Options.Expression)
                .register()
        }

        override val options = Options
    }

    override suspend fun execute(context: CommandContext, args: CommandArguments) {
        val expression = args[options.expression]

        try {
            // Regra de três:tm:
            if (expression.contains("---")) {
                val split = expression.split("/")
                val firstSide = split[0].split("---")
                val secondSide = split[1].split("---")
                val number0 = firstSide[0].trim()
                val number1 = firstSide[1].trim()

                val number2 = secondSide[0].trim()
                val number3 = secondSide[1].trim()

                val resultNumber0 = MathUtils.evaluate(number0)
                val resultNumber1 = MathUtils.evaluate(number1)
                val resultNumber2 = MathUtils.evaluate(number2)

                // resultNumber0 --- resultNumber1
                // resultNumber2 --- x
                context.sendReply(
                    content = context.i18nContext.get(
                        CalculatorCommand.I18N_PREFIX.Result(
                            (resultNumber2 * resultNumber1) / resultNumber0
                        )
                    ),
                    prefix = emotes.loriReading
                )
                return
            }

            // TODO: Evaluate based on the user's locale (or maybe find what locale they are using by figuring out the number?)
            val result = MathUtils.evaluate(expression)

            context.sendReply(
                content = context.i18nContext.get(
                    CalculatorCommand.I18N_PREFIX.Result(
                        result
                    )
                ),
                prefix = emotes.loriReading
            )
        } catch (e: Exception) {
            // TODO: Fix stripCodeMarks
            context.sendReply(
                content = context.i18nContext.get(
                    CalculatorCommand.I18N_PREFIX.Invalid(
                        expression
                    )
                ) + " ${emotes.loriSob}",
                prefix = emotes.loriHm
            )
        }
    }
}