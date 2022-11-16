package net.perfectdreams.loritta.morenitta.utils.extensions

import net.perfectdreams.loritta.morenitta.utils.ImageFormat
import net.perfectdreams.loritta.morenitta.dao.ServerConfig
import kotlinx.coroutines.future.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.Permission.*
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.entities.Message.MentionType
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.entities.emoji.EmojiUnion
import net.dv8tion.jda.api.exceptions.ErrorResponseException
import net.dv8tion.jda.api.requests.ErrorResponse
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder
import net.dv8tion.jda.api.utils.messages.MessageEditData
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.morenitta.LorittaBot

suspend fun <T> RestAction<T>.await() : T = this.submit().await()

suspend fun MessageChannel.sendMessageAsync(text: String) = this.sendMessage(text).await()
suspend fun MessageChannel.sendMessageAsync(message: MessageCreateData) = this.sendMessage(message).await()
suspend fun MessageChannel.sendMessageAsync(embed: MessageEmbed) = this.sendMessageEmbeds(embed).await()

suspend fun Message.edit(message: String, embed: MessageEmbed, clearReactions: Boolean = true): Message {
    return this.edit(MessageCreateBuilder().setEmbeds(embed).setContent(if (message.isEmpty()) " " else message).build(), clearReactions)
}

suspend fun Message.edit(content: MessageCreateData, clearReactions: Boolean = true): Message {
    if (this.isFromType(ChannelType.PRIVATE) || !this.guild.selfMember.hasPermission(this.textChannel, Permission.MESSAGE_MANAGE)) {
        // Nós não podemos limpar as reações das mensagens caso a gente esteja em uma DM ou se a Lori não tem permissão para gerenciar mensagens
        // Nestes casos, iremos apenas deletar a mensagem e reenviar
        this.delete().queue()
        return this.channel.sendMessage(content).await()
    }

    // Se não, vamos apagar as reações e editar a mensagem atual!
    if (clearReactions)
        this.clearReactions().await()
    return this.editMessage(MessageEditData.fromCreateData(content)).await()
}

suspend fun MessageHistory.retrievePastChunked(quantity: Int): List<Message> {
    val messages = mutableListOf<Message>()

    for (x in 0 until quantity step 100) {
        val newMessages = this.retrievePast(100).await()
        if (newMessages.isEmpty())
            break

        messages += newMessages
    }
    return messages
}

suspend fun MessageHistory.retrieveAllMessages(): List<Message> {
    val messages = mutableListOf<Message>()

    while (true) {
        val newMessages = this.retrievePast(100).await()
        if (newMessages.isEmpty())
            break

        messages += newMessages
    }

    return messages
}

suspend fun Guild.retrieveMemberOrNullById(id: String) = retrieveMemberOrNullById(id.toLong())

/**
 * Retrieves a member, if the member isn't in the guild then null is returned
 *
 * @param the member's id
 * @return the member or null
 */
suspend fun Guild.retrieveMemberOrNullById(id: Long): Member? {
    return try {
        this.retrieveMemberById(id).await()
    } catch (e: ErrorResponseException) {
        if (e.errorResponse == ErrorResponse.UNKNOWN_MEMBER || e.errorResponse == ErrorResponse.UNKNOWN_USER)
            return null
        throw e
    }
}

/**
 * Retrieves a member, if the member isn't in the guild then null is returned
 *
 * @param the member's id
 * @return the member or null
 */
suspend fun Guild.retrieveMemberOrNull(user: User): Member? {
    return try {
        this.retrieveMember(user).await()
    } catch (e: ErrorResponseException) {
        if (e.errorResponse == ErrorResponse.UNKNOWN_MEMBER || e.errorResponse == ErrorResponse.UNKNOWN_USER)
            return null
        throw e
    }
}

/**
 * Edits the message, but only if the content was changed
 *
 * This reduces the number of API requests needed
 */
suspend fun Message.editMessageIfContentWasChanged(message: String): Message {
    if (this.contentRaw == message)
        return this

    return this.editMessage(message).await()
}

