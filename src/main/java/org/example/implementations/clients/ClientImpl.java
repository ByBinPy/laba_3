package org.example.implementations.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.example.declarations.Client;
import org.example.declarations.notifying.Subscriber;
import org.example.implementations.records.Message;

/**
 * Class where data about clients are stored
 */
@Getter
@Builder
public class ClientImpl implements Client, Subscriber {
    private @NonNull Integer id;
    private @NonNull String name;
    private @NonNull String surname;
    @Builder.Default
    private @NonNull Message message = new Message(" ");
    private String address;
    private Integer passportNumber;

    /* method for checking client status */
    public boolean isSuspicious() {
        return address != null && passportNumber != null;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + surname.hashCode() + address.hashCode() + passportNumber;
    }

    @Override
    public void update(String message) {
        this.message = new Message(message);
    }

    @Override
    public int getId() {
        return id;
    }
}
