/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Facturasrecibidas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC-HP
 */
@Stateless
public class FacturasrecibidasFacade extends AbstractFacade<Facturasrecibidas> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public FacturasrecibidasFacade() {
		super(Facturasrecibidas.class);
	}
	/* La versión mejorada está en AbstractFacade
	// Tocó de esta forma, por que no quería borrar de la otra forma.
	@Override
	public void remove(Facturasrecibidas entity) {
		//getEntityManager().remove(getEntityManager().merge(entity));
		//getEntityManager().flush();
		//http://stackoverflow.com/questions/26349213/hibernate-entitymanager-remove-referenced-entity-not-working
		// Esto hay que hacerlo antes de remover las referencias a esta entidad en
		// la otra entidad o entidades.
		entity = getEntityManager().merge(entity);
		entity.getIdProyecto().getFacturasCollection().remove(entity);
		getEntityManager().remove(entity);
	}
*/
}
