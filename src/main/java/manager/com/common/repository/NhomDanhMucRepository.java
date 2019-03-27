package manager.com.common.repository;

import manager.com.common.domain.NhomDanhMuc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhomDanhMuc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhomDanhMucRepository extends JpaRepository<NhomDanhMuc, Long> {

}
