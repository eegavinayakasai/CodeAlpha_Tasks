package com.evs.chatbot.service;

import com.evs.chatbot.model.ChatMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatHistoryService {

    private static final String HISTORY_KEY = "chatHistory";
    private static final int MAX_MESSAGES = 20;

    @SuppressWarnings("unchecked")
    public List<ChatMessage> getHistory(HttpSession session) {
        Object history = session.getAttribute(HISTORY_KEY);
        if (history instanceof List<?>) {
            return (List<ChatMessage>) history;
        }

        List<ChatMessage> newHistory = new ArrayList<>();
        session.setAttribute(HISTORY_KEY, newHistory);
        return newHistory;
    }

    public void addMessage(HttpSession session, String role, String text) {
        List<ChatMessage> history = getHistory(session);
        history.add(new ChatMessage(role, text));

        if (history.size() > MAX_MESSAGES) {
            history.remove(0);
        }
    }

    public void clearHistory(HttpSession session) {
        session.removeAttribute(HISTORY_KEY);
    }
}
