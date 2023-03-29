package com.skt.task.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * class that represents a product of domain
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank
    @Size(max = 150)
    private String name;
    @DecimalMin(value = "1.00")
    @DecimalMax(value = "999999999999.99")
    @Digits(integer = 12, fraction = 2)
    @Min(1)
    private BigDecimal price;
}
