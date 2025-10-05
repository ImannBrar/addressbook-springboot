package ca.carleton.s4806.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBuddyRequest(
        @NotBlank String name,
        @NotBlank String phone
) {}
