package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.undertale.textbox.characters

import dev.kord.rest.builder.component.SelectMenuBuilder
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.undertale.declarations.UndertaleCommand

object UndertaleToriel : CharacterData() {
    override fun menuOptions(i18nContext: I18nContext, activePortrait: String, builder: SelectMenuBuilder) {
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Neutral),
            "undertale/toriel/neutral",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Annoyed),
            "undertale/toriel/annoyed",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Annoyed2), "undertale/toriel/annoyed2", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Blushing),
            "undertale/toriel/blushing",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyes),
            "undertale/toriel/closed_eyes",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesGlasses), "undertale/toriel/closed_eyes_glasses", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesHappy), "undertale/toriel/closed_eyes_happy", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad),
            "undertale/toriel/closed_eyes_sad",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad2), "undertale/toriel/closed_eyes_sad2", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad3), "undertale/toriel/closed_eyes_sad3", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad4), "undertale/toriel/closed_eyes_sad4", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad5), "undertale/toriel/closed_eyes_sad5", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSad6), "undertale/toriel/closed_eyes_sad6", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.ClosedEyesSadSmile), "undertale/toriel/closed_eyes_sad_smile", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Crazy),
            "undertale/toriel/crazy",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Crazy2), "undertale/toriel/crazy2", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Crazy3), "undertale/toriel/crazy3", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Disbelief),
            "undertale/toriel/disbelief",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.DisbeliefLookingAway),
            "undertale/toriel/disbelief_looking_away",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Glasses), "undertale/toriel/glasses", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Happy),
            "undertale/toriel/happy",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.HappyGlasses), "undertale/toriel/happy_glasses", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Hurt),
            "undertale/toriel/hurt",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.HurtLaughing), "undertale/toriel/hurt_laughing", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Incredulous),
            "undertale/toriel/incredulous",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.IncredulousLookingAway),
            "undertale/toriel/incredulous_looking_away",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.LookingAway),
            "undertale/toriel/looking_away",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.LookingAwayGlasses), "undertale/toriel/looking_away_glasses", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Pissed),
            "undertale/toriel/pissed",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.PissedGlasses), "undertale/toriel/pissed_glasses", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Sad),
            "undertale/toriel/sad",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SadLookingAway),
            "undertale/toriel/sad_looking_away",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SadSmile),
            "undertale/toriel/sad_smile",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SemiClosedEyes),
            "undertale/toriel/semi_closed_eyes",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SemiClosedEyesGlasses), "undertale/toriel/semi_closed_eyes_glasses", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SemiClosedEyesHappy), "undertale/toriel/semi_closed_eyes_happy", activePortrait)
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SemiClosedEyesSad), "undertale/toriel/semi_closed_eyes_sad", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Serious),
            "undertale/toriel/serious",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SeriousLookingAway),
            "undertale/toriel/serious_looking_away",
            activePortrait
        )
        // builder.optionAndAutomaticallySetDefault(i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.SeriousTalking), "undertale/toriel/serious_talking", activePortrait)
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Shocked),
            "undertale/toriel/shocked",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Surprised),
            "undertale/toriel/surprised",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.Uhhh),
            "undertale/toriel/uhhh",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.What),
            "undertale/toriel/what",
            activePortrait
        )
        builder.optionAndAutomaticallySetDefault(
            i18nContext.get(UndertaleCommand.I18N_TEXTBOX_PREFIX.Portraits.WhatFunny),
            "undertale/toriel/what_funny",
            activePortrait
        )
    }
}