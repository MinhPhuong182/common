package manager.com.common.repository;

import manager.com.common.domain.DoiTuong;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DoiTuong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoiTuongRepository extends JpaRepository<DoiTuong, Long> {

}
