import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

public class DatabaseManager {
    private Connection connection;
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String DB_USER = "shiva050";
    private final String DB_PASSWORD = "Shiva2735";

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("Connected to the database.");
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }
	public List<Victim> getAllVictims() throws SQLException {
		List<Victim> victims = new ArrayList<>();
		String query = "SELECT * FROM Victim";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int id = resultSet.getInt("ID");
			String name = resultSet.getString("Name");
			long phoneNo = resultSet.getLong("PhoneNo");
			String address = resultSet.getString("Address");
			Victim victim = new Victim(id, name, phoneNo, address);
			victims.add(victim);
		}
		return victims;
	}
	public List<Policy> getAllPolicies() throws SQLException {
		List<Policy> policies = new ArrayList<>();
		String query = "SELECT * FROM Policy_";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int policyId = resultSet.getInt("policyId");
			String policyName = resultSet.getString("PolicyName");
			String description = resultSet.getString("Description");
			Policy policy = new Policy(policyId, policyName, description);
			policies.add(policy);
		}
		return policies;
	}
	public List<Department> getAllDepartments() throws SQLException {
		List<Department> departments = new ArrayList<>();
		String query = "SELECT * FROM Dept";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int deptId = resultSet.getInt("deptid");
			String deptName = resultSet.getString("deptname");
			long helpLine = resultSet.getLong("HelpLine");
			String location = resultSet.getString("location");
			Department department = new Department(deptId, deptName, helpLine, location);
			departments.add(department);
		}
		return departments;
	}
	public List<DeptOfficer> getAllDeptOfficers() throws SQLException {
		List<DeptOfficer> deptOfficers = new ArrayList<>();
		String query = "SELECT * FROM DeptOfficer";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int officerId = resultSet.getInt("OfficerId");
			String name = resultSet.getString("Name");
			int deptId = resultSet.getInt("deptid");
			String role = resultSet.getString("role");
			DeptOfficer deptOfficer = new DeptOfficer(officerId, name, deptId, role);
			deptOfficers.add(deptOfficer);
		}
		return deptOfficers;
	}
	public List<Crime> getAllCrimes() throws SQLException {
		List<Crime> crimes = new ArrayList<>();
		String query = "SELECT * FROM Crime";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int crimeId = resultSet.getInt("crimeId");
			String crimeName = resultSet.getString("crimeName");
			Crime crime = new Crime(crimeId, crimeName);
			crimes.add(crime);
		}
		return crimes;
	}
	public List<CrimeScene> getAllCrimeScenes() throws SQLException {
		List<CrimeScene> crimeScenes = new ArrayList<>();
		String query = "SELECT * FROM CrimeScene";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int victimId = resultSet.getInt("victimId");
			String crimeName = resultSet.getString("crimeName");
			String policyName = resultSet.getString("PolicyName");
			int officerId = resultSet.getInt("OfficerId");
			String location = resultSet.getString("location");
			Date crimeDate = resultSet.getDate("crimedate");
			LocalDate localDate = crimeDate.toLocalDate();
			CrimeScene crimeScene = new CrimeScene(victimId, crimeName, policyName, officerId, location, crimeDate);
			crimeScenes.add(crimeScene);
		}
		return crimeScenes;
	}
	// Victim

    public void insertVictim(int id, String name, Long phoneNo, String address) throws SQLException {
        String query = "INSERT INTO Victim (ID, Name, PhoneNo, Address) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setLong(3, phoneNo);
        statement.setString(4, address);
        statement.executeUpdate();
    }

    public void updateVictim(int id, String name, Long phoneNo, String address) throws SQLException {
        String query = "UPDATE Victim SET Name = ?, PhoneNo = ?, Address = ? WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setLong(2, phoneNo);
        statement.setString(3, address);
        statement.setInt(4, id);
        statement.executeUpdate();
    }

    public void deleteVictim(int id) throws SQLException {
        String query = "DELETE FROM Victim WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    // Policy_

    public void insertPolicy(int policyId, String policyName, String description) throws SQLException {
        String query = "INSERT INTO Policy_ (policyId, PolicyName, Description) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, policyId);
        statement.setString(2, policyName);
        statement.setString(3, description);
        statement.executeUpdate();
    }

    public void updatePolicy(int policyId, String policyName, String description) throws SQLException {
        String query = "UPDATE Policy_ SET PolicyName = ?, Description = ? WHERE policyId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, policyName);
        statement.setString(2, description);
        statement.setInt(3, policyId);
        statement.executeUpdate();
    }

    public void deletePolicy(int policyId) throws SQLException {
        String query = "DELETE FROM Policy_ WHERE policyId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, policyId);
        statement.executeUpdate();
    }

    // Dept

    public void insertDept(int deptId, String deptName, long helpLine, String location) throws SQLException {
        String query = "INSERT INTO Dept (deptid, deptname, HelpLine, location) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, deptId);
        statement.setString(2, deptName);
        statement.setLong(3, helpLine);
        statement.setString(4, location);
        statement.executeUpdate();
    }

    public void updateDept(int deptId, String deptName, long helpLine, String location) throws SQLException {
        String query = "UPDATE Dept SET deptname = ?, HelpLine = ?, location = ? WHERE deptid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deptName);
        statement.setLong(2, helpLine);
        statement.setString(3, location);
        statement.setInt(4, deptId);
        statement.executeUpdate();
    }

    public void deleteDept(int deptId) throws SQLException {
        String query = "DELETE FROM Dept WHERE deptid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, deptId);
        statement.executeUpdate();
    }

    // DeptOfficer

    public void insertDeptOfficer(int officerId, String name, int deptId, String role) throws SQLException {
        String query = "INSERT INTO DeptOfficer (OfficerId, Name, deptid, role) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, officerId);
        statement.setString(2, name);
        statement.setInt(3, deptId);
        statement.setString(4, role);
        statement.executeUpdate();
    }

    public void updateDeptOfficer(int officerId, String name, int deptId, String role) throws SQLException {
        String query = "UPDATE DeptOfficer SET Name = ?, deptid = ?, role = ? WHERE OfficerId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setInt(2, deptId);
        statement.setString(3, role);
        statement.setInt(4, officerId);
        statement.executeUpdate();
    }

    public void deleteDeptOfficer(int officerId) throws SQLException {
        String query = "DELETE FROM DeptOfficer WHERE OfficerId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, officerId);
        statement.executeUpdate();
    }

    // Crime

    public void insertCrime(int crimeId, String crimeName) throws SQLException {
        String query = "INSERT INTO Crime (crimeId, crimeName) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, crimeId);
        statement.setString(2, crimeName);
        statement.executeUpdate();
    }

    public void updateCrime(int crimeId, String crimeName) throws SQLException {
        String query = "UPDATE Crime SET crimeName = ? WHERE crimeId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, crimeName);
        statement.setInt(2, crimeId);
        statement.executeUpdate();
    }

    public void deleteCrime(int crimeId) throws SQLException {
        String query = "DELETE FROM Crime WHERE crimeId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, crimeId);
        statement.executeUpdate();
    }

    // CrimeScene

    public void insertCrimeScene(int victimId, String crimeName, String policyName, int officerId, String location, String crimeDate) throws SQLException {
        String query = "INSERT INTO CrimeScene (victimId, crimeName, PolicyName, OfficerId, location, crimedate) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, victimId);
        statement.setString(2, crimeName);
        statement.setString(3, policyName);
        statement.setInt(4, officerId);
        statement.setString(5, location);
        statement.setString(6, crimeDate);
        statement.executeUpdate();
    }

    public void updateCrimeScene(int victimId, int newVictimId, String crimeName, String policyName, int officerId, String location, String crimeDate) throws SQLException {
        String query = "UPDATE CrimeScene SET victimId = ?, crimeName = ?, PolicyName = ?, OfficerId = ?, location = ?, crimedate = ? WHERE victimId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, newVictimId);
        statement.setString(2, crimeName);
        statement.setString(3, policyName);
        statement.setInt(4, officerId);
        statement.setString(5, location);
        statement.setString(6, crimeDate);
        statement.setInt(7, victimId);
        statement.executeUpdate();
    }

    public void deleteCrimeScene(int victimId) throws SQLException {
        String query = "DELETE FROM CrimeScene WHERE victimId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, victimId);
        statement.executeUpdate();
    }
	public List<LocationCrimeCount> getCrimeCountsByLocation() throws SQLException {
        String query = "SELECT location, COUNT(*) AS crimeCount FROM crimeScene GROUP BY location";
        List<LocationCrimeCount> crimeCounts = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                int crimeCount = resultSet.getInt("crimeCount");
                LocationCrimeCount crimeCountObj = new LocationCrimeCount(location, crimeCount);
                crimeCounts.add(crimeCountObj);
            }
        }

        return crimeCounts;
    }
	public List<CrimeTypeCount> getCrimeCountsByType() throws SQLException {
		String query = "SELECT crimeName, COUNT(*) AS crimeCount FROM crimeScene GROUP BY crimeName";
		List<CrimeTypeCount> crimeCounts = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				String crimeName = resultSet.getString("crimeName");
				int crimeCount = resultSet.getInt("crimeCount");
				CrimeTypeCount crimeCountObj = new CrimeTypeCount(crimeName, crimeCount);
				crimeCounts.add(crimeCountObj);
			}
		}

		return crimeCounts;
	}


}
