package by.gvu.firstservice.baselogic.controller;

import by.gvu.firstservice.baselogic.service.CategoryService;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/first", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Slf4j
public class FirstController {
    private final CategoryService categoryService;
    private final Tracer tracer;

    @PostMapping("/categories")
    public List<String> getProductById() {
        log.warn("{} Baggages are {}", "getProductById", tracer.getAllBaggage());
        return categoryService.findAll();
    }
}
