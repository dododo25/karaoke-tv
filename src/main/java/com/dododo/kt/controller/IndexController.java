package com.dododo.kt.controller;

import com.dododo.kt.holder.SessionsHolder;
import com.dododo.kt.holder.TokensHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final Random RANDOM = new Random();

    private final SessionsHolder sessionsHolder;

    private final TokensHolder tokensHolder;

    @GetMapping("/")
    public String page(Model model, HttpServletRequest request, HttpSession session) {
        String code  = generateCode();
        String token = tokensHolder.generateAndSave(code);

        session.setMaxInactiveInterval(Integer.MAX_VALUE);
        session.setAttribute("code", code);
        session.setAttribute("connected", false);
        session.setAttribute("gameMode", null);
        session.setAttribute("trackId", null);
        session.setAttribute("answers", null);
        session.setAttribute("refreshed", false);
        session.setAttribute("refreshType", null);

        if (Pattern.matches("X\\d{1,2}|Android", String.valueOf(request.getAttribute("User-Agent")))) {
            session.setAttribute("deviceType", "android");
        } else {
            session.setAttribute("deviceType", "pc");
        }

        LOGGER.info(String.valueOf(session.getAttribute("deviceType")));

        sessionsHolder.add(token, session);

        model.addAttribute("code", IntStream.range(0, code.length() / 2)
                .mapToObj(i -> code.substring(i * 2, i * 2 + 2))
                .collect(Collectors.joining(" ")));

        return "index";
    }

    @GetMapping("/ping")
    public ResponseEntity<Integer> ping(HttpSession session) throws InterruptedException {
        boolean connected = Optional.ofNullable(session.getAttribute("connected"))
                .map(Boolean.class::cast)
                .orElse(false);

        for (int i = 0; i < 10 && !connected; i++) {
            Thread.sleep(1000);

            connected = Optional.ofNullable(session.getAttribute("connected"))
                    .map(Boolean.class::cast)
                    .orElse(false);
        }

        return ResponseEntity.ok(connected ? 0 : 1);
    }

    public String generateCode() {
        List<String> parts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            parts.add(String.valueOf(RANDOM.nextInt(100)));
        }

        return parts.stream()
                .map(p -> "0".repeat(Math.max(0, 2 - p.length())) + p)
                .collect(Collectors.joining());
    }
}