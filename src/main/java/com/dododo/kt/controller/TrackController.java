package com.dododo.kt.controller;

import com.dododo.kt.controller.dto.TrackResponseDTO;
import com.dododo.kt.model.Track;
import com.dododo.kt.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TrackController {

    private final TrackService service;

    @GetMapping("/api/track")
    public List<TrackResponseDTO> findAllTrackNames() {
        return service.findAll().stream()
                .map(TrackController::create)
                .toList();
    }

    private static TrackResponseDTO create(Track track) {
        TrackResponseDTO dto = new TrackResponseDTO();

        dto.setId(track.getId());
        dto.setName(track.getName());
        dto.setArtist(track.getArtist());

        return dto;
    }
}