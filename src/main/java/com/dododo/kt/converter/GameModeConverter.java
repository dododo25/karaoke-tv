package com.dododo.kt.converter;

import com.dododo.kt.model.GameMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GameModeConverter implements AttributeConverter<GameMode, String> {

    @Override
    public String convertToDatabaseColumn(GameMode gameMode) {
        return gameMode.name().toLowerCase();
    }

    @Override
    public GameMode convertToEntityAttribute(String gameMode) {
        return GameMode.valueOf(gameMode.toUpperCase());
    }
}