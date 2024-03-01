package org.example.impl.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.example.declarations.Client;
import org.example.impl.Message;

@Getter
@Builder
public class ClientImpl implements Client
{
    private @NonNull Integer id;
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull Message message = new Message(" ");
    private String address = null;

    private Integer passportNumber = null;
    public boolean isSuspicious()
    {
        return address != null && passportNumber != null;
    }
    @Override
    public int hashCode()
    {
        return name.hashCode()+surname.hashCode()+address.hashCode() + passportNumber;
    }

    @Override
    public void update(String message)
    {
        this.message = new Message(message);
    }
}