package com.ssf.assessment.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.ssf.assessment.util.Constants.*;

@Repository
public class BookRepository {
    @Autowired
    @Qualifier(BEAN_BOOK_CACHE)
    private RedisTemplate<String, String> template;

    public void save(String searchTerm, String value) {
        template.opsForValue().set(normalize(searchTerm), value, 10L, TimeUnit.MINUTES);
    }

    public Optional<String> get(String searchTerm) {
        String value = template.opsForValue().get(normalize(searchTerm));
        return Optional.ofNullable(value);
    }

    private String normalize(String k) { return k.trim().toLowerCase();}
}
