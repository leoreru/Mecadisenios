/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Materialescategoria;
import entities.Materialestipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author PC-HP
 */
@Stateless
public class MaterialestipoFacade extends AbstractFacade<Materialestipo> {

    @PersistenceContext(unitName = "Mecadisenios-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialestipoFacade() {
        super(Materialestipo.class);
    }
		
    public List<Materialestipo> findByCategoria(Materialescategoria MC) {
		short idCat;
		
		if(MC == null)
			idCat = 1;
		else
			idCat = MC.getId();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Materialestipo> query = cb.createQuery(Materialestipo.class);
		Root<Materialestipo> materialestipo = query.from(Materialestipo.class);
		query.select(materialestipo).where(cb.equal(materialestipo.get("idCategoria"), idCat));		
		return em.createQuery(query).getResultList();
	}
}
