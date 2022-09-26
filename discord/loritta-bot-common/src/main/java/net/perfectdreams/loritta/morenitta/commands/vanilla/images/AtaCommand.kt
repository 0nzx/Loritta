package net.perfectdreams.loritta.morenitta.commands.vanilla.images

import net.perfectdreams.loritta.morenitta.platform.discord.LorittaDiscord
import net.perfectdreams.loritta.morenitta.commands.vanilla.images.base.GabrielaImageServerCommandBase

class AtaCommand(m: LorittaDiscord) : GabrielaImageServerCommandBase(
	m,
	listOf("ata", "monicaata", "mônicaata"),
	1,
	"commands.command.ata.description",
	"/api/v1/images/monica-ata",
	"ata.png",
	slashCommandName = "ata monica"
)