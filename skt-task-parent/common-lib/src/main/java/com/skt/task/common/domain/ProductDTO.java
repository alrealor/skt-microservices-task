package com.skt.task.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * class that represents a product of domain
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank
    private String name;
    @Min(1)
    private BigDecimal price;
}
