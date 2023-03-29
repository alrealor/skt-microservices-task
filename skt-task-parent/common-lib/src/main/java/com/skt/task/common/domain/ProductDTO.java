package com.skt.task.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @DecimalMax(value = "9999999999.99")
    @NotNull
    private BigDecimal price;
}
