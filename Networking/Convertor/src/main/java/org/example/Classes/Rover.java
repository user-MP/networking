package org.example.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rover {
    @JsonProperty("id")
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("landing_date")
    String landing_date;
    @JsonProperty("launch_date")
    String launch_date;
    @JsonProperty("status")
    String status;
}
