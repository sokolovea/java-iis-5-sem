package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleDataBaseDAOFactory;


public enum DBType {
//	XML {
//		@Override
//		public DAOFactory getDAOFactory() {
//			DAOFactory xmlDAOFactory = new XMLDAOFactory();
//			return xmlDAOFactory;
//		}
//	},
	ORACLE {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory oracleDataBaseDAOFactory = null;
			try {
				oracleDataBaseDAOFactory = OracleDataBaseDAOFactory.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oracleDataBaseDAOFactory;
		}
	};

	public static DBType getTypeByName(String dbType) {
		try {
			return DBType.valueOf(dbType.toUpperCase());
		} catch (Exception e) {
			throw new DBTypeException();
		}
	}

	public abstract DAOFactory getDAOFactory();

}
