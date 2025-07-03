package api.service;

import java.util.Map;

public record Session(String sessionCookie) {

    public Map<String, String> getAuthHeaders() {
        return Map.of("Cookie", sessionCookie);
    }
}
