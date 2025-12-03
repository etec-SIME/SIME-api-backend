package br.com.sime.api.entities.chamados;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Imagem_Chamado")
public class ImagemChamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem_chamado", nullable = false)
    Long idImagemChamado;

    @Column(name = "nome_arquivo_imagem_chamado")
    String nomeArquivo;

    @Column(name = "caminho_imagem_chamado")
    String caminho;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_chamado")
    Chamado chamado;
}
