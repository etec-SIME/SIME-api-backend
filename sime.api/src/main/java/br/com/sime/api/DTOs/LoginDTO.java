package br.com.sime.api.DTOs;
import lombok.Data;

@Data
public class LoginDTO {
    private String rmUsuario;
    private Long idTipoPerfil;
    private String senhaUsuario;
    private String codEscola;
}
