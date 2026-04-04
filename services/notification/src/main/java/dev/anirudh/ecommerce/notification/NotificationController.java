package dev.anirudh.ecommerce.notification;

import dev.anirudh.ecommerce.kafka.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationSearchService searchService;

    @GetMapping
    public ResponseEntity<List<NotificationDocument>> getAllNotifications() {
        return ResponseEntity.ok(searchService.findAll());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDocument>> getByType(
            @PathVariable NotificationType type
    ) {
        return ResponseEntity.ok(searchService.findByType(type));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NotificationDocument>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(searchService.searchByKeyword(keyword));
    }

    @GetMapping("/payment-method/{method}")
    public ResponseEntity<List<NotificationDocument>> getByPaymentMethod(
            @PathVariable PaymentMethod method
    ) {
        return ResponseEntity.ok(searchService.findByPaymentMethod(method));
    }
}
