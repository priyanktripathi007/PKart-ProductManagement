package com.pramati.kart.productManagement.utility;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Response implements Serializable {
    private String message;

    public Response(String message) {
        this.message = message;
    }
}