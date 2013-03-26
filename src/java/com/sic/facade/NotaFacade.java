/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.facade;

import com.sic.entity.Nota;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class NotaFacade extends AbstractFacade<Nota> {
    @PersistenceContext(unitName = "SIC.PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public NotaFacade() {
        super(Nota.class);
    }
    
}
