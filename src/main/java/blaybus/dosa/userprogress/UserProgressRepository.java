package blaybus.dosa.userprogress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {

    Optional<UserProgressEntity> findByUserIdAndMachineKey(Long userId, String machineKey);

    @Transactional
    @Query(value = """
        INSERT INTO user_progress (
            user_id, machine_key, selected_part_key, explode_t,
            cam_pos_x, cam_pos_y, cam_pos_z,
            cam_tar_x, cam_tar_y, cam_tar_z,
            updated_at
        )
        VALUES (
            :userId, :machineKey, :selectedPartKey, :explodeT,
            :camPosX, :camPosY, :camPosZ,
            :camTarX, :camTarY, :camTarZ,
            now()
        )
        ON CONFLICT (user_id, machine_key)
        DO UPDATE SET
            selected_part_key = EXCLUDED.selected_part_key,
            explode_t         = EXCLUDED.explode_t,
            cam_pos_x         = EXCLUDED.cam_pos_x,
            cam_pos_y         = EXCLUDED.cam_pos_y,
            cam_pos_z         = EXCLUDED.cam_pos_z,
            cam_tar_x         = EXCLUDED.cam_tar_x,
            cam_tar_y         = EXCLUDED.cam_tar_y,
            cam_tar_z         = EXCLUDED.cam_tar_z,
            updated_at        = now()
        RETURNING id
        """, nativeQuery = true)
    Long upsertReturningId(
            @Param("userId") Long userId,
            @Param("machineKey") String machineKey,
            @Param("selectedPartKey") String selectedPartKey,
            @Param("explodeT") BigDecimal explodeT,
            @Param("camPosX") BigDecimal camPosX,
            @Param("camPosY") BigDecimal camPosY,
            @Param("camPosZ") BigDecimal camPosZ,
            @Param("camTarX") BigDecimal camTarX,
            @Param("camTarY") BigDecimal camTarY,
            @Param("camTarZ") BigDecimal camTarZ
    );
}
