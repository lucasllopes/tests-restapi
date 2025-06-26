package com.estudos.fiap.bdd;

import com.estudos.fiap.helper.MensagemHelper;
import com.estudos.fiap.model.Mensagem;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class CucumberSteps {

    private Response response;

    private Mensagem mensagemResposta;

    private String ENDPOINT = "http://localhost:8080/mensagens";

    @Quando("submeter uma nova mensagem")
    public void submeter_uma_nova_mensagem() {
        var payload = MensagemHelper.getMensagem();
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(payload)
                .when().post(ENDPOINT);
        mensagemResposta = response.then().extract().as(Mensagem.class);
    }

    @Então("a mensagem é registrada com sucesso")
    public void a_mensagem_é_registrada_com_sucesso() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Dado("que uma mensagem já foi registrada")
    public void que_uma_mensagem_já_foi_registrada() {
        submeter_uma_nova_mensagem();

    }

    @Quando("buscar a mensagem registrada")
    public void buscar_a_mensagem_registrada() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT + "/{id}", mensagemResposta.getId().toString());
    }

    @Então("a mensagem é exibida com sucesso")
    public void a_mensagem_é_exibida_com_sucesso() {
        // Write code here that turns the phrase above into concrete actions
        response.then().statusCode(HttpStatus.OK.value());
    }

    /*@Quando("requisitar a remoção da mensagem")
    public void requisitar_a_remoção_da_mensagem() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(ENDPOINT + "/{id}", mensagemResposta.getId().toString());
    }

    @Então("a mensagem é removida com sucesso")
    public void a_mensagem_é_removida_com_sucesso() {
        response.then().statusCode(HttpStatus.OK.value());
    }*/
}
