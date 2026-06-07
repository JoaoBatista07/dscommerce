package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).get(); //precisa do .get para transformar o Optional em Product.
        return new ProductDTO(product); // no return ja instanciei um DTO sem a necessidade de criar uma variável DTO.
    }

    /*Este metodo retorna uma lista de produto paginada
    * usando o metodo findAll do JPA Repository*/
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insertProduct(ProductDTO dto){
        // copiando os dados do DTO para Product
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        //para criar no banco, utilizamos o Save do JPA Repository
        entity = productRepository.save(entity);
        //retornamos um DTO com o objeto inserido.
        return new ProductDTO(entity);
    }

}
