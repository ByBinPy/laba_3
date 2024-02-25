package org.example;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Client
{
    private int id;
    private @NonNull String name;
    private @NonNull String surname;
    private String address;
    private int passwordNumber;
    @Override
    public int hashCode()
    {
        return name.hashCode()+surname.hashCode()+address.hashCode() + passwordNumber;
    }

}
