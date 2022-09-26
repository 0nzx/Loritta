package net.perfectdreams.loritta.morenitta.commands.vanilla.social

import net.perfectdreams.loritta.morenitta.commands.AbstractCommand
import net.perfectdreams.loritta.morenitta.commands.CommandContext
import net.perfectdreams.loritta.morenitta.dao.Reputation
import net.perfectdreams.loritta.morenitta.tables.Reputations
import net.perfectdreams.loritta.morenitta.utils.Constants
import net.perfectdreams.loritta.morenitta.utils.DateUtils
import net.perfectdreams.loritta.common.locale.BaseLocale
import net.perfectdreams.loritta.common.locale.LocaleKeyData
import net.perfectdreams.loritta.morenitta.utils.loritta
import net.perfectdreams.loritta.morenitta.utils.stripCodeMarks
import net.perfectdreams.loritta.common.commands.ArgumentType
import net.perfectdreams.loritta.common.commands.arguments
import net.perfectdreams.loritta.morenitta.messages.LorittaReply
import net.perfectdreams.loritta.morenitta.utils.AccountUtils
import net.perfectdreams.loritta.common.utils.Emotes

class RepCommand : AbstractCommand("rep", listOf("reputation", "reputação", "reputacao"), net.perfectdreams.loritta.common.commands.CommandCategory.SOCIAL) {
	override fun getDescriptionKey() = LocaleKeyData("commands.command.reputation.description")
	override fun getExamplesKey() = LocaleKeyData("commands.command.reputation.examples")

	override fun canUseInPrivateChannel(): Boolean {
		return false
	}

	override fun getUsage() = arguments {
		argument(ArgumentType.USER) {}
	}

	override suspend fun run(context: CommandContext, locale: BaseLocale) {
		val arg0 = context.rawArgs.getOrNull(0)

		val user = context.getUserAt(0)
		val lastReputationGiven = loritta.newSuspendedTransaction {
			Reputation.find {
				(Reputations.givenById eq context.userHandle.idLong)
			}.sortedByDescending { it.receivedAt }.firstOrNull()
		}

		if (lastReputationGiven != null) {
			val diff = System.currentTimeMillis() - lastReputationGiven.receivedAt

			if (3_600_000 > diff) {
				val fancy = DateUtils.formatDateDiff(lastReputationGiven.receivedAt + 3.6e+6.toLong(), locale)
				context.sendMessage(Constants.ERROR + " **|** " + context.getAsMention(true) + context.locale["commands.command.reputation.wait", fancy])
				return
			}
		}

		if (user != null) {
			if (user == context.userHandle) {
				context.reply(
                        LorittaReply(
                                message = locale["commands.command.reputation.repSelf"],
                                prefix = Constants.ERROR
                        )
				)
				return
			}

			val dailyReward = AccountUtils.getUserTodayDailyReward(context.lorittaUser.profile)

			if (dailyReward == null) { // Nós apenas queremos permitir que a pessoa aposte na rifa caso já tenha pegado sonhos alguma vez hoje
				context.reply(
						LorittaReply(
								locale["commands.youNeedToGetDailyRewardBeforeDoingThisAction", context.config.commandPrefix],
								Constants.ERROR
						)
				)
				return
			}

			var url = "${loritta.instanceConfig.loritta.website.url}user/${user.id}/rep"
			if (!context.isPrivateChannel)
				url += "?guild=${context.guild.id}&channel=${context.message.channel.id}"

			context.reply(
                    LorittaReply(
                            locale["commands.command.reputation.reputationLink", url],
                            Emotes.LORI_HAPPY
                    )
			)
		} else {
			if (context.args.isEmpty()) {
				this.explain(context)
			} else {
				context.reply(
                        LorittaReply(
                                message = locale["commands.userDoesNotExist", arg0?.stripCodeMarks()],
                                prefix = Constants.ERROR
                        )
				)
			}
		}
	}
}