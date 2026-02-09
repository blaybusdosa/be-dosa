package blaybus.dosa.callLLM;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String chat(@RequestBody String prompt) {
        return callLLMService.getChatResponse(prompt);
    }


}
