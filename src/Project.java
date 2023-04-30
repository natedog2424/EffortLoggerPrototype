import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



//Assigned to: Evan

public class Project implements Serializable{

	// base constructor
	public Project() {
		ProjectLogs = new ArrayList<Log>();
		ProductBacklog = new ArrayList<BacklogItem>();
		SprintBacklog = new ArrayList<BacklogItem>();
		CompletedBacklog = new ArrayList<BacklogItem>();
		UnresolvedDefects = new ArrayList<Defect>();
		ResolvedDefects = new ArrayList<Defect>();
	}

	// constructor with name
	public Project(String name) {
		Name = name;
		DatabaseName = name.trim().replaceAll("\\s", "");
		// role = "";
		ProjectLogs = new ArrayList<Log>();
		ProductBacklog = new ArrayList<BacklogItem>();
		SprintBacklog = new ArrayList<BacklogItem>();
		CompletedBacklog = new ArrayList<BacklogItem>();
		UnresolvedDefects = new ArrayList<Defect>();
		ResolvedDefects = new ArrayList<Defect>();

		try {
			// create database
			App.dbManager.connect(this);

			// create tables

			App.dbManager.executeUpdate(
					// "CREATE TABLE IF NOT EXISTS info (name TEXT, description TEXT)"
					"CREATE TABLE IF NOT EXISTS info (name TEXT, description TEXT, role TEXT)");
			
			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY AUTOINCREMENT, lifecyclestep STRING, backlogItem STRING, startdate STRING, enddate STRING, duration STRING)");

			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS productBacklog (name TEXT, effort INTEGER, time REAL)");

			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS sprintBacklog (name TEXT, effort INTEGER, time REAL)");

			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS completedBacklog (name TEXT, effort INTEGER, time REAL)");

			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS unresolvedDefects (type TEXT, description TEXT, effort INTEGER)");

			App.dbManager.executeUpdate(
					"CREATE TABLE IF NOT EXISTS resolvedDefects (type TEXT, description TEXT, effort INTEGER)");

			// update or insert project info
			App.dbManager.executeUpdate(
					"INSERT INTO info (name) VALUES (?)",
					Name);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String Name;

	public String Description;

	public String role;

	public String DatabaseName;
	
	public ArrayList<Log> ProjectLogs;

	public ArrayList<BacklogItem> ProductBacklog;

	public ArrayList<BacklogItem> SprintBacklog;

	public ArrayList<BacklogItem> CompletedBacklog;

	public ArrayList<Defect> UnresolvedDefects;

	public ArrayList<Defect> ResolvedDefects;

	static Project fromDatabase(String databaseName) {
		Project loadedProject = new Project();
		loadedProject.DatabaseName = databaseName;

		// fetch data from database
		try {
			App.dbManager.connect(loadedProject);

			// get project info
			ResultSet projectInfo = App.dbManager.executeQuery(
					"SELECT * FROM info");

			// set name and description
			loadedProject.Name = projectInfo.getString("name");
			loadedProject.Description = projectInfo.getString("description");
			loadedProject.role = projectInfo.getString("role");
			

			ResultSet Logs = App.dbManager.executeQuery(
					"SELECT * FROM logs"
			);

			while(Logs.next()){
				Log newLog = new Log();
				newLog.id = new SimpleIntegerProperty(Logs.getInt("id"));
				newLog.lifecycleStep = new SimpleStringProperty(Logs.getString("lifecyclestep"));
				newLog.backlogItem =  new SimpleStringProperty(Logs.getString("backlogItem"));
				newLog.startDate =  new SimpleStringProperty(Logs.getString("startdate"));
				newLog.endDate =  new SimpleStringProperty(Logs.getString("enddate"));
				newLog.duration =  new SimpleStringProperty(Logs.getString("duration"));

				loadedProject.ProjectLogs.add(newLog);
			}
			// get product backlog
			ResultSet productBacklog = App.dbManager.executeQuery(
					"SELECT * FROM productBacklog");


			// add items to product backlog
			while (productBacklog.next()) {
				BacklogItem item = new BacklogItem();
				item.BacklogItemName = productBacklog.getString("name");
				item.EffortValueEstimation = productBacklog.getInt("effort");
				item.EstimatedTime = productBacklog.getFloat("time");

				loadedProject.ProductBacklog.add(item);
			}

			// get sprint backlog
			ResultSet sprintBacklog = App.dbManager.executeQuery(
					"SELECT * FROM sprintBacklog");

			// add items to sprint backlog
			while (sprintBacklog.next()) {
				BacklogItem item = new BacklogItem();
				item.BacklogItemName = sprintBacklog.getString("name");
				item.EffortValueEstimation = sprintBacklog.getInt("effort");
				item.EstimatedTime = sprintBacklog.getFloat("time");

				loadedProject.SprintBacklog.add(item);
			}

			// get completed backlog
			ResultSet completedBacklog = App.dbManager.executeQuery(
					"SELECT * FROM completedBacklog");

			// add items to completed backlog
			while (completedBacklog.next()) {
				BacklogItem item = new BacklogItem();
				item.BacklogItemName = completedBacklog.getString("name");
				item.EffortValueEstimation = completedBacklog.getInt("effort");
				item.EstimatedTime = completedBacklog.getFloat("time");

				loadedProject.CompletedBacklog.add(item);
			}

			// get unresolved defects
			ResultSet unresolvedDefects = App.dbManager.executeQuery(
					"SELECT * FROM unresolvedDefects");

			// add items to unresolved defects
			while (unresolvedDefects.next()) {
				Defect item = new Defect();
				item.DefectType = unresolvedDefects.getString("type");
				item.DefectDescription = unresolvedDefects.getString("description");
				item.EffortLevel = unresolvedDefects.getInt("effort");

				loadedProject.UnresolvedDefects.add(item);
			}

			// get resolved defects
			ResultSet resolvedDefects = App.dbManager.executeQuery(
					"SELECT * FROM resolvedDefects");

			// add items to resolved defects
			while (resolvedDefects.next()) {
				Defect item = new Defect();
				item.DefectType = resolvedDefects.getString("type");
				item.DefectDescription = resolvedDefects.getString("description");
				item.EffortLevel = resolvedDefects.getInt("effort");

				loadedProject.ResolvedDefects.add(item);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return loadedProject;
	}

	// add method for backlog item
	public void add(BacklogItem item, ArrayList<BacklogItem> list) {
		list.add(item);

		// add to database
		String tableName = "";

		if (list == ProductBacklog) {
			tableName = "productBacklog";
		} else if (list == SprintBacklog) {
			tableName = "sprintBacklog";
		} else if (list == CompletedBacklog) {
			tableName = "completedBacklog";
		}

		try {
			App.dbManager.executeUpdate(
					"INSERT INTO " + tableName + " (name, effort, time) VALUES (?, ?, ?)",
					item.BacklogItemName,
					item.EffortValueEstimation,
					item.EstimatedTime);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeRole(String newRole) {
		try {
			App.dbManager.executeUpdate(
					"UPDATE info SET role = ?",
					newRole);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(Log item, ArrayList<Log> list){
		list.add(item);
		try{
			App.dbManager.executeUpdate(
				"INSERT INTO logs (lifecyclestep,backlogItem, startdate,endate,duration) VALUES (?,?,?,?,?)",
				item.lifecycleStep,
				item.backlogItem,
				item.startDate,
				item.endDate,
				item.duration
			);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void remove(Log item, ArrayList<Log> list){
		list.remove(item);

		try{
			App.dbManager.executeUpdate(
				"DELETE FROM logs WHERE lifecyclestep = ? AND backlogItem = ? AND startdate = ? AND endate = ? AND duration = ?",
				item.lifecycleStep,
				item.backlogItem,
				item.startDate,
				item.endDate,
				item.duration
			);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// add method for defect
	public void add(Defect item, ArrayList<Defect> list) {
		list.add(item);

		// add to database
		String tableName = "";

		if (list == UnresolvedDefects) {
			tableName = "unresolvedDefects";
		} else if (list == ResolvedDefects) {
			tableName = "resolvedDefects";
		}

		try {
			App.dbManager.executeUpdate(
					"INSERT INTO " + tableName + " (type, description, effort) VALUES (?, ?, ?)",
					item.DefectType,
					item.DefectDescription,
					item.EffortLevel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// remove method for backlog item
	public void remove(BacklogItem item, ArrayList<BacklogItem> list) {
		list.remove(item);

		// remove from database
		String tableName = "";

		if (list == ProductBacklog) {
			tableName = "productBacklog";
		} else if (list == SprintBacklog) {
			tableName = "sprintBacklog";
		} else if (list == CompletedBacklog) {
			tableName = "completedBacklog";
		}

		try {
			App.dbManager.executeUpdate(
					"DELETE FROM " + tableName + " WHERE name = ?",
					item.BacklogItemName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// remove method for defect
	public void remove(Defect item, ArrayList<Defect> list) {
		list.remove(item);

		// remove from database
		String tableName = "";

		if (list == UnresolvedDefects) {
			tableName = "unresolvedDefects";
		} else if (list == ResolvedDefects) {
			tableName = "resolvedDefects";
		}

		try {
			App.dbManager.executeUpdate(
					"DELETE FROM " + tableName + " WHERE type = ? AND description = ? AND effort = ?",
					item.DefectType,
					item.DefectDescription,
					item.EffortLevel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String stringProject() {
		return "";
	}

}
