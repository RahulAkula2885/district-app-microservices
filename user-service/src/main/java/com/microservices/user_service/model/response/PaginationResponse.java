package com.microservices.user_service.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse {

    private Long totalCount;
    private List<?> response;
}
