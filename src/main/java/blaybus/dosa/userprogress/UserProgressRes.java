package blaybus.dosa.userprogress;

import java.math.BigDecimal;

public record UserProgressRes(
        String machineKey,
        String selectedPartKey,
        BigDecimal explodeT,
        BigDecimal camPosX,
        BigDecimal camPosY,
        BigDecimal camPosZ,
        BigDecimal camTarX,
        BigDecimal camTarY,
        BigDecimal camTarZ
) {}