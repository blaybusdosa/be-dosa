package blaybus.dosa.memonote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class MemoNoteResponse {
    private Long id;
    private Long userId;
    private String machineKey;
    private String partKey;
    private String title;
    private String body;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
