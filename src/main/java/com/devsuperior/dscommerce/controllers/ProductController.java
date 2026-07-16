package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired // Injeção de dependência
    private ProductService productService; //inversão de controle

    @GetMapping(value = "/{id}") // adiciona um parametro na URL: /products/1
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){ //PathVariable captura o id que veio na requisição e joga no argumento.
        ProductDTO dto = productService.findById(id); /*chamada do Service que busca o produto no banco de dados e retorna um DTO para ser retornada pro consumidor da API*/
        return ResponseEntity.ok(dto); //Personalizando a responsa HTTP com o ResponseEntity
    }

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){ //Pageable é uma funcionalidade do Spring que evita lentidão ou
             Page<ProductMinDTO> dto = productService.findAll(name,pageable); // estouro de memória, permitindo retornar um tamanho especifico
                                                            // de objetos por páginas que é definido pelos argumentos da API Web
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@Valid @RequestBody ProductDTO dto){ //O @RequestBody permite instanciar um novo dto com
        dto = productService.insertProduct(dto); //reaproveita o dto recebido no argumento e passa pro service salvar
        URI uri = ServletUriComponentsBuilder //Pega a URL feita na requisição. Ex: http://localhost:8080/products
                .fromCurrentRequest().path("/{id}") //Cria um novo paramêtro na URL com o id. Ex: http://localhost:8080/products/{id}
                .buildAndExpand(dto.getId()).toUri(); //substitui o {id} pelo valor real do novo id do novo objeto. Ex: http://localhost:8080/products/26
        return ResponseEntity.created(uri).body(dto); //Retorna o status 201 com a URL do novo produto criado junto com o JSON
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        dto = productService.updateProduct(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
