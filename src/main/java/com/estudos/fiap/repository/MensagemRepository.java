package com.estudos.fiap.repository;

import com.estudos.fiap.model.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

    @Query("SELECT m FROM Mensagem m ORDER BY m.dataCriacao DESC")
    Page<Mensagem> obterMensagens(Pageable pageable);
}
