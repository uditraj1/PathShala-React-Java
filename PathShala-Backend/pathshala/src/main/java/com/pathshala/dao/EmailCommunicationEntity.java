package com.pathshala.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity(name = "emailCommunication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCommunicationEntity extends MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int parentId = -1;
    private String receiverEmail;
    private String senderEmail;
    private String subject;
    private String content;
    private String attachment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailCommunicationEntity that = (EmailCommunicationEntity) o;
        return id == that.id && parentId == that.parentId && Objects.equals(receiverEmail, that.receiverEmail) && Objects.equals(senderEmail, that.senderEmail) && Objects.equals(subject, that.subject) && Objects.equals(content, that.content) && Objects.equals(attachment, that.attachment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, receiverEmail, senderEmail, subject, content, attachment);
    }

    @Override
    public String toString() {
        return "EmailCommunicationEntity{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}
