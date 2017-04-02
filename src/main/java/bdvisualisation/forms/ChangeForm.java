package bdvisualisation.forms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bson.Document;

import com.toedter.calendar.JDateChooser;

import worker.BDWorker;
import worker.MongoDBWorker;

public class ChangeForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int currentId;

	public ChangeForm(BDWorker worker) {
		setLayout(new GridLayout(6, 1));

		JLabel idLabel = new JLabel("Id:");
		JLabel nameLabel = new JLabel("Obj_name:");
		JLabel dateLabel = new JLabel("Data:");
		JLabel descriptionLabel = new JLabel("Description:");

		JTextField id = new JTextField();
		JTextField name = new JTextField();
		JDateChooser date = new JDateChooser();

		JComboBox<String> description = new JComboBox<>();
		description.addItem("Fruit");
		description.addItem("Vegetable");

		JPanel idPanel = new JPanel();
		idPanel.setLayout(new GridLayout(1, 2));
		idPanel.add(idLabel);
		idPanel.add(id);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1, 2));
		namePanel.add(nameLabel);
		namePanel.add(name);

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(1, 2));
		datePanel.add(dateLabel);
		datePanel.add(date);

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new GridLayout(1, 2));
		descriptionPanel.add(descriptionLabel);
		descriptionPanel.add(description);

		name.setEditable(false);
		date.setEnabled(false);
		description.setEnabled(false);

		String[] keys = { "name", "date", "description" };

		JButton find = new JButton("Find to change");
		find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					Document currentObj = worker.find(MongoDBWorker.defName, Integer.parseInt(id.getText()));
					currentId = currentObj.getInteger("id");
					name.setEditable(true);
					date.setEnabled(true);
					id.setEditable(false);
					description.setEnabled(true);
					name.setText(currentObj.getString("name"));
					date.setDate(df.parse(currentObj.getString("date")));
					description.setSelectedItem(currentObj.getString("description"));
				} catch (NumberFormatException e1) {
					id.setText("Wrong id formad");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton ok = new JButton("Save changes");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
				worker.update(MongoDBWorker.defName, keys, new String[] { name.getText(), df.format(date.getDate()),
						(String) description.getSelectedItem() }, Integer.decode(id.getText()));
			}
		});

		add(idPanel);
		add(find);
		add(namePanel);
		add(datePanel);
		add(descriptionPanel);
		add(ok);
		setSize(200, 300);
		setVisible(true);
	}
}
