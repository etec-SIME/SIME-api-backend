package br.com.sime.api.exceptions;

import lombok.Data;

@Data
public class ConflictException extends RuntimeException{
    private final String title;
    public ConflictException(String title, String message){
        super(message);
        this.title = title;
    }
}
