/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

/**
 *
 * @author PC-HP
 */
public abstract class AbstractFacade<T> {

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		//getEntityManager().remove(getEntityManager().merge(entity));

		//http://stackoverflow.com/questions/26349213/hibernate-entitymanager-remove-referenced-entity-not-working
		// Esto hay que hacerlo antes de remover las referencias a esta entidad en
		// la otra entidad o entidades:
		entity = getEntityManager().merge(entity);

		Map<String, Object> gettersEntity;
		Map<String, Object> gettersMember;
		gettersEntity = beanProperties(entity);
		for (Map.Entry<String, Object> entry : gettersEntity.entrySet()) {
//			if (sessionFactory.getClassMetadata(HibernateProxyHelper.getClassWithoutInitializingProxy(entry.getValue())) != null) {
			boolean isEntity = true;
			/*
			// Tocó asi:
			try {
				getEntityManager().contains(entry.getValue());
			} catch (Exception e) {
				// Al parecer aqui se cancela la transacción y aparece un error('transacción
			  // requerida') al tratar de remover el registro, por eso no lo voy a usar. 
				isEntity = false;
			}
			 */
			if (isEntity) {
				//System.out.println(entry.getKey() + "/" + entry.getValue());
				Object entityM;
				try {
					entityM = ((Method) entry.getValue()).invoke(entity);
					gettersMember = beanProperties(entityM);
					for (Map.Entry<String, Object> entryM : gettersMember.entrySet()) {
						try {
							Object entityMM = ((Method) entryM.getValue()).invoke(entityM);
							if (entityMM instanceof Collection<?>) {
								Collection<?> coll = (Collection<?>) entityMM;

								if (!coll.isEmpty()) {
									if (coll.iterator().next().getClass() == entity.getClass()) {
										coll.remove(entity);
									}
								}
							}
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
				}
			}
		}
		// Así se removian las referencias antes(se debía hacer en cada fachada no aquí):
		//entity.getIdBanco().getCuentasCollection().remove(entity);
		//entity.getIdTipo().getCuentasCollection().remove(entity);
		getEntityManager().remove(entity);
	}

	//----------------------------------------------------------------------------
	public static Map<String, Object> beanProperties(Object bean) {
		if(bean == null)
			return Collections.emptyMap();
		try {
			return Arrays.asList(
							Introspector.getBeanInfo(bean.getClass(), Object.class)
											.getPropertyDescriptors()
			)
							.stream()
							// filter out properties with setters only
							.filter(pd -> Objects.nonNull(pd.getReadMethod()))
							.collect(Collectors.toMap(
											// bean property name
											PropertyDescriptor::getName,
											pd -> { // invoke method to get value
													return pd.getReadMethod();
											}));
//		} catch (IntrospectionException e) {
		} catch (Exception e) {
			// and this, too
			return Collections.emptyMap();
		}
	}

	//----------------------------------------------------------------------------
	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
