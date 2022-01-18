package com.ssf.assessment.service;

import com.ssf.assessment.repository.BookRepository;
import com.ssf.assessment.util.Book;
import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BookCacheService {

    private final Logger logger = Logger.getLogger(BookCacheService.class.getName());

    @Autowired
    private BookRepository bookRepo;

    public void save(String workId, List<Book> book) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        book.stream()
                .forEach(v -> arrBuilder.add(v.toJson()));
        bookRepo.save(workId, arrBuilder.build().toString());
    }

    public Optional<List<Book>> get(String searchTerm) {
        Optional<String> opt = bookRepo.get(searchTerm);
        if (opt.isEmpty())
            return Optional.empty();

        JsonArray jsonArray = parseJsonArry(opt.get());
        List<Book> book = jsonArray.stream()
                .map(v -> (JsonObject)v)
                .map(Book::create)
                .collect(Collectors.toList());
        return Optional.of(book);
    }

    private JsonArray parseJsonArry(String s) {
        try (InputStream is = new ByteArrayInputStream(s.getBytes())){
            JsonReader reader = Json.createReader(is);
            return reader.readArray();
        } catch (Exception e) {
            logger.warning("PARSING >> " + e);
        }

        return Json.createArrayBuilder().build();
    }
}
