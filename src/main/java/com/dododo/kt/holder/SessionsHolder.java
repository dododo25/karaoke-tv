package com.dododo.kt.holder;

import jakarta.servlet.http.HttpSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SessionsHolder {

    private final Map<String, HttpSession> sessionMap;

    private final Collection<HttpSession> activeSessions;

    public SessionsHolder() {
        this.sessionMap = new HashMap<>();
        this.activeSessions = new HashSet<>();
    }

    public void add(String token, HttpSession session) {
        if (activeSessions.contains(session)) {
            sessionMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(session))
                    .map(Map.Entry::getKey)
                    .toList()
                    .forEach(sessionMap::remove);
        }

        activeSessions.add(session);
        sessionMap.put(token, session);
    }

    public HttpSession get(String token) {
        return sessionMap.getOrDefault(token, null);
    }
}
