package com.ewallet.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseSuccess<T> {
    private T data;
    private String message;
    private int totalData;
    private PaginationResponse pagination;
}
