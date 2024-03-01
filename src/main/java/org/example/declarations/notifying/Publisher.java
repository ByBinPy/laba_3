package org.example.declarations.notifying;

public interface Publisher<T>
{
    void subscribe(int subscriberId);
    void unsubscribe(int subscriberId);
    void notify(T message);
}
