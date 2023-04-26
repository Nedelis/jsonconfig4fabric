# jsonconfig4fabric

Welcome to the jsonconfig4fabric! This is relatively simple utility for creating and use .json configs in your mods. Here you can find out how to use jsonconfig4fabric.

<img src="https://github.com/Nedelis/jsonconfig4fabric/blob/main/jsonconfig4fabric_logo.png?raw=true" alt="logo" width="200px">

**This README is for the version 2.0b!**

**If you have any questions, you can ask them in my discord — `Nedelis#9496`!**

# Table of contents:
1. [Installation](#installation)
2. [Set up the Logger](#set-up-the-logger)
3. [Usage](#usage)

# Installation
1. Download the latest release from the [releases page](https://github.com/Nedelis/jsonconfig4fabric/releases)
2. Open your mod's project folder and put latest release file in java folder. For example:
```
SomeModProject
└── src
    └── main
        └── java
            └── com
                └── example
                    └── somemod
                        └── config
                            └── JSONConfigHandler.java <- HERE
```
3. That's all. Now you are ready to use jsonconfig4fabric!
> [Return to the table of contents](#table-of-contents)

# Set up the Logger
1. Open the `JSONConfigHandler.java`
2. First way: <br/>
   Find this line: `private static Logger LOGGER = LoggerFactory.getLogger("modID:JSONConfigHandler");` <br/>
   Replace `modId` with your mod's ID or replace the whole logger name (`"modId:JSONConfigHandler"`) with the name you want <br/>
   Ready! <br/>
3. Second way: <br/>
   Write the following line in your mod's onInitialize() method: `JSONConfigHandler.setLogger("some_logger_name")` and import the `JSONConfigHandler class` <br/>
   Ready! <br/>
   *(please notice, that using this way to set up your Logger is not recommended, because you'll have to read config files in onInitialize() method too)* <br/>
4. That's all. You have specified Logger of jsonconfig4fabric!
> [Return to the table of contents](#table-of-contents)

# Usage
Let's imagine that we have fabric project like this:
```
SomeModProject
├── ...
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── somemod
│       │               ├── config
│       │               │   └── JSONConfigHandler.java
│       │               └── SomeMod.java
│       ├── resources
│       │   └── assets
│       │       └── somemod
│       │           ├── config
│       │           ├── lang
│       │           └── icon.png
│       └── ...
└── ...
```
`...` means other folders which we don't need for following examples

Before reading examples, you should open JSONConfigHandler.java and read javadocs for each method we'll use.

> [Return to the table of contents](#table-of-contents)

> [Simple example](#simple-example)

> [Hard example](#hard-example)

## Simple Example

First, let's add a default config file to our mod. Create a file `somemod_config.json` in this folder: `./SomeModProject/src/main/resources/assets/somemod/config/`.

Now let's fill our default config file. We will asume that our mod adds tiered weapons, so we need to specify damage boost for each weapon tier, like this:
```json
{
  "firstTier": 0,
  "secondTier": 5,
  "thirdTier": 15,
  "fourthTier": 30
}
```

Next, we need do create java-class which will representing our config in java. Let's create it in `./SomeModProject/src/main/java/com/example/somemod/config/` with name `ModsConfig.java` and fill it like so:
```java
  // package ...
  // imports ...
  
  public class ModConfigs {
    // Reading default config file from resources package
    private static final File defaultConfigFile = new File(SomeMod.class.getClassLoader().getResource("assets/somemod/config/somemod_config.json").getPath());
    
    // Creating config
    private static final JSONConfigHandler config = JSONConfigHandler.of("somemod_config", defaultConfigFile);
    
    // And finally assign config values to java fields
    // asInt has two arguments - (String key, int def), where key is value key and def is default int value
    public static final int FIRST_TIER = config.asInt("firstTier", 0);
    public static final int SECOND_TIER = config.asInt("secondTier", 5);
    public static final int THIRD_TIER = config.asInt("thirdTier", 15);
    public static final int FOURTH_TIER = config.asInt("fourthTier", 30);
  }
```

By so, we've moved in our config values in java, and now we can use it for our mod!

> [Return to the table of contents](#table-of-contents)

> [Return to the usage chapter](#usage)

## Hard Example

OK, now we'll do something more hard. Let's imagine that we have this config:
```json
{
  "disabledItems": {
    "allWorlds": ["minecraft:bedrock", "somemod:creative_wand"],
    "overWorld": ["somemod:world_regen_wand"],
    "nether": ["somemod:world_regen_wand"],
    "end": ["somemod:wand_of_fly"]
  },
  "modDifficultyLevel": 5,
  "flightsAllowed": true,
  "specialItemsSettings": {
    "destructionWands": {
      "wandOfFullDestruction": {
        "radius": 3,
        "reload": 5000
      },
      "chunkDestructorWand": {
        "reload": 10000
      }
    },
    "transformationWands": {
      "diamondTransformationWand": {
        "transformableBlocks": ["minecraft:golden_block"]
      }
    }
  }
}
```

This looks pretty hard, isn't it? Yeah, but we can move it to java relatively easy!
```java
  // package ...
  // imports ...
  
  public class ModConfigs {
    // Reading default config file from resources package
    private static final File defaultConfigFile = new File(SomeMod.class.getClassLoader().getResource("assets/somemod/config/somemod_config.json").getPath());
    
    // Creating config
    private static final JSONConfigHandler config = JSONConfigHandler.of("somemod_config", defaultConfigFile);
    
    // Assign values
    public static final Map<String, Object> DISABLED_ITEMS = config.asMap("disabledItems", new HashMap<String, Object>());
    // And, for example, let's get allWorlds disabled items!
    public static final List<Object> ALL_WORLDS_DISABLED_ITEMS = JSONConfigHandler.ParseUtils.asList(DISABLED_ITEMS.get("allWorlds"), new ArrayList<Object>());
    // skip some other well-known stuff...
    // And know, for example, we need reload time of chunkDestructorWand. Let's find it!
    public static final int CHUNK_DESTRUCTOR_WAND_RELOAD_TIME =
    JSONConfigHandler.ParseUtils.asInt(JSONConfigHandler.ParseUtils.asMap(JSONConfigHandler.ParseUtils.asMap(config.asMap(
    "specialItemsSettings", new HashMap<String, Object>()).get(
    "destructionWands"), new HashMap<String, Object>()).get(
    "chunkDestructorWand"), new HashMap<String, Object>()).get(
    "relaod"), 10000)
    // The remaining values can be obtained in the same way...
```

Phew, that wasn't easy, let me understand, how we've got chunk destructor wand reload.
```java
  //First we've got specialItemsSettings as map
  Map<String, Object> SPECIAL_ITEMS_SETTINGS = config.asMap("specialItemsSettings", new HashMap<String, Object>());
  // Next we've got destructionWands and chunkDestructorWand as maps, by doing so
  Map<String, Object> DESTRUCTION_WANDS = JSONConfigHandler.ParseUtils.asMap(SPECIAL_ITEMS_SETTINGS.get("destructionWands", new HashMap<String, Object>());
  Map<String, Object> CHUNK_DESTRUCTOR_WAND = JSONConfigHandler.ParseUtils.asMap(DESTRUCTION_WANDS.get("chunkDestructorWand", new HashMap<String, Object>());
  // And finally we've got a reload value
  int CHUNK_DESTRUCTOR_WAND_RELOAD = JSONConfigHandler.ParseUtils.asInt(CHUNK_DESTRUCTOR_WAND.get("reload", 10000);
```

To my mind, this looks much more understandable, but in the main example I've just done it in one line! 

That's all, I hope, that now you know how to use jsonconfig4fabric!

> [Return to the table of contents](#table-of-contents)

> [Return to the usage chapter](#usage)
