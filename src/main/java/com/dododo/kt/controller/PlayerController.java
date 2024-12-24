package com.dododo.kt.controller;

import com.dododo.kt.controller.dto.AnswersRequestDTO;
import com.dododo.kt.controller.dto.CodeRequestDTO;
import com.dododo.kt.controller.dto.GameParamsRequestDTO;
import com.dododo.kt.controller.dto.RefreshRequestDTO;
import com.dododo.kt.holder.SessionsHolder;
import com.dododo.kt.holder.TokensHolder;
import com.dododo.kt.model.BasicInfo;
import com.dododo.kt.model.GameMode;
import com.dododo.kt.service.TrackService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PlayerController {

    private final TrackService service;

    private final SessionsHolder sessionsHolder;

    private final TokensHolder tokensHolder;

    @PostMapping("/api/player/join")
    public ResponseEntity<Object> join(@RequestBody CodeRequestDTO dto) {
        String token = tokensHolder.get(dto.getCode());
        HttpSession session = sessionsHolder.get(token);

        if (session == null) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("connected", true);

        return ResponseEntity.ok(new JSONObject().put("token", token).toString());
    }

    @PostMapping("/api/player/select")
    public ResponseEntity<Void> select(@RequestBody @Validated(BasicInfo.class) GameParamsRequestDTO dto) {
        HttpSession session = sessionsHolder.get(dto.getToken());

        if (session == null) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("gameMode", dto.getGameMode());
        session.setAttribute("trackId", dto.getTrackId());

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/player/track/active/options", params = "token")
    public ResponseEntity<List<String>> findAllActiveOptions(@RequestParam("token") String token) {
        HttpSession session = sessionsHolder.get(token);

        if (session == null) {
            return ResponseEntity.badRequest().build();
        }

        GameMode gameMode = (GameMode) session.getAttribute("gameMode");
        Integer trackId = (Integer) session.getAttribute("trackId");

        return ResponseEntity.ok(service.findById(trackId)
                .getModes()
                .get(gameMode)
                .getOptions());
    }

    @PostMapping("/api/player/answers")
    public ResponseEntity<Void> answers(@RequestBody AnswersRequestDTO dto) {
        HttpSession session = sessionsHolder.get(dto.getToken());

        if (session == null) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("answers", dto.getValues());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/player/refresh")
    public ResponseEntity<Void> refresh(@RequestBody RefreshRequestDTO dto) {
        HttpSession session = sessionsHolder.get(dto.getToken());

        if (session == null) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("refreshType", dto.getType());
        session.setAttribute("refreshed", true);

        return ResponseEntity.ok().build();
    }
}