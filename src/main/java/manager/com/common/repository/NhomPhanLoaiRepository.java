package manager.com.common.repository;

import manager.com.common.domain.NhomPhanLoai;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhomPhanLoai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhomPhanLoaiRepository extends JpaRepository<NhomPhanLoai, Long> {

}
