package com.ecommerce.microcommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProduitIntrouvableException extends RuntimeException {
    public ProduitIntrouvableException(String s) {
        super(s);
    }
}
