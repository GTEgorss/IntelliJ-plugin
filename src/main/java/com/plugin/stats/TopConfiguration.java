package com.plugin.stats;

import java.util.LinkedHashMap;
import java.util.Set;

public class TopConfiguration {
    private final LinkedHashMap<String, Boolean> stats;

    public TopConfiguration() {
        stats = new LinkedHashMap<>();
        stats.put("pid", true);
        stats.put("command", true);
        stats.put("cpu", true);
        stats.put("cpu_me", true);
        stats.put("cpu_others", true);
        stats.put("csw", false);
        stats.put("time", true);
        stats.put("threads", true);
        stats.put("ports", true);
        stats.put("mregion", false);
        stats.put("mem", true);
        stats.put("rprvt", false);
        stats.put("purg", true);
        stats.put("vsize", false);
        stats.put("vprvt", false);
        stats.put("kprvt", false);
        stats.put("kshrd", false);
        stats.put("pgrp", true);
        stats.put("ppid", true);
        stats.put("state", true);
        stats.put("uid", true);
        stats.put("wq", false);
        stats.put("faults", true);
        stats.put("cow", true);
        stats.put("user", true);
        stats.put("msgsent", true);
        stats.put("msgrecv", true);
        stats.put("sysbsd", true);
        stats.put("sysmach", true);
        stats.put("pageins", false);
        stats.put("boosts", false);
        stats.put("instrs", false);
        stats.put("cycles", false);
    }

    public int getSize() {
        return stats.size();
    }

    public Set<String> getKeys() {
        return stats.keySet();
    }

    public boolean getValue(String key) {
        return stats.get(key);
    }

    public void changeValue(String key, boolean value) {
        stats.replace(key, value);
    }

    public String getConfiguration() {
        String configuration = "";

        for (String key : stats.keySet()) {
            if (stats.get(key)) {
                if (configuration.isEmpty()) {
                    configuration = configuration.concat(" -stats ").concat(key);
                } else {
                    configuration = configuration.concat(",").concat(key);
                }
            }
        }

        return configuration.concat(" ");
    }
}
