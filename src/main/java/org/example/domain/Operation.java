package org.example.domain;

import org.example.exception.NotFoundByOrdinalException;

public enum Operation {
    GET_FORECAST,
    GET_CURRENT_TEMPERATURE,
    GET_AVERAGE_TEMPERATURE
    ;

    public static Operation getByOrdinal(Integer ordinal) {
        Operation operation;
        try {
            operation = Operation.values()[ordinal];
        } catch (Exception ex){
            throw new NotFoundByOrdinalException("This command does not exist");
        }
        return operation;
    }
}
