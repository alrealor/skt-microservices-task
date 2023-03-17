package com.skt.task.common.message;

import com.skt.task.common.domain.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseGet {
    private List<ProductDTO> products;
}
