package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.morenitta.LorittaBot

class BuckShirtExecutor(
    loritta: LorittaBot,
    client: GabrielaImageServerClient
) : GabrielaImageServerSingleCommandBase(
    loritta,
    client,
    { client.images.buckShirt(it) },
    "buck_shirt.png"
)