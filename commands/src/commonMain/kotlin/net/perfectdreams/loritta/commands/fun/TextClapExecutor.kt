package net.perfectdreams.loritta.commands.`fun`

import net.perfectdreams.loritta.commands.`fun`.declarations.TextTransformDeclaration
import net.perfectdreams.loritta.common.commands.CommandArguments
import net.perfectdreams.loritta.common.commands.CommandContext
import net.perfectdreams.loritta.common.commands.CommandExecutor
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration
import net.perfectdreams.loritta.common.commands.options.CommandOptions
import net.perfectdreams.loritta.common.emotes.Emotes

class TextClapExecutor(val emotes: Emotes) : CommandExecutor() {
    companion object : CommandExecutorDeclaration(TextClapExecutor::class) {
        object Options : CommandOptions() {
            val text = string("text", TextTransformDeclaration.I18N_PREFIX.Clap.Options.Text(TextTransformDeclaration.CLAP_EMOJI))
                .register()

            val emoji = optionalString("emoji", TextTransformDeclaration.I18N_PREFIX.Clap.Options.Emoji)
                .register()
        }

        override val options = Options
    }

    override suspend fun execute(context: CommandContext, args: CommandArguments) {
        val text = args[options.text]
        val emoji = args[options.emoji] ?: TextTransformDeclaration.CLAP_EMOJI

        context.sendReply(
            content = "$emoji${text.split(" ").joinToString(emoji)}$emoji",
            prefix = "✍"
        )
    }
}