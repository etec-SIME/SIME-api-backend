package br.com.sime.api.exceptions;

import lombok.Data;

@Data
public class ConflictException extends RuntimeException{

    public ConflictException(String message) {super(message);}

}
