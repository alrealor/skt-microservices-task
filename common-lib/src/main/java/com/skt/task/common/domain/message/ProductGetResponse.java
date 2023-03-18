package com.skt.task.common.domain.message;

import com.skt.task.common.domain.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * class that represents the message for product get response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetResponse {
    private List<ProductDTO> products;
}
