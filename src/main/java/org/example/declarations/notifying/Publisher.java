package org.example.declarations.notifying;

import org.example.exceptions.InvalidClientIdException;

public interface Publisher
{
    void subscribe(int subscriberId) throws InvalidClientIdException;
    void unsubscribe(int subscriberId) throws InvalidClientIdException;
    void notify(String message);

}
