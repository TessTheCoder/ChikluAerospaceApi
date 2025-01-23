package io.github.TessTheCoder.ChikluAerospace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureResponse {
    private String url;
    private String title;
    private String explanation;
}