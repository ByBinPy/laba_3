package org.example.imp.clients;

import java.time.LocalTime;

public record Transaction(int id, LocalTime time,double differenceAmount){}
