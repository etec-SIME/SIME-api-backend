package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum PrioridadeChamadoEnum {

    ALTA_PRIORIDADE("Alta"),

    MEDIA_PRIORIDADE("Média"),

    BAIXA_PRIORIDADE("Baixa");

    private final String descricao;

    PrioridadeChamadoEnum(String descricao) {
        this.descricao = descricao;
    }
}
