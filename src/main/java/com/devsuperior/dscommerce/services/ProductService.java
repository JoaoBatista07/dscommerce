package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")); //precisa do .get para transformar o Optional em Product.
        return new ProductDTO(product); // no return ja instanciei um DTO usando a sobrecarga no construtor sem a necessidade de criar uma variável DTO.
    }

    /*Este metodo retorna uma lista de produto paginada
    * usando o metodo findAll do JPA Repository*/
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable){
        Page<Product> result = productRepository.searchByName(name, pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insertProduct(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto,entity); // copiando os dados do DTO para Product
        entity = productRepository.save(entity); //para criar no banco, utilizamos o Save do JPA Repository
        return new ProductDTO(entity); //retornamos um DTO com o objeto inserido.
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto){
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);  // copiando os dados do DTO para Product
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            productRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
