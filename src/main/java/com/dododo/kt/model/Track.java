package com.dododo.kt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

public class Track {

    @Getter
    private int id;

    @Getter
    private String name;

    @Getter
    private String artist;

    @JsonProperty(value = "modes")
    private Map<String, TrackGameMode> rawModes;

    @JsonIgnore
    private Map<GameMode, TrackGameMode> modes;

    public Map<GameMode, TrackGameMode> getModes() {
        if (modes == null) {
            modes = rawModes.entrySet()
                    .stream()
                    .collect(Collectors.toUnmodifiableMap(
                            entry -> GameMode.valueOf(entry.getKey().toUpperCase()),
                            Map.Entry::getValue));
        }

        return modes;
    }
}
