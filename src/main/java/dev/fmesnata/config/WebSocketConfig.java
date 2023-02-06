package dev.fmesnata.config;

import dev.fmesnata.todolist.TodoListWebSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    protected final TodoListWebSocket todoListWebSocket;

    public WebSocketConfig(TodoListWebSocket todoListWebSocket) {
        this.todoListWebSocket = todoListWebSocket;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(todoListWebSocket, "/todolist-ws");
    }
}
