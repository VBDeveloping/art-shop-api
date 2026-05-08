package br.com.vbartshop.art_shop_api.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;
}
