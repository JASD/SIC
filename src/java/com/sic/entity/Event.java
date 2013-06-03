/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Antonio
 */
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "date", "userName", "entityName", "description"})
public class Event {

    private Date date;
    private String title;
    private String userName;
    private String description;
    private String entityName;

    public Event() {
    }

    public Event(Date date, String title, String userName, String description, String entityName) {
        this.date = date;
        this.title = title;
        this.userName = userName;
        this.description = description;
        this.entityName = entityName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
