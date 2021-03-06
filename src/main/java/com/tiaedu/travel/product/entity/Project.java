package com.tiaedu.travel.product.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiaedu.travel.common.web.JsonDateTypeConvert;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建项目实体(例一个具体的旅游计划)
 * 实现了序列化接口，实现序列化接口的场景通常有：
 *    1.对象可能需要缓存到磁盘
 *    2.对象可能直接通过网络传输
 * 在类中添加序列化id作用：
 *    当对象的成员发生变化时，可以使用反序列化正常执行。
 *    例如：增加或者缺少了某些属性
 * 对象默认序列化的过程是不安全的
 *    Java提供了一些机制保证安全性，也可以自定义序列化过程保证安全性
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 8238110775705102895L;

    /**项目id*/
    private Integer id;
    /**项目名称*/
    private String name;
    /**项目编号*/
    private String code;
    /**开始日期*/
    //归属于Spring
    //此格式需要与页面传来的数据的格式相同
    //否则会出现400异常
    //Spring默认格式为yyyy/MM/dd，因此若为此格式则不需要写以下注解
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date  beginDate;
    /**结束日期*/
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date  endDate;
    /**有效*/
    private Integer valid=1;
    /**备注*/
    private String note;
    /**创建时间*/
    private Date createdTime;
    /**修改时间*/
    private Date modifiedTime;
    /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //归属于Jackson
    //将此值填充到json串时先按照指定格式进行转换，否则为长整型格式
    //即按照此日期格式传到客户端页面
    //（com.tiaedu.travel.common.web.JsonDateTypeConvert.java）
    @JsonSerialize(using = JsonDateTypeConvert.class)
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonSerialize(using = JsonDateTypeConvert.class)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", valid=" + valid +
                ", note='" + note + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", createdUser='" + createdUser + '\'' +
                ", modifiedUser='" + modifiedUser + '\'' +
                '}';
    }
}
