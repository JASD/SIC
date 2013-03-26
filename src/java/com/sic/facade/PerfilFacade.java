/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sic.facade;

import com.sic.entity.Perfil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Antonio
 */
@Stateless
public class PerfilFacade extends AbstractFacade<Perfil> {
    @PersistenceContext(unitName = "SIC.PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilFacade() {
        super(Perfil.class);
    }
    
}
