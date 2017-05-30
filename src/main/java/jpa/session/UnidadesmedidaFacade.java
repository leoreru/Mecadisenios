/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Unidadesmedida;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC-HP
 */
@Stateless
public class UnidadesmedidaFacade extends AbstractFacade<Unidadesmedida> {

    @PersistenceContext(unitName = "Mecadisenios-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnidadesmedidaFacade() {
        super(Unidadesmedida.class);
    }
    
}
