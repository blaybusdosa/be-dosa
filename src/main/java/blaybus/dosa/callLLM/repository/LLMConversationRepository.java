package blaybus.dosa.callLLM.repository;

import blaybus.dosa.callLLM.entity.LLMConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * LLMConversationEntity에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스입니다.
 */
@Repository
public interface LLMConversationRepository extends JpaRepository<LLMConversationEntity, Long> {
    List<LLMConversationEntity> findBySocialAccount_Id(Long socialAccountId);
    Optional<LLMConversationEntity> findByIdAndSocialAccount_Id(Long id, Long socialAccountId);
    void deleteBySocialAccount_Id(Long socialAccountId);
}
