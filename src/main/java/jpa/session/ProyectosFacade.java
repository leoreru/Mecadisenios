/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import entities.Contactoscliente;
import entities.Facturasgeneradas;
import entities.Personajuridica;
import entities.Personanatural;
import entities.Proyectos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author PC-HP
 */
@Stateless
public class ProyectosFacade extends AbstractFacade<Proyectos> {

    @PersistenceContext(unitName = "Mecadisenios-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectosFacade() {
        super(Proyectos.class);
    }
    
		public List<Object[]> SP_GenerarFactura(Proyectos proy){
			List<Object[]> valoresInforme;
			
			StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("SP_GenerarFactura");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("idProy", Integer.class, ParameterMode.IN);
			storedProcedure.setParameter("idProy", proy.getId());
			// execute SP
			//storedProcedure.execute();
			// get result
			//Double tax = (Double)storedProcedure.getOutputParameterValue("tax");
			valoresInforme = storedProcedure.getResultList(); //Object[]
			
			// El id de las facturas generadas es el idProyecto.
			// Esto es necesario por que las entidades no se actualizan automáticamente
			// despues de ejecutado el SP.
			proy.setFacturasgeneradas((Facturasgeneradas) em.find(Facturasgeneradas.class, proy.getId()));
			return valoresInforme;
			//em.refresh(facGen);
		}
		
		public void actualizarYLlenar(Proyectos proy) {
			actualizarCliente(proy);
			llenarCollectionContactos(proy);
		}
		
		private void actualizarCliente(Proyectos proy) {
			if(proy.getIdClientePerNat() != null)
				proy.setIdClientePerNat((Personanatural) em.find(Personanatural.class, proy.getIdClientePerNat().getId()));			
			if(proy.getIdClientePerJur() != null)
				proy.setIdClientePerJur((Personajuridica) em.find(Personajuridica.class, proy.getIdClientePerJur().getId()));			
		}
		private void llenarCollectionContactos(Proyectos proy) {
			//Collection<Contactoscliente> temp=null;
			CriteriaBuilder cb = em.getCriteriaBuilder();

			// Se supone que uno u otro es null pero no ambos.
			if(proy.getIdClientePerNat()!=null) {
				//temp = proy.getIdClientePerNat().getContactosclienteCollection();
				CriteriaQuery<Contactoscliente> query = cb.createQuery(Contactoscliente.class);
				Root<Personanatural> personanatural = query.from(Personanatural.class);
				Join<Personanatural, Contactoscliente> contactoscliente = personanatural.join("contactosclienteCollection");
				query.select(contactoscliente).where(cb.equal(personanatural.get("id"), proy.getIdClientePerNat().getId()));		
				proy.getIdClientePerNat().setContactosclienteCollection(em.createQuery(query).getResultList());
			}
			if(proy.getIdClientePerJur()!=null) {
				//temp = proy.getIdClientePerJur().getContactosclienteCollection();
				CriteriaQuery<Contactoscliente> query = cb.createQuery(Contactoscliente.class);
				Root<Personajuridica> personajuridica = query.from(Personajuridica.class);
				Join<Personajuridica, Contactoscliente> contactoscliente = personajuridica.join("contactosclienteCollection");
				query.select(contactoscliente).where(cb.equal(personajuridica.get("id"), proy.getIdClientePerJur().getId()));		
				proy.getIdClientePerJur().setContactosclienteCollection(em.createQuery(query).getResultList());
			}
		}
/*		
		public void refrescarFacGen(Facturasgeneradas facGen) {
			facGen = (Facturasgeneradas)em.find(Facturasgeneradas.class, facGen.getIdProyecto());
			em.refresh(facGen);
		}
*/
}
