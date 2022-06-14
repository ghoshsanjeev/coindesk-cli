package com.kn.cd.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Time {
    private String updated;     //TODO: Date field string format to UTC
    private String updatedISO;  //TODO: Date field string format to ISO
    @JsonProperty("updateduk")
    private String updatedUK;   //TODO: Date field string format to BST
}
