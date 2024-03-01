package org.example.declarations;

import org.example.declarations.notifying.Subscriber;

public interface Client extends Subscriber {
    boolean isSuspicious();
    int getId();
}
