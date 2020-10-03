package com.mercadolibre.quasarfireoperationjg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by JUAN on 2020/10/03.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Calculation error")
public class CalculationException extends RuntimeException {

}