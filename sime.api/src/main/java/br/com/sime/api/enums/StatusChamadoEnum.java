package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum StatusChamadoEnum {

    AGUARDANDO_APROVACAO("Aguardando aprovação"),

    RECUSADO("Recusado"),

    PENDENTE("Pendente"),

    CONCLUIDO("Concluído");

    private final String descricao;

    StatusChamadoEnum(String descricao) {
        this.descricao = descricao;
    }
}
