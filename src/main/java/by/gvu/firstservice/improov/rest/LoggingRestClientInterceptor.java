package by.gvu.firstservice.improov.rest;

import by.gvu.firstservice.improov.MvcConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class LoggingRestClientInterceptor implements ClientHttpRequestInterceptor {

    private final RestClientLoggingTimerHelper restClientLoggingTimerHelper;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            restClientLoggingTimerHelper.start();
            ClientHttpResponse response = execution.execute(request, body);
            restClientLoggingTimerHelper.stop();

            restClientLoggingTimerHelper.setRemoteRequestTimeStart(getHeaderAsLong(response, MvcConfiguration.HEADER_TIME_START));
            restClientLoggingTimerHelper.setRemoteRequestTimeEnd(getHeaderAsLong(response, MvcConfiguration.HEADER_TIME_END));

            return response;
        } finally {
            Map<String, Long> localDuration = new ConcurrentHashMap<>();

            restClientLoggingTimerHelper.submit(localDuration::putAll);
            log.warn(localDuration.toString());
        }
    }

    private Long getHeaderAsLong(ClientHttpResponse response, String headerName) {
        return Optional.ofNullable(response.getHeaders().get(headerName))
                .orElse(Collections.emptyList())
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(Long::parseLong)
                .findFirst()
                .orElse(null);
    }
}
