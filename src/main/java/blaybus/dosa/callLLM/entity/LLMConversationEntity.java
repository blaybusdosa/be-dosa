package blaybus.dosa.callLLM.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import blaybus.dosa.user.SocialAccountEntity;

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
    @JoinColumn(name = "social_account_id", nullable = false)
    private SocialAccountEntity socialAccount;

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

    public LLMConversationEntity(SocialAccountEntity socialAccount, String prompt, String response) {
        this.socialAccount = socialAccount;
        this.prompt = prompt;
        this.response = response;
    }
}
