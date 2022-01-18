package com.ssf.assessment.service;

import com.ssf.assessment.util.Book;
import jakarta.json.*;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.ssf.assessment.util.Constants.*;

@Service(BEAN_BOOK_SERVICE)
public class BookServiceImpl implements BookService{

    // logger class
    private final Logger logger = Logger.getLogger(BookServiceImpl.class.getName());

    // search book method for URL
    public List<Book> search(String searchTerm) {

        // build the url string
        final String url = UriComponentsBuilder
                .fromUriString(URL_BOOK)
                .queryParam("q", searchTerm.trim().replace(" ", "+"))
                .queryParam("limit", 20)
                .toUriString();
        logger.info("URL >>> " + url);


        final RequestEntity<Void> request = RequestEntity.get(url).build();
        logger.info("REQUEST >>> " + request);
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(request, String.class);
//        logger.info("RESP >>> " + resp);

        final String body = resp.getBody();
//        logger.info("payload >>> %s".formatted(body));

        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray readings = result.getJsonArray("docs");
//             logger.info("READINGS >>> " + readings);

            return readings.stream()
                    .map(v -> (JsonObject)v)
                    .map(Book::create)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


//     search book details after clicking URL
    public List<Book> getDetails(String key) {
        //build the url string
        final String url = UriComponentsBuilder
                .fromUriString(URL_BOOK_DETAILS)
                .path("/works/")
                .path(key)
                .path(".json")
                .toUriString();
        logger.info("URL2 >> " + url);
        final RequestEntity<Void> request = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(request, String.class);
        final String body = resp.getBody();

        try (InputStream is = new ByteArrayInputStream(body.getBytes())){
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();

            return Collections.singletonList(Book.getDetails(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
