package blaybus.dosa.userprogress;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(
        name = "user_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_user_progress_user_machine",
                columnNames = {"user_id", "machine_key"}
        )
)
public class UserProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="machine_key", nullable=false, length=50)
    private String machineKey;

    @Column(name="selected_part_key", length=100)
    private String selectedPartKey;

    @Column(name="explode_t", precision=19, scale=6)
    private BigDecimal explodeT;

    @Column(name="cam_pos_x", precision=19, scale=6) private BigDecimal camPosX;
    @Column(name="cam_pos_y", precision=19, scale=6) private BigDecimal camPosY;
    @Column(name="cam_pos_z", precision=19, scale=6) private BigDecimal camPosZ;

    @Column(name="cam_tar_x", precision=19, scale=6) private BigDecimal camTarX;
    @Column(name="cam_tar_y", precision=19, scale=6) private BigDecimal camTarY;
    @Column(name="cam_tar_z", precision=19, scale=6) private BigDecimal camTarZ;

    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt;
}
