package com.devsuperior.dscommerce.dto;

import org.springframework.beans.propertyeditors.FileEditor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{

    /*Classe que herda o CustomError(corpo tradicional de erro)
    * porém adicionamos uma lista de FieldMessage que representa a descrição dos erros
    * de cada campo invalido que foi digitado na requisição*/

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){

        errors.removeIf(x -> x.getFildName().equals(fieldName));
        errors.add(new FieldMessage(fieldName, message)); /*Adicionando os erros na lista de campos invalidados*/
    }
}
