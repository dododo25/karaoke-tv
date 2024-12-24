package com.dododo.kt.controller;

import com.dododo.kt.model.GameMode;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class SelectGameController {

    @GetMapping("/select")
    public String page(Model model, HttpSession session) {
        boolean isConnected = Optional.ofNullable(session.getAttribute("connected"))
                .map(Boolean.class::cast)
                .orElse(false);

        if (!isConnected) {
            return "redirect:/";
        }

        session.setAttribute("gameMode", null);
        session.setAttribute("trackId", null);
        session.setAttribute("answers", null);
        session.setAttribute("refreshed", false);
        session.setAttribute("refreshType", null);

        return "select";
    }

    @GetMapping("/select/ping")
    public ResponseEntity<Integer> ping(HttpSession session) throws InterruptedException {
        GameMode mode = (GameMode) session.getAttribute("gameMode");
        Integer trackId = (Integer) session.getAttribute("trackId");

        for (int i = 0; i < 10 && (mode == null || trackId == null); i++) {
            Thread.sleep(1000);

            mode = (GameMode) session.getAttribute("gameMode");
            trackId = (Integer) session.getAttribute("trackId");
        }

        return ResponseEntity.ok(mode == null || trackId == null ? 1 : 0);
    }
}
