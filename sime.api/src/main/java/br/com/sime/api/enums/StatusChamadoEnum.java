package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum StatusChamadoEnum {

    AGUARDANDO_APROVACAO("Aguardando Aprovação"),

    PENDENTE("Pendente"),

    CONCLUIDO("Concluído");

    private final String descricao;

    StatusChamadoEnum(String descricao) {
        this.descricao = descricao;
    }
}
