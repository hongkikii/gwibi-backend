package com.mju.gwibi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.gwibi.dto.response.ItemResponse;
import com.mju.gwibi.dto.response.LambdaInvocationResult;
import com.mju.gwibi.dto.response.LambdaResponse;
import com.mju.gwibi.entity.Category;
import com.mju.gwibi.entity.Item;
import com.mju.gwibi.infrastructure.ItemRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LambdaService {

	private final WebClient webClient;
	private final String lambdaUrl;
	private final ObjectMapper objectMapper;
	private final ItemRepository itemRepository;
	private final CategoryService categoryService;

	public LambdaService(WebClient webClient, @Value("${lambda.function.url}") String lambdaUrl,
		ObjectMapper objectMapper, ItemRepository itemRepository, CategoryService categoryService) {
		this.webClient = webClient;
		this.lambdaUrl = lambdaUrl;
		this.objectMapper = objectMapper;
		this.itemRepository = itemRepository;
		this.categoryService = categoryService;
	}

	public Mono<LambdaInvocationResult> invokeLambdaFunction(String payload) {
		return webClient.post()
			.uri(lambdaUrl)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(payload)
			.retrieve()
			.bodyToMono(String.class)
			.flatMap(this::parseResponse)
			.flatMap(this::processResponses)
			.onErrorResume(e -> {
				System.err.println("Error occurred: " + e.getMessage());
				return Mono.just(LambdaInvocationResult.builder()
					.success(false)
					.createdItemCount(0)
					.createdItemList(List.of())
					.build());
			});
	}

	private Mono<List<LambdaResponse>> parseResponse(String response) {
		return Mono.fromCallable(() -> {
			System.out.println("Raw response: " + response);  // 디버깅을 위한 로그
			Map<String, List<LambdaResponse>> resultMap = objectMapper.readValue(response,
				new TypeReference<>() {});
			List<LambdaResponse> products = resultMap.get("result");
			System.out.println("Parsed products: " + products);  // 디버깅을 위한 로그
			return products;
		});
	}

	public Mono<LambdaInvocationResult> processResponses(List<LambdaResponse> responses) {
		LocalDate now = LocalDate.now();
		List<ItemResponse> createdItemList = new ArrayList<>();
		int[] createdItemCount = {0};

		return Mono.fromCallable(() -> {
			for (LambdaResponse response : responses) {
				Category category = categoryService.getByName(response.getProduct());
				int count = "default".equals(response.getCount()) ? 1 : Integer.parseInt(response.getCount());
				int period = "default".equals(response.getPeriod()) ? category.getUsagePeriod() : Integer.parseInt(response.getPeriod());

				for (int i = 1; i <= count; i++) {
					String itemName = response.getProduct() + i + "(" + now + ")";
					Item item = Item.builder()
						.imageKey(category.getImageKey())
						.name(itemName)
						.startDate(now)
						.endDate(now.plusDays(period))
						.category(category)
						.build();
					itemRepository.save(item);
					createdItemList.add(ItemResponse.of(item));
					createdItemCount[0]++;
				}
			}
			return LambdaInvocationResult.builder()
				.success(true)
				.createdItemCount(createdItemCount[0])
				.createdItemList(createdItemList)
				.build();
		}).onErrorResume(e -> {
			System.err.println("Error processing responses: " + e.getMessage());
			return Mono.just(LambdaInvocationResult.builder()
				.success(false)
				.createdItemCount(createdItemCount[0])
				.createdItemList(createdItemList)
				.build());
		});
	}
}
