package com.evs.chatbot.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class NlpService
{

    private static final Map<String, String> messages = Map.ofEntries(
            Map.entry("hi", "Hi 👋 How can I help you today?"),
            Map.entry("hello", "Hello 😊 What can I do for you?"),
            Map.entry("how are you", "I’m doing great 😄 Thanks for asking!"),
            Map.entry("who are you", "I’m a chatbot 🤖 built to assist you."),
            Map.entry("what can you do", "I can answer questions and guide you 😊"),
            Map.entry("help", "Sure 👍 Tell me how I can help you."),
            Map.entry("thank you", "You’re welcome 😊 Happy to help!"),
            Map.entry("confused", "It’s okay 😄 Tell me what’s confusing you."),
            Map.entry("bye", "Bye 👋 Have a great day!"),
            Map.entry("hey", "Hey there \uD83D\uDE04 How can I assist?"),
            Map.entry("good morning","Good morning \uFE0F hope you have great day"),
            Map.entry("good evening","Good evening \uD83C\uDF06 How can I help you?"),
            Map.entry("good afternoon","Good afternoon human"),
            Map.entry("are you fine","Yes \uD83D\uDC4D I’m fine and ready to help!"),
            Map.entry("what are you doing","I’m here to assist you \uD83D\uDE0A"),
            Map.entry("whats up","Not much \uD83D\uDE0A How can I help you today?"),
            Map.entry("what are you","I’m an AI chatbot designed to help user"),
            Map.entry("are you human","No \uD83D\uDE04 I’m a chatbot, but I try to be friendly!"),
            Map.entry("why are you here","I’m here to help you with your queries \uD83D\uDC4D"),
            Map.entry("can you repeat","Sure \uD83D\uDC4D Please ask me again."),
            Map.entry("thanks","anytime \uD83D\uDE04"),
            Map.entry("nice","Glad you liked it \uD83D\uDE0A"),
            Map.entry("cool","\uD83D\uDE0E Happy to hear that!"),
            Map.entry("okay","\uD83D\uDC4D Alright! Let me know if you need anything."),
            Map.entry("ok","\uD83D\uDC4D Alright! Let me know if you need anything."),
            Map.entry("i don’t understand","No worries \uD83D\uDE0A I’ll try to explain better ask again"),
            Map.entry("not clear","Let me try to explain it clearly \uD83D\uDE0A"),
            Map.entry("what do you mean","I’ll explain it in a simpler way \uD83D\uDC4D"),
            Map.entry("i am bored","Let’s chat \uD83D\uDE04 How can I help you?"),
            Map.entry("feeling sad","Sorry to hear that \uD83D\uDE14 Hope things get better soon"),
            Map.entry("i am happy","That’s great \uD83D\uDE04 I’m happy for you!"),
            Map.entry("i am angry","Take a deep breath \uD83D\uDE0C I’m here to listen."),
            Map.entry("random text","Sorry \uD83D\uDE14 I didn’t understand that."),
            Map.entry("???","Oops \uD83D\uDE05 Could you rephrase that?"),
            Map.entry("nonsense","Hmm \uD83E\uDD14 I couldn’t understand that."),
            Map.entry("something else","Please explain a bit more \uD83D\uDE0A"),
            Map.entry("see you","See you soon \uD83D\uDE04"),
            Map.entry("goodbye","Goodbye \uD83D\uDE0A Take care!"),
            Map.entry("talk later","Sure \uD83D\uDC4D Talk to you later."),
            Map.entry("exit","Sure \uD83D\uDC4D Talk to you later."),
            Map.entry("close","Chat closed \uD83D\uDC4B Have a nice day!"),
            Map.entry("tell me a joke","\uD83D\uDE02 Sure!\n" +
                    "\n" +
                    "Why do programmers prefer dark mode?\n" +
                    "\n" +
                    "Because light attracts bugs \uD83D\uDC1B\uD83D\uDCBB\n" +
                    "\n" +
                    "Want another one? \uD83D\uDE04"),
            Map.entry("another one","Sure \uD83D\uDE04\n" +
                    "\n" +
                    "Why do Java developers wear glasses?\n" +
                    "Because they don’t C# \uD83D\uDC53"),
            Map.entry("another joke","Which is faster, hot or cold? Hot, because you can catch cold."),
            Map.entry("another","Why are mountains so funny? They’re hill areas 😄."),
            Map.entry("tell me joke","Where do young trees go to learn? Elementree school"),
            Map.entry("tell me about this internship", "This internship focuses on AI chatbot development."),
            Map.entry("internship duration", "The internship lasts 1 month."),
            Map.entry("internship tasks", "My main task is building an AI chatbot using Java."),
            Map.entry("how to use", "Just type your questions and I’ll try to help."),
            Map.entry("what should i ask", "You can ask greetings, jokes, or internship info."),
            Map.entry("what time is it", "I can't check real time yet, but your device shows it 😄"),
            Map.entry("what day is today", "Check your calendar 😄 I’m still learning that."),
            Map.entry("who made you", "I was created by a Java developer as an internship project."),
            Map.entry("are you smart", "I'm learning more every day 😄"),
            Map.entry("do you sleep", "Nope 😄 I’m always ready to chat."),
            Map.entry("contact", "You can contact our team via email."),
            Map.entry("support", "Support is available during working hours.")
            );

    private  String username = "";
    private static final List<String> emotions = List.of(
            "happy","sad","angry","tired","bored","fine","okay"
    );
    private static final List<String> POSITIVE = List.of(
            "happy","great","good","awesome","fantastic","nice","love","excited"
    );

    private static final List<String> NEGATIVE = List.of(
            "sad","angry","tired","upset","bad","depressed","bored","worried"
    );

    public  String getReply(String message)
    {
        String msg = message.toLowerCase()
                            .replaceAll("[^a-zA-Z0-9\\s]","")
                            .replaceAll("\\s+"," ")
                            .replaceAll("\\biam\\b","i am")
                            .replaceAll("\\bim\\b","i am")
                            .replaceAll("\\bu\\b","you")
                            .replaceAll("\\br\\b","are")
                            .trim();

        boolean b = msg.contains("i am")
                || msg.contains("feel")
                || msg.contains("feeling");

        for(String word : POSITIVE){
            if(msg.matches(".*\\b" + word + "\\b.*") && b){
                return "I'm glad you're feeling " + word + " 😊";
            }
        }

        for(String word : NEGATIVE){
            if(msg.matches(".*\\b" + word + "\\b.*") && b){
                return "I'm sorry you're feeling " + word + " 😔 I'm here to help.";
            }
        }



        if(msg.startsWith("i am ")) {
            String[] parts = msg.split(" ");

            if(parts.length >= 3) {
                String possibleName = parts[2];

                if(!emotions.contains(possibleName)) {
                    username = possibleName;
                    return "Hey " + username + " glad to meet you 😊";
                }
            }
        }


        if(msg.contains("who am i"))
        {
            return username.isEmpty() ? "I don't know your name yet" : "Your name is " + username + " right?";
        }

        if(msg.contains("no my name is"))
        {
            username = msg.replace("no my name is","")
                              .trim();
            return "Nice to meet you " + username + " what's up";
        }

        if(msg.contains("my name is"))
        {
            username = msg.replace("my name is","")
                              .trim();
            return "Nice to meet you " + username + " what's up";
        }


        if(msg.contains("what is my name") || msg.contains("you know my name") || msg.contains("do you know my name"))
        {
            return username.isEmpty() ? "I don't know your name yet" : "Your name is " + username + " right?";
        }

        if(msg.matches(".*\\b(hi|hey|hello)\\b.*"))
        {
            return "Hello \uD83D\uDC4B how can i help?";
        }

        for(Map.Entry<String, String> entry : messages.entrySet())
        {
            String key = Pattern.quote(entry.getKey());
            if(msg.matches(".*\\b" + key + "\\b.*"))
            {
                return entry.getValue();
            }
        }

        return "Sorry \uD83D\uDE14 I can't understand what you are saying";
    }
}
