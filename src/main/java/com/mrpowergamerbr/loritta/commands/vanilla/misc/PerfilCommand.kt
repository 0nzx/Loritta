package com.mrpowergamerbr.loritta.commands.vanilla.misc

import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.Loritta
import com.mrpowergamerbr.loritta.LorittaLauncher
import com.mrpowergamerbr.loritta.userdata.LorittaProfile
import com.mrpowergamerbr.loritta.utils.ImageUtils
import org.apache.commons.codec.binary.Base64
import java.awt.*
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

class PerfilCommand : CommandBase() {
    override fun getLabel():String {
        return "perfil";
    }

    override fun run(context: CommandContext) {
        val base = BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB); // Base
        val graphics = base.graphics as Graphics2D;
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        val profileWrapper = ImageIO.read(File(Loritta.FOLDER + "profile_wrapper.png")); // Wrapper do perfil
        var userProfile = context.lorittaUser.profile
        var user = if (context.message.mentionedUsers.size == 1) context.message.mentionedUsers[0] else context.userHandle
        if (user == null) {
            context.sendMessage(context.getAsMention(true) + "Não foi encontrado nenhum usuário com este nome!");
            return;
        }

        if (context.message.mentionedUsers.size == 1) {
            userProfile = LorittaLauncher.getInstance().getLorittaProfileForUser(context.message.mentionedUsers[0].id)
        }

        var background: BufferedImage?;

        if (userProfile.userId == Loritta.config.ownerId) {
           background = ImageIO.read(File(Loritta.FOLDER + "shantae_bg.png")); // Background padrão
        } else {
           background = ImageIO.read(File(Loritta.FOLDER + "default_background.png")); // Background padrão
        }

        graphics.drawImage(background, 0, 0, null); // Background fica atrás de tudo

        val imageUrl = URL(user.effectiveAvatarUrl) // Carregar avatar do usuário
        val connection = imageUrl.openConnection() as HttpURLConnection
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
        val avatar = ImageIO.read(connection.inputStream)

        val avatarImg = avatar.getScaledInstance(64, 64, Image.SCALE_SMOOTH)

        val bebasNeue = Font.createFont(Font.TRUETYPE_FONT,
                FileInputStream(File(Loritta.FOLDER + "BebasNeue.otf")))

        val bariolRegular = Font.createFont(Font.TRUETYPE_FONT,
                FileInputStream(File(Loritta.FOLDER + "bariol_regular.otf")))

        val mavenProBold = Font.createFont(Font.TRUETYPE_FONT,
                FileInputStream(File(Loritta.FOLDER + "mavenpro-bold.ttf")))

        val guildImages = ArrayList<Image>();

        val guilds = LorittaLauncher.getInstance().jda.guilds.filter { guild -> guild.isMember(user) };

