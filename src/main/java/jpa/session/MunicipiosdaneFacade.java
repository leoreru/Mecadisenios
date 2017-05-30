/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Departamentosdane;
import entities.Municipiosdane;
import java.util.ArrayList;
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
public class MunicipiosdaneFacade extends AbstractFacade<Municipiosdane> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MunicipiosdaneFacade() {
		super(Municipiosdane.class);
	}

	public List<Municipiosdane> findByDepartamento(Departamentosdane DD) {
		short codDep;

		if (DD == null) {
			//codDep = 5; // Medellín.
			return new ArrayList<>();
		} else {
			codDep = DD.getCodDep();
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Municipiosdane> query = cb.createQuery(Municipiosdane.class);
		Root<Municipiosdane> municipiosdane = query.from(Municipiosdane.class);
		query.select(municipiosdane).where(cb.equal(municipiosdane.get("departamentosdane"), codDep));
		return em.createQuery(query).getResultList();
	}
}
