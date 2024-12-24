package com.dododo.kt.model;

import lombok.Getter;

import java.util.List;

@Getter
public class TrackGameMode {

    private String src;

    private List<String> text;

    private List<String> commands;

    private List<String> options;

    private List<List<String>> blocks;
}
