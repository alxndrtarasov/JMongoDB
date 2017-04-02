package bdvisualisation.forms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import worker.BDWorker;
import worker.MongoDBWorker;

public class DelForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DelForm(BDWorker worker) {
		setLayout(new GridLayout(3, 2));

		JLabel inputLabel = new JLabel("Input:");
		JLabel fieldLabel = new JLabel("Field:");

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
					worker.deleteById(MongoDBWorker.defName, input.getText());
				} else {
					worker.deleteByFieldValue(MongoDBWorker.defName, (String) fieldChooser.getSelectedItem(),
							input.getText());
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
