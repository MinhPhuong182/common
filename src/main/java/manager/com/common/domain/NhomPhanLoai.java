package manager.com.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NhomPhanLoai.
 */
@Entity
@Table(name = "nhom_phan_loai")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhomPhanLoai implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nhom_phan_loai_code", nullable = false)
    private String nhomPhanLoaiCode;

    @Column(name = "don_ci_code")
    private String donCiCode;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "program")
    private String program;

    @OneToMany(mappedBy = "nhomphanloai")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoiTuong> doituongs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("nhomphanloais")
    private DonVi donvi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNhomPhanLoaiCode() {
        return nhomPhanLoaiCode;
    }

    public NhomPhanLoai nhomPhanLoaiCode(String nhomPhanLoaiCode) {
        this.nhomPhanLoaiCode = nhomPhanLoaiCode;
        return this;
    }

    public void setNhomPhanLoaiCode(String nhomPhanLoaiCode) {
        this.nhomPhanLoaiCode = nhomPhanLoaiCode;
    }

    public String getDonCiCode() {
        return donCiCode;
    }

    public NhomPhanLoai donCiCode(String donCiCode) {
        this.donCiCode = donCiCode;
        return this;
    }

    public void setDonCiCode(String donCiCode) {
        this.donCiCode = donCiCode;
    }

    public String getName() {
        return name;
    }

    public NhomPhanLoai name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public NhomPhanLoai userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public NhomPhanLoai createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public NhomPhanLoai updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public NhomPhanLoai status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public NhomPhanLoai program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Set<DoiTuong> getDoituongs() {
        return doituongs;
    }

    public NhomPhanLoai doituongs(Set<DoiTuong> doiTuongs) {
        this.doituongs = doiTuongs;
        return this;
    }

    public NhomPhanLoai addDoituong(DoiTuong doiTuong) {
        this.doituongs.add(doiTuong);
        doiTuong.setNhomphanloai(this);
        return this;
    }

    public NhomPhanLoai removeDoituong(DoiTuong doiTuong) {
        this.doituongs.remove(doiTuong);
        doiTuong.setNhomphanloai(null);
        return this;
    }

    public void setDoituongs(Set<DoiTuong> doiTuongs) {
        this.doituongs = doiTuongs;
    }

    public DonVi getDonvi() {
        return donvi;
    }

    public NhomPhanLoai donvi(DonVi donVi) {
        this.donvi = donVi;
        return this;
    }

    public void setDonvi(DonVi donVi) {
        this.donvi = donVi;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NhomPhanLoai nhomPhanLoai = (NhomPhanLoai) o;
        if (nhomPhanLoai.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhomPhanLoai.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhomPhanLoai{" +
            "id=" + getId() +
            ", nhomPhanLoaiCode='" + getNhomPhanLoaiCode() + "'" +
            ", donCiCode='" + getDonCiCode() + "'" +
            ", name='" + getName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
