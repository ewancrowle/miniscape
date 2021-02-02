package net.miniscape.messaging.event;

public interface EventCallback<T> {
    void execute(T data);
}