

<p align="center">
<img width="65%" src="https://cdn.discordapp.com/attachments/708017680677863505/709793353478176768/lori_github_logo.png">
<br>

<h1 align="center">⭐ Loritta Morenitta ⭐</h1>

<p align="center">
<a href="https://discordbots.org/bot/297153970613387264?utm_source=widget">
  <img src="https://discordbots.org/api/widget/297153970613387264.png?test=123456" alt="Discord Bots" />
</a>
 </p>
<p align="center">
<a href="https://loritta.website"><img src="https://img.shields.io/badge/website-loritta-blue.svg"></a>
</a>
<a href="https://loritta.website/donate"><img src="https://img.shields.io/badge/donate-loritta-00CE44.svg"></a>
<a href="https://loritta.website/support"><img src="https://discordapp.com/api/guilds/297732013006389252/widget.png"></a>
<a href="https://mrpowergamerbr.com/"><img src="https://img.shields.io/badge/website-mrpowergamerbr-blue.svg"></a>
</p>
<p align="center">
<a href="https://github.com/LorittaBot/Loritta/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-AGPL%20v3-lightgray.svg"></a>
</p>

All Discord servers have the same problems. Members want entertainment, moderators want automation... and you just want to rest.

Your life is too precious to spend your time with useless junk, let me take care of the boring parts while you have fun on your server!

With features to entertain and engage your members, moderation features to keep your server always safe and enjoyable, and with an easy way to set up but with an unmatched power of customization...

And everything thanks to a 16 year old girl trying to make the world a better place!

_Making your server unique and extraordinary has never been easier!_

## 🤔 How can I add her?

