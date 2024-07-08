package com.mju.gwibi.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LambdaInvocationResult {
	private boolean success;
	private int createdItemCount;
	private List<ItemResponse> createdItemList;
}
