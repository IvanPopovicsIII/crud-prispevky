package com.example.crudPrispevky.config;

import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UseExistingIdOtherwiseGenerateUsingIdentity implements IdentifierGenerator  {


	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Object id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        String sqlQuery = "SELECT MAX(id) FROM postEntity";
        Optional<Integer> query = session.createQuery(sqlQuery, Integer.class).getResultStream().findFirst();
        return id != null ? id : query.get() +  1;
    }
	
}
