package net.perfectdreams.loritta.cinnamon.discord.interactions.vanilla.roleplay

import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import net.perfectdreams.randomroleplaypictures.client.RandomRoleplayPicturesClient

class RoleplayHeadPatExecutor(
    loritta: LorittaCinnamon,
    client: RandomRoleplayPicturesClient,
) : RoleplayPictureExecutor(
    loritta,
    client,
    RoleplayUtils.HEAD_PAT_ATTRIBUTES
)