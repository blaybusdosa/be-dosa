package blaybus.dosa.memonote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoNoteCreateRequest {
    private Long userId;
    private String machineKey;
    private String partKey;
    private String title;
    private String body;
}
