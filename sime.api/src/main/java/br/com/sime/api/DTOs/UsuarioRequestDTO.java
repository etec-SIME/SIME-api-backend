package br.com.sime.api.DTOs;

import br.com.sime.api.entities.outros.Departamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioRequestDTO {
    @NotBlank(message = "Rm do usuário é obrigatório")
    @NotNull
    private String rmUsuario;

    @NotBlank(message = "Email do usuário é obrigatório")
    @Email(message = "Email inválido")
    private String emailUsuario;

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Senha do usuário é obrigatório")
    private String senhaUsuario;

    @NotBlank(message = "Telefone do usuário é obrigatório")
    private String telefoneUsuario;

    //@NotBlank(message = "CPF do usuário é obrigatório")
    //private String cpfUsuario;

    @NotBlank(message = "Id do tipo perfil do usuário é obrigatório")
    private Long idTipoPerfil;

    private List<Long> departamentoIds;//Se Tipo Perfil for Gestor de Departamento
}
