package io.github.TessTheCoder.ChikluAerospace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NeoResponse {
    private Links links;

    @JsonProperty("near_earth_objects")
    private Map<String, List<NearEarthObject>> NearEarthObjects;
    
    @Data
    public static class Links {
        private String next;
        private String prev;
        private String self;
    }
    @Data
    public static class NearEarthObject {
        private String nasa_jpl_url;
    }
}




