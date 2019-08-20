package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.IRememberMeDoa;
import io.rainrobot.wake.fx.doa.entity.RMEntity;
@Deprecated
public class FxHibernateRMDoa extends FxSingleValueDoa<RMEntity, Boolean> implements IRememberMeDoa{

	public FxHibernateRMDoa(HibernateExecuter executer) {
		super(executer);
	}

	@Override
	public boolean isOn() {
		return getValue();
	}

	@Override
	public void set(boolean flag) {
		setValue(flag);
	}

	@Override
	protected RMEntity newEntityInstance(Boolean val, int id) {
		return new RMEntity(val, id);
	}

	@Override
	protected Class<RMEntity> getEntityClass() {
		return RMEntity.class;
	}
}

