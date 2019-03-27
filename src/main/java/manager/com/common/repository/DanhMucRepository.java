package manager.com.common.repository;

import manager.com.common.domain.DanhMuc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DanhMuc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {

}
