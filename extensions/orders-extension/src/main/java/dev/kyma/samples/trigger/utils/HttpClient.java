package dev.kyma.samples.trigger.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import static io.vavr.API.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class HttpClient {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public  <T> Optional<T> get(String url, Class<T> deserializeTo) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Optional<String> string = getResponseBodyString(response);
            return string.map(unchecked(s -> objectMapper.readValue(s, deserializeTo)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Optional<String> getResponseBodyString(Response response) {
        return Optional.ofNullable(response.body()).map(unchecked(ResponseBody::string));

    }
}
