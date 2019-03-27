package manager.com.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChiTieu.
 */
@Entity
@Table(name = "chi_tieu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChiTieu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chi_tieu_code", nullable = false)
    private String chiTieuCode;

    @Column(name = "name")
    private String name;

    @Column(name = "nhom_chi_tieu_code")
    private String nhomChiTieuCode;

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
    @JsonIgnoreProperties("chitieus")
    private NhomChiTieu nhomchitieu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChiTieuCode() {
        return chiTieuCode;
    }

    public ChiTieu chiTieuCode(String chiTieuCode) {
        this.chiTieuCode = chiTieuCode;
        return this;
    }

    public void setChiTieuCode(String chiTieuCode) {
        this.chiTieuCode = chiTieuCode;
    }

    public String getName() {
        return name;
    }

    public ChiTieu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNhomChiTieuCode() {
        return nhomChiTieuCode;
    }

    public ChiTieu nhomChiTieuCode(String nhomChiTieuCode) {
        this.nhomChiTieuCode = nhomChiTieuCode;
        return this;
    }

    public void setNhomChiTieuCode(String nhomChiTieuCode) {
        this.nhomChiTieuCode = nhomChiTieuCode;
    }

    public String getUserName() {
        return userName;
    }

    public ChiTieu userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ChiTieu createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public ChiTieu updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public ChiTieu status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public ChiTieu program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public NhomChiTieu getNhomchitieu() {
        return nhomchitieu;
    }

    public ChiTieu nhomchitieu(NhomChiTieu nhomChiTieu) {
        this.nhomchitieu = nhomChiTieu;
        return this;
    }

    public void setNhomchitieu(NhomChiTieu nhomChiTieu) {
        this.nhomchitieu = nhomChiTieu;
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
        ChiTieu chiTieu = (ChiTieu) o;
        if (chiTieu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chiTieu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChiTieu{" +
            "id=" + getId() +
            ", chiTieuCode='" + getChiTieuCode() + "'" +
            ", name='" + getName() + "'" +
            ", nhomChiTieuCode='" + getNhomChiTieuCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
