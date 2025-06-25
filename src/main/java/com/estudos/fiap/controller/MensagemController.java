package com.estudos.fiap.controller;

import com.estudos.fiap.exception.MensagemNotFoundException;
import com.estudos.fiap.model.Mensagem;
import com.estudos.fiap.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensagem> registrarMensagem(@Valid @RequestBody Mensagem mensagem){
        var mensagemRegistrado = mensagemService.registrarMensagem(mensagem);
        return new ResponseEntity<>(mensagemRegistrado, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}"
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obterMensagem(@PathVariable String id) {
        try {
            var uuid = UUID.fromString(id);
            var mensagemEncontrada = mensagemService.obterMensagem(uuid);
            return new ResponseEntity<>(mensagemEncontrada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (MensagemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
