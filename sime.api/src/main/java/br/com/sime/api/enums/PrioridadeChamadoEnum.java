package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum PrioridadeChamadoEnum {

    ALTA_PRIORIDADE("Alta Prioridade"),

    MEDIA_PRIORIDADE("Média Prioridade"),

    BAIXA_PRIORIDADE("Baixa Prioridade");

    private final String descricao;

    PrioridadeChamadoEnum(String descricao) {
        this.descricao = descricao;
    }
}