/**
 * Adds the [emotes] to the [message] if needed, this avoids a lot of unnecessary API requests
 */
suspend fun Message.doReactions(vararg emotes: String): Message {
    var message = this

    var clearAll = false

    // Vamos pegar todas as reações que não deveriam estar aqui

    val emoteOnlyIds = emotes.map { str -> str.split(":").getOrNull(1) }.filterNotNull()

    val invalidReactions = this.reactions.filterNot {
        if (it.emoji.type == Emoji.Type.CUSTOM)
            emoteOnlyIds.contains(it.emoji.asCustom().id)
        else
            emotes.contains(it.emoji.name)
    }

    if (invalidReactions.isNotEmpty())
        clearAll = true

    // Se o número de reações for diferente das reações na mensagem, então algo está errado ;w;
    if (this.reactions.size != emotes.size)
        clearAll = true

    if (clearAll) { // Pelo visto tem alguns emojis que não deveriam estar aqui, vamos limpar!
        this.clearReactions().await() // Vamos limpar todas as reações
        message = this.refresh().await() // E pegar o novo obj da mensagem

        emotes.forEach {
            // E agora vamos readicionar os emotes!
            message.addReaction(it).await()
        }
    }
    return message
}

/**
 * Hacky workaround for JDA v4 (and JDA v5 lol) support
 */
fun EmojiUnion.isEmote(id: String): Boolean {
    return if (this.type == Emoji.Type.CUSTOM)
        this.asCustom().id == id || this.asCustom().name == id
    else
        this.name == id
}

/**
 * Hacky workaround for JDA v5 support
 */
val MessageChannelUnion.textChannel
    get() = this.asTextChannel()

/**
 * Hacky workaround for JDA v5 support
 */
val Message.textChannel
    get() = this.channel.textChannel

/**
 * Hacky workaround for JDA v5 support
 */
fun MessageCreateBuilder.denyMentions(vararg mentions: MentionType): MessageCreateBuilder {
    this.setAllowedMentions(
        MentionType.values().toMutableSet().apply {
            this.removeAll(mentions)
        }
    )
    return this
}

/**
 * Hacky workaround for JDA v5 support
 */
fun Message.addReaction(reaction: String): RestAction<Void> {
    val emoji = if (reaction.contains(":"))
        Emoji.fromFormatted(reaction)
    else Emoji.fromUnicode(reaction)

    return this.addReaction(emoji)
}

fun Message.refresh(): RestAction<Message> {
    return this.channel.retrieveMessageById(this.idLong)
}

/**
 * Checks if a role is a valid giveable role (not managed, not a public role, etc).
 *
 * @return       if the role can be given to the specified member
 */
fun Role.canBeGiven() = !this.isPublicRole &&
        !this.isManaged &&
        guild.selfMember.canInteract(this)

/**
 * Filters a role list with [canBeGiven].
 *
 * @param member the member that the role will be given to
 * @return       all roles that can be given to the member
 */
fun Collection<Role>.filterOnlyGiveableRoles() = this.filter { it.canBeGiven() }

/**
 * Filters a role list with [canBeGiven].
 *
 * @param member the member that the role will be given to
 * @return       all roles that can be given to the member
 */
fun Sequence<Role>.filterOnlyGiveableRoles() = this.filter { it.canBeGiven() }

/**
 * Tries to send [targetMessagesPerSecond] messages every second.
 *
 * Discord has a 50 messages every 10s global rate limit (10 messages per second) and, sometimes,
 * we need to queue messages to be sent.
 *
 * This will try to queue all messages to fit in the [targetMessagesPerSecond] messages per second, avoiding
 * getting globally rate limited by Discord.
 *
 * @param sentMessages            how many messages were sent
 * @param targetMessagesPerSecond what is the message per second target
 */
