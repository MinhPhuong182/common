package manager.com.common.repository;

import manager.com.common.domain.PhamVi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PhamVi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhamViRepository extends JpaRepository<PhamVi, Long> {

}
