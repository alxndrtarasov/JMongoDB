package bdvisualisation;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.bson.Document;

public class ContentTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public ContentTable(List<Document> objs) {
		Object[] columnTitles = { "id", "obj_name", "data", "description" };
		System.out.println(objs);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		int rsSize = objs.size();

		Object[][] rowData = new Object[rsSize][4];
		int i = 0;
		Iterator<Document> iterator = objs.iterator();
		while (iterator.hasNext()) {
			Document current = iterator.next();
			rowData[i][0] = current.get("id");
			rowData[i][1] = current.get("name");
			rowData[i][2] = current.get("date");
			rowData[i][3] = current.get("description");
			i++;
		}
		table = new JTable(rowData, columnTitles);
		this.add(table);
		;
	}
}
