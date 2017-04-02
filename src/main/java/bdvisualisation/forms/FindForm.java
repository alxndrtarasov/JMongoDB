package bdvisualisation.forms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.bson.Document;

import bdvisualisation.ContentTable;
import worker.BDWorker;
import worker.MongoDBWorker;

public class FindForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FindForm(BDWorker worker) {
		setLayout(new GridLayout(3, 2));
		JFrame findResult = new JFrame("Results of search");

		JLabel inputLabel = new JLabel("Input:");
		JLabel fieldLabel = new JLabel("Field:");

		findResult.setSize(400, 400);

		JComboBox<String> fieldChooser = new JComboBox<>();

		fieldChooser.addItem("id");
		fieldChooser.addItem("name");
		fieldChooser.addItem("date");
		fieldChooser.addItem("description");

		JTextField input = new JTextField();

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldChooser.getSelectedItem().equals("id")) {
					Document foundObj = worker.find(MongoDBWorker.defName, Integer.parseInt(input.getText()));
					if (foundObj != null) {
						List<Document> nList = new ArrayList<Document>();
						nList.add(foundObj);
						findResult.add(new ContentTable(nList));
						findResult.setVisible(true);
					} else {
						input.setText("Not found");
					}
				} else {
					try {
						List<Document> foundObjs = worker.find(MongoDBWorker.defName,
								(String) fieldChooser.getSelectedItem(), input.getText());
						if (foundObjs != null) {
							findResult.add(new ContentTable(foundObjs));
							findResult.setVisible(true);
						} else {
							input.setText("Not found");
						}
					} catch (Exception e2) {
						input.setText("Not found");
					}
				}
			}
		});

		add(fieldLabel);
		add(fieldChooser);
		add(inputLabel);
		add(input);
		add(ok);

		setSize(300, 200);

		setVisible(true);
	}
}
