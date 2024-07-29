package by.gvu.firstservice.baselogic.service.impl;

import by.gvu.firstservice.baselogic.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecondCategoryService implements CategoryService {
    private final RestClient restClient;

    @Override
    public List<String> findAll() {
        ParameterizedTypeReference<List<String>> refProductList = new ParameterizedTypeReference<List<String>>() {};

        return restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/second").path("/categories").build())
                .retrieve()
                .body(refProductList);
    }
}
