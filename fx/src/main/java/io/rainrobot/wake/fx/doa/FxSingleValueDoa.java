package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.fx.doa.entity.SingleValueEntity;
import org.hibernate.SessionFactory;
@Deprecated
public abstract class FxSingleValueDoa <Entity extends SingleValueEntity<Value>, Value> {

	private SessionFactory sFact;
	private HibernateExecuter<Entity> executer;
	private final static int ID = 0;

	public FxSingleValueDoa(HibernateExecuter executer) {
		this.executer = executer;
	}
	

	protected Value getValue() {
		Entity entity = executer.execute((session) -> {
			return session.get(getEntityClass(), ID);
		});
		return entity.getVal();
	}

	protected abstract Class<Entity> getEntityClass();

	protected void setValue(Value val) {
		executer.execute((session) -> {
			session.saveOrUpdate(newEntityInstance(val, ID));
		});
	}

	protected abstract Entity newEntityInstance(Value val, int id);

}