        var idx = 0;
        for (guild in guilds) {
            if (guild.iconUrl != null) {
                if (idx > 14) {
                    break;
                }
                val connection = URL(guild.iconUrl).openConnection() as HttpURLConnection
                connection.setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
                var guild = ImageIO.read(connection.inputStream)
                var guildImg = ImageUtils.toBufferedImage(guild.getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                guildImg = guildImg.getSubimage(1, 1, guildImg.height - 1, guildImg.width - 1);
                guildImg = ImageUtils.makeRoundedCorner(guildImg, 999);
                guildImages.add(guildImg)
                idx++;
            }
        }
        graphics.drawImage(avatarImg, 5, 65, null); // Colar avatar do usuário no profile
        graphics.drawImage(profileWrapper, 0, 0, null); // Colar wrapper (precisa ser o último para ficar certo)
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.color = Color(211, 211, 211); // Cinza
        graphics.font = bebasNeue.deriveFont(24F);
        graphics.drawString(user.name, 78, 122)
        graphics.color = Color(255, 255, 255); // Branca
        graphics.font = bebasNeue.deriveFont(24F);
        graphics.drawString(user.name, 78, 120)
        graphics.font = bebasNeue.deriveFont(24F);

        if (idx > 14) {
            val minecraftia = Font.createFont(Font.TRUETYPE_FONT,
                    FileInputStream(File(Loritta.FOLDER + "minecraftia.ttf")))

            graphics.font = minecraftia.deriveFont(8F);
            graphics.drawString("+" + (guilds.size - 14) + " guilds", 20, 277)
        }

        var guildX = 10;
        var guildY = 141;
        for (guild in guildImages) {
            graphics.drawImage(guild, guildX, guildY, null);
            guildX += 24;

            if (guildX >= 10 + (24 * 3)) {
                guildX = 10;
                guildY += guild.getHeight(null);
            }
        }

        // Barrinha de XP
        graphics.color = Color(128, 128, 128)
        graphics.fillRect(87, 143, 202, 19);

        graphics.color = Color(0, 0, 0)
        graphics.fillRect(88, 144, 200, 17);

        // Calcular quanto a barrinha deveria ficar
        // 145 - 199
        val nextLevel = userProfile.getExpToAdvanceFrom(userProfile.getCurrentLevel() + 1) - userProfile.getExpToAdvanceFrom(userProfile.getCurrentLevel());
        val currentLevel = userProfile.xp - userProfile.getExpToAdvanceFrom(userProfile.getCurrentLevel());

        val percentage = (currentLevel.toDouble() / nextLevel.toDouble());

        graphics.color = Color(114, 137, 218)
        graphics.fillRect(89, 145, (percentage * 198).toInt(), 15);

        graphics.color = Color(255, 255, 255);

        graphics.font = bariolRegular.deriveFont(10F);
        ImageUtils.drawCenteredString(graphics, "$currentLevel/$nextLevel XP", Rectangle(89, 145, 198, 15), graphics.font);

        graphics.font = mavenProBold.deriveFont(24F)
        graphics.color = Color(118, 118, 118);
        graphics.drawString("NÍVEL", 86, 187);
        graphics.color = Color(90, 90, 90);
        graphics.font = mavenProBold.deriveFont(28F)
        ImageUtils.drawCenteredString(graphics, userProfile.getCurrentLevel().toString(), Rectangle(86, 189, 66, 23), graphics.font);
        graphics.color = Color(118, 118, 118);
        graphics.font = bariolRegular.deriveFont(12F)
        graphics.drawString("XP Total", 163, 178)
        graphics.drawString("Tempo Online", 163, 193)
        graphics.drawString("Reputação", 163, 208)

        graphics.drawString(userProfile.xp.toString(), 235, 178)

        val hours = userProfile.tempoOnline / 3600;
        val minutes = (userProfile.tempoOnline % 3600) / 60;
        val seconds = userProfile.tempoOnline % 60;

        graphics.drawString("${hours}h${minutes}m${seconds}s", 235, 193)
        graphics.drawString(0.toString(), 235, 208)
        graphics.font = bariolRegular.deriveFont(12F)
        graphics.drawString(userProfile.aboutMe, 89, 244)

        if (!userProfile.games.isEmpty()) {
            graphics.font = bariolRegular.deriveFont(10F)
            val games = ArrayList<GamePlayed>();
            for (entry in userProfile.games.entries) {
                games.add(GamePlayed(entry.key.replace("[---DOT---]", "."), entry.value));
            }

            val sorted = games.sortedWith(compareBy({ it.timeSpent })).reversed();

            graphics.drawString("Jogo mais jogado: " + sorted[0].game, 89, 280)
        }

        val os = ByteArrayOutputStream()
        ImageIO.write(base, "png", os)
        val inputStream = ByteArrayInputStream(os.toByteArray())

        context.sendFile(inputStream, "profile.png", "📝 | Perfil"); // E agora envie o arquivo
    }

    data class GamePlayed(val game: String, val timeSpent: Long)
}