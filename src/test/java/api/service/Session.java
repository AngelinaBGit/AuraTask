package api.service;

import java.util.Map;

public class Session {
    private final String sessionCookie;

    public Session(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public Map<String, String> getAuthHeaders() {
        return Map.of("Cookie", sessionCookie);
    }
}
