/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.broadleafcommerce.gwt.admin.client.datasource.order;

import org.broadleafcommerce.gwt.admin.client.datasource.CeilingEntities;
import org.broadleafcommerce.gwt.admin.client.datasource.EntityImplementations;
import org.broadleafcommerce.gwt.admin.client.presenter.order.OrderPresenter;
import org.broadleafcommerce.gwt.client.datasource.DataSourceFactory;
import org.broadleafcommerce.gwt.client.datasource.dynamic.ListGridDataSource;
import org.broadleafcommerce.gwt.client.datasource.dynamic.module.DataSourceModule;
import org.broadleafcommerce.gwt.client.datasource.dynamic.module.MapStructureClientModule;
import org.broadleafcommerce.gwt.client.datasource.relations.ForeignKey;
import org.broadleafcommerce.gwt.client.datasource.relations.PersistencePerspective;
import org.broadleafcommerce.gwt.client.datasource.relations.PersistencePerspectiveItemType;
import org.broadleafcommerce.gwt.client.datasource.relations.SimpleValueMapStructure;
import org.broadleafcommerce.gwt.client.datasource.relations.operations.OperationType;
import org.broadleafcommerce.gwt.client.datasource.relations.operations.OperationTypes;
import org.broadleafcommerce.gwt.client.service.AppServices;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;

/**
 * 
 * @author jfischer
 *
 */
public class OrderItemFeesDataSourceFactory implements DataSourceFactory {
	
	public static final SimpleValueMapStructure MAPSTRUCTURE = new SimpleValueMapStructure(String.class.getName(), "key", "Key", "java.math.BigDecimal", "value", "Value", "additionalFees");
	public static ListGridDataSource dataSource = null;
	
	protected OrderPresenter presenter;
	
	public OrderItemFeesDataSourceFactory(OrderPresenter presenter) {
		this.presenter = presenter;
	}
	
	public void createDataSource(String name, OperationTypes operationTypes, Object[] additionalItems, AsyncCallback<DataSource> cb) {
		if (dataSource == null) {
			operationTypes = new OperationTypes(OperationType.MAPSTRUCTURE, OperationType.MAPSTRUCTURE, OperationType.MAPSTRUCTURE, OperationType.MAPSTRUCTURE, OperationType.MAPSTRUCTURE);
			PersistencePerspective persistencePerspective = new PersistencePerspective(operationTypes, new String[]{}, new ForeignKey[]{});
			persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.FOREIGNKEY, new ForeignKey("id", EntityImplementations.ORDER_ITEM, null));
			persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.MAPSTRUCTURE, MAPSTRUCTURE);
			DataSourceModule[] modules = new DataSourceModule[]{
				new MapStructureClientModule(CeilingEntities.ORDER_ITEM, persistencePerspective, AppServices.DYNAMIC_ENTITY, presenter.getDisplay().getOrderItemFeeDisplay().getGrid())
			};
			dataSource = new ListGridDataSource(name, persistencePerspective, AppServices.DYNAMIC_ENTITY, modules);
			dataSource.buildFields(null, false, cb);
		} else {
			if (cb != null) {
				cb.onSuccess(dataSource);
			}
		}
	}

}