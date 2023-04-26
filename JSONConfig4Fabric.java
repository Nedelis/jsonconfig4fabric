package com.github.nedelis.itemsacuendo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;

/*
                   ALL CHANGES TO THIS FILE MUST BE DOCUMENTED
                   AUTHOR OF THE CHANGES MUST BE MENTIONED HERE

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (an example is provided in the Appendix below).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

   Copyright [2023] [github.com/nedelis]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

/**
 * Simple and useful utility for creating JSONConfigs for your mod
 * <p>
 * You should read the <a href="https://github.com/Nedelis/jsonconfig4fabric">documentation<a/>
 * to find out how to use this utility
 * @version 3.0b
 */
@SuppressWarnings("unused")
public class JSONConfig4Fabric {
    public static final String PATH_TO_MODS_CONFIGS = FabricLoaderImpl.INSTANCE.getConfigDir().toString();

    // replace 'modID' with your mod's id or replace the entire LOGGER's name with the name you need
    private static Logger LOGGER = LoggerFactory.getLogger("modID:JSONConfig4Fabric");

    private final Map<String, Object> config = new HashMap<>();
    private final File configFile;
    private boolean broken = false;

    /**
     * Creates a new config file in config files directory and fills it with default values
     * @param configFile config file to be created
     * @param defaultConfig default values for config file
     * @throws IOException thrown if file cannot be created
     */
    private void createConfig(@NotNull File configFile, @NotNull Map<?, ?> defaultConfig) throws IOException {
        Files.createFile(configFile.toPath());
        try (var writer = Files.newBufferedWriter(configFile.toPath())) {
            new GsonBuilder().setPrettyPrinting().create().toJson(defaultConfig, writer);
        }
    }

    /**
     * Loads the configuration file values to {@link #config}
     * @param configFile config file to load from
     * @throws IOException if config file is not found or any other I/O error occurs
     */
    private void loadConfig(@NotNull File configFile) throws IOException {
        var temp = new HashMap<String, Object>();
        var JSONConfig = new Gson().fromJson(Files.newBufferedReader(configFile.toPath(), StandardCharsets.UTF_8), Map.class);
        for (var key : JSONConfig.keySet()) {
            temp.put(key.toString(), JSONConfig.get(key));
        }
        config.putAll(temp);
    }

    /**
     * Creates new JSONConfig4Fabric from config file name and default config file
     * @param configFileName config file name without extension
     * @param defaultConfig default config file (you may want to put it in your mod's resources folder)
     * @return new JSONConfig4Fabric with loaded config values
     */
    @Contract("_, _ -> new")
    public static @NotNull JSONConfig4Fabric of(@NotNull String configFileName, @NotNull File defaultConfig) {
        return ofExtended(PATH_TO_MODS_CONFIGS, configFileName, defaultConfig);
    }

    /**
     * Extended variant of {@link #of(String, File)}; Here you can specify path to configs directory
     * @param pathToConfigDir path to config files directory
     */
    public static @NotNull JSONConfig4Fabric ofExtended(@NotNull String pathToConfigDir, @NotNull String configFileName, @NotNull File defaultConfig) {
        return new JSONConfig4Fabric(new File(pathToConfigDir + File.separator + configFileName + ".json"), defaultConfig);
    }

    /**
     * Another way to specify the Logger; You should use this method in your mod's init function
     * <p>
     * This way to specify the Logger is not required because if you use it, you'll must read
     * your mod's configs in your mod's init function too
     * <p>
     * Instead of this method, just replace the name of the {@link #LOGGER Logger} with the name you want
     * @param logger new Logger which you want to use
     */
    public static void setLogger(@NotNull Logger logger) {
        LOGGER = logger;
    }

    /**
     * You shouldn't use this constructor directly, use one of the 'of' methods instead
     * @see #of(String, File)
     * @see #ofExtended(String, String, File)
     */
    private JSONConfig4Fabric(@NotNull File configFile, @NotNull File defaultConfig) {
        if (!configFile.exists()) {
            try {
                createConfig(configFile, new Gson().fromJson(Files.newBufferedReader(
                        defaultConfig.toPath(), StandardCharsets.UTF_8), Map.class));
            } catch (IOException e) {
                LOGGER.error("Failed to generate [" + configFile.getName() + "] config file!", e);
            }
        }

        if (!broken) {
            try {
                loadConfig(configFile);
            } catch (IOException e) {
                LOGGER.error("Failed to load [" + configFile.getName() + "] config file!", e);
                broken = true;
            }
        }

        if (broken) {
            try {
                loadConfig(defaultConfig);
            } catch (IOException e) {
                LOGGER.error("Failed to load default config file for [" + configFile.getName() + "] config file!", e);
            }
        }

        this.configFile = configFile;
    }

    private JSONConfig4Fabric(@NotNull Map<String, Object> config) {
        this.configFile = null;
        this.config.putAll(config);
    }

