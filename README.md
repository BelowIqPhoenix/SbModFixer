# SbModFixer
What does this mod do?
This mod allows you to control lag caused by entities on your Minecraft forge server. The mod executes a server wide clear lag script which removes all loaded entities.

 
What are the commands?
There are 4 commands in total, which are:
- /killall (Running this kills all the entities, excluding those in the blacklist)

- lr-blacklist-add [entity] (Adds an entity to the blacklist)

- lr-blacklist-remove [entity] (Removes and entity from the blacklist)

- lr-config-reload (Reloads the mod's config and blacklist)

 

Where are the mods config files?
There is a config for the mod in the "config/Lag Removal" folder called "config.json" which will allow you to configure the mod, changing the delay between automatic executions of the lag removal script as well as configure whether the mod will warn users before execution and finally an option to specify whether entities with nametags are excluded from the killall regardless of the blacklist.

 

What version of Minecraft is this for!?
The current version will run on 1.14, 1.15 and 1.16

 

Do players need to have the mod installed as well as the server?
Only the server will need to have the mod installed.

 

How do I install this mod?
Place the downloaded jar into the mods folder of your server and it should all work

 
IMPORTANT
Please note that this is a server side mod and is designed to run on a multiplayer server, it will not work if used in single player.
