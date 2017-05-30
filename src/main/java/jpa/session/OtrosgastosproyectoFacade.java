/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Otrosgastosproyecto;
import entities.Proyectos;
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
public class OtrosgastosproyectoFacade extends AbstractFacade<Otrosgastosproyecto> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public OtrosgastosproyectoFacade() {
		super(Otrosgastosproyecto.class);
	}

	public List<Otrosgastosproyecto> findByProyecto(Proyectos proy) {

		if (proy == null) {
			// Esto ocurre cuando se carga la página por primeta vez.
			String sql = "SELECT t FROM Proyectos t";
			Query query1 = em.createQuery(sql);
			query1.setFirstResult(0);
			query1.setMaxResults(1);
			List<Proyectos> result = query1.getResultList();
			if(!result.isEmpty())
				proy = result.get(0);
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Otrosgastosproyecto> query = cb.createQuery(Otrosgastosproyecto.class);
		Root<Otrosgastosproyecto> otrosgastosproyecto = query.from(Otrosgastosproyecto.class);
		query.select(otrosgastosproyecto).where(cb.equal(otrosgastosproyecto.get("proyectos"), proy));
		return em.createQuery(query).getResultList();

	}
}
