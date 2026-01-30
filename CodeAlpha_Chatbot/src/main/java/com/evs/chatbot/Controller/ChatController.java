package com.evs.chatbot.Controller;

import com.evs.chatbot.service.NlpService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final NlpService nlpService;

    public ChatController(NlpService nlpService) {
        this.nlpService = nlpService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return nlpService.getReply(message);
    }
}
