/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "aseguradora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aseguradora.findAll", query = "SELECT a FROM Aseguradora a"),
    @NamedQuery(name = "Aseguradora.findByIdAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.idAseguradora = :idAseguradora"),
    @NamedQuery(name = "Aseguradora.findByNombreAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.nombreAseguradora = :nombreAseguradora"),
    @NamedQuery(name = "Aseguradora.findByTelefonoAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.telefonoAseguradora = :telefonoAseguradora"),
    @NamedQuery(name = "Aseguradora.findByEmailAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.emailAseguradora = :emailAseguradora"),
    @NamedQuery(name = "Aseguradora.findByEstadoAseguradora", query = "SELECT a FROM Aseguradora a WHERE a.estadoAseguradora = :estadoAseguradora")})
public class Aseguradora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_ASEGURADORA")
    private String idAseguradora;
    @Size(max = 100)
    @Column(name = "NOMBRE_ASEGURADORA")
    private String nombreAseguradora;
    @Size(max = 9)
    @Column(name = "TELEFONO_ASEGURADORA")
    private String telefonoAseguradora;
    @Size(max = 50)
    @Column(name = "EMAIL_ASEGURADORA")
    private String emailAseguradora;
    @Column(name = "ESTADO_ASEGURADORA")
    private Boolean estadoAseguradora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAseguradora")
    private List<Cliente> clienteList;

    public Aseguradora() {
    }

    public Aseguradora(String idAseguradora) {
        this.idAseguradora = idAseguradora;
    }

    public String getIdAseguradora() {
        return idAseguradora;
    }

    public void setIdAseguradora(String idAseguradora) {
        this.idAseguradora = idAseguradora;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public String getTelefonoAseguradora() {
        return telefonoAseguradora;
    }

    public void setTelefonoAseguradora(String telefonoAseguradora) {
        this.telefonoAseguradora = telefonoAseguradora;
    }

    public String getEmailAseguradora() {
        return emailAseguradora;
    }

    public void setEmailAseguradora(String emailAseguradora) {
        this.emailAseguradora = emailAseguradora;
    }

    public Boolean getEstadoAseguradora() {
        return estadoAseguradora;
    }

    public void setEstadoAseguradora(Boolean estadoAseguradora) {
        this.estadoAseguradora = estadoAseguradora;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAseguradora != null ? idAseguradora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aseguradora)) {
            return false;
        }
        Aseguradora other = (Aseguradora) object;
        if ((this.idAseguradora == null && other.idAseguradora != null) || (this.idAseguradora != null && !this.idAseguradora.equals(other.idAseguradora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreAseguradora;
    }
    
}
