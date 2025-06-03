package br.com.sime.api.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private final String title;
    public NotFoundException(String message) {
        super(message);
        this.title = "Recurso não encontrado";
    }

    public NotFoundException(String title, String message) {
        super(message);
        this.title = title;
    }
}
