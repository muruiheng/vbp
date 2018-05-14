package com.sgai.vbp.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.sgai.vbp.dao.annotation.Column;
import com.sgai.vbp.dao.annotation.Entity;
import com.sgai.vbp.dao.annotation.PrimaryKey;
import com.sgai.vbp.dao.exception.DAOException;
import com.sgai.vbp.util.AssertUtil;
import com.sgai.vbp.util.exception.CheckException;
import com.sgai.vbp.util.page.PageBean;

/**
 * 
 * 新架构共用组件和spring结合
 */
public abstract class DAOSupport<T> {
	private static Logger LOGGER = LoggerFactory.getLogger(DAOSupport.class);

	/**
	 * 构造函数
	 * 
	 */
	public DAOSupport() {
	}

	/**
	 * 获取执行sql的JDBCTemplate
	 * 
	 * @return
	 */
	abstract protected VbpJdbcTemplate getJdbcTemplate();

	/**
	 * 新增方法
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int create(final T t) {
		int result = 0;
		try {
			String sql = this.getCreatePreSql();
			result = this.getJdbcTemplate().update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pstmt) throws SQLException {
					prepareCreate(pstmt, t);
				}
			});
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}

		return result;
	}

	/**
	 * 批处理-新增方法
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int createBatch(final List<T> list) {
		try {
			String sql = this.getCreatePreSql();
			int[] result = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pstmt, int i) throws SQLException {
					prepareCreate(pstmt, list.get(i));
				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
			return result.length;
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 更新
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int update(final T t) {
		try {
			this.verify(t);
			String sql = this.getUpdatePreSql();
			int result = this.getJdbcTemplate().update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					prepareUpdate(ps, t);
				}
			});
			return result;
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 批处理-更新
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int updateBatch(final List<T> list) {
		try {
			String sql = this.getUpdatePreSql();
			int[] result = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pstmt, int i) throws SQLException {
					prepareUpdate(pstmt, list.get(i));
				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
			return result.length;
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 批处理-删除
	 * 
	 * @param list
	 * @return int 批量删除的条数
	 * @throws SQLException
	 */
	public int deleteBatch(final List<T> list) {
		try {
			String sql = this.getDeletePreSql();
			int[] result = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pstmt, int i) throws SQLException {
					prepareDelete(pstmt, list.get(i));
				}

				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
			return result.length;
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 该方法用来更新数据库的资料
	 * 
	 * @param sql
	 *            String SQL语句
	 * @return int 返回值为0时，表示更新失败
	 * @exception SQLException
	 *                数据库操作失败
	 */
	public int removePK(Object pkid) {
		try {
			return this.getJdbcTemplate().update(this.getDeletePreSql(), pkid);
		} catch (SQLException | DataAccessException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 删除
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int delete(final T t) {
		try {
			String sql = this.getDeletePreSql();
			return this.getJdbcTemplate().update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pstmt) throws SQLException {
					prepareDelete(pstmt, t);
				}
			});
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 根据主键查询对象
	 * 
	 * @param pk
	 * @return
	 */
	public T queryByPK(Object pk) {
		try {
			return this.queryObj(this.getPKQueryPreSql(), pk);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 该方法用来更新数据库的资料
	 * 
	 * @param sql
	 *            String SQL语句
	 * @return int 返回值为0时，表示更新失败
	 * @exception SQLException
	 *                数据库操作失败
	 */
	protected int executeUpdate(final String sql) {
		try {
			return this.getJdbcTemplate().update(sql);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 该方法用来更新数据库的资料
	 * 
	 * @param sql
	 *            String SQL语句
	 * @param args
	 *            args 参数
	 * @return int 返回值为0时，表示更新失败
	 * 
	 * @exception SQLException
	 *                数据库操作失败
	 */
	protected int executeUpdate(final String sql, Object... args) {
		try {
			return this.getJdbcTemplate().update(sql, args);
		} catch (DataAccessException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * 根据输入的sql查询出结果集 结果将以List&lt;Map&lt;String, Object>>的方式返回
	 * 
	 * @param sql
	 * @return 结果集
	 */
	protected List<Map<String, Object>> queryAllList(final String sql, Object... objects) {
		this.getJdbcTemplate().setMaxRows(-1);
		return this.getJdbcTemplate().queryForList(sql, objects);
	}

	/**
	 * 根据输入的sql查询出结果集 结果将以List<Map.的方式返回
	 * 
	 * @param sql
	 * @param limit
	 *            the maxRows limit
	 * @return 结果集
	 */
	protected List<Map<String, Object>> queryAllList(final String sql, final int limit) {
		this.getJdbcTemplate().setMaxRows(limit);
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 根据输入的sql查询出结果集 结果将以List<Map>的方式返回; 当limit参数设置为-1时， 查询结果不限制结果集行数
	 * 
	 * @param sql
	 * @param limit
	 *            the maxRows limit 
	 * @return 结果集
	 */
	protected List<Map<String, Object>> queryAllList(final String sql, final int limit, Object... objects) {
		this.getJdbcTemplate().setMaxRows(limit);
		return this.getJdbcTemplate().queryForList(sql, objects);
	}

	/**
	 * 根据输入的sql查询出结果集，结果将以Map的方式返回，如果没有结果，返回null
	 * 
	 * @param sql
	 *            Sql语句
	 * @return Map数组
	 * @throws SQLException
	 *             查询数据错误
	 * 
	 */
	protected Map<String, Object> queryMap(final String sql, Object... objects) {
		this.getJdbcTemplate().setMaxRows(1);
		List<Map<String, Object>> listmap = this.getJdbcTemplate().queryForList(sql, objects);
		if (AssertUtil.isVal(listmap)) {
			return listmap.get(0);
		}
		return null;
	}

	/**
	 * 根据传入的Sql进行查询，查询结果为Entity,如果没有结果，回传null;
	 * 
	 * @param sql
	 *            String SQL语句
	 * @param index
	 *            int 转换为对象的形式
	 * @return Object 查对对象
	 * @throws SQLException
	 *             查询数据库错误 转换对象错误
	 */
	@SuppressWarnings({ "unchecked" })
	protected T queryObj(final String sql, Object... objects) {
		this.getJdbcTemplate().setMaxRows(1);
		return this.getJdbcTemplate().query(sql, objects, new ResultSetExtractor<T>() {
			public T extractData(ResultSet resultset) throws SQLException, DataAccessException {
				Object obj = null;
				try {
					if (resultset.next()) {
						obj = getObjFromRS(resultset);
					}
				} catch (Exception e) {
					LOGGER.error("queryObj error" + sql, e);
					throw new DAOException("queryObj error" + sql, e);
				}
				return (T) obj;
			}
		});
	}

	/**
	 * 执行预编译查询
	 * 
	 * @param sql
	 *            查询sql
	 * @param objects
	 *            Object[] sql参数
	 * @return
	 */
	protected List<T> queryObjList(final String sql, Object... objects) {
		this.getJdbcTemplate().setMaxRows(-1);
		return this.getJdbcTemplate().query(sql, objects, new ResultSetExtractor<List<T>>() {
			public List<T> extractData(ResultSet resultset) throws SQLException, DataAccessException {
				List<T> list = new ArrayList<T>();
				T obj = null;
				try {
					while (resultset.next()) {
						obj = getObjFromRS(resultset);
						if (AssertUtil.isVal(obj)) {
							list.add(obj);
						}
					}
				} catch (Exception e) {
					LOGGER.error("queryObjAll error" + sql, e);
					throw new DAOException("queryObjAll error" + sql, e);
				}
				return list;
			}
		});
	}

	/**
	 * 
	 * @param sql
	 * @param pageBean
	 * @return 分页数据
	 * @throws Exception
	 * @throws SQLException
	 */
	protected PageBean queryList(String sql, PageBean pageBean, Object... objects) {
		Integer recordsTotal = this.getJdbcTemplate().queryForObject("select count(1) from (" + sql + ") temp",
				Integer.class, objects);
		pageBean.setRecordsTotal(recordsTotal.intValue());
		this.getJdbcTemplate().setMaxRows(pageBean.getLength());
		String pageSQL = this.getPageSQL(sql, pageBean.getStart(), pageBean.getLength());
		List<Map<String, Object>> list = this.queryAllList(pageSQL, objects);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 数据分页查询
	 * 
	 * @param queryString
	 *            :SQL
	 * @param dbType
	 *            :数据库类型
	 * @param startIndex
	 *            ,起始索引
	 * @param pageSize
	 *            ,分页大小
	 * @return
	 */
	abstract protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize);

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset
	 *            ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws SQLException
	 *             操作数据库失败
	 * @throws Exception
	 *             操作失败
	 */
	@SuppressWarnings({ "unchecked" })
	protected T getObjFromRS(ResultSet resultset) throws SecurityException, ClassNotFoundException {
		Map<String, Field> fields = this.getALLFields(this.getGenericType());
		if (!AssertUtil.isVal(fields)) {
			return null;
		}
		T entity = null;
		Object value = null;
		try {
			entity = (T) this.getGenericType().newInstance();
			for (Entry<String, Field> key : fields.entrySet()) {
				value = this.getValue(resultset, key.getKey());
				if (value != null) {
					Method setter = this.getSetterMethod(key.getValue().getName(), this.getGenericType());
					if (setter != null && setter.getReturnType().equals(Void.TYPE))
						setter.invoke(entity, value);
				}
			}
		} catch (Exception e) {
			
			throw new DAOException(e);
		}
		return entity;
	}

	/**
	 * 获取泛型的class
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	protected Class<?> getGenericType() {
		try {
			Type superClass = this.getClass().getGenericSuperclass();
			if (ParameterizedType.class.isAssignableFrom(superClass.getClass())) {
				for (Type genericType : ((ParameterizedType) superClass).getActualTypeArguments()) {
					return Class.forName(genericType.getTypeName());
				}
			}
		} catch (ClassNotFoundException e) {
			throw new DAOException(e);
		}
		return null;
	}

	/**
	 * 新增预处理语句定义，由子类覆写
	 * 
	 * @return 新增预处理的SQL语句
	 * @throws SQLException
	 *             新增错误
	 * @throws ClassNotFoundException
	 */
	protected String getCreatePreSql() throws SQLException, ClassNotFoundException {
		Class<?> genericType = this.getGenericType();
		String tableName = this.getTableName(genericType);

		Map<String, Field> allFields = this.getALLFields(genericType);

		StringBuffer value = new StringBuffer(" values (");
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		sql.append(tableName).append(" (");

		for (String key : allFields.keySet()) {
			sql.append(key).append(",");
			value.append("?,");
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 1).toString());
		value = new StringBuffer(value.subSequence(0, value.length() - 1).toString());
		sql.append(") ").append(value.append(")"));
		return sql.toString();

	}

	/**
	 * 修改预处理语句定义，由子类覆写
	 * 
	 * @return 修改的预处理语句
	 * @throws SQLException
	 *             操作失败
	 * @throws ClassNotFoundException
	 */
	protected String getUpdatePreSql() throws SQLException, ClassNotFoundException {
		Class<?> genericType = this.getGenericType();
		Map<String, Field> primaryKeys = this.getPrimaryKey(genericType);
		
		Map<String, Field> allFields = this.getALLFields(genericType);
		String tableName = this.getTableName(genericType);

		StringBuffer sql = new StringBuffer("UPDATE ");
		sql.append(tableName).append(" ");
		Set<String> colums = allFields.keySet();
		colums.removeAll(primaryKeys.keySet());

		for (String key : colums) {
			sql.append(" set ").append(key).append("=?,");
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 1).toString());
		sql.append(" where 1=1 ");
		
		for (String key : primaryKeys.keySet()) {
			sql.append(" and ").append(key).append("=? ");
		}
		return sql.toString();
	}

	/**
	 * 删除预处理语句定义，由子类覆写
	 * 
	 * @return 删除预处理语句
	 * @throws SQLException
	 *             操作失败
	 * @throws ClassNotFoundException
	 */
	protected String getDeletePreSql() throws SQLException, ClassNotFoundException {
		Class<?> genericType = this.getGenericType();
		Map<String, Field> primaryKeys = this.getPrimaryKey(genericType);
		String tableName = this.getTableName(genericType);
		StringBuffer sql = new StringBuffer("delete from ");
		sql.append(tableName);
		sql.append(" where 1=1 ");
		for (String primaryKey: primaryKeys.keySet()) {
			sql.append(" and ").append(primaryKey).append("=?");
		}
		return sql.toString();
	}

	/**
	 * 查询预处理语句定义，由子类覆写
	 * 
	 * @return 查询预处理的SQL语句
	 * @throws SQLException
	 *             查询数据错误
	 * @throws ClassNotFoundException
	 */
	protected String getPKQueryPreSql() throws SQLException, ClassNotFoundException {
		Class<?> genericType = this.getGenericType();
		Map<String, Field> primaryKeys = this.getPrimaryKey(genericType);
		String tableName = this.getTableName(genericType);

		StringBuffer sqlStr = new StringBuffer("SELECT * FROM ");
		sqlStr.append(tableName);
		
		sqlStr.append(" WHERE 1=1 ");
		for (String primaryKey: primaryKeys.keySet()) {
			sqlStr.append(" AND ").append(primaryKey).append("=?");
		}
		
		return sqlStr.toString();
	}

	/**
	 * 新增预处理语句值的定义，由子类覆写
	 * 
	 * @param obj
	 *            新增对象
	 * @throws SQLException
	 *             操作失败
	 */
	protected void prepareCreate(PreparedStatement pstmt, T obj) throws SQLException {
		Map<String, Field> allFields = this.getALLFields(obj.getClass());
		int index = 1;
		for (String colName : allFields.keySet()) {
			this.setPreparedStatement(index++, pstmt, obj, allFields.get(colName));
		}
	}

	/**
	 * 修改预处理语句值的定义，由子类覆写
	 * 
	 * @param obj
	 *            修改对象
	 * @throws SQLException
	 *             操作失败
	 */
	protected void prepareUpdate(PreparedStatement pstmt, T obj) throws SQLException {
		Map<String, Field> allFields = this.getALLFields(obj.getClass());
		Map<String, Field> primaryKeys = this.getPrimaryKey(obj.getClass());
		
		Set<String> colums = allFields.keySet();
		colums.removeAll(primaryKeys.keySet());
		int index = 1;
		
		//设置非主键
		for (String colName : colums) 
			this.setPreparedStatement(index++, pstmt, obj, allFields.get(colName));
		
		//设置主键
		for (String primaryKey : primaryKeys.keySet()) 
			this.setPreparedStatement(index++, pstmt, obj, primaryKeys.get(primaryKey));
	}

	/**
	 * 删除预处理语句值的定义，由子类覆写
	 * 
	 * @param obj
	 *            删除对象
	 * @throws SQLException
	 *             操作失败
	 */
	protected void prepareDelete(PreparedStatement pstmt, T obj) throws SQLException {
		Map<String, Field> primaryKeys = this.getPrimaryKey(obj.getClass());
		int index = 1;
		for (String key : primaryKeys.keySet())
			this.setPreparedStatement(index++, pstmt, obj, primaryKeys.get(key));
	}

	/**
	 * 验证数据存储长度是否合理，只验证String类型长度
	 * 
	 * @param obj
	 * @throws DAOException
	 */
	protected boolean verify(T obj) throws DAOException {
		Map<String, Field> allFields = this.getALLFields(obj.getClass());

		for (Field field : allFields.values()) {
			Column column = field.getAnnotation(Column.class);
			PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
			if ((AssertUtil.isVal(column) && !column.isNull()) || AssertUtil.isVal(primaryKey)) {
				Object value = this.getValue(obj, field);
				if (!AssertUtil.isVal(value)) {
					throw new CheckException("字段" + field.getName() + "，不能为空！");
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @param resultset
	 * @param field
	 * @return
	 * @throws SQLException
	 */
	private Object getValue(ResultSet resultset, String key) throws SQLException {
		Object value = null;
		Map<String, String> propertys = this.getEntityProperty(resultset);
		String labelName = key.toUpperCase();
		String sqlDataType = propertys.get(labelName);
		if (!AssertUtil.isVal(sqlDataType)) {
			return null;
		}
		LOGGER.debug(labelName + " = " + sqlDataType);
		if ("VARCHAR".equalsIgnoreCase(sqlDataType) || "NVARCHAR".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getString(labelName);
		} else if ("TIMESTAMP".equalsIgnoreCase(sqlDataType) || "DATETIME".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getTimestamp(labelName);
		} else if ("DATE".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getDate(labelName);
		} else if ("INT".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getInt(labelName);
		} else if ("LONGVARCHAR".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getString(labelName);
		} else if ("NUMERIC".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getBigDecimal(labelName);
		} else if ("DECIMAL".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getBigDecimal(labelName);
		} else if ("DOUBLE".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getDouble(labelName);
		} else if ("FLOAT".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getFloat(labelName);
		} else if ("BIGINT".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getLong(labelName);
		} else if ("CHAR".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getString(labelName);
		} else if ("LONGVARBINARY".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getString(labelName);
		} else if ("TINYINT".equalsIgnoreCase(sqlDataType) || "INTEGER".equalsIgnoreCase(sqlDataType)
				|| "BIT".equalsIgnoreCase(sqlDataType) || "SMALLINT".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getInt(labelName);
			if (!AssertUtil.isVal(value))
				value = Integer.parseInt("0");
		} else if ("VARBINARY".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getString(labelName);
		} else if ("REAL".equalsIgnoreCase(sqlDataType)) {
			value = resultset.getFloat(labelName);
		} else if ("OTHER".equalsIgnoreCase(sqlDataType)) {
			throw new DAOException("The data Type  is unsupport!");
		} else {
			throw new DAOException("The data Type is unsupport!");
		}
		return value;
	}

	/**
	 * 
	 * @param resultset
	 * @param field
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void setPreparedStatement(int parameterIndex, PreparedStatement psmt, T obj, Field field)
			throws SQLException {
		switch (field.getType().getClass().getName()) {
		case "java.lang.Integer":
			Integer value = (Integer) this.getValue(obj, field);
			psmt.setInt(parameterIndex, value);
			break;
		case "java.lang.String":
			String string = (String) this.getValue(obj, field);
			psmt.setString(parameterIndex, string);
			break;
		case "java.lang.Long":
			Long longData = (Long) this.getValue(obj, field);
			psmt.setLong(parameterIndex, longData);
			break;
		case "java.math.Long":
			BigDecimal decimal = (BigDecimal) this.getValue(obj, field);
			psmt.setBigDecimal(parameterIndex, decimal);
			break;
		case "java.lang.Float":
			Float floatData = (Float) this.getValue(obj, field);
			psmt.setFloat(parameterIndex, floatData);
			break;
		case "java.lang.Double":
			Double doubleData = (Double) this.getValue(obj, field);
			psmt.setDouble(parameterIndex, doubleData);
			break;
		case "java.sql.Date":
			Date date = (Date) this.getValue(obj, field);
			psmt.setDate(parameterIndex, date);
			break;
		case "java.sql.Timestamp":
			Timestamp timestamp = (Timestamp) this.getValue(obj, field);
			psmt.setTimestamp(parameterIndex, timestamp);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据泛型对象 获取对应字段值
	 * 
	 * @param obj
	 * @param field
	 * @return
	 */
	protected Object getValue(T obj, Field field) {
		try {
			Method method = this.getterMethod(field.getName(), this.getGenericType().getClass());
			Object invoke = method.invoke(obj);
			return invoke;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error("getValue.....", e);
		}
		return null;
	}

	/**
	 * 获取数据库中的属性值
	 * 
	 * @param resultset
	 *            ResultSet
	 * @return
	 */
	protected Map<String, String> getEntityProperty(ResultSet resultset) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			ResultSetMetaData metaData = resultset.getMetaData();
			int count = metaData.getColumnCount();
			for (int index = 1; index <= count; index++) {
				String columnName = metaData.getColumnName(index);
				map.put(columnName.toUpperCase(), metaData.getColumnTypeName(index));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return map;
	}

	/**
	 * 获取setter方法
	 * 
	 * @param fieldName
	 *            String
	 * @param clazz
	 *            Class
	 * @return Method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Method getSetterMethod(String fieldName, Class clazz) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		// 获得和属性对应的setXXX()/setXXX()方法的名字
		String setMethodName = "set" + firstLetter + fieldName.substring(1);
		String getMethodName = "get" + firstLetter + fieldName.substring(1);
		Method setMethod;
		try {
			Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
			setMethod = clazz.getMethod(setMethodName, new Class[] { getMethod.getReturnType() });
			return setMethod;
		} catch (NoSuchMethodException e) {
			String message = clazz.getName() + "类中的字段 " + fieldName + " 没有set方法！";
			LOGGER.debug(message);
		} catch (SecurityException e) {
			String message = clazz.getName() + "类中的字段 " + fieldName + " 获取Setter失败！";
			LOGGER.warn(message);
		}
		return null;
	}

	private Method getterMethod(String fieldName, Class<?> clazz) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		// 获得和属性对应的setXXX()/setXXX()方法的名字
		String getMethodName = "get" + firstLetter + fieldName.substring(1);
		try {
			Method getMethod = clazz.getMethod(getMethodName, new Class<?>[] {});
			return getMethod;
		} catch (NoSuchMethodException e) {
			String message = clazz.getName() + "类中的字段 " + fieldName + " 没有set方法！";
			LOGGER.debug(message);
		} catch (SecurityException e) {
			String message = clazz.getName() + "类中的字段 " + fieldName + " 获取Setter失败！";
			LOGGER.warn(message);
		}
		return null;
	}

	/**
	 * 获取主键信息
	 * @param genericType
	 * @return
	 */
	protected Map<String, Field> getPrimaryKey(Class<?> genericType) {
		Field[] declaredFields = genericType.getDeclaredFields();
		Map<String, Field> keys = new HashMap<>();
		for (Field field : declaredFields) {
			if (!field.isAccessible() && !field.getName().equals("serialVersionUID")) {
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				if (AssertUtil.isVal(primaryKey) && AssertUtil.isVal(primaryKey.value())) {
					keys.put(primaryKey.value(), field);
				} else if (AssertUtil.isVal(primaryKey)) {
					keys.put(field.getName(), field);
				}
			}
		}
		if (!AssertUtil.isVal(keys)) {
			throw new DAOException(genericType.getName() + " 未标记主键！");
		}
		return keys;
	}

	protected String getTableName(Class<?> genericType) {
		if (!AssertUtil.isVal(genericType)) {
			throw new DAOException(genericType.getName() + " 表空间信息未标注！");
		}
		Entity entity = genericType.getAnnotation(Entity.class);
		if (!AssertUtil.isVal(entity)) {
			throw new DAOException(genericType.getName() + " 表空间信息未标注！");
		}
		String tableName = entity.table();
		if (!AssertUtil.isVal(tableName)) {
			tableName = entity.value();
		}
		if (!AssertUtil.isVal(tableName)) {
			throw new DAOException(genericType.getName() + " 表空间信息未标注！");
		}
		return tableName;
	}

	/**
	 * 获取所有的数据库字段
	 * 
	 * @param genericType
	 * @return
	 */
	private Map<String, Field> getALLFields(Class<?> genericType) {
		Field[] declaredFields = genericType.getDeclaredFields();
		Map<String, Field> fieldMap = new HashMap<>();
		for (Field field : declaredFields) {
			if (!field.isAccessible() && !field.getName().equals("serialVersionUID")) {
				Column column = field.getAnnotation(Column.class);
				PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
				if (AssertUtil.isVal(column) && AssertUtil.isVal(column.value())) {
					fieldMap.put(column.value(), field);
				} else if (AssertUtil.isVal(primaryKey) && AssertUtil.isVal(primaryKey.value())) {
					fieldMap.put(primaryKey.value(), field);
				} else {
					fieldMap.put(field.getName(), field);
				}
			}
		}
		return fieldMap;
	}
}
