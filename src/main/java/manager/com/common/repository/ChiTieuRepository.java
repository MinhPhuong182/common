package manager.com.common.repository;

import manager.com.common.domain.ChiTieu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChiTieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTieuRepository extends JpaRepository<ChiTieu, Long> {

}
