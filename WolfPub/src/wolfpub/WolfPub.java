package wolfpub;
/**
 * 
 */


import java.sql.SQLException;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import tasks.*;

/**
 * @author Chris Suh
 *
 */
@Command(name = "wolfpub", sortOptions = false, header = "@|blue WolfPub Publishing House|@", description = {
		"", "Edit, Publish, and Order publications", }, optionListHeading = "@|bold %nOptions|@:%n",
				subcommands = {
						Distribution.class,/*
						EditingPublishing.class,
						Production.class,*/
						Reports.class						
				})
public class WolfPub implements Runnable {
	private static WolfPubDb db = null;
	
	@Option(names = {"-l", "--list_tables"}, description = "Connect to wolfPubDb and print a list of tables")
	boolean list;
	
	@Option(names = {"-d", "--dump_table"}, paramLabel = "table", split = ",", description = "Show the contents of any table in the database")
	List<String> tables;

	@Option(names = { "-h", "--help" }, usageHelp = true, description = "Display a help message")
	private boolean helpRequested = false;

	/**
	 * @param args Parsed by picocli.
	 */
	public static void main(String[] args) {
		try {
			db = new WolfPubDb();
		} catch (SQLException e) {
			System.err.println("DB Creation error");
			e.printStackTrace();
			return;
		}
		
		int exitCode = new CommandLine(new WolfPub()).execute(args);
		assert exitCode == 0;
	}

	/**
	 *  Run gets called by picocli after parsing.
	 */
	@Override
	public void run() {
		if (!helpRequested
				&& !list
				&& tables == null) {
			CommandLine.usage(this, System.err);
			return;
		}

		if (list) { 
			list();
		}
		
		if (tables != null && !tables.isEmpty()) {
			dumpTables();
		}
		
		if (helpRequested) {
			longHelp();
		}
		
		getDb().close();
	}

	/**
	 * Dump all tables requested.
	 */
	private void dumpTables() {
		for (String table : tables) {
			System.out.printf("%s:%n",table);
			getDb().selectTable(table);
			System.out.println();
		}
	}

	/**
	 * Print all tables in database
	 */
	public void list() {
		getDb().listTables();
	}
	
	/**
	 * Print longer help text
	 */
	public void longHelp() {
		System.out.println("Stub for help output longer than normal usage");
	}

	public static WolfPubDb getDb() {
		return db;
	}
}
