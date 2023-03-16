package common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to define the generic error structure from a response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseError {
    private String errorCode;
    private String errorMessage;
}
