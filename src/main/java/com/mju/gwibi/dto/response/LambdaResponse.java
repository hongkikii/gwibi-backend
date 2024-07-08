package com.mju.gwibi.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LambdaResponse {
	private final String product;
	private final String period;
	private final String count;

	@JsonCreator
	public static LambdaResponse of(
		@JsonProperty("product") String product,
		@JsonProperty("period") String period,
		@JsonProperty("count") String count) {
		return LambdaResponse.builder()
			.product(product)
			.period(period)
			.count(count)
			.build();
	}
}