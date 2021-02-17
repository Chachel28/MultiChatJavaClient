package net.juanxxiii.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Message implements Serializable {
    private String username;
    private String message;
    private Timestamp timestamp;
}
