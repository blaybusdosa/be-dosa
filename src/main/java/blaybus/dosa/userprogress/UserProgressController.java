package blaybus.dosa.userprogress;

import blaybus.dosa.userprogress.UserProgressRes;
import blaybus.dosa.userprogress.UserProgressSaveReq;
import blaybus.dosa.userprogress.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("progress")
public class UserProgressController {

    private final UserProgressService service;

    @PutMapping("/{machineKey}")
    public ResponseEntity<UserProgressRes> save(
            @PathVariable String machineKey,
            @RequestParam Long userId,
            @RequestBody UserProgressSaveReq req
    ) {
        return ResponseEntity.ok(service.saveOrUpdate(userId, machineKey, req));
    }

    @GetMapping("/{machineKey}")
    public ResponseEntity<UserProgressRes> load(
            @PathVariable String machineKey,
            @RequestParam Long userId
    ) {
        UserProgressRes res = service.load(userId, machineKey);
        return (res == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(res);
    }
}