fun RestAction<Message>.queueAfterWithMessagePerSecondTarget(
    sentMessages: Int,
    targetMessagesPerSecond: Int = 5
) {
    // Technically we can send 50 messages every 10s (so 10 messages per second)
    // To avoid getting global ratelimited to heck (and dying!), we need to have some delays to avoid that.
    //
    // So, let's reserve 5 of the total 10 messages to sending notification updates.
    // This should avoid spamming the API with requests.
    this.queueAfter(
        (sentMessages / targetMessagesPerSecond).toLong(),
        java.util.concurrent.TimeUnit.SECONDS
    )
}

/**
 * Tries to send [targetMessagesPerSecond] messages every second.
 *
 * Discord has a 50 messages every 10s global rate limit (10 messages per second) and, sometimes,
 * we need to queue messages to be sent.
 *
 * This will try to queue all messages to fit in the [targetMessagesPerSecond] messages per second, avoiding
 * getting globally rate limited by Discord.
 *
 * This also tries to load balance between all clusters, useful for multi cluster notifications.
 *
 * @param sentMessages            how many messages were sent
 * @param targetMessagesPerSecond what is the message per second target
 */
fun RestAction<Message>.queueAfterWithMessagePerSecondTargetAndClusterLoadBalancing(
    loritta: LorittaBot,
    sentMessages: Int,
    targetMessagesPerSecond: Int = 5
) {
    // Technically we can send 50 messages every 10s (so 10 messages per second)
    // To avoid getting global ratelimited to heck (and dying!), we need to have some delays to avoid that.
    //
    // Because we have multiple clusters, we need to split up the load depending on how many clusters
    // Loritta has. The target messages per second will be (target - how many clusters), minimum value is 1
    //
    // So, let's reserve (5 - how many clusters we have) of the total 10 messages to sending notification updates.
    // This should avoid spamming the API with requests.
    this.queueAfter(
        sentMessages / Math.max(1, targetMessagesPerSecond - loritta.config.loritta.clusters.instances.size.toLong()),
        java.util.concurrent.TimeUnit.SECONDS
    )
}

/**
 * Make the message a reply to the referenced message.
 *
 * This checks if the bot has [net.dv8tion.jda.api.Permission.MESSAGE_HISTORY] and, if it has, the message is referenced.
 *
 * @param message The target message
 *
 * @return Updated MessageAction for chaining convenience
 */
fun MessageCreateAction.referenceIfPossible(message: Message): MessageCreateAction {
    if (message.isFromGuild && !message.guild.selfMember.hasPermission(message.channel as GuildChannel, MESSAGE_HISTORY))
        return this
    return this.setMessageReference(message)
}

/**
 * Make the message a reply to the referenced message.
 *
 * This has the same checks as [referenceIfPossible] plus a check to see if [addInlineReply] is enabled and to check if [ServerConfig.deleteMessageAfterCommand] is false.
 *
 * @param message The target message
 *
 * @return Updated MessageAction for chaining convenience
 */
fun MessageCreateAction.referenceIfPossible(message: Message, serverConfig: ServerConfig, addInlineReply: Boolean = true): MessageCreateAction {
    // We check if deleteMessageAfterCommand is true because it doesn't matter trying to reply to a message that's going to be deleted.
    if (!addInlineReply || serverConfig.deleteMessageAfterCommand)
        return this
    return this.referenceIfPossible(message)
}

