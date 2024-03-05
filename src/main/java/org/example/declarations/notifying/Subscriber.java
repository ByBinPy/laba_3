package org.example.declarations.notifying;

/**
 * Declaration methods for pattern observer
 * who accept notification
 */
public interface Subscriber
{
    int getId();
    void update(String message);
}
