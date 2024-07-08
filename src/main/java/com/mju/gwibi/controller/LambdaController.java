package com.mju.gwibi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mju.gwibi.dto.response.LambdaInvocationResult;
import com.mju.gwibi.service.LambdaService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/parsing")
public class LambdaController {

	private final LambdaService lambdaService;

	@PostMapping("/text")
	public Mono<ResponseEntity<LambdaInvocationResult>> invokeLambda(@RequestBody String text) {
		return lambdaService.invokeLambdaFunction(text)
			.map(result -> {
				LambdaInvocationResult invocationResult = LambdaInvocationResult.builder()
					.success(result.isSuccess())
					.createdItemCount(result.getCreatedItemCount())
					.createdItemList(result.getCreatedItemList())
					.build();
				return ResponseEntity.ok(invocationResult);
			})
			.onErrorResume(e -> {
				LambdaInvocationResult errorResult = LambdaInvocationResult.builder()
					.success(false)
					.createdItemCount(0)
					.createdItemList(List.of())
					.build();
				return Mono.just(ResponseEntity.internalServerError().body(errorResult));
			});
	}
}
