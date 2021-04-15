package net.perfectdreams.loritta.platform.interaktions.commands

import net.perfectdreams.discordinteraktions.api.entities.User
import net.perfectdreams.discordinteraktions.commands.SlashCommandArguments
import net.perfectdreams.discordinteraktions.commands.SlashCommandExecutor
import net.perfectdreams.discordinteraktions.context.SlashCommandContext
import net.perfectdreams.loritta.common.commands.CommandArguments
import net.perfectdreams.loritta.common.commands.CommandExecutor
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration
import net.perfectdreams.loritta.common.commands.options.CommandOption
import net.perfectdreams.loritta.common.commands.options.CommandOptionType
import net.perfectdreams.loritta.common.images.URLImageReference
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.platform.interaktions.LorittaInteraKTions
import net.perfectdreams.loritta.platform.interaktions.entities.InteraKTionsMessageChannel

/**
 * Bridge between Cinnamon's [CommandExecutor] and Discord InteraKTions' [SlashCommandExecutor].
 *
 * Used for argument conversion between the two platforms
 */
class SlashCommandExecutorWrapper(
    val loritta: LorittaInteraKTions,
    val locale: BaseLocale,
    val declarationExecutor: CommandExecutorDeclaration,
    val executor: CommandExecutor,
    val rootSignature: Int
) : SlashCommandExecutor() {
    override suspend fun execute(context: SlashCommandContext, args: SlashCommandArguments) {
        println("Executed something $rootSignature")

        // Map Cinnamon Arguments to Discord InteraKTions Arguments
        val cinnamonArgs = mutableMapOf<CommandOption<*>, Any?>()
        val interaKTionsArgumentEntries = args.types.entries

        declarationExecutor.options.arguments.forEach {
            when (it.type) {
                is CommandOptionType.StringList -> {
                    // Special case: Lists
                    val listsValues = interaKTionsArgumentEntries.filter { opt -> it.name.startsWith(it.name) }
                    cinnamonArgs[it] = mutableListOf<String>().also {
                        it.addAll(listsValues.map { it.value as String })
                    }
                }

                is CommandOptionType.ImageReference -> {
                    // Special case: Image References
                    val imageReferenceArgs = interaKTionsArgumentEntries.filter { opt -> it.name.startsWith(it.name) }

                    var found = false
                    for ((interaKTionOption, value) in imageReferenceArgs) {
                        if (interaKTionOption.name.endsWith("_avatar") && value != null) {
                            // If the type is a user OR a nullable user, and the value isn't null...
                            val interaKTionUser = value as User

                            // TODO: Animated Avatars?
                            cinnamonArgs[it] = URLImageReference(
                                "https://cdn.discordapp.com/avatars/${interaKTionUser.id.value}/${interaKTionUser.avatar}.png?size=256"
                            )
                            found = true
                            break
                        }

                        if (interaKTionOption.name.endsWith("_url") && value != null) {
                            cinnamonArgs[it] = URLImageReference(value as String)
                            found = true
                            break
                        }

                        if (interaKTionOption.name.endsWith("_emote") && value != null) {
                            val strValue = value as String
                            val emoteId = strValue.substringAfterLast(":").substringBefore(">")
                            cinnamonArgs[it] = URLImageReference("https://cdn.discordapp.com/emojis/${emoteId}.png?v=1")
                            found = true
                            break
                        }
                    }

                    if (!found) {
                        // TODO: Improve this lol
                        context.sendMessage {
                            content = "tá mas cadê a imagem nn sei"
                        }
                        return
                    }
                }

                else -> {
                    val interaKTionArgument =
                        interaKTionsArgumentEntries.firstOrNull { opt -> it.name == opt.key.name }
                    // If the value is null but it *wasn't* meant to be null, we are going to throw a exception!
                    // (This should NEVER happen!)
                    if (interaKTionArgument?.value == null && it.type !is CommandOptionType.Nullable)
                        throw UnsupportedOperationException("Argument ${interaKTionArgument?.key} valie is null, but the type of the argument is ${it.type}! Bug?")

                    cinnamonArgs[it] = interaKTionArgument?.value
                }
            }
        }

        executor.execute(
            InteraKTionsCommandContext(
                loritta,
                locale,
                InteraKTionsMessageChannel(context)
            ),
            CommandArguments(cinnamonArgs)
        )
    }

    override fun signature() = rootSignature
}