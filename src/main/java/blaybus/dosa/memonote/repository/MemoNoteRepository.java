package blaybus.dosa.memonote.repository;

import blaybus.dosa.memonote.entity.MemoNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoNoteRepository extends JpaRepository<MemoNoteEntity, Long> {
    List<MemoNoteEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}
