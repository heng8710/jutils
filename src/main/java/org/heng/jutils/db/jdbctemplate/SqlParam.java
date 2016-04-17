package org.heng.jutils.db.jdbctemplate;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableList;

/**
 * 取自:{@link org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource}和{@link org.springframework.jdbc.core.namedparam.MapSqlParameterSource}
 * 的实现 ，作了一个合并的处理。
 * 使得在bean封装之上，还能再添加新参数（封装到map之中）。<br/>
 * 
 * map在bean的上一层，先后顺序是先map再bean<br/>
 * 
 * 事实上这只是支持一个扁平的结构（一层） <br/>
 * 
 * 来自spring： 3.2.16.RELEASE（后续版本会不会变就不知道了）<br/>
 * 
 */
public final class SqlParam extends AbstractSqlParameterSource{

	
	//来自bean
	private final BeanWrapper beanWrapper;
	private String[] propertyNames;

	
	
	//来自map
	private final Map<String, Object> values = new HashMap<String, Object>();

	
	/**
	 * Create a new BeanPropertySqlParameterSource for the given bean.
	 * @param object the bean instance to wrap
	 */
	public SqlParam(final Object object) {
		this.beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
	}



	public boolean hasValue(String paramName) {
		//合并
		//map的优先
		return this.values.containsKey(paramName) || this.beanWrapper.isReadableProperty(paramName);
	}

	public Object getValue(String paramName) throws IllegalArgumentException {
		//合并
		//map的优先
		if (hasValue(paramName)) {
//			throw new IllegalArgumentException("No value registered for key '" + paramName + "'");
			return this.values.get(paramName);
		}
		
		try {
			return this.beanWrapper.getPropertyValue(paramName);
		}
		catch (NotReadablePropertyException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

//	/**
//	 * Provide access to the property names of the wrapped bean.
//	 * Uses support provided in the {@link PropertyAccessor} interface.
//	 * @return an array containing all the known property names
//	 */
	public List<String> allKeys() {
		if (this.propertyNames == null) {
			List<String> names = new ArrayList<String>();
			PropertyDescriptor[] props = this.beanWrapper.getPropertyDescriptors();
			for (PropertyDescriptor pd : props) {
				if (this.beanWrapper.isReadableProperty(pd.getName())) {
					names.add(pd.getName());
				}
			}
			this.propertyNames = names.toArray(new String[names.size()]);
		}
//		return this.propertyNames;
		return ImmutableList.<String>builder().addAll(values.keySet()).add(this.propertyNames).build();
		
	}

	/**
	 * map中没有实现，直接引用的abstract，bean中是下面的实现，直接引用bean的实现<br/>
	 * 下面的逻辑中，也是先从abstract取，再从bean中计算的，而map在每次add的时候，都会调用register，所以顺序应该是符合的。
	 *
	 */
	@Override
	public int getSqlType(String paramName) {
		//大部分情况下，如果只是调用add(name, val)的话，还是不会注册类型的，帮下面返回的还是未知（abstract类里的sqlTypes还是空map）
		int sqlType = super.getSqlType(paramName);
		if (sqlType != TYPE_UNKNOWN) {
			return sqlType;
		}
		Class propType = this.beanWrapper.getPropertyType(paramName);
		//可能为null
		if(propType != null){
			return StatementCreatorUtils.javaTypeToSqlParameterType(propType);
		}
		//其实就是：TYPE_UNKNOWN
		return sqlType;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//map中的实现
	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public SqlParam addValue(String paramName, Object value) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName, value);
		if (value instanceof SqlParameterValue) {
			registerSqlType(paramName, ((SqlParameterValue) value).getSqlType());
		}
		return this;
	}

	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @param sqlType the SQL type of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public SqlParam addValue(String paramName, Object value, int sqlType) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName, value);
		registerSqlType(paramName, sqlType);
		return this;
	}

	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @param sqlType the SQL type of the parameter
	 * @param typeName the type name of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public SqlParam addValue(String paramName, Object value, int sqlType, String typeName) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName, value);
		registerSqlType(paramName, sqlType);
		registerTypeName(paramName, typeName);
		return this;
	}

	/**
	 * Add a Map of parameters to this parameter source.
	 * @param values a Map holding existing parameter values (can be {@code null})
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public SqlParam addValues(Map<String, ?> values) {
		if (values != null) {
			for (Map.Entry<String, ?> entry : values.entrySet()) {
				this.values.put(entry.getKey(), entry.getValue());
				if (entry.getValue() instanceof SqlParameterValue) {
					SqlParameterValue value = (SqlParameterValue) entry.getValue();
					registerSqlType(entry.getKey(), value.getSqlType());
				}
			}
		}
		return this;
	}

}
