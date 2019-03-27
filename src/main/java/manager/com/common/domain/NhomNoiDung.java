package manager.com.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A NhomNoiDung.
 */
@Entity
@Table(name = "nhom_noi_dung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhomNoiDung implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nhom_noi_dung_code", nullable = false)
    private String nhomNoiDungCode;

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

    @OneToMany(mappedBy = "nhomnoidung")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NoiDung> noiDungs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNhomNoiDungCode() {
        return nhomNoiDungCode;
    }

    public NhomNoiDung nhomNoiDungCode(String nhomNoiDungCode) {
        this.nhomNoiDungCode = nhomNoiDungCode;
        return this;
    }

    public void setNhomNoiDungCode(String nhomNoiDungCode) {
        this.nhomNoiDungCode = nhomNoiDungCode;
    }

    public String getName() {
        return name;
    }

    public NhomNoiDung name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public NhomNoiDung userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public NhomNoiDung createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public NhomNoiDung updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public NhomNoiDung status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public NhomNoiDung program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Set<NoiDung> getNoiDungs() {
        return noiDungs;
    }

    public NhomNoiDung noiDungs(Set<NoiDung> noiDungs) {
        this.noiDungs = noiDungs;
        return this;
    }

    public NhomNoiDung addNoiDung(NoiDung noiDung) {
        this.noiDungs.add(noiDung);
        noiDung.setNhomnoidung(this);
        return this;
    }

    public NhomNoiDung removeNoiDung(NoiDung noiDung) {
        this.noiDungs.remove(noiDung);
        noiDung.setNhomnoidung(null);
        return this;
    }

    public void setNoiDungs(Set<NoiDung> noiDungs) {
        this.noiDungs = noiDungs;
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
        NhomNoiDung nhomNoiDung = (NhomNoiDung) o;
        if (nhomNoiDung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhomNoiDung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhomNoiDung{" +
            "id=" + getId() +
            ", nhomNoiDungCode='" + getNhomNoiDungCode() + "'" +
            ", name='" + getName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
