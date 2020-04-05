/**
 * 
 */
package wolfpub;

import java.sql.SQLException;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import wolfpub.WolfPubDb;

/**
 * @author Chris Suh
 *
 */
@Command(name = "project3.WolfPub", sortOptions = false, header = "@|blue WolfPub Publishing House|@", description = {
		"", "Edit, Publish, and Order publications", }, optionListHeading = "@|bold %nOptions|@:%n")
public class WolfPub implements Runnable {
	static WolfPubDb db = null;
	
	@Option(names = {"--list_tables"}, description = "Connect to wolfPubDb and print a list of tables")
	boolean list;

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

	// Run gets called by picocli after parsing.
	@Override
	public void run() {
		if (!helpRequested
				&& !list) {
			CommandLine.usage(this, System.err);
			return;
		}

		if (list) { 
			list();
		}
		
		if (helpRequested) {
			longHelp();
		}
	}

	/**
	 * Print longer help text
	 */
	public void longHelp() {
		System.out.println("Stub for help output longer than normal usage");
	}
	
	/**
	 * Print all tables in database
	 */
	public void list() {
		db.listTables();
	}
}
