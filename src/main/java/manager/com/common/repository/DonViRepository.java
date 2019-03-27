package manager.com.common.repository;

import manager.com.common.domain.DonVi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DonVi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonViRepository extends JpaRepository<DonVi, Long> {

}
