package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum StatusGeralEnum {

    AGUARDANDO_APROVACAO("Aguardando Aprovação"),

    PENDENTE("Pendente"),

    CONCLUIDO("Concluído");

    private final String descricao;

    StatusGeralEnum(String descricao) {
        this.descricao = descricao;
    }
}
