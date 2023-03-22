package com.skt.task.microservice.mapper;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.dao.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class to transform from model to domain classes and vice-versa
 */
@Mapper
public interface ProductMapper {

    /**
     * Mapper of type {@link ProductMapper}
     */
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    /**
     * Mapper method to transform from model class to domain class
     *
     * @param product model class of type {@link Product}
     * @return domain class of type {@link ProductDTO}
     */
    ProductDTO map(Product product);
}
