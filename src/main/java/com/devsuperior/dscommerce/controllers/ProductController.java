package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired // Injeção de dependência
    private ProductService productService; //inversão de controle

    @GetMapping(value = "/{id}") // adiciona um parametro na URL: /products/1
    public ProductDTO findById(@PathVariable Long id){ //PathVariable captura o id que veio na requisição e joga no argumento.
        return productService.findById(id); /*chamada do Service que busca o produto no banco de dados e retorna um DTO para ser retornada pro consumidor da API*/
    }

    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable){ //Pageable
        return productService.findAll(pageable);
    }
}
