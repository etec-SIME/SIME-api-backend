package br.com.sime.api.handlers;


import br.com.sime.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EscolaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEscolaNotFoundException(EscolaNotFoundException ex) {
        System.out.println("Handler personalizado chamado!");
        ErrorResponse errorResponse = new ErrorResponse(
                "Escola Não Encontrada",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoPerfilNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTipoPerfilNotFoundException(TipoPerfilNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Tipo de Perfil Não Encontrado",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Usuário Não Encontrado",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<ErrorResponse> handleSenhaIncorretaException(SenhaIncorretaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Senha Incorreta",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
