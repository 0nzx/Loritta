package com.mrpowergamerbr.loritta.commands.vanilla.`fun`

import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.LorittaUtils
import com.mrpowergamerbr.loritta.utils.f
import com.mrpowergamerbr.loritta.utils.gifs.MentionGIF
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale

class DiscordiaCommand : CommandBase() {
	override fun getLabel(): String {
		return "discordia"
	}

	override fun getAliases(): List<String> {
		return listOf("discórdia")
	}

	override fun getDescription(locale: BaseLocale): String {
		return locale.DISCORDIA_DESCRIPTION.f();
	}

	override fun getExample(): List<String> {
		return listOf("@Loritta");
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.FUN;
	}

	override fun getUsage(): String {
		return "<imagem>";
	}

	override fun run(context: CommandContext) {
		var contextImage = LorittaUtils.getImageFromContext(context, 0);
		if (!LorittaUtils.isValidImage(context, contextImage)) {
			return;
		}
		var file = MentionGIF.getGIF(contextImage);

		context.sendFile(file, "discordia.gif", context.getAsMention(true));
		file.delete()
	}
}