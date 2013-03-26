/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.findByNumeroNota", query = "SELECT n FROM Nota n WHERE n.numeroNota = :numeroNota"),
    @NamedQuery(name = "Nota.findByFechaInicioNota", query = "SELECT n FROM Nota n WHERE n.fechaInicioNota = :fechaInicioNota"),
    @NamedQuery(name = "Nota.findByFechaFinNota", query = "SELECT n FROM Nota n WHERE n.fechaFinNota = :fechaFinNota"),
    @NamedQuery(name = "Nota.findByContenidoNota", query = "SELECT n FROM Nota n WHERE n.contenidoNota = :contenidoNota"),
    @NamedQuery(name = "Nota.findByEstadoNota", query = "SELECT n FROM Nota n WHERE n.estadoNota = :estadoNota")})
public class Nota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO_NOTA", insertable=false, nullable=false, updatable=true)
    private Long numeroNota;
    @Column(name = "FECHA_INICIO_NOTA")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioNota;
    @Column(name = "FECHA_FIN_NOTA")
    @Temporal(TemporalType.DATE)
    private Date fechaFinNota;
    @Size(max = 100)
    @Column(name = "CONTENIDO_NOTA")
    private String contenidoNota;
    @Column(name = "ESTADO_NOTA")
    private Boolean estadoNota;

    public Nota() {
    }

    public Nota(Long numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Long getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Long numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Date getFechaInicioNota() {
        return fechaInicioNota;
    }

    public void setFechaInicioNota(Date fechaInicioNota) {
        this.fechaInicioNota = fechaInicioNota;
    }

    public Date getFechaFinNota() {
        return fechaFinNota;
    }

    public void setFechaFinNota(Date fechaFinNota) {
        this.fechaFinNota = fechaFinNota;
    }

    public String getContenidoNota() {
        return contenidoNota;
    }

    public void setContenidoNota(String contenidoNota) {
        this.contenidoNota = contenidoNota;
    }

    public Boolean getEstadoNota() {
        return estadoNota;
    }

    public void setEstadoNota(Boolean estadoNota) {
        this.estadoNota = estadoNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroNota != null ? numeroNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.numeroNota == null && other.numeroNota != null) || (this.numeroNota != null && !this.numeroNota.equals(other.numeroNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sic.entity.Nota[ numeroNota=" + numeroNota + " ]";
    }
    
}