    /**
     * Quires a value from config, returns null if the key does not exist
     *
     * @return value of corresponding key
     * @deprecated Use {@link #getRawOrDefault(String, Object)} instead
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @Nullable
    public Object getRaw(String key) {
        return config.get(key);
    }

    public Object getRawOrDefault(String key, Object def) {
        var val = getRaw(key);
        return val != null ? val : def;
    }

    public JSONValue get(String key) {
        return JSONValue.of(getRaw(key));
    }

    public JSONValue getOrDefault(String key, Object def) {
        return JSONValue.of(getRawOrDefault(key, def));
    }

    /**
     * Shortcut for "JSONValue.of(config.get("some_key")).toJavaValue()"
     * @see JSONValue#toJavaValue(Object)
     */
    public <T> T getAsJavaValue(String key, T def) {
        return get(key).toJavaValue(def);
    }

    /**
     * Returns the value corresponding to the given key only if the value is string
     * <p>
     * You should not use Object.toString() instead of this method because the Object may be
     * not a string or may be null
     * <p>
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public String asStr(String key, String def) {
        LOGGER.info("Trying to get string value corresponding to the key '" + key + "'...");
        return ParseUtils.asStr(getRaw(key), def);
    }

    /**
     * IMPORTANT: Use this method only if you are sure that the value corresponding to the key is int
     * <p>
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public int asInt(String key, int def) {
        return (int) asDouble(key, def);
    }

    /**
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public double asDouble(String key, double def) {
        LOGGER.info("Trying to get double value corresponding to the key '" + key + "'...");
        return JSONValue.JSONValueType.toJavaValue(getRaw(key), def);
    }

    /**
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public boolean asBool(String key, boolean def) {
        LOGGER.info("Trying to get boolean value corresponding to the key '" + key + "'...");
        return ParseUtils.asBool(getRaw(key), def);
    }

    /**
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public List<?> asList(String key, List<?> def) {
        LOGGER.info("Trying to get list value corresponding to the key '" + key + "'...");
        return ParseUtils.asList(getRaw(key), def);
    }

    /**
     * Will be removed soon
     * @deprecated Use {@link #getAsJavaValue(String, Object)} instead
     */
    @Deprecated
    public Map<String, ?> asMap(String key, Map<String, ?> def) {
        LOGGER.info("Trying to get map value corresponding to the key '" + key + "'...");
        return ParseUtils.asMap(getRaw(key), def);
    }

    /**
     * Creates a copy of the {@link #config} which should be used instead of the direct access to the {@link #config}
     * @return copy of the {@link #config}
     */
    public @NotNull JSONConfig4Fabric copy() {
        return new JSONConfig4Fabric(Map.copyOf(config));
    }

    /**
     * Check if the config is broken
     * @return {@link #broken}
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Deletes the config file from the config files directory
     * @return value of File.delete() function
     */
    public boolean delete() {
        LOGGER.warn("Config file [" + configFile.getName() + "] was deleted. Please, restart the game to regenerate it!");
        return configFile.delete();
    }

    /**
     * This class is quite useful for parsing any object from config
     * <p>
     * It also can be used to parse {@link Object} from the existing Maps or Lists
     * <p>
     * IMPORTANT:
     * Because in future it may become private, you should use more user-friendly parsing method —
     * {@link JSONValue.JSONValueType#toJavaValue(Object, Object)}
     */
    public static final class ParseUtils {

        public static String asStr(Object mayBeStr, String def) {
            LOGGER.info("[ParseUtils] Trying to convert '" + mayBeStr + "' to a string...");
            if (mayBeStr instanceof String realStr) {
                LOGGER.info("[ParseUtils] Successfully converted '" + realStr + "' to a string");
                return realStr;
            }
            LOGGER.warn("[ParseUtils] Unable to convert '" + mayBeStr + "' to a string, because it is not a string!");
            return def;
        }

        public static int asInt(Object mayBeInt, int def) {
            return (int) asDouble(mayBeInt, def);
        }

        public static double asDouble(Object mayBeDouble, double def) {
            LOGGER.info("[ParseUtils] Trying to convert '" + mayBeDouble + "' to a double...");
            if (mayBeDouble instanceof Double realDouble) {
                LOGGER.info("[ParseUtils] Successfully converted '" + realDouble + "' to a double");
                return realDouble;
            }
            LOGGER.warn("[ParseUtils] Unable to convert'" + mayBeDouble + "' to a double, because it is not a double!");
            return def;
        }

        public static boolean asBool(Object mayBeBoolean, boolean def) {
            LOGGER.info("[ParseUtils] Trying to convert '" + mayBeBoolean + "' to a boolean...");
            if (mayBeBoolean instanceof Boolean realBoolean) {
                LOGGER.info("[ParseUtils] Successfully converted '" + realBoolean + "' to a boolean");
                return realBoolean;
            }
            LOGGER.warn("[ParseUtils] Unable to convert'" + mayBeBoolean + "' to a double, because it is not a boolean!");
            return def;
        }

