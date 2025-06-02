package br.com.sime.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PrioridadeChamadoEnum {
    @JsonProperty("Alta Prioridade")
    ALTA_PRIORIDADE,
    @JsonProperty("Média Prioridade")
    MEDIA_PRIORIDADE,
    @JsonProperty("Baixa Prioridade")
    BAIXA_PRIORIDADE
}
