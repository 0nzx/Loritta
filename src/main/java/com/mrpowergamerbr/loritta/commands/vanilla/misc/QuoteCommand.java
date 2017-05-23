package com.mrpowergamerbr.loritta.commands.vanilla.misc;

import java.util.Arrays;
import java.util.List;

import com.mrpowergamerbr.loritta.Loritta;
import com.mrpowergamerbr.loritta.LorittaLauncher;
import com.mrpowergamerbr.loritta.commands.CommandBase;
import com.mrpowergamerbr.loritta.commands.CommandCategory;
import com.mrpowergamerbr.loritta.commands.CommandContext;
import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import com.mrpowergamerbr.temmiewebhook.embed.AuthorEmbed;
import com.mrpowergamerbr.temmiewebhook.embed.FooterEmbed;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class QuoteCommand extends CommandBase {
	@Override
	public String getLabel() {
		return "mencionar";
	}

	@Override
	public String getDescription() {
		return "Menciona uma mensagem, dica: para copiar o ID da mensagem, ative o modo de desenvolvedor do Discord e clique com botão direito em uma mensagem!";
	}

	@Override
	public boolean hasCommandFeedback() {
		return false;
	}

	@Override
	public List<String> getExample() {
		return Arrays.asList("msgId Olá!");
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.DISCORD;
	}

	@Override
	public void run(CommandContext context) {
		if (context.getArgs().length >= 1) {
			TemmieWebhook temmie = Loritta.getOrCreateWebhook(context.getEvent().getTextChannel(), "Quote Webhook");

			Message msg = context.getEvent().getTextChannel().getMessageById(context.getArgs()[0]).complete();

			if (msg == null) {
				return;
			}

			if (context.getGuild().getMember(LorittaLauncher.getInstance().getJda().getSelfUser()).hasPermission(Permission.MESSAGE_MANAGE)) {
				context.getMessage().delete().complete(); // ok, vamos deletar a msg original
			}

			String content = msg.getAuthor().getAsMention() + " " + context.getEvent().getMessage().getRawContent().replace(context.getConfig().commandPrefix() + "mencionar " + context.getArgs()[0], "").trim();
			content = content.replace("@here", "");
			content = content.replace("@everyone", "");
			
			DiscordEmbed embed = DiscordEmbed
					.builder()
					.author(new AuthorEmbed(msg.getAuthor().getName() + " disse...", null, msg.getAuthor().getEffectiveAvatarUrl(), null))
					.color(123)
					.description(msg.getRawContent())
					// .title("Wow!")
					.footer(new FooterEmbed("em #" + context.getMessage().getTextChannel().getName() + (context.getGuild().getMember(LorittaLauncher.getInstance().getJda().getSelfUser()).hasPermission(Permission.MESSAGE_MANAGE) ? "" : " | Não tenho permissão para deletar mensagens!"), null, null))
					.build();

			DiscordMessage dm = DiscordMessage
					.builder()
					.avatarUrl(context.getMessage().getAuthor().getEffectiveAvatarUrl())
					.username(context.getMessage().getAuthor().getName())
					.content(content)
					.embed(embed)
					.build();


			dm.setEmbeds(Arrays.asList(embed));

			temmie.sendMessage(dm);

			System.out.println("Enviado!");
		} else {
			context.explain();
		}
	}
}
