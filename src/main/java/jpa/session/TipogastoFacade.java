/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Tipogasto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC-HP
 */
@Stateless
public class TipogastoFacade extends AbstractFacade<Tipogasto> {

    @PersistenceContext(unitName = "Mecadisenios-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipogastoFacade() {
        super(Tipogasto.class);
    }
    
}
