package com.dododo.kt.service;

import com.dododo.kt.model.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackService.class);

    private final Map<Integer, Track> tracks;

    public TrackService() throws IOException, URISyntaxException {
        this.tracks = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Track> typeReference = new TypeReference<>() {};

        try (Stream<Path> paths = Files.list(Paths.get(TrackService.class.getResource("/tracks").toURI()))) {
            paths.collect(Collectors.toUnmodifiableList()).forEach(path -> {
                try (Stream<String> lines = Files.lines(path)) {
                    Track track = mapper.readValue(lines.collect(Collectors.joining()), typeReference);
                    tracks.put(track.getId(), track);
                } catch (IOException  e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });
        }
    }

    public List<Track> findAll() {
        return List.copyOf(tracks.values());
    }

    public Track findById(int id) {
        return tracks.getOrDefault(id, null);
    }
}
