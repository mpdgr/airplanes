package mpdgr.planealert.data.aircraft;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftJpaRepository extends JpaRepository<AircraftEntity, String> {
}