fun Permission.localized(locale: BaseLocale): String {
    return when (this) {
        CREATE_INSTANT_INVITE -> locale["discord.permissions.createInstantInvite"]
        KICK_MEMBERS -> locale["discord.permissions.kickMembers"]
        BAN_MEMBERS -> locale["discord.permissions.banMembers"]
        ADMINISTRATOR -> locale["discord.permissions.administrator"]
        MANAGE_CHANNEL -> locale["discord.permissions.manageChannel"]
        MANAGE_SERVER -> locale["discord.permissions.manageServer"]
        MESSAGE_ADD_REACTION -> locale["discord.permissions.addReactions"]
        VIEW_AUDIT_LOGS -> locale["discord.permissions.viewAuditLogs"]
        PRIORITY_SPEAKER -> locale["discord.permissions.prioritySpeaker"]
        VIEW_CHANNEL -> locale["discord.permissions.viewChannel"]
        MESSAGE_SEND -> locale["discord.permissions.messageWrite"]
        MESSAGE_TTS -> locale["discord.permissions.messageTTS"]
        MESSAGE_MANAGE -> locale["discord.permissions.messageManage"]
        MESSAGE_EMBED_LINKS -> locale["discord.permissions.messageEmbedLinks"]
        MESSAGE_ATTACH_FILES -> locale["discord.permissions.attachFiles"]
        MESSAGE_HISTORY -> locale["discord.permissions.messageHistory"]
        MESSAGE_MENTION_EVERYONE -> locale["discord.permissions.mentionEveryone"]
        MESSAGE_EXT_EMOJI -> locale["discord.permissions.messageExtEmoji"]
        VOICE_CONNECT -> locale["discord.permissions.connect"]
        VOICE_SPEAK -> locale["discord.permissions.speak"]
        VOICE_MUTE_OTHERS -> locale["discord.permissions.muteVoiceMembers"]
        VOICE_DEAF_OTHERS -> locale["discord.permissions.disableVoiceAudio"]
        VOICE_MOVE_OTHERS -> locale["discord.permissions.moveVoiceMembers"]
        VOICE_USE_VAD -> locale["discord.permissions.useVoiceDetection"]
        NICKNAME_CHANGE -> locale["discord.permissions.changeNickname"]
        NICKNAME_MANAGE -> locale["discord.permissions.manageNicknames"]
        MANAGE_ROLES -> locale["discord.permissions.manageRoles"]
        MANAGE_PERMISSIONS -> locale["discord.permissions.managePermissions"]
        MANAGE_WEBHOOKS -> locale["discord.permissions.manageWebhooks"]
        MANAGE_EMOJIS_AND_STICKERS -> locale["discord.permissions.manageEmotes"]
        VOICE_STREAM -> locale["discord.permissions.voiceStream"]
        VIEW_GUILD_INSIGHTS -> locale["discord.permissions.viewGuildInsights"]
        USE_APPLICATION_COMMANDS -> locale["discord.permissions.useSlashCommands"]
        MESSAGE_EXT_STICKER -> locale["discord.permissions.messageExtSticker"]
        MANAGE_THREADS -> locale["discord.permissions.manageThreads"]
        REQUEST_TO_SPEAK -> locale["discord.permissions.requestToSpeak"]
        VOICE_START_ACTIVITIES -> locale["discord.permissions.voiceStartActivities"]
        MANAGE_EVENTS -> locale["discord.permissions.manageEvents"]
        MODERATE_MEMBERS -> locale["discord.permissions.moderateMembers"]
        CREATE_PUBLIC_THREADS ->  locale["discord.permissions.usePublicThreads"]
        CREATE_PRIVATE_THREADS -> locale["discord.permissions.usePrivateThreads"]
        MESSAGE_SEND_IN_THREADS -> locale["discord.permissions.messageSendInThreads"]
        UNKNOWN -> "This should never, ever happen!"
    }
}

/**
 * Gets the effective avatar URL in the specified [format]
 *
 * @see getEffectiveAvatarUrl
 */
fun User.getEffectiveAvatarUrl(format: ImageFormat) = getEffectiveAvatarUrl(format, 128)

/**
 * Gets the effective avatar URL in the specified [format] and [ímageSize]
 *
 * @see getEffectiveAvatarUrlInFormat
 */
fun User.getEffectiveAvatarUrl(format: ImageFormat, imageSize: Int): String {
    val extension = format.extension

    return if (avatarId != null) {
        "https://cdn.discordapp.com/avatars/$id/$avatarId.${extension}?size=$imageSize"
    } else {
        val avatarId = idLong % 5
        // This only exists in png AND doesn't have any other sizes
        "https://cdn.discordapp.com/embed/avatars/$avatarId.png"
    }
}