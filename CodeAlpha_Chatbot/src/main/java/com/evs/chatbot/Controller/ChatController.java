package com.evs.chatbot.Controller;

import com.evs.chatbot.model.ChatMessage;
import com.evs.chatbot.service.Aiservice;
import com.evs.chatbot.service.ChatHistoryService;
import com.evs.chatbot.service.NlpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final Aiservice aiservice;
    private final NlpService nlpService;
    private final ChatHistoryService chatHistoryService;

    public ChatController(Aiservice aiservice, NlpService nlpService, ChatHistoryService chatHistoryService) {
        this.aiservice = aiservice;
        this.nlpService = nlpService;
        this.chatHistoryService = chatHistoryService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message, HttpSession session) {
        chatHistoryService.addMessage(session, "user", message);

        String response = nlpService.getReply(message);
        if (response == null || response.isEmpty() || response.equalsIgnoreCase("Sorry \uD83D\uDE14 I can't understand what you are saying")) {
            response = aiservice.getResponse(chatHistoryService.getHistory(session));
        }

        chatHistoryService.addMessage(session, "bot", response);
        return response;
    }

    @GetMapping("/history")
    public List<ChatMessage> history(HttpSession session) {
        return chatHistoryService.getHistory(session);
    }

    @DeleteMapping("/history")
    public void clearHistory(HttpSession session) {
        chatHistoryService.clearHistory(session);
    }
}
