/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByNumeroCliente", query = "SELECT c FROM Cliente c WHERE c.numeroCliente = :numeroCliente"),
    @NamedQuery(name = "Cliente.findByPrimerNombreCliente", query = "SELECT c FROM Cliente c WHERE c.primerNombreCliente = :primerNombreCliente"),
    @NamedQuery(name = "Cliente.findBySegundoNombreCliente", query = "SELECT c FROM Cliente c WHERE c.segundoNombreCliente = :segundoNombreCliente"),
    @NamedQuery(name = "Cliente.findByPrimerApellidoCliente", query = "SELECT c FROM Cliente c WHERE c.primerApellidoCliente = :primerApellidoCliente"),
    @NamedQuery(name = "Cliente.findBySegundoApellidoCliente", query = "SELECT c FROM Cliente c WHERE c.segundoApellidoCliente = :segundoApellidoCliente"),
    @NamedQuery(name = "Cliente.findByTelefonoCliente", query = "SELECT c FROM Cliente c WHERE c.telefonoCliente = :telefonoCliente"),
    @NamedQuery(name = "Cliente.findByEmailCliente", query = "SELECT c FROM Cliente c WHERE c.emailCliente = :emailCliente"),
    @NamedQuery(name = "Cliente.findByEstadoCliente", query = "SELECT c FROM Cliente c WHERE c.estadoCliente = :estadoCliente")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO_CLIENTE", insertable=false, nullable=false, updatable=true)
    private Long numeroCliente;
    @Size(max = 50)
    @Column(name = "PRIMER_NOMBRE_CLIENTE")
    private String primerNombreCliente;
    @Size(max = 50)
    @Column(name = "SEGUNDO_NOMBRE_CLIENTE")
    private String segundoNombreCliente;
    @Size(max = 50)
    @Column(name = "PRIMER_APELLIDO_CLIENTE")
    private String primerApellidoCliente;
    @Size(max = 50)
    @Column(name = "SEGUNDO_APELLIDO_CLIENTE")
    private String segundoApellidoCliente;
    @Size(max = 9)
    @Column(name = "TELEFONO_CLIENTE")
    private String telefonoCliente;
    @Size(max = 50)
    @Column(name = "EMAIL_CLIENTE")
    private String emailCliente;
    @Column(name = "ESTADO_CLIENTE")
    private Boolean estadoCliente;
    @JoinColumn(name = "ID_ASEGURADORA", referencedColumnName = "ID_ASEGURADORA")
    @ManyToOne(optional = false)
    private Aseguradora idAseguradora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<ServicioCliente> servicioClienteList;

    public Cliente() {
    }

    public Cliente(Long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public Long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(Long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getPrimerNombreCliente() {
        return primerNombreCliente;
    }

    public void setPrimerNombreCliente(String primerNombreCliente) {
        this.primerNombreCliente = primerNombreCliente;
    }

    public String getSegundoNombreCliente() {
        return segundoNombreCliente;
    }

    public void setSegundoNombreCliente(String segundoNombreCliente) {
        this.segundoNombreCliente = segundoNombreCliente;
    }

    public String getPrimerApellidoCliente() {
        return primerApellidoCliente;
    }

    public void setPrimerApellidoCliente(String primerApellidoCliente) {
        this.primerApellidoCliente = primerApellidoCliente;
    }

    public String getSegundoApellidoCliente() {
        return segundoApellidoCliente;
    }

    public void setSegundoApellidoCliente(String segundoApellidoCliente) {
        this.segundoApellidoCliente = segundoApellidoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public Boolean getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(Boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public Aseguradora getIdAseguradora() {
        return idAseguradora;
    }

    public void setIdAseguradora(Aseguradora idAseguradora) {
        this.idAseguradora = idAseguradora;
    }

    @XmlTransient
    public List<ServicioCliente> getServicioClienteList() {
        return servicioClienteList;
    }

    public void setServicioClienteList(List<ServicioCliente> servicioClienteList) {
        this.servicioClienteList = servicioClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroCliente != null ? numeroCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.numeroCliente == null && other.numeroCliente != null) || (this.numeroCliente != null && !this.numeroCliente.equals(other.numeroCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return primerNombreCliente + " " + primerApellidoCliente;
    }
    
}
