package io.github.alinacozy.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Command {
    @NotBlank
    @Size(max = 1000)
    String description;

    @NotNull
    Priority priority;

    @NotBlank
    @Size(max = 100)
    String author;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
    String time;

    public Command() {
    }
}
