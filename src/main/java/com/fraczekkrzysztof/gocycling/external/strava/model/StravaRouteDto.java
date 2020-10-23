package com.fraczekkrzysztof.gocycling.external.strava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "athlete",
        "description",
        "distance",
        "elevation_gain",
        "id",
        "map",
        "name",
        "private",
        "resource_state",
        "starred",
        "sub_type",
        "timestamp",
        "created_at",
        "updated_at",
        "type",
        "estimated_moving_time"
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StravaRouteDto {

    @JsonProperty("athlete")
    public SummaryAthlete athlete;
    @JsonProperty("description")
    public String description;
    @JsonProperty("distance")
    public Double distance;
    @JsonProperty("elevation_gain")
    public Double elevationGain;
    @JsonProperty("id")
    public Long id;
    @JsonProperty("map")
    public MapDto map;
    @JsonProperty("name")
    public String name;
    @JsonProperty("private")
    public Boolean _private;
    @JsonProperty("resource_state")
    public Integer resourceState;
    @JsonProperty("starred")
    public Boolean starred;
    @JsonProperty("sub_type")
    public Integer subType;
    @JsonProperty("timestamp")
    public Integer timestamp;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("type")
    public Integer type;
    @JsonProperty("estimated_moving_time")
    public Integer estimatedMovingTime;

}
