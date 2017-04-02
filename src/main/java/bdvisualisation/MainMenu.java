package bdvisualisation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import worker.BDWorker;
import worker.MongoDBWorker;

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SimpleBDOpenner openner;
	private BDWorker worker = openner.getVisualizator().getWorker();

	public BDOpenner getOpenner() {
		return openner;
	}

	public void setOpenner(SimpleBDOpenner openner) {
		this.openner = openner;
	}

	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		worker.connect(MongoDBWorker.defDbName, MongoDBWorker.login, MongoDBWorker.password);
		setLayout(new GridLayout(1, 1));
		JLabel label = new JLabel("Enter name of DataBase here:");
		JTextField bdName = new JTextField();
		JButton defStart = new JButton("Start DB");
		defStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					worker.connect(MongoDBWorker.defDbName, MongoDBWorker.login, MongoDBWorker.password);
					((MongoDBWorker) worker).setDefName(bdName.getText());
					JFrame baseFrame = openner.open();
					baseFrame.setVisible(true);
				} catch (Exception ex) {
					System.err.println("no such db yet. Create it first");
					worker.connect(MongoDBWorker.defDbName, MongoDBWorker.login, MongoDBWorker.password);
				}
			}
		});

		JButton create = new JButton("Create DB");
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				worker.connect(MongoDBWorker.defDbName, MongoDBWorker.login, MongoDBWorker.password);
				worker.createDatabase(bdName.getText());
				((MongoDBWorker) worker).setDefName(bdName.getText());
				JFrame baseFrame = openner.open();
				baseFrame.setVisible(true);
			}

		});

		JButton delete = new JButton("Delete DB");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worker.connect(MongoDBWorker.defDbName, MongoDBWorker.login, MongoDBWorker.password);
				try {
					worker.deleteDatabase(bdName.getText());
				} catch (Exception exe) {
					bdName.setText("a ee i net");
				}
			}
		});
		add(label);
		add(bdName);
		add(defStart);
		add(create);
		add(delete);
		setVisible(true);
	}

	public static void main(String[] args) {
		openner = new SimpleBDOpenner();
		openner.setVisualizator(new SelectionListenerBDVisualizator(new MongoDBWorker()));
		MainMenu menu = new MainMenu();
	}
}
