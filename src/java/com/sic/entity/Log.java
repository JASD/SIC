/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Log {

    @XmlElement(name = "event")
    private List<Event> events;

    public Log() {
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    
}
