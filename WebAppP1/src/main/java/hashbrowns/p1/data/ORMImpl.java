package hashbrowns.p1.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hashbrowns.p1.utils.*;
import hashbrowns.p1.annotations.Id;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.utils.Connect;

public class ORMImpl implements ORM{

	private Connect con = Connect.getConnect();
	Logger logger = Logger.getLogger();


	// Might have to return object for future use
	public <T> Object insertObject(Object object) throws UsernameAlreadyExistsException, RecipeNameAlreadyExists{
		Object obj = null;
		try (Connection connection = con.getConnection()) {
			logger.log("ORM Attemps insertion", LoggingLevel.TRACE);
			T temp = (T) object.getClass().getConstructor().newInstance();
			PreparedStatement ps;
			int rowsAffected;
			connection.setAutoCommit(false);
			StringBuilder info = new StringBuilder();
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			info.append("insert into " + clazz.getSimpleName().toLowerCase() + " values (");
			for (Field field : fields) {
				field.setAccessible(true);
				
				
				Annotation annId = field.getAnnotation(Id.class);
				annId = field.getAnnotation(Id.class);
				
				if (annId != null) {
					info.append("default, ");
					field.set(temp, field.get(object));
				} else {
					info.append("'" + field.get(object) + "', ");
					field.set(temp, field.get(object));
				}
				

			}
			info.delete(info.length() - 2, info.length());
			info.append(");");
			ps = connection.prepareStatement(info.toString());
			rowsAffected = ps.executeUpdate();

			if (rowsAffected == 1) {
				logger.log("Insertion Completed", LoggingLevel.TRACE);
				connection.commit();
				obj = temp;
			} else {
				logger.log("Insertion Went Wrong", LoggingLevel.WARN);
				connection.rollback();
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new UsernameAlreadyExistsException();
		}
		return obj;
	}

	public <T> Object deleteObject(Object object) {
		Object obj = null;
		try (Connection connection = con.getConnection();) {
			logger.log("ORM Attemps Deletion", LoggingLevel.TRACE);
			T temp = (T) object.getClass().getConstructor().newInstance();
			PreparedStatement ps;
			int rowsAffected;
			connection.setAutoCommit(false);
			StringBuilder info = new StringBuilder();
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			info.append("delete from " + clazz.getSimpleName().toLowerCase() + " where id =");
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annId = field.getAnnotation(Id.class);
				annId = field.getAnnotation(Id.class);
				if (annId != null) {
					info.append(" " + field.get(object) + ";");
					field.set(temp, field.get(object));
				} else {
					field.set(temp, field.get(object));
				}
			}
			ps = connection.prepareStatement(info.toString());
			rowsAffected = ps.executeUpdate();

			if (rowsAffected == 1) {
				logger.log("Deletion Completed", LoggingLevel.TRACE);
				connection.commit();
				obj = temp;
			} else {
				logger.log("Deletion Went Wrong", LoggingLevel.WARN);
				connection.rollback();
			}

		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public Object findById(Object object) {
		//
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String id = null;
		String idValue = null;
		String table = clazz.getSimpleName().toLowerCase();
		//
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				field.setAccessible(true);
				id = field.getName().toString();
				try {
					idValue = field.get(object).toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//
		String query = "SELECT * FROM " + table + " WHERE " + id + " = '" + idValue + "'";

		try (Connection conn = con.getConnection()) {
			Field[] fields1 = object.getClass().getDeclaredFields();
			Map<String, Object> row = new HashMap<String, Object>();
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			//
			while (resultSet.next()) {
				for (int i = 1; i <= columns; i++) {
					row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
				}
				//
				row.entrySet().stream().forEach(e -> {
					for (Field field : fields1) {
						field.setAccessible(true);
						try {
							if (field.getName().equals(e.getKey())&& field.getType().getSimpleName().equals("String")) {
								
								field.set(object, e.getValue().toString());
								
							} else if (field.getName().equals(e.getKey()) && field.getType().getSimpleName().equals("int")) {
								
								field.setInt(object, (int) e.getValue());
								
							} else if (field.getName().equals(e.getKey()) && field.getType().getSimpleName().equals("Double")) {
								
								double d = Double.parseDouble((String) e.getValue());
								field.set(object, d);
								
							} else if (field.getName().equals(e.getKey()) && field.getType().getSimpleName().equals("boolean")) {
								field.setBoolean(object, (boolean) e.getValue());
							} else {

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;

	}

	public <T> Object updateObject(Object object) {
		Object obj = null;
		try (Connection connection = con.getConnection()) {
			logger.log("ORM Attemps to update", LoggingLevel.TRACE);
			T temp = (T) object.getClass().getConstructor().newInstance();
			PreparedStatement ps;
			int rowsAffected;
			connection.setAutoCommit(false);
			StringBuilder info = new StringBuilder();
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			info.append("update " + clazz.getSimpleName().toLowerCase() + " set");
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annId = field.getAnnotation(Id.class);
				annId = field.getAnnotation(Id.class);
				if (annId == null) {
					info.append(" " + field.getName() + " = " + "'" + field.get(object) + "'" + " ,");
					field.set(temp, field.get(object));
				}
			}
			info.delete(info.length() - 2, info.length());
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annId = field.getAnnotation(Id.class);
				annId = field.getAnnotation(Id.class);
				if (annId != null) {
					info.append(" where " + field.getName() + " = " + field.get(object) + ";");
					field.set(temp, field.get(object));
				}
			}

			ps = connection.prepareStatement(info.toString());
			rowsAffected = ps.executeUpdate();

			if (rowsAffected == 1) {
				logger.log("Update Completed", LoggingLevel.TRACE);
				obj = temp;
				connection.commit();
			} else {
				logger.log("Update Went Wrong", LoggingLevel.WARN);
				connection.rollback();
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public <T> List<T> getAll(Object object) {
		
		
		
		List<T> all = new ArrayList<>();
		try (Connection conn = con.getConnection()) {
			
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			String table = clazz.getSimpleName().toLowerCase();
			
			String sql = "SELECT * FROM " + table;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			
			rs = ps.executeQuery();
			while (rs.next()) {
				T temp = (T) object.getClass().getConstructor().newInstance();
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation annId = field.getAnnotation(Id.class);
					annId = field.getAnnotation(Id.class);
					if (annId == null) {
						int column = rs.findColumn(field.getName());
						if (field.getType().getSimpleName().equals("String")) {
							field.set(temp, rs.getObject(column));
						} else if (field.getType().getSimpleName().equals("int")) {
							field.setInt(temp, (int) rs.getObject(column));
						} else if (field.getType().getSimpleName().equals("Double")) {
							double d = Double.parseDouble((String) rs.getObject(column));
							field.set(temp, d);
						} else if (field.getType().getSimpleName().equals("boolean")) {
							field.setBoolean(temp, (boolean) rs.getObject(column));
						}
					} else {
						int column = rs.findColumn(field.getName());
						field.set(temp, rs.getObject(column));
					}
				}
				all.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}

}
