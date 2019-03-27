package manager.com.common.repository;

import manager.com.common.domain.NhomNoiDung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhomNoiDung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhomNoiDungRepository extends JpaRepository<NhomNoiDung, Long> {

}
