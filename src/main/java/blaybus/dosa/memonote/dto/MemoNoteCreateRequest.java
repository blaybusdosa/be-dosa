package blaybus.dosa.memonote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoNoteCreateRequest {
    @NotNull
    @Positive
    private Long userId;
    @NotBlank
    @Size(max = 100)
    private String machineKey;
    @Size(max = 255)
    private String partKey;
    @NotBlank
    @Size(max = 255)
    private String title;
    private String body;
}
