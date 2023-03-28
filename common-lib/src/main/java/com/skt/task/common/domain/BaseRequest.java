package com.skt.task.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Generic class to handle request types
 * @param <T> request type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest<T extends Serializable> {
    T payload;
}
