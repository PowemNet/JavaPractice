package com.powem.inv.algos;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//        Problem: Configuration Parser
//        Problem Statement:
//        Develop a parser for a configuration file format. The file contains key-value pairs, where each key is
//        separated from its value by an equals sign (=). Comments in the file start with a hash symbol (#) and should
//        be ignored.
//
//        Create a parser that reads a configuration string, extracts key-value pairs, and ignores any lines that are
//        comments or improperly formatted.
//
//        Keys and values are strictly alphanumeric and underscored (no spaces).
//        Each valid key-value pair appears on a new line.
//        Lines that do not conform to the key-value format should be ignored.
//
//        Implement the ConfigParser class that takes a configuration string and provides methods to get the value for
//        a specified key and list all keys.

//        Function signature:
//        public class ConfigParser {
//          private Map<String, String> configMap;
//        }
//

public class ConfigParser {
    private Map<String, String> configMap;

    public ConfigParser(String config) {
        this.configMap = parseConfig(config);
    }

    public String getValue(String key) {
        return configMap.get(key);
    }

    public Set<String> getAllKeys() {
        return configMap.keySet();
    }

    private Map<String, String> parseConfig(String config) {
        Map<String, String> map = new HashMap<>();
        String[] lines = config.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.startsWith("#") && line.contains("=")) {
                String[] parts = line.split("=");
                if (parts.length == 2 && isValidKey(parts[0]) && isValidKey(parts[1])) {
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return map;
    }

    private boolean isValidKey(String key) {
        return key.matches("[\\w]+");
    }
}

//import com.powem.inv.algos.ConfigParser;
//
//import java.util.Arrays;
//import java.util.Set;
//
//public class Main {
//    public static void main(String[] args) {
//        testValidConfigurations();
//        testIgnoreComments();
//        testIgnoreMalformedLines();
//        testEmptyAndWhitespaceLines();
//        testAllKeysRetrieval();
//    }
//
//    private static void testValidConfigurations() {
//        String config = "key1=value1\nkey2=value2\nkey3=value3";
//        ConfigParser parser = new ConfigParser(config);
//
//        //TEST
//        assert "value1".equals(parser.getValue("key1"));
//        //TEST_END
//
//        //TEST
//        assert "value2".equals(parser.getValue("key2"));
//        //TEST_END
//
//        //TEST
//        assert "value3".equals(parser.getValue("key3"));
//        //TEST_END
//    }
//
//    private static void testIgnoreComments() {
//        String config = "# This is a comment\nkey1=value1\n# Another comment\nkey2=value2";
//        ConfigParser parser = new ConfigParser(config);
//
//        //TEST
//        assert "value1".equals(parser.getValue("key1"));
//        //TEST_END
//
//        //TEST
//        assert "value2".equals(parser.getValue("key2"));
//        //TEST_END
//    }
//
//    private static void testIgnoreMalformedLines() {
//        String config = "key1=value1\nNotAValidLine\nkey2=value2";
//        ConfigParser parser = new ConfigParser(config);
//
//        //TEST
//        assert "value1".equals(parser.getValue("key1"));
//        //TEST_END
//
//        //TEST
//        assert "value2".equals(parser.getValue("key2"));
//        //TEST_END
//    }
//
//    private static void testEmptyAndWhitespaceLines() {
//        String config = "key1=value1\n\n \nkey2=value2";
//        ConfigParser parser = new ConfigParser(config);
//
//        //TEST
//        assert "value1".equals(parser.getValue("key1"));
//        //TEST_END
//
//        //TEST
//        assert "value2".equals(parser.getValue("key2"));
//        //TEST_END
//    }
//
//    private static void testAllKeysRetrieval() {
//        String config = "key1=value1\nkey2=value2\nkey3=value3";
//        ConfigParser parser = new ConfigParser(config);
//        Set<String> keys = parser.getAllKeys();
//
//        //TEST
//        assert keys.containsAll(Arrays.asList("key1", "key2", "key3"));
//        //TEST_END
//    }
//}
