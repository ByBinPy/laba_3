package org.example.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Client
{
    private final int defaultId = -1;
    private int id = defaultId;
    private @NonNull String name;
    private @NonNull String surname;
    private String address;
    private Integer passportNumber;
    public boolean isSuspicious()
    {
        return address != null && passportNumber != null;
    }
    @Override
    public int hashCode()
    {
        return name.hashCode()+surname.hashCode()+address.hashCode() + passportNumber;
    }

}
