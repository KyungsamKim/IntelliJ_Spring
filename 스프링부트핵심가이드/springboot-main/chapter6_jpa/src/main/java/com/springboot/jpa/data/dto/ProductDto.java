package com.springboot.jpa.data.dto;

import lombok.*;

// 예제 6.19
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class ProductDto {

    private String name;

    private int price;

    private int stock;

}
