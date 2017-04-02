package bdvisualisation;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bson.Document;

import worker.BDWorker;

public class SelectionListenerBDVisualizator implements BDVisualizator {

	BDWorker worker;

	@Override
	public BDWorker getWorker() {
		return worker;
	}

	public void setWorker(BDWorker worker) {
		this.worker = worker;
	}

	SelectionListenerBDVisualizator(BDWorker worker) {
		this.worker = worker;
	}

	@Override
	public JScrollPane getTable() {
		final JTable table;
		Object[] columnTitles = { "id", "name", "date", "description" };
		Object[][] rowData = null;
		List<Document> objs = worker.getAllObjects();
		System.out.println(objs);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		int rsSize = objs.size();

		rowData = new Object[rsSize][4];
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
		table.setCellSelectionEnabled(false);
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selectedData = null;

				int[] selectedRow = table.getSelectedRows();
				int[] selectedColumns = table.getSelectedColumns();

				for (int i = 0; i < selectedRow.length; i++) {
					for (int j = 0; j < selectedColumns.length; j++) {
						selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
					}
				}
				System.out.println("Selected: " + selectedData);

			}
		});

		JScrollPane result = new JScrollPane(table);
		result.setSize(300, 200);
		return result;
	}

}
