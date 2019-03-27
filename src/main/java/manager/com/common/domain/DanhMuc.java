package manager.com.common.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DanhMuc.
 */
@Entity
@Table(name = "danh_muc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DanhMuc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "danh_muc_code", nullable = false)
    private String danhMucCode;

    @Column(name = "name")
    private String name;

    @Column(name = "nhom_danh_muc_code")
    private String nhomDanhMucCode;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDanhMucCode() {
        return danhMucCode;
    }

    public DanhMuc danhMucCode(String danhMucCode) {
        this.danhMucCode = danhMucCode;
        return this;
    }

    public void setDanhMucCode(String danhMucCode) {
        this.danhMucCode = danhMucCode;
    }

    public String getName() {
        return name;
    }

    public DanhMuc name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNhomDanhMucCode() {
        return nhomDanhMucCode;
    }

    public DanhMuc nhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
        return this;
    }

    public void setNhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
    }

    public String getUserName() {
        return userName;
    }

    public DanhMuc userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public DanhMuc createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public DanhMuc updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public DanhMuc status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public DanhMuc program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
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
        DanhMuc danhMuc = (DanhMuc) o;
        if (danhMuc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), danhMuc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
            "id=" + getId() +
            ", danhMucCode='" + getDanhMucCode() + "'" +
            ", name='" + getName() + "'" +
            ", nhomDanhMucCode='" + getNhomDanhMucCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
