package net.perfectdreams.loritta.commands.`fun`

import net.perfectdreams.loritta.commands.`fun`.declarations.TextTransformDeclaration
import net.perfectdreams.loritta.common.commands.CommandArguments
import net.perfectdreams.loritta.common.commands.CommandContext
import net.perfectdreams.loritta.common.commands.CommandExecutor
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration
import net.perfectdreams.loritta.common.commands.options.CommandOptions
import net.perfectdreams.loritta.common.emotes.Emotes

class TextUppercaseExecutor(val emotes: Emotes) : CommandExecutor() {
    companion object : CommandExecutorDeclaration(TextUppercaseExecutor::class) {
        object Options : CommandOptions() {
            val text = string("text", TextTransformDeclaration.I18N_PREFIX.Uppercase.Description)
                .register()
        }

        override val options = Options
    }

    override suspend fun execute(context: CommandContext, args: CommandArguments) {
        val text = args[options.text]

        context.sendReply(
            content = text.uppercase(),
            prefix = "✍"
        )
    }
}