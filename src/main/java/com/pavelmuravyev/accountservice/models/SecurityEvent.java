package com.pavelmuravyev.accountservice.models;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.dbutils.SecurityActionEnumStringConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SECURITY_EVENTS")
public class SecurityEvent {

    @Column(name = "SECURITY_EVENT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "SECURITY_EVENT_DATE")
    private LocalDateTime date;

    @Column(name = "SECURITY_EVENT_ACTION")
    @Convert(converter = SecurityActionEnumStringConverter.class)
    private SecurityAction action;

    @Column(name = "SECURITY_EVENT_SUBJECT")
    private String subject;

    @Column(name = "SECURITY_EVENT_OBJECT")
    private String object;

    @Column(name = "SECURITY_EVENT_PATH")
    private String path;

    public SecurityEvent() {
    }

    public SecurityEvent(Long id,
                         LocalDateTime date,
                         SecurityAction action,
                         String subject,
                         String object,
                         String path) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SecurityAction getAction() {
        return action;
    }

    public void setAction(SecurityAction action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SecurityEvent{" +
                "id=" + id +
                ", date=" + date +
                ", action=" + action +
                ", subject='" + subject + '\'' +
                ", object='" + object + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityEvent that = (SecurityEvent) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(date, that.date) &&
               action == that.action &&
               Objects.equals(subject, that.subject) &&
               Objects.equals(object, that.object) &&
               Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, action, subject, object, path);
    }
}
