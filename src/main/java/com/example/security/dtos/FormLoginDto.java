package com.example.security.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormLoginDto {
    private String id;
    private String password;
}
