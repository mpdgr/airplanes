package mpdgr.planealert.domain.ports.data;

import mpdgr.planealert.domain.model.HexCode;

import java.util.List;
import java.util.Optional;

public interface HexCodeRepository {
    Optional<HexCode> findHexCode(String hexCode);
    List<HexCode> getAll();
    void saveHexCode(HexCode hexCode);
    boolean isListed(String hexCode);
}
