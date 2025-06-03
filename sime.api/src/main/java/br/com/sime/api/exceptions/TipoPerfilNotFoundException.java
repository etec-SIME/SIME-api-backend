package br.com.sime.api.exceptions;

public class TipoPerfilNotFoundException extends RuntimeException {
    public TipoPerfilNotFoundException(String message) {
        super(message);
    }
}
