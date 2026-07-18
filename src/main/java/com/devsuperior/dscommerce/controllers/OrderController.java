package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insertOrder(@Valid @RequestBody OrderDTO dto){ //O @RequestBody permite instanciar um novo dto com
        dto = service.insertOrder(dto); //reaproveita o dto recebido no argumento e passa pro service salvar
        URI uri = ServletUriComponentsBuilder //Pega a URL feita na requisição. Ex: http://localhost:8080/products
                .fromCurrentRequest().path("/{id}") //Cria um novo paramêtro na URL com o id. Ex: http://localhost:8080/products/{id}
                .buildAndExpand(dto.getId()).toUri(); //substitui o {id} pelo valor real do novo id do novo objeto. Ex: http://localhost:8080/products/26
        return ResponseEntity.created(uri).body(dto); //Retorna o status 201 com a URL do novo produto criado junto com o JSON
    }

}
