/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.facade;

import com.sic.entity.Servicio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class ServicioFacade extends AbstractFacade<Servicio> {
    @PersistenceContext(unitName = "SIC.PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServicioFacade() {
        super(Servicio.class);
    }
    
}
