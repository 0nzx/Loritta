package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.declarations

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.loritta.cinnamon.locale.LanguageManager
import net.perfectdreams.loritta.cinnamon.utils.TodoFixThisData
import net.perfectdreams.loritta.cinnamon.i18n.I18nKeysData
import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CommandCategory
import net.perfectdreams.loritta.cinnamon.discord.interactions.commands.CinnamonSlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.BolsoFrameExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.Bolsonaro2Executor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.BolsonaroExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.BriggsCoverExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.CanellaDvdExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.CepoDeMadeiraExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.ChicoAtaExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.CortesFlowExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.EdnaldoBandeiraExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.EdnaldoTvExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.GessyAtaExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.LoriAtaExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.MonicaAtaExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.RomeroBrittoExecutor
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.images.SAMExecutor

class BRMemesCommand(languageManager: LanguageManager) : CinnamonSlashCommandDeclarationWrapper(languageManager) {
   companion object {
       val I18N_PREFIX = I18nKeysData.Commands.Command.Brmemes
       const val I18N_CORTESFLOW_KEY_PREFIX = "commands.command.brmemes.cortesflow"
       val cortesFlowThumbnails = listOf(
           "arthur-benozzati-smile",
           "douglas-laughing",
           "douglas-pointing",
           "douglas-pray",
           "gaules-sad",
           "igor-angry",
           "igor-naked",
           "igor-pointing",
           "julio-cocielo-eyes",
           "lucas-inutilismo-exalted",
           "metaforando-badge",
           "metaforando-surprised",
           "mitico-succ",
           "monark-discussion",
           "monark-smoking",
           "monark-stop",
           "peter-jordan-action-figure",
           "poladoful-discussion",
           "rato-borrachudo-disappointed",
           "rato-borrachudo-no-glasses"
       )
   }

    override fun declaration() = slashCommand("brmemes", CommandCategory.IMAGES, TodoFixThisData) {
        subcommandGroup("bolsonaro", I18N_PREFIX.Bolsonaro.Description) {
            subcommand("tv", I18N_PREFIX.Bolsonaro.Tv.Description) {
                executor = { BolsonaroExecutor(it, it.gabrielaImageServerClient) }
            }

            subcommand("tv2", I18N_PREFIX.Bolsonaro.Tv.Description) {
                executor = { Bolsonaro2Executor(it, it.gabrielaImageServerClient) }
            }

            subcommand("frame", I18N_PREFIX.Bolsonaro.Frame.Description) {
                executor = { BolsoFrameExecutor(it, it.gabrielaImageServerClient) }
            }
        }

        subcommandGroup("ata", I18N_PREFIX.Ata.Description) {
            subcommand("monica", I18N_PREFIX.Ata.Monica.Description) {
                executor = { MonicaAtaExecutor(it, it.gabrielaImageServerClient) }
            }

            subcommand("chico", I18N_PREFIX.Ata.Chico.Description) {
                executor = { ChicoAtaExecutor(it, it.gabrielaImageServerClient) }
            }

            subcommand("lori", I18N_PREFIX.Ata.Lori.Description) {
                executor = { LoriAtaExecutor(it, it.gabrielaImageServerClient) }
            }

            subcommand("gessy", I18N_PREFIX.Ata.Gessy.Description) {
                executor = { GessyAtaExecutor(it, it.gabrielaImageServerClient) }
            }
        }

        subcommandGroup("ednaldo", TodoFixThisData) {
            subcommand(
                "flag",
                I18N_PREFIX.Ednaldo.Bandeira.Description
            ) {
                executor = { EdnaldoBandeiraExecutor(it, it.gabrielaImageServerClient) }
            }

            subcommand("tv", I18N_PREFIX.Ednaldo.Tv.Description) {
                executor = { EdnaldoTvExecutor(it, it.gabrielaImageServerClient) }
            }
        }

        subcommand("cortesflow", I18N_PREFIX.Cortesflow.Description) {
            executor = { CortesFlowExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("sam", I18N_PREFIX.Sam.Description) {
            executor = { SAMExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("canelladvd", I18N_PREFIX.Canelladvd.Description) {
            executor = { CanellaDvdExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("cepo", I18N_PREFIX.Cepo.Description) {
            executor = { CepoDeMadeiraExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("romerobritto", I18N_PREFIX.Romerobritto.Description) {
            executor = { RomeroBrittoExecutor(it, it.gabrielaImageServerClient) }
        }

        subcommand("briggscover", I18N_PREFIX.Briggscover.Description) {
            executor = { BriggsCoverExecutor(it, it.gabrielaImageServerClient) }
        }
    }
}