package com.saurabh.microservices.limitsservice.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Limits {
    private Integer minimum;
    private Integer maximum;
}
