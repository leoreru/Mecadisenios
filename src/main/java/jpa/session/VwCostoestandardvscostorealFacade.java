/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.VwCostoestandardvscostoreal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author PC-HP
 */
@Stateless
public class VwCostoestandardvscostorealFacade extends AbstractFacade<VwCostoestandardvscostoreal> {

	@PersistenceContext(unitName = "Mecadisenios-warPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public VwCostoestandardvscostorealFacade() {
		super(VwCostoestandardvscostoreal.class);
	}
	
	public void SP_PARAMSvwCostEstVsCostReal(boolean todosProy) {
		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("SP_PARAMSvwCostEstVsCostReal");
		storedProcedure.registerStoredProcedureParameter("todosProy", Integer.class, ParameterMode.IN);
		storedProcedure.setParameter("todosProy", (todosProy ? 1:0));// No funcionó con la variable boolean todosProy
		storedProcedure.execute();
	}
}
