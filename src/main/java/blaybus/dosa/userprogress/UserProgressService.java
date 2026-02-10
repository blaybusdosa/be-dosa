package blaybus.dosa.userprogress;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserProgressService {

    private static final Set<String> ALLOWED = Set.of("robotarm", "drone", "v4-engine", "suspension");

    private final UserProgressRepository repo;

    @Transactional
    public UserProgressRes saveOrUpdate(Long userId, String machineKey, UserProgressSaveReq req) {
        validateMachineKey(machineKey);

        Long id = repo.upsertReturningId(
                userId,
                machineKey,
                req.selectedPartKey(),
                req.explodeT(),
                req.camPosX(), req.camPosY(), req.camPosZ(),
                req.camTarX(), req.camTarY(), req.camTarZ()
        );

        UserProgressEntity up = repo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Upsert succeeded but row not found: id=" + id));

        return toRes(up);
    }

    @Transactional(readOnly = true)
    public UserProgressRes load(Long userId, String machineKey) {
        validateMachineKey(machineKey);

        return repo.findByUserIdAndMachineKey(userId, machineKey)
                .map(this::toRes)
                .orElse(null);
    }

    private void validateMachineKey(String machineKey) {
        if (machineKey == null || !ALLOWED.contains(machineKey)) {
            throw new IllegalArgumentException("Invalid machineKey: " + machineKey);
        }
    }

    private UserProgressRes toRes(UserProgressEntity up) {
        return new UserProgressRes(
                up.getMachineKey(),
                up.getSelectedPartKey(),
                up.getExplodeT(),
                up.getCamPosX(), up.getCamPosY(), up.getCamPosZ(),
                up.getCamTarX(), up.getCamTarY(), up.getCamTarZ()
        );
    }
}
