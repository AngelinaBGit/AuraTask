package org.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Integer id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    private String title;
    private String content;
    private String status;
    private Boolean published;
    private Integer publisher;

    @JsonProperty("someJson")
    private SomeJsonItem[] someJson;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SomeJsonItem {
        private Integer number;
        private String string;

        @JsonProperty("boolean")
        private Boolean isBoolean;

        private String date;
    }
}