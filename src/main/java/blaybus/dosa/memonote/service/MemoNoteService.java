package blaybus.dosa.memonote.service;

import blaybus.dosa.memonote.dto.MemoNoteCreateRequest;
import blaybus.dosa.memonote.dto.MemoNoteResponse;
import blaybus.dosa.memonote.dto.MemoNoteUpdateRequest;
import blaybus.dosa.memonote.entity.MemoNoteEntity;
import blaybus.dosa.memonote.repository.MemoNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoNoteService {

    private final MemoNoteRepository memoNoteRepository;

    public MemoNoteResponse create(MemoNoteCreateRequest request) {
        validateCreate(request);

        MemoNoteEntity entity = MemoNoteEntity.builder()
                .userId(request.getUserId())
                .machineKey(request.getMachineKey())
                .partKey(request.getPartKey())
                .title(request.getTitle())
                .body(request.getBody())
                .build();

        MemoNoteEntity saved = memoNoteRepository.save(entity);
        return toResponse(saved);
    }

    public List<MemoNoteResponse> listByUser(Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        return memoNoteRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MemoNoteResponse update(Long id, MemoNoteUpdateRequest request) {
        if (request == null || request.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }

        MemoNoteEntity entity = memoNoteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memo note not found"));

        if (!entity.getUserId().equals(request.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "userId mismatch");
        }

        if (request.getMachineKey() != null) {
            entity.setMachineKey(request.getMachineKey());
        }
        if (request.getPartKey() != null) {
            entity.setPartKey(request.getPartKey());
        }
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getBody() != null) {
            entity.setBody(request.getBody());
        }

        MemoNoteEntity saved = memoNoteRepository.save(entity);
        return toResponse(saved);
    }

    public void delete(Long id, Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }

        MemoNoteEntity entity = memoNoteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memo note not found"));

        if (!entity.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "userId mismatch");
        }

        memoNoteRepository.delete(entity);
    }

    private void validateCreate(MemoNoteCreateRequest request) {
        if (request == null
                || request.getUserId() == null
                || request.getMachineKey() == null
                || request.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId, machineKey, title are required");
        }
    }

    private MemoNoteResponse toResponse(MemoNoteEntity entity) {
        return new MemoNoteResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getMachineKey(),
                entity.getPartKey(),
                entity.getTitle(),
                entity.getBody(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
