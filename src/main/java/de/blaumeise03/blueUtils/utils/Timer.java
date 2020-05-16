/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.utils;

import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for easy logging of timings
 */
public class Timer {
    private final Map<Long, Long> timings = new HashMap<>();
    private long lastStart = -1;
    private String name;
    private File f;

    /**
     * @param name the name for this timer. This name will be printed in the first line of the log
     */
    public Timer(String name, String path) {
        this.name = name;
        File f = new File(path);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().warning(
                    "Logfile for timings \"" + path + "\" already exists! Overwriting...");
        }
    }

    /**
     * Starts the timer
     *
     * @throws IllegalStateException if timer is already running
     */
    public void start() {
        if (lastStart != -1) throw new IllegalStateException("Timer is already running!");
        lastStart = System.nanoTime();
    }

    /**
     * Stops the timer and save the timings
     *
     * @throws IllegalStateException if timer is not running
     */
    public void stop() {
        if (lastStart == -1) throw new IllegalStateException("Timer is not running!");
        long time = System.nanoTime() - lastStart;
        lastStart = -1;
        timings.put(System.currentTimeMillis(), time);
        if (timings.size() > 100) {
            try {
                logTimings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes all measured timings
     */
    public void clear() {
        timings.clear();
        this.reset();
    }

    /**
     * Stops the timer if running
     */
    public void reset() {
        lastStart = -1;
    }

    /**
     * @throws IOException if file could not be saved
     */
    public void logTimings() throws IOException {
        try {
            try (
                    FileWriter fWriter = new FileWriter(f);
                    BufferedWriter writer = new BufferedWriter(fWriter)
            ) {
                Date cDate = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss:SSSS");
                writer.append(name).append("\n");
                writer.append("Current time: ").append(dateFormat.format(cDate)).append("\n");
                for (Map.Entry<Long, Long> entry : timings.entrySet()) {
                    Date date = new Date(entry.getKey());
                    double time = (double) entry.getValue() / 1000000000;
                    double ticks = time * 20;
                    writer.append("[").append(dateFormat.format(date)).append("]: ").append(String.valueOf(entry.getValue())).append(" Nanoseconds | ").append(String.valueOf(ticks)).append(" Ticks\n");
                }
                timings.clear();
            }
        } catch (IOException e) {
            throw new IOException("Couldn't save log", e);
        }
    }

    public boolean isRunning() {
        return lastStart != -1;
    }

    public Map<Long, Long> getTimings() {
        return timings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
