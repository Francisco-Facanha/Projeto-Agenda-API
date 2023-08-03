package Francisco.agendaapi.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;



@Entity
    @Getter
    @Setter
    @NoArgsConstructor
    public class Contato {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer Id;
        @Column (length = 155, nullable = false)
        private String nome;
        @Column (length = 155, nullable = false)
        private String email;
        @Column (length = 155, nullable = false)
        private Boolean favorito;
        @Column
        @Lob
        private byte[] foto;

        }



