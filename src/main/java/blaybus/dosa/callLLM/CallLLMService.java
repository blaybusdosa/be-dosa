package blaybus.dosa.callLLM;


import blaybus.dosa.callLLM.dto.ChatRequest;
import blaybus.dosa.callLLM.dto.ChatResponse;
import blaybus.dosa.callLLM.dto.Message;
import blaybus.dosa.callLLM.entity.LLMConversationEntity;
import blaybus.dosa.callLLM.repository.LLMConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * GPT-5 mini (LLM) API와 통신을 담당하는 서비스 클래스입니다.
 * RestTemplate을 사용하여 채팅 요청을 보내고 응답을 받습니다.
 */
@Service
public class CallLLMService {

    private final RestTemplate restTemplate; // HTTP 요청을 만들기 위해 주입된 RestTemplate

    // application.yml에서 LLM 모델 이름을 주입합니다.
    @Value("${openai.api.model:}")
    private String model;

    // application.yml에서 OpenAI API URL을 주입합니다.
    @Value("${openai.api.url:}")
    private String apiUrl;

    // application.yml에서 시스템 메시지를 주입합니다.
    @Value("${openai.api.system-message:}")
    private String systemMessage;

    /**
     * CallLLMService의 생성자로, 구성된 RestTemplate을 주입합니다.
     * @param restTemplate API 키 인터셉터로 구성된 RestTemplate 인스턴스.
     */
    private final LLMConversationRepository llmConversationRepository;

    @Autowired
    public CallLLMService(RestTemplate restTemplate, LLMConversationRepository llmConversationRepository) {
        this.restTemplate = restTemplate;
        this.llmConversationRepository = llmConversationRepository;
    }

    /**
     * GPT-5 mini API에 채팅 프롬프트를 보내고 LLM의 응답을 반환합니다.
     * @param prompt 사용자의 입력 프롬프트.
     * @return LLM의 응답 메시지 내용.
     */
    public String getChatResponse(String prompt) {
        // 시스템 메시지와 사용자 프롬프트를 포함하는 메시지 목록을 생성합니다.
        List<Message> messages = List.of(
                new Message("system", systemMessage),
                new Message("user", prompt)
        );

        // 구성된 모델과 메시지 목록을 사용하여 채팅 요청 객체를 생성합니다.
        ChatRequest request = new ChatRequest(model, messages);
        // OpenAI API에 POST 요청을 보내고 응답을 받습니다.
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        // LLM 응답이 유효한지 확인합니다.
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            // 운영 환경에서는 로깅을 추가하거나 더 구체적인 예외 처리를 고려해야 합니다.
            throw new IllegalStateException("LLM으로부터 유효한 응답을 받지 못했습니다.");
        }

        // 첫 번째 선택지 메시지의 내용을 추출합니다.
        String llmResponseContent = response.getChoices().get(0).getMessage().getContent();

        // LLM 대화 내용을 데이터베이스에 저장합니다.
        LLMConversationEntity conversation = new LLMConversationEntity(prompt, llmResponseContent);
        llmConversationRepository.save(conversation);

        // LLM 응답 메시지 내용을 반환합니다.
        return llmResponseContent;
    }

    /**
     * 저장된 모든 LLM 대화 기록을 조회합니다.
     * @return 모든 LLM 대화 기록 목록.
     */
    public List<LLMConversationEntity> getAllConversations() {
        return llmConversationRepository.findAll();
    }

    /**
     * ID를 사용하여 특정 LLM 대화 기록을 조회합니다.
     * @param id 조회할 대화 기록의 ID.
     * @return 지정된 ID를 가진 LLM 대화 기록 (존재하지 않으면 Optional.empty()).
     */
    public Optional<LLMConversationEntity> getConversationById(Long id) {
        return llmConversationRepository.findById(id);
    }

    /**
     * ID를 사용하여 특정 LLM 대화 기록을 삭제합니다.
     * @param id 삭제할 대화 기록의 ID.
     * @return 삭제 성공 여부.
     * @throws IllegalStateException 지정된 ID의 대화 기록을 찾을 수 없는 경우.
     */
    public void deleteConversationById(Long id) {
        if (!llmConversationRepository.existsById(id)) {
            throw new IllegalStateException("ID " + id + "에 해당하는 대화 기록을 찾을 수 없어 삭제할 수 없습니다.");
        }
        llmConversationRepository.deleteById(id);
    }



}