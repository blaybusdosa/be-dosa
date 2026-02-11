package blaybus.dosa.callLLM;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import blaybus.dosa.callLLM.entity.LLMConversationEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
@Tag(name = "LLM", description = "LLM 채팅 및 질의 응답 API")
public class CallLLMController {

    private final CallLLMService callLLMService;

    public CallLLMController(CallLLMService callLLMService) {
        this.callLLMService = callLLMService;
    }
    @Operation(
            summary = "LLM 채팅",
            description = "일반 텍스트 프롬프트를 보내고 LLM 응답을 반환합니다."
    )
    @PostMapping("/chat")
    public String chat(@RequestParam Long userId, @RequestBody String prompt) {
        return callLLMService.getChatResponse(prompt, userId);
    }

    @Operation(
            summary = "LLM 대화 기록 조회",
            description = "특정 사용자 ID의 모든 LLM 대화 기록을 반환합니다."
    )
    @GetMapping("/conversations")
    public List<LLMConversationEntity> getAllConversations(@RequestParam Long userId) {
        return callLLMService.getAllConversations(userId);
    }

    @Operation(
            summary = "LLM 대화 기록 ID 조회",
            description = "ID와 사용자 ID를 사용하여 저장된 LLM 대화 기록을 반환합니다."
    )
    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<LLMConversationEntity> getConversationById(@PathVariable Long conversationId, @RequestParam Long userId) {
        Optional<LLMConversationEntity> conversation = callLLMService.getConversationById(conversationId, userId);
        return conversation.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + conversationId + "에 해당하는 대화 기록을 찾을 수 없습니다."));
    }

    @Operation(
            summary = "LLM 대화 기록 삭제",
            description = "ID와 사용자 ID를 사용하여 저장된 LLM 대화 기록을 삭제합니다."
    )
    @DeleteMapping("/conversations/{conversationId}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable Long conversationId, @RequestParam Long userId) {
        try {
            callLLMService.deleteConversationById(conversationId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(
            summary = "특정 사용자 ID의 모든 LLM 대화 기록 삭제",
            description = "주어진 사용자 ID에 해당하는 모든 LLM 대화 기록을 삭제합니다."
    )
    @DeleteMapping("/conversations")
    public ResponseEntity<Void> deleteAllConversationsByUserId(@RequestParam Long userId) {
        try {
            callLLMService.deleteAllConversationsByUserId(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

