package org.example.imp.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.example.declarations.Client;
import org.example.declarations.notifying.Subscriber;
import org.example.imp.Message;

import javax.print.attribute.SupportedValuesAttribute;

/**
 * Class where data about clients are stored
 */
@Getter
@Builder
public class ClientImp implements Client, Subscriber
{
    private @NonNull Integer id;
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull Message message = new Message(" ");
    private String address = null;
    private Integer passportNumber = null;

    /* method for checking client status */
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
