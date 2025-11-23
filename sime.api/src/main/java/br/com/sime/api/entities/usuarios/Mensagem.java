package br.com.sime.api.entities.usuarios;

import br.com.sime.api.entities.chamados.Chamado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long idMensagem;

    @Column(name = "tipo_mensagem")
    private String tipoMensagem;

    @Column(name = "texto")
    private String texto;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_chamado")
    private Chamado chamado;

    @OneToMany(mappedBy = "mensagem")
    @JsonManagedReference
    private List<Notificacao> notificacaoList;
}
