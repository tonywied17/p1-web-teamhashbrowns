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
		//
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String id = null;
		String idValue = null;
		String table = clazz.getSimpleName().toLowerCase();
		//
		for (Field field : fields) {
			field.setAccessible(true);

			if (field.isAnnotationPresent(Id.class)) {
				id = field.getName().toString();
				try {
					idValue = field.get(object).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		}

		//
		String sql = "DELETE FROM " + table + " WHERE " + id + "='" + idValue + "';";
		try (Connection conn = con.getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		object = null;
		return object;
	}

	@Override
	public <T> Object findById(Object object) {
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
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
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
							if (field.getName().equals(e.getKey())
									&& field.getType().getSimpleName().equals("String")) {
								field.set(object, e.getValue().toString());
							} else if (field.getName().equals(e.getKey())
									&& field.getType().getSimpleName().equals("int")) {
								field.setInt(object, (int) e.getValue());
							} else if (field.getName().equals(e.getKey())
									&& field.getType().getSimpleName().equals("Double")) {
								double d = Double.parseDouble((String) e.getValue());
								field.set(object, d);
							} else if (field.getName().equals(e.getKey())
									&& field.getType().getSimpleName().equals("boolean")) {
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
				if (!field.isAnnotationPresent(Id.class) && (field.get(object) != null)) {

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
