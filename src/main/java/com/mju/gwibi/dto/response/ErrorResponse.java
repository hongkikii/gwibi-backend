package com.mju.gwibi.dto.response;

import java.util.List;

public record ErrorResponse(List<String> messages) {
}
