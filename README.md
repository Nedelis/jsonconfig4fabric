# jsonconfig4fabric

Welcome to the jsonconfig4fabric! This is relatively simple utility for creating and use .json configs in your mods. Here you can find out how to use jsonconfig4fabric.

<img src="https://github.com/Nedelis/jsonconfig4fabric/blob/main/jsonconfig4fabric_logo.png?raw=true" alt="logo" width="200px">

**This README is for the version 3.1b!**

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
                            └── JSONConfig4Fabric.java <- HERE
```
3. That's all. Now you are ready to use jsonconfig4fabric!
> [Return to the table of contents](#table-of-contents)

# Set up the Logger
1. Open the `JSONConfig4Fabric.java`
2. First way: <br/>
   Find this line: `private static Logger LOGGER = LoggerFactory.getLogger("modID:JSONConfig4Fabric");` <br/>
   Replace `modId` with your mod's ID or replace the whole logger name (`"modId:JSONConfig4Fabric"`) with the name you want <br/>
   Ready! <br/>
3. Second way: <br/>
   Write the following line in your mod's onInitialize() method: `JSONConfig4Fabric.setLogger("some_logger_name")` and import the `JSONConfig4Fabric class` <br/>
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
│       │               │   └── JSONConfig4Fabric.java
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

Before reading examples, you should open [JSONConfig4Fabric.java](https://github.com/Nedelis/jsonconfig4fabric/blob/main/JSONConfig4Fabric.java) and read javadocs for each method we'll use.

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
    private static final JSONConfig4Fabric config = JSONConfig4Fabric.of("somemod_config", defaultConfigFile);
    
    // And finally assign config values to java fields
    // T getAsJavaValue(String key, T def) has 2 agruments:
    // String key — key corresponding to the required value;
    // T def — default value, which will be returned if conversion fails (T specifies the return type of method)
    public static final int FIRST_TIER = config.getAsJavaValue("firstTier", 0);
    public static final int SECOND_TIER = config.getAsJavaValue("secondTier", 5);
    public static final int THIRD_TIER = config.getAsJavaValue("thirdTier", 15);
    public static final int FOURTH_TIER = config.getAsJavaValue("fourthTier", 30);
  }
```

By so, we've converted our config values to java values, and now we can use it for our mod!

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
    private static final JSONConfig4Fabric config = JSONConfig4Fabric.of("somemod_config", defaultConfigFile);
    
    // Assign values
    public static final Map<String, Object> DISABLED_ITEMS = config.getAsJavaValue("disabledItems", new HashMap<String, Object>());
    // And, for example, let's get allWorlds disabled items!
    public static final List<Object> ALL_WORLDS_DISABLED_ITEMS = JSONConfig4Fabric.JSONValueType.toJavaValue(DISABLED_ITEMS.get("allWorlds"), new ArrayList<Object>());
    // let's skip all the other values obtained in a similar way...
    // And now, for example, we need reload time of chunkDestructorWand. Let's find it!
    public static final int CHUNK_DESTRUCTOR_WAND_RELOAD_TIME =
    JSONConfig4Fabric.JSONValue.of(JSONConfig4Fabric.JSONValue.of(JSONConfig4Fabric.JSONValue.of(config.getAsJavaValue(
    "specialItemsSettings", new HashMap<String, Object>()).get(
    "destructionWands")).toJavaValue(new HashMap<String, Object>()).get(
    "chunkDestructorWand")).toJavaValue(new HashMap<String, Object>()).get(
    "reload")).toJavaValue(10000);
    // The remaining values can be obtained in the same way...
```

Phew, that wasn't easy, let me explain, how we've got chunk destructor wand reload.
```java
  //First we've got specialItemsSettings as map
  Map<String, Object> SPECIAL_ITEMS_SETTINGS = config.getAsJavaValue("specialItemsSettings", new HashMap<String, Object>());
  // Next we've got destructionWands and chunkDestructorWand as maps, by doing so
  Map<String, Object> DESTRUCTION_WANDS = JSONConfig4Fabric.JSONValue.of(SPECIAL_ITEMS_SETTINGS.get("destructionWands")).toJavaValue(new HashMap<String, Object>());
  Map<String, Object> CHUNK_DESTRUCTOR_WAND = JSONConfig4Fabric.JSONValue.of(DESTRUCTION_WANDS.get("chunkDestructorWand")).toJavaValue(new HashMap<String, Object>());
  // And finally we've got a reload value
  int CHUNK_DESTRUCTOR_WAND_RELOAD = JSONConfig4Fabric.JSONValue.of(CHUNK_DESTRUCTOR_WAND.get("reload")).toJavaValue(10000);
```

To my mind, this looks much more understandable, but in the main example I've just done it in one line! 

That's all, I hope, that now you know how to use jsonconfig4fabric!

> [Return to the table of contents](#table-of-contents)

> [Return to the usage chapter](#usage)
