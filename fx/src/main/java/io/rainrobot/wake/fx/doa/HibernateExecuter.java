package io.rainrobot.wake.fx.doa;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import io.rainrobot.wake.core.util.Log;

public class HibernateExecuter <T>{
	private final SessionFactory sFact;
	
	public HibernateExecuter(SessionFactory sFact) {
		this.sFact = sFact;
	}
	
	public T execute(Function<Session, T> function) {
		Session s = null;
		T  entity = null;
		try {			
			s = sFact.openSession();
			s.beginTransaction();
			entity = function.apply(s);
			s.getTransaction().commit();
		} catch (Exception e){
			Log.err(this, e);
		} finally {
			if(s != null) s.close();
		}
		return entity;
	}
	
	public void execute(Consumer<Session> consumer) {
		Session s = null;
		try {			
			s = sFact.openSession();
			s.beginTransaction();
			consumer.accept(s);
			s.getTransaction().commit();
		} catch (Exception e){
			Log.err(this, e);
		} finally {
			if(s != null) s.close();
		}
	}

}
