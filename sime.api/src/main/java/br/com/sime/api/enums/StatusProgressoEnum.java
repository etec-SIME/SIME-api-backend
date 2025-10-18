package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum StatusProgressoEnum {
    EM_ANALISE("Em análise"),

    APROVADO("Aprovado"),

    ANALISE_DA_APM("Análise da APM"),

    EM_ANDAMENTO("Em andamento"),

    CONCLUIDO("Concluído");

    private final String descricao;

    StatusProgressoEnum(String descricao) {
        this.descricao = descricao;
    }
}
