package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.base.SingleImageOptions

class MonicaAtaExecutor(
    loritta: LorittaCinnamon,
    client: GabrielaImageServerClient
) : GabrielaImageServerSingleCommandBase(
    loritta,
    client,
    { client.images.monicaAta(it) },
    "ata.png"
)