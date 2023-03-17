package com.skt.task.common.message;

import com.skt.task.common.domain.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestPost {
    private ProductDTO product;
}
