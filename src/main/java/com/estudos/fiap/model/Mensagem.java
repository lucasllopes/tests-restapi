package com.estudos.fiap.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotEmpty(message = "Usuario nao pode estar vazio")
    private String usuario;

    @NotEmpty(message = "Conteudo nao pode estar vazio")
    private String conteudo;

    @Builder.Default
    private int gostei = 0;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSSSS")
    private LocalDateTime dataCriacao;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSSSS")
    private LocalDateTime dataAlteracao;

}
