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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

import hashbrowns.p1.utils.*;
import hashbrowns.p1.annotations.Id;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.utils.Connect;

public class ORMImpl implements ORM {

	private Connect con = Connect.getConnect();
	Logger logger = Logger.getLogger();

	// Might have to return object for future use
	public <T> Object insertObject(Object object) throws UsernameAlreadyExistsException, RecipeNameAlreadyExists {
		
		//
		StringJoiner comma1 = new StringJoiner(", ");
		StringJoiner comma2 = new StringJoiner("', '");
		
		//
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String table = clazz.getSimpleName().toLowerCase();
		
		Stream<Field> strArray = Arrays.stream(fields);
		//
		strArray.forEach(field -> {
			field.setAccessible(true);
			try {
				if (!field.get(object).equals(null) & !field.isAnnotationPresent(Id.class)) {

					comma1.add(field.getName());
					comma2.add(field.get(object).toString());
				} else if (field.isAnnotationPresent(Id.class)) {
				}
			} catch (Exception e) {
				logger.log("Nulled fields are being excluded from the statement", LoggingLevel.INFO);
			}
		});
		//
		String query = "INSERT INTO " + table + "(" + comma1.toString() + ") VALUES ('" + comma2.toString() + "')";

		try (Connection conn = con.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return object;
		
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

	@Override
	public <T> Object findById(Object object) {
		Object obj = null;
		try (Connection connection = con.getConnection();) {
			logger.log("ORM Attemps findByID", LoggingLevel.TRACE);
			T temp = (T) object.getClass().getConstructor().newInstance();
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
				logger.log("ID + Object Was Found", LoggingLevel.TRACE);
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					Annotation annId = field.getAnnotation(Id.class);
					annId = field.getAnnotation(Id.class);
					int column = rs.findColumn(field.getName());

					if (annId == null) {

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

					} else if (annId != null) {
						field.set(temp, rs.getObject(column));
					}
				}
				obj = temp;
			} else {
				logger.log("Invalid ID was inserted", LoggingLevel.INFO);
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public <T> Object updateObject(Object object) {

		//
		StringBuilder fieldStr = new StringBuilder();
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		//
		String id = null;
		String idValue = null;
		//
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				if (!field.isAnnotationPresent(Id.class)
						&& (field.get(object) != null || !field.get(object).toString().equals("0"))) {

					fieldStr.append(field.getName());
					fieldStr.append("='");
					fieldStr.append(field.get(object));
					fieldStr.append("', ");

				} else if (field.isAnnotationPresent(Id.class)) {

					field.setAccessible(true);
					id = field.getName().toString();
					idValue = field.get(object).toString();

				} else {

				}

			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		;
		fieldStr.setLength(fieldStr.length() - 2);
		//
		String query = "UPDATE " + clazz.getSimpleName().toLowerCase() + " SET " + fieldStr.toString() + " where " + id
				+ "=" + idValue + "";
		try (Connection conn = con.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(query);
		return object;

	}

	public <T> List<T> getAll(Object object) {

		Class<?> clazz = object.getClass();

		String sql = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();

		List<T> all = new ArrayList<>();
		try (Connection conn = con.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;

			Field[] fields = clazz.getDeclaredFields();
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
		} catch (SQLException | IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}

}
