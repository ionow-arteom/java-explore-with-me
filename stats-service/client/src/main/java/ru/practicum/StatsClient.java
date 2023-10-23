package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.HitDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> add(HitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> findStats(String start, String end, String[] uris, boolean unique) {
        return get(buildUri("/stats", start, end, uris, unique), buildParameters(start, end, uris, unique));
    }

    public ResponseEntity<Object> findStatsWithoutUris(String start, String end, boolean unique) {
        return get(buildUri("/stats", start, end, null, unique), buildParameters(start, end, null, unique));
    }

    private String buildUri(String baseUri, String start, String end, String[] uris, boolean unique) {
        StringBuilder uriBuilder = new StringBuilder(baseUri);
        uriBuilder.append("?start=").append(start)
                .append("&end=").append(end)
                .append("&unique=").append(unique);
        if (uris != null && uris.length > 0) {
            uriBuilder.append("&uris=").append(String.join(",", uris));
        }
        return uriBuilder.toString();
    }

    private Map<String, Object> buildParameters(String start, String end, String[] uris, boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("unique", unique);
        if (uris != null && uris.length > 0) {
            parameters.put("uris", uris);
        }
        return parameters;
    }
}