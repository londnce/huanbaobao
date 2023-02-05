package com.wsf.huanbaobao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 实体类基类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date  createdTime;
    private String modifiedUser;
    private Date modifiedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getCreatedUser(), that.getCreatedUser()) &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getModifiedUser(), that.getModifiedUser()) &&
                Objects.equals(getModifiedTime(), that.getModifiedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedUser(), getCreatedTime(), getModifiedUser(), getModifiedTime());
    }

}
