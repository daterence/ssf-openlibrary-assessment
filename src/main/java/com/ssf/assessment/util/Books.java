//package com.ssf.assessment.util;
//
//import jakarta.json.Json;
//import jakarta.json.JsonObject;
//import jakarta.json.JsonReader;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//public class Books {
//    private String title;
//    private String description;
//    private String excerpts;
//    private String key;
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getExcerpts() {
//        return excerpts;
//    }
//    public void setExcerpts(String excerpts) {
//        this.excerpts = excerpts;
//    }
//
//    public static Books create(JsonObject jo) {
//        final Books b = new Books();
//        if (jo.containsKey("key")) {
//            b.setKey(jo.getString("key").replace("/works", ""));
//        }
//        if (jo.containsKey("title")) {
//            try {
//                b.setTitle(jo.getString("title"));
//            } catch (Exception e) {
//                b.setTitle("NOT AVAILABLE");
//            }
//        }
//        if (jo.containsKey("description")) {
//            try {
//                b.setDescription(jo.getString("description"));
//            } catch (Exception e) {
//                b.setDescription("NOT AVAILABLE");
//            }
//        } else {
//            b.setDescription("NOT AVAILABLE");
//        }
//
//        if (jo.containsKey("excerpt")) {
//            try {
//                b.setExcerpts(jo.getString("excerpt"));
//            } catch (Exception e) {
//                b.setExcerpts("NOT AVAILABLE");
//            }
//        } else {
//            b.setExcerpts("NOT AVAILABLE");
//        }
//
//        return b;
//    }
//
//    public static Books create(String jsonString) {
//        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
//            final JsonReader reader = Json.createReader(is);
//            return create(reader.readObject());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new Books();
//    }
//
//    @Override
//    public String toString() {
//        return "key: %s title: %s, description: %s excerpts: %s".formatted(key, title, description, excerpts);
//    }
//
//    public JsonObject toJson() {
//        return Json.createObjectBuilder()
//                .add("key", key)
//                .add("title", title)
//                .add("description", description)
//                .add("excerpt", excerpts)
//                .build();
//    }
//}
