package com.devsuperior.dscommerce.dto;

public class FieldMessage {

    /*Classe para representar a mensagem de campos com dados invalidos*/

    private String fildName; /*Atributo para representar o campo. Ex: Name, price etc...*/
    private String message; /*Atributo para representar a descrição do erro de campo invalido*/

    public FieldMessage(String fildName, String message) {
        this.fildName = fildName;
        this.message = message;
    }

    public String getFildName() {
        return fildName;
    }

    public String getMessage() {
        return message;
    }
}
