package net.perfectdreams.loritta.deviousfun.events.message.delete

import dev.kord.common.entity.Snowflake
import net.perfectdreams.loritta.deviousfun.DeviousFun
import net.perfectdreams.loritta.deviousfun.entities.Channel
import net.perfectdreams.loritta.deviousfun.entities.Guild
import net.perfectdreams.loritta.deviousfun.events.message.GenericMessageEvent
import net.perfectdreams.loritta.deviousfun.gateway.DeviousGateway

class MessageDeleteEvent(
    deviousFun: DeviousFun,
    gateway: DeviousGateway,
    messageIdSnowflake: Snowflake,
    channel: Channel,
    val guild: Guild?
) : GenericMessageEvent(deviousFun, gateway, messageIdSnowflake, channel)