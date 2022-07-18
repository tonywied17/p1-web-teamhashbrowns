package hashbrowns.p1.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hashbrowns.p1.annotations.Id;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.utils.Connect;

public class ORMImpl implements ORM{

	private Connect con = Connect.getConnect();

	// Might have to return object for future use
	public <T> Object insertObject(Object object) throws UsernameAlreadyExistsException, RecipeNameAlreadyExists{
		Object obj = null;
		try (Connection connection = con.getConnection()) {
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
				connection.commit();
				obj = temp;
			} else {
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
				connection.commit();
				obj = temp;
			} else {
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
		try (Connection connection = con.getConnection();) {
			PreparedStatement ps;
			ResultSet rs;
			connection.setAutoCommit(false);
			StringBuilder info = new StringBuilder();
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			info.append("select * from " + clazz.getSimpleName().toLowerCase() + " where id=");

			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annId = field.getAnnotation(Id.class);
				annId = field.getAnnotation(Id.class);
				if (annId != null) {
					info.append(" " + field.get(object) + ";");
				}
			}
			ps = connection.prepareStatement(info.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					Annotation annId = field.getAnnotation(Id.class);
					annId = field.getAnnotation(Id.class);
					if (annId == null) {
						field.set(object, rs.getObject(field.getName().toString()));
					}
				}

			} else {
				object = null;
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;

	}

	public <T> Object updateObject(Object object) {
		Object obj = null;
		try (Connection connection = con.getConnection()) {
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
				obj = temp;
				connection.commit();
			} else {
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
		try (Connection connection = con.getConnection()) {
			PreparedStatement ps;
			ResultSet rs;
			StringBuilder info = new StringBuilder();
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			info.append("select * from " + clazz.getSimpleName().toLowerCase() + ";");
			ps = connection.prepareStatement(info.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				T temp = (T) object.getClass().getConstructor().newInstance();
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation annId = field.getAnnotation(Id.class);
					annId = field.getAnnotation(Id.class);
					if (annId == null) {
						int column = rs.findColumn(field.getName());
						field.set(temp, rs.getObject(column));
					} else {
						int column = rs.findColumn(field.getName());
						field.set(temp, rs.getObject(column));
					}
				}
				all.add(temp);

			}

		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return all;
	}

}
