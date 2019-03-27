package manager.com.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A NoiDung.
 */
@Entity
@Table(name = "noi_dung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NoiDung implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noi_dung_code")
    private String noiDungCode;

    @Column(name = "chi_tieu_code")
    private String chiTieuCode;

    @Column(name = "nhom_danh_muc_code")
    private String nhomDanhMucCode;

    @Column(name = "nhom_noi_dung_code")
    private String nhomNoiDungCode;

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

    @ManyToOne
    @JsonIgnoreProperties("noiDungs")
    private NhomNoiDung nhomnoidung;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoiDungCode() {
        return noiDungCode;
    }

    public NoiDung noiDungCode(String noiDungCode) {
        this.noiDungCode = noiDungCode;
        return this;
    }

    public void setNoiDungCode(String noiDungCode) {
        this.noiDungCode = noiDungCode;
    }

    public String getChiTieuCode() {
        return chiTieuCode;
    }

    public NoiDung chiTieuCode(String chiTieuCode) {
        this.chiTieuCode = chiTieuCode;
        return this;
    }

    public void setChiTieuCode(String chiTieuCode) {
        this.chiTieuCode = chiTieuCode;
    }

    public String getNhomDanhMucCode() {
        return nhomDanhMucCode;
    }

    public NoiDung nhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
        return this;
    }

    public void setNhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
    }

    public String getNhomNoiDungCode() {
        return nhomNoiDungCode;
    }

    public NoiDung nhomNoiDungCode(String nhomNoiDungCode) {
        this.nhomNoiDungCode = nhomNoiDungCode;
        return this;
    }

    public void setNhomNoiDungCode(String nhomNoiDungCode) {
        this.nhomNoiDungCode = nhomNoiDungCode;
    }

    public String getUserName() {
        return userName;
    }

    public NoiDung userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public NoiDung createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public NoiDung updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public NoiDung status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public NoiDung program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public NhomNoiDung getNhomnoidung() {
        return nhomnoidung;
    }

    public NoiDung nhomnoidung(NhomNoiDung nhomNoiDung) {
        this.nhomnoidung = nhomNoiDung;
        return this;
    }

    public void setNhomnoidung(NhomNoiDung nhomNoiDung) {
        this.nhomnoidung = nhomNoiDung;
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
        NoiDung noiDung = (NoiDung) o;
        if (noiDung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noiDung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoiDung{" +
            "id=" + getId() +
            ", noiDungCode='" + getNoiDungCode() + "'" +
            ", chiTieuCode='" + getChiTieuCode() + "'" +
            ", nhomDanhMucCode='" + getNhomDanhMucCode() + "'" +
            ", nhomNoiDungCode='" + getNhomNoiDungCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
