package com.skt.task.common.domain.message;

import com.skt.task.common.domain.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class that represents the message for product post request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPostRequest {
    private ProductDTO product;
}