If you want to use Loritta on your server, you can add our public instance by [clicking here](https://loritta.website/dashboard)! We recommend using the public instance, after all, more than 400k guilds already use, trust and love her, so why not try it out?

You can also host Loritta yourself, however we won't give support for people that are trying to selfhost her, we don't want to spend hours trying to troubleshoot other people issues that only happens on selfhosted instances, so you should at least know how to troubleshoot issues, if you find any.

## 📁 Project Structure
* 📜 **Loritta's API** [`loritta-api`]

> Multiplatform Loritta API, commands and features that only depend on the Loritta's API can be ported to other platforms, as long as they implement Loritta's API.

* 🎀 **Loritta (Discord/JDA)** [`loritta-discord` ]

> Discord implementation of Loritta's API, this is the public bot you all know and love so much! If you are planning to help Loritta's development, this is where to start!

* 🔌**Loritta's Plugins** [`loritta-plugins`]

> Sometimes restarting Loritta just to fix a small bug in a command can be a pain, that's why plugins exist! Plugins can be loaded/unloaded/updated during runtime, so you don't need to restart just to add a new cool command.
* * 🥩 **Rosbife** [`rosbife`]

>> Commands related to image edits.

* * 🎨 **Profile Designs** [`profile-designs`]

>> Defines the profiles users can buy for their `+profile`.

* * 🤑 **Donators Ostentation** [`donators-ostentation`]

>> Handles Nitro Boost features, premium slots channels automation, auto sonhos payout and other miscellaneous features.

* * 🖼️ **Auto Banner Changer** [`auto-banner-changer`]

>> Automatically changes the banner in the offical Loritta support servers... yup, that's it.

* * 👩‍💻 **Parallax Routes** [`parallax-routes`]

>> Creates endpoints for the Parallax Code Server.

* * And many others!

* 🔗 **Loritta Website** [`loritta-website`]
* * 🌶️ **Spicy Morenitta** [`spicy-morenitta`]

>> Spicying up Loritta's frontend! This is the code that gets executed in the browser.

* 🐶 **Loritta Watchdog (Discord/JDA)** [`loritta-watchdog-bot`]

> bark bark! Used to track Loritta's cluster statuses and other miscellaneous stuff.

* 🐱‍💻 **Parallax Code Server** [`parallax-code-server`]

> Executes custom JavaScript commands with GraalJS. Runs in a separate JVM to avoid malicious users crashing Loritta or breaking out of the sandbox, also because it is easier to update the code server with new features!

* 💫 **Shard Controller** [`shard-controller`]

> Large bots with the "Sharding for very large bots" feature requires something to synchronize their shard login status to avoid getting ratelimited during login. The shard controller (named [Tsuki](https://fortnite.fandom.com/wiki/Tsuki)) is a very small http server that controls what shards can login at any given time.

* 💸 **Loritta Premium** [`loritta-premium`]

> Does absolutely nothing! No, really, this is just a bot for premium users to show off that they bought premium features. All premium features are in the main bot.

* 🚧 **Loritta (Discord/Eris)** [`loritta-eris`]

> *Very* experimental (proof of concept) implementation of Loritta's API on a node.js environment using Kotlin/JS. This is just a "Hey look at this! *Code sharing* between modules! Sooooo cool!" project.

* 🐱 **Temmie Discord Auth** [`temmie-discord-auth`]

> Discord OAuth2 Client, named after [Temmie](https://youtu.be/_BD140nCDps). Why Temmie? Why *not* Temmie!

## 👨‍💻 Compiling Loritta
0. *(Pre-requisite)* You need to have the [Java Development Kit](https://adoptopenjdk.net/) and Git installed on your machine. Check if you can access `java` from your OS command line. If yes, then great!
1. Clone the repository with git: `git clone https://github.com/LorittaBot/Loritta.git`
2. Go inside the newly created folder and open the command prompt, terminal or PowerShell inside the folder.
3. Build Loritta with Gradle: `./gradlew build`
4. If the build is successful, then congratulations 🎉! You successfully compiled Loritta! Now why not run it?

## 🚀 Selfhosting Loritta (Discord)

If you are planning to selfhost Loritta, here are some stuff that you should keep in mind...
1. We keep the source code open so people can see, learn and be inspired by how Loritta was made and, if they want to, they can help the project with features and bug fixes.
2. This is a community project, if you make changes to Loritta's source code you need to follow the [AGPL-3.0](LICENSE) and keep the changes open source! And, if you want to help Loritta, why not create a pull request? 😉
3. We **do not** give support for selfhosted instances, you need to know how to troubleshoot the issues yourself. We tried to make the selfhost process as painless as possible, but it is impossible to know all the different issues you may find.
4. Don't lie saying that you "created Loritta". Please give credits to the creators!
5. Loritta requires a lot of different API keys for a lot of features. While they aren't required, you may encounter issues when trying to use some of the features.
6. Loritta's assets (fonts, images, etc) aren't not distributed with the source code, you will need to create and include your own assets.
7. We use Ubuntu 18.04 to run her, she may work on other Linux operating systems or even in Windows, but we recommend hosting her on Ubuntu!
8. To avoid problems and confusions, we **do not allow** using the name "Loritta", "Lori" or any similar names on your selfhosted versions. Call her "Nicole" if you don't have a creative name to give to your selfhosted version.

Seems too hard but you *really* want to use Loritta? Don't worry, you can use our free public instance by clicking here [clicking here](https://loritta.website/dashboard)!

Feeling adventurous? Then follow the steps! 

0. *(Pre-requisite)* You need to have the [Java Development Kit](https://adoptopenjdk.net/) and Git installed on your machine. Check if you can access `java` from your OS command line. If yes, then great!
1. *(Optional, but highly recommended)* Install PostgreSQL, while Loritta also supports SQLite as a database, we recommend and support using PostgreSQL as the database!
2. Create a empty folder somewhere in your OS, why an empty folder? Just to keep things tidy! :3
3. In the folder, you will need to have Loritta's JAR and Loritta's libraries.
* **If you compiled it yourself:**
* * **Loritta (Discord) JAR:** `loritta-discord/build/libs/` (get the Fat JAR version!)
* * **Loritta Libraries:** `libs/`
* **If you are lazy and don't want to compile it yourself:**
* * **You can find precompiled artifacts here:** https://github.com/LorittaBot/Loritta/actions?query=workflow%3A%22Build+Loritta%22
* * You will need to get `Loritta (Discord)` and `Loritta (Libs)`
* **If you did everything correctly, you will have two files on your folder:** `loritta-discord-fat.jar` and a folder named `libs` with all the libraries Loritta uses inside of it.
4. Run Loritta with `java -jar loritta-discord-fat.jar`, this will create the default configurations to run her.
5. Update the configurations with your own values. You don't need to configure everything, just the bare minimum (bot token, folders, etc) to get her up and running!
6. Download the locales from the [LorittaLocales repository](https://github.com/LorittaBot/LorittaLocales) and extract the files inside the locales folder you configured in the previous step.
7. Run Loritta again with `java -jar loritta-discord-fat.jar`
8. After booting up, try using `+ping` on your Discord server.
9. If everything went well, your very own Loritta instance should be up and running! Congratulations! 🎉
10. *(Optional)* You can add plugins to your instance!
11. *(Optional)* Set up the Parallax Code Server + Parallax Routes plugin if you want to be able to execute custom JavaScript commands.
12. *(Optional)* If you are planning on using it on a bot that has the "Sharding for very large bots" feature, set up the Shard Controller.

<p align="center">
<img src="https://cdn.discordapp.com/attachments/708017680677863505/709834156145770534/lori_deitada.png">
</p>