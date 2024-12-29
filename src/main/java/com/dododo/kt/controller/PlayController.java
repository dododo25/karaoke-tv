package com.dododo.kt.controller;

import com.dododo.kt.model.GameMode;
import com.dododo.kt.model.Track;
import com.dododo.kt.service.TrackService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class PlayController {

    private final TrackService service;

    @GetMapping("/play")
    public String page(Model model, HttpSession session) {
        boolean connected = Optional.ofNullable(session.getAttribute("connected"))
                .map(Boolean.class::cast)
                .orElse(false);
        GameMode gameMode = Optional.ofNullable(session.getAttribute("gameMode"))
                .map(GameMode.class::cast)
                .orElse(null);
        Integer trackId = Optional.ofNullable(session.getAttribute("trackId"))
                .map(Integer.class::cast)
                .orElse(null);

        if (!connected || gameMode == null || trackId == null) {
            return "redirect:/select";
        }

        Track track = service.findById(trackId);

        session.setAttribute("answers", null);
        session.setAttribute("refreshed", false);
        session.setAttribute("refreshType", null);

        TrackGameMode mode = track.getModes().get(gameMode);

        model.addAttribute("label", String.format("\"%s - %s\" is now playing", track.getArtist(), track.getName()));
        model.addAttribute("src", mode.getSrc());
        model.addAttribute("text", mode.getText());
        model.addAttribute("rawCommands", mode.getCommands().get(session.getAttribute("deviceType")));

        return "play";
    }

    @GetMapping("/play/ping")
    public ResponseEntity<String> ping(HttpSession session) throws InterruptedException {
        boolean refreshed = Optional.ofNullable(session.getAttribute("refreshed"))
                .map(Boolean.class::cast)
                .orElse(false);
        String refreshType = Optional.ofNullable(session.getAttribute("refreshType"))
                .map(String.class::cast)
                .orElse(null);

        for (int i = 0; i < 10 && !(refreshed || refreshType != null); i++) {
            Thread.sleep(1000);

            refreshed = Optional.ofNullable(session.getAttribute("refreshed"))
                    .map(Boolean.class::cast)
                    .orElse(false);
            refreshType = Optional.ofNullable(session.getAttribute("refreshType"))
                    .map(String.class::cast)
                    .orElse(null);
        }

        if (!refreshed) {
            return ResponseEntity.ok(null);
        }

        if (refreshType != null && refreshType.equalsIgnoreCase("stay")) {
            return ResponseEntity.ok("play");
        }

        return ResponseEntity.ok(session.getAttribute("answers") == null ? "select" : "answers");
    }
}
