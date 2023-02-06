package dev.fmesnata.todolist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import dev.fmesnata.todolist.service.TodoListService;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Component
public class TodoListWebSocket extends TextWebSocketHandler {

    private final TodoListService todoListService;
    private final ObjectMapper mapper;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    record TodoRequest(String text) {
    }

    public TodoListWebSocket(TodoListService todoListService, ObjectMapper mapper) {
        this.todoListService = todoListService;
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage jsonTextMessage) throws Exception {
        TodoRequest todoRequest = convertWebSocketMessageToTodoRequest(jsonTextMessage);
        Todo newTodo = todoListService.addTodo(todoRequest.text());
        String htmlFragment = createHtmlFragmentForTodo(newTodo);
        sendNewTodoToEachWebSocketSessions(htmlFragment);
    }

    private TodoRequest convertWebSocketMessageToTodoRequest(TextMessage jsonTextMessage) throws JsonProcessingException {
        String payload = jsonTextMessage.getPayload();
        return mapper.readValue(payload, TodoRequest.class);
    }

    private static String createHtmlFragmentForTodo(Todo newTodo) throws IOException {
        File file = ResourceUtils.getFile("classpath:templates/fragments/todo.mustache");
        String fileContent = Files.readString(file.toPath());
        Template template = Mustache.compiler().compile(fileContent);
        Map<String, Todo> data = Map.of("newTodo", newTodo);
        return "<div hx-swap-oob=\"beforeend:#todos\">" + template.execute(data) + "</div>";
    }

    private void sendNewTodoToEachWebSocketSessions(String htmlFragment) throws IOException {
        for (WebSocketSession session : sessions.values()) {
            session.sendMessage(new TextMessage(htmlFragment));
        }
    }
}
