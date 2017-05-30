/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Materiales;
import entities.Materialescategoria;
import entities.Materialestipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author PC-HP
 */
@Stateless
public class MaterialesFacade extends AbstractFacade<Materiales> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MaterialesFacade() {
		super(Materiales.class);
	}

	public List<Materiales> findByCategoria(Materialescategoria MC) {
		short idCat;
		
		if(MC == null)
			idCat = 1;
		else
			idCat = MC.getId();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		/*
		Metamodel m = em.getMetamodel();
		EntityType<Materiales> mmMateriales = m.entity(Materiales.class);
		EntityType<Materialestipo> mmMaterialestipo = m.entity(Materialestipo.class);
		//EntityType<Materialescategoria> Materialescategoria_ = m.entity(Materialescategoria.class);
		CriteriaQuery<Materiales> cq = cb.createQuery(Materiales.class);
		Root<Materiales> materiales = cq.from(Materiales.class);
		Join<Materiales, Materialestipo> materialestipo = materiales.join(mmMateriales.getSet("materialestipo",Materialestipo.class));
		cq.where(materialestipo.get(mmMaterialestipo.getSet("idCategoria")));
		
		/*
		CriteriaQuery<Materialestipo> query = cb.createQuery(Materialestipo.class);
		Root<Materiales> materiales = query.from(Materiales.class);
		Join<Materiales, Materialestipo> materialestipo = materiales.join("materialestipo");
		query.select(materialestipo).where(cb.equal(materiales.get("idMaterialTipo"), 2));
		return new ArrayList<Materiales>();
		*/
		
		CriteriaQuery<Materiales> query = cb.createQuery(Materiales.class);
		Root<Materialestipo> materialestipo = query.from(Materialestipo.class);
		Join<Materialestipo, Materiales> materiales = materialestipo.join("materialesCollection");
		query.select(materiales).where(cb.equal(materialestipo.get("idCategoria"), idCat));		
		return em.createQuery(query).getResultList();
		
/*
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(Materiales.class));
		return getEntityManager().createQuery(cq).getResultList();
*/
	}

}
