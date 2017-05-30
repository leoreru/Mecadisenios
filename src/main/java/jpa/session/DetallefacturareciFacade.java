/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Detallefacturareci;
import entities.Facturasrecibidas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author PC-HP
 */
@Stateless
public class DetallefacturareciFacade extends AbstractFacade<Detallefacturareci> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public DetallefacturareciFacade() {
		super(Detallefacturareci.class);
	}

	public List<Detallefacturareci> findByFacturareci(Facturasrecibidas FR) {
		if (FR == null) {
			// Esto ocurre cuando se carga la página por primeta vez.
			String sql = "SELECT t FROM Facturasrecibidas t";
			Query query1 = em.createQuery(sql);
			query1.setFirstResult(0);
			query1.setMaxResults(1);
			List<Facturasrecibidas> result = query1.getResultList();
			if(!result.isEmpty())
				FR = result.get(0);
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Detallefacturareci> query = cb.createQuery(Detallefacturareci.class);
		Root<Detallefacturareci> detallefacturareci = query.from(Detallefacturareci.class);
		query.select(detallefacturareci).where(cb.equal(detallefacturareci.get("facturasrecibidas"), FR));
		return em.createQuery(query).getResultList();

	}	
}
