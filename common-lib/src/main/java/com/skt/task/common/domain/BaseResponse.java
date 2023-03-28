package com.skt.task.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Generic class to handle response types
 * @param <T> response type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T extends Serializable> {
    private T payload;
    private BaseError error;
}
