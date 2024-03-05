package org.example.imp.clients;

import java.time.LocalTime;
/**
 * Record for logging bank operations
 */
public record Transaction(int id, LocalTime time,double differenceAmount){}
