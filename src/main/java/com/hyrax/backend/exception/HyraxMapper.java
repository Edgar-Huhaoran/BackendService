package com.hyrax.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class HyraxMapper implements ExceptionMapper<HyraxException> {

    private static final Logger log = LoggerFactory.getLogger(HyraxMapper.class);

    public Response toResponse(HyraxException exception) {
        log.warn("response error with status:{}, errorCode:{}, clientMessage:{}",
                exception.getStatus().value(), exception.getErrorCode(), exception.getClientMessage(), exception);

        return Response.status(exception.getStatus().value())
                .header("X-Hyrax-ErrorCode", exception.getErrorCode())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(exception))
                .build();
    }

}
