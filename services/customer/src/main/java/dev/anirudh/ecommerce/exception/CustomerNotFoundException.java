package dev.anirudh.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {

    private final String msg;

    //below isnot in mainfile.
    public CustomerNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }
}