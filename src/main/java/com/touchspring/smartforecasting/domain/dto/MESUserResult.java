package com.touchspring.smartforecasting.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public
class MESUserResult {
    private List<MESUser> data;
    private Integer code;
}