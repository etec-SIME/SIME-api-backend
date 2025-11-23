package br.com.sime.api.enums;

import lombok.Getter;

@Getter
public enum TipoMensagemEnum {

    AUTOMATICA("Automática"),

    PERSONALIZADA("Personalizada");

    private final String descricao;

    TipoMensagemEnum(String descricao) { this.descricao = descricao; }
}
