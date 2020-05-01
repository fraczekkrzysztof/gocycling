package com.fraczekkrzysztof.gocycling.external.strava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "summary_polyline",
        "resource_state"
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MapDto {

    @JsonProperty("id")
    public String id;
    @JsonProperty("summary_polyline")
    public String summaryPolyline;
    @JsonProperty("resource_state")
    public Integer resourceState;

}
