package com.mrpowergamerbr.loritta.commands.vanilla.fun;

import java.io.File;
import java.io.IOException;

import com.mrpowergamerbr.loritta.Loritta;
import com.mrpowergamerbr.loritta.commands.CommandBase;
import com.mrpowergamerbr.loritta.commands.CommandCategory;
import com.mrpowergamerbr.loritta.commands.CommandContext;
import com.mrpowergamerbr.loritta.utils.Jankenpon;
import com.mrpowergamerbr.loritta.utils.Jankenpon.JankenponStatus;

public class PedraPapelTesouraCommand extends CommandBase {
	@Override
	public String getLabel() {
		return "ppt";
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.FUN;
	}

	@Override
	public void run(CommandContext context) {
		if (context.getArgs().length >= 1) {
			String val = context.getArgs()[0];

			Jankenpon janken = Jankenpon.getFromLangString(val.toLowerCase());

			if (janken != null) {
				Jankenpon opponent = Jankenpon.values()[Loritta.getRandom().nextInt(Jankenpon.values().length)];

				JankenponStatus status = janken.getStatus(opponent);

				String fancy = null;
				if (status == JankenponStatus.WIN) {
					fancy = "**Parabéns, você ganhou! :)**";
				}
				if (status == JankenponStatus.LOSE) {
					fancy = "**Que pena... você perdeu, mas o que vale é a intenção! :)**";
				}
				if (status == JankenponStatus.DRAW) {
					fancy = "**Empate! Que tal uma revanche? :)**";
				}
				context.sendMessage(context.getAsMention(true) + "Você escolheu " + janken.getEmoji() + ", eu escolhi " + opponent.getEmoji() + "\n" + fancy);
			} else {
				if (val.equalsIgnoreCase("jesus")) {
					String fancy = "**Empate...? 🤔 🤷**";
					context.sendMessage(context.getAsMention(true) + "Você escolheu 🙇 *JESUS CRISTO*🙇, eu escolhi 🙇 *JESUS CRISTO*🙇\n" + fancy);
				} else if (val.equalsIgnoreCase("velberan")) {
					Jankenpon opponent = Jankenpon.values()[Loritta.getRandom().nextInt(Jankenpon.values().length)];
					
					String fancy = "🕹🕹🕹";
					context.sendMessage(context.getAsMention(true) + "Você escolheu 🕹 *VELBERAN*🕹, eu escolhi " + opponent.getEmoji() + "\n" + fancy);
					try {
						context.sendFile(new File(Loritta.FOLDER + "velberan.gif"), "velberan.gif", " ");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					String fancy = "**Que pena... você perdeu, dá próxima vez escolha algo que seja válido, ok? :)**";
					context.sendMessage(context.getAsMention(true) + "Você escolheu 💩, eu escolhi 🙇 *JESUS CRISTO*🙇\n" + fancy);
				}
			}
		} else {
			context.explain();
		}
	}
}
