package org.example.notifying;

public interface Publisher<T>
{
    void subscribe(int subscriberId);
    void unsubscribe(int subscriberId);
    void notify(T message);
}