        public static List<?> asList(Object mayBeList, List<?> def) {
            LOGGER.info("[ParseUtils] Trying to convert '" + mayBeList + "' to a list...");
            if (mayBeList instanceof List<?> realList) {
                LOGGER.info("[ParseUtils] Successfully converted '" + realList + "' to a list");
                return realList;
            }
            LOGGER.warn("[ParseUtils] Unable to convert'" + mayBeList + "' to a list, because it is not a list!");
            return def;
        }

        public static Map<String, ?> asMap(Object mayBeMap, Map<String, ?> def) {
            LOGGER.info("[ParseUtils] Trying to convert '" + mayBeMap + "' to a map...");
            if (mayBeMap instanceof Map<?, ?> realMap) {
                var tempMap = new HashMap<String, Object>();
                for (var realMapKey : realMap.keySet())
                    tempMap.put(realMapKey.toString(), realMap.get(realMapKey));
                LOGGER.info("[ParseUtils] Successfully converted '" + tempMap + "' to a map");
                return tempMap;
            }
            LOGGER.warn("[ParseUtils] Unable to convert '" + mayBeMap + "' to a map, because it is not a map!");
            return def;
        }

    }

    /**
     * This class is used to wrap a json value; After wrapping a json value you can
     * call {@link #toJavaValue(Object)} which will return you converted JSONValue
     */
    public record JSONValue(Object value) {

        /**
         * You shouldn't use this class directly if you don't know what you're doing;
         * <p>
         * Use {@link #toJavaValue(Object)} instead
         */
        @SuppressWarnings("unchecked")
        public enum JSONValueType {
            STR(obj -> obj instanceof String) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) ParseUtils.asStr(toConvert, (String) def);
                }
            },
            INT(obj -> obj instanceof Integer) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) Integer.valueOf(ParseUtils.asInt(toConvert, (int) def));
                }
            },
            DOUBLE(obj -> obj instanceof Double) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) Double.valueOf(ParseUtils.asDouble(toConvert, (double) def));
                }
            },
            BOOL(obj -> obj instanceof Boolean) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) Boolean.valueOf(ParseUtils.asBool(toConvert, (boolean) def));
                }
            },
            LIST(obj -> obj instanceof List) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) ParseUtils.asList(toConvert, (List<?>) def);
                }
            },
            MAP(obj -> obj instanceof Map) {
                @Override
                protected <T> T convert(Object toConvert, @NotNull T def) {
                    return (T) ParseUtils.asMap(toConvert, ParseUtils.asMap(def, new HashMap<>()));
                }
            };

            private final Function<Object, Boolean> checkForInstance;

            JSONValueType(Function<Object, Boolean> checkForInstance) {
                this.checkForInstance = checkForInstance;
            }

            /**
             * Unsafe method to convert a json value into a java value; It's unsafe
             * because you may suddenly use a default value that won't be the same
             * type as the provided JSONValueType
             * @param toConvert object representing the json value to convert
             * @param def default value that will be returned if the conversion failed; specifies the "T"
             * @return converted to provided JSONValueType json value
             * @param <T> specifies which type the java value will be
             */
            @Deprecated
            @SuppressWarnings("DeprecatedIsStillUsed")
            protected abstract <T> T convert(Object toConvert, @NotNull T def);

            /**
             * Checks whether the given object to be checked is instanceof the provided JSONValueType or not
             * @param toCheck object to be checked
             * @return true if the give object to be checked is instanceof the provided JSONValueType
             */
            public boolean checkForInstance(Object toCheck) {
                return checkForInstance.apply(toCheck);
            }

            /**
             * Safe variant of {@link #convert(Object, Object)} method; It tries to find correct
             * JSONValueType for "T def" and converts given value to java value; If it can't find
             * correct JSONValueType for "T def", then it'll issue a warning and return given default value
             * @param value value to convert
             * @param def default value that will be returned if conversion failed; specifies the "T"
             * @return converted json value
             * @param <T> specifies which type the java value will be
             */
            public static <T> T toJavaValue(Object value, @NotNull T def) {
                for (var type : values())
                    if (type.checkForInstance(def))
                        return type.convert(value, def);
                LOGGER.warn(
                        "Error during converting '" +
                        value + "' to a '" + def.getClass().getName() +
                        "', because there is no such value type as '" +
                        def.getClass().getName() + "' in JSON!"
                );
                return def;
            }

        }

        /**
         * Shortcut for JSONValue constructor
         * @param value some json value
         * @return new JSONValue
         */
        @Contract("_ -> new")
        public static @NotNull JSONValue of(Object value) {
            return new JSONValue(value);
        }

        /**
         * Converting some json value into its representation in java; if value can't be converted,
         * returns the given default value
         * @param def default value that will be returned if the conversion failed; specifies the "T"
         * @return converted json value
         * @param <T> specifies which type the java value will be
         */
        public <T> T toJavaValue(T def) {
            return JSONValueType.toJavaValue(value, def);
        }

    }

}
