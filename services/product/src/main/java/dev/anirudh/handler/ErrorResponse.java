package dev.anirudh.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
