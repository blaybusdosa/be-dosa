package blaybus.dosa.memonote.controller;

import blaybus.dosa.memonote.dto.MemoNoteCreateRequest;
import blaybus.dosa.memonote.dto.MemoNoteResponse;
import blaybus.dosa.memonote.dto.MemoNoteUpdateRequest;
import blaybus.dosa.memonote.service.MemoNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memonote")
@Validated
@Tag(name = "MemoNote", description = "사용자 메모 API")
public class MemoNoteController {

    private final MemoNoteService memoNoteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "메모 생성")
    public MemoNoteResponse create(@RequestBody @Valid MemoNoteCreateRequest request) {
        return memoNoteService.create(request);
    }

    @GetMapping
    @Operation(summary = "사용자 메모 전체 조회")
    public List<MemoNoteResponse> list(@RequestParam @NotNull @Positive Long userId) {
        return memoNoteService.listByUser(userId);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "메모 수정")
    public MemoNoteResponse update(@PathVariable Long id, @RequestBody MemoNoteUpdateRequest request) {
        return memoNoteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "메모 삭제")
    public void delete(@PathVariable Long id, @RequestParam Long userId) {
        memoNoteService.delete(id, userId);
    }
}
