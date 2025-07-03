package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum StatusChamadoEnum {

    CONCLUIDO("Concluído"),

    PENDENTE("Pendente");

    private final String descricao;

    StatusChamadoEnum(String descricao) {
        this.descricao = descricao;
    }
}
