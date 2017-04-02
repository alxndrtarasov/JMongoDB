package bdvisualisation;

import javax.swing.JScrollPane;

import worker.BDWorker;

public interface BDVisualizator {
	public JScrollPane getTable();

	public BDWorker getWorker();
}
