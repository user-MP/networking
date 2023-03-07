package org.example.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Camera {
    @JsonProperty("id")
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("rover_id")
    int rover_id;
    @JsonProperty("full_name")
    String full_name;
}
