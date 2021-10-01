package net.perfectdreams.loritta.cinnamon.platform.commands.images

import net.perfectdreams.loritta.cinnamon.platform.commands.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.cinnamon.platform.commands.images.base.SingleImageOptions
import net.perfectdreams.loritta.cinnamon.platform.commands.declarations.CommandExecutorDeclaration
import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient

class GessyAtaExecutor(
    client: GabrielaImageServerClient
) : GabrielaImageServerSingleCommandBase(
    client,
    { client.images.gessyAta(it) },
    "gessy_ata.png"
) {
    companion object : CommandExecutorDeclaration(GessyAtaExecutor::class) {
        override val options = SingleImageOptions
    }
}