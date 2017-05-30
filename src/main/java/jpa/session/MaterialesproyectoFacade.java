/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Materialesproyecto;
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
public class MaterialesproyectoFacade extends AbstractFacade<Materialesproyecto> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MaterialesproyectoFacade() {
		super(Materialesproyecto.class);
	}

	public List<Materialesproyecto> findByProyecto(Proyectos proy) {

		if (proy == null) {
			String sql = "SELECT t FROM Proyectos t";
			Query query1 = em.createQuery(sql);
			query1.setFirstResult(0);
			query1.setMaxResults(1);
			List<Proyectos> result = query1.getResultList();
			proy = result.get(0);
		}
	
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Materialesproyecto> query = cb.createQuery(Materialesproyecto.class);
		Root<Materialesproyecto> materialesproyecto = query.from(Materialesproyecto.class);
		query.select(materialesproyecto).where(cb.equal(materialesproyecto.get("proyectos"), proy));
		return em.createQuery(query).getResultList();

	}
}
