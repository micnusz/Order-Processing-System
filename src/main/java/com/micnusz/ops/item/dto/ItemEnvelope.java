package com.micnusz.ops.item.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemEnvelope {
    @NotBlank
    String title;
    @NotBlank
    String description;
    @NotNull
    Float price;
    @NotNull
    Integer quantity;
}
