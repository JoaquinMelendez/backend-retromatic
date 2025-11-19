package com.retromatic.backend_retromatic.uptime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Uptimer_robot {
    @GetMapping("/health")
        public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
}
}
