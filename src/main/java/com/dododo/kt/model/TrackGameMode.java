package com.dododo.kt.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class TrackGameMode {

    private String src;

    private List<String> text;

    private Map<String, List<String>> commands;

    private List<String> options;

    private List<List<String>> blocks;
}
