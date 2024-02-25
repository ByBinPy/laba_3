package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client.ClientBuilder().name(null).surname(null).address(" ").passwordNumber(123).build();
        }
        catch (Exception e)
        {
            String message = e.getMessage();
        }
    }
}