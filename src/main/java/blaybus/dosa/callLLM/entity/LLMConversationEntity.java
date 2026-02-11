package blaybus.dosa.callLLM.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import blaybus.dosa.user.UserEntity;

@Entity
@Table(name = "llm_conversations")
@Getter
@Setter
@NoArgsConstructor
public class LLMConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String response;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    public LLMConversationEntity(UserEntity user, String prompt, String response) {
        this.user = user;
        this.prompt = prompt;
        this.response = response;
    }
}
