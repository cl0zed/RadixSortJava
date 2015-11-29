//Комментарии относятся к строчке ниже ккомментария

package RadixSort;
//Подключение библиотек
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

public class gui extends JFrame {
	/**
	 * 
	 */
//Серийный номер класса
	private static final long serialVersionUID = -8535992190957340816L;
//Элементы GUI
	JTextArea startedArea;
	JTextArea resultArea;
	JCheckBox isReverse;
	gui()
	{
		// Создание окна
		super("Sorting GUI"); 
		// Создание текстовых полей
		startedArea = new JTextArea(37, 55); 
		resultArea = new JTextArea(37, 55);
		//операция при закрытии окна
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		//размеры окна
		this.setSize(1366, 720);
		setVisible(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		startedArea.setText("Введите текст");
		//обработка сигналов мыши на левом поле
		startedArea.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == 3)
					startedArea.setText("");
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {			
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
		});
		//обработка нажатий клавиш
		startedArea.addKeyListener(new KeyListener(){
			int count = 0;
			@Override
			
			public void keyPressed(KeyEvent arg0) {
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) count = 0; else 
					if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						if (count < 0) count = 70; else --count;
					else ++count;
				if (count > 60)
				{
					startedArea.append("\n");
					count = 0;
				}				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
		});
		JMenu menu = new JMenu("Menu");
		JMenuItem item = new JMenuItem("Info");
		//создания меню  описания программы
		item.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent ae)
			{
				JOptionPane.showConfirmDialog(null, "Введите список слов в левый столбец, " +
								"нажмите кнопку и в правом столбце " + 
								"получите отсортированный список. \n" + "Если хотите сортировать в обратном порядке(то есть "+
								"начиная с последней буквы), то поставьте галочку в поле Reverse Sorting.\n"+
								"Вы можете открыть текстовый файл с помощью кнопки Open в панели Menu.", "Information", 
								JOptionPane.DEFAULT_OPTION);
			}
		});
		
		JMenuItem closeItem = new JMenuItem("Exit");
		
		
		closeItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				//выход из программы
				System.exit(0);
			}
		});
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				class myFilter extends FileFilter
				{

					@Override
					public boolean accept(File file) {
						return file.isDirectory() || file.getAbsolutePath().endsWith("txt");
					}

					@Override
					public String getDescription() {
						return "Text documents ";
					}
					
				}
				JFileChooser fileOpener = new JFileChooser();
				
	//Создание диалогового окна и фильтра			fileOpener.setAcceptAllFileFilterUsed(false);
				fileOpener.setFileFilter(new myFilter());
				fileOpener.setCurrentDirectory(new File("E"));
				int ret = fileOpener.showDialog(null, "Открыть файл");
				if (ret == JFileChooser.APPROVE_OPTION) 
				{
					File file = fileOpener.getSelectedFile();
					FileReader fr;
					try {
						fr = new FileReader(file);
						startedArea.setText("");
						int c;
						char lastChar;
						int count = 0;
						while((c = fr.read()) != -1) {
							startedArea.append(Character.toString((char)c));
							++count;
							lastChar = (char) c;
							if (Character.toString((char)c ).equals( "\n")) count = 0;
							if (count > 60 )
							{
								if (lastChar != ' ') startedArea.append("-");
								startedArea.append("\n");
								count = 0;
							}
						
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		menu.add(open);
		menu.add(item);
		menu.add(closeItem);
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		this.setJMenuBar(bar);
		
		
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		
		
		resultArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(startedArea);
		scrollPane.setSize(startedArea.getSize());
		JScrollPane	secondScrollPane = new JScrollPane(resultArea);
		
		JLabel lab = new JLabel("====>");
		
		pane.add(scrollPane);
		pane.add(lab);
		pane.add(secondScrollPane);
		
		JButton button = new JButton("Sort left Area");
		
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				long time = System.currentTimeMillis();
				String[] toSort = startedArea.getText().split("\\n");
				String[] Sorted = new String[toSort.length];
				
				if (isReverse.isSelected()) toSort = reverseWords(toSort);
				
				RadixSort.SORT.findMaxLenght(toSort, toSort.length);
				RadixSort.SORT.RadixSort(toSort, Sorted, toSort.length);
				
				if (isReverse.isSelected()) Sorted = reverseWords(Sorted);
				Sorted = correctWords(Sorted);
				String result = "";
				
				for (int i = 0; i < Sorted.length; ++i)
				{
					result = result + Sorted[i] + "\n";
				}
				
				resultArea.setText(result);
				startedArea.setText("Введите новые строки");
				System.out.println("Vremya raboty: " + (System.currentTimeMillis() - time));
			}
		});
		
		isReverse = new JCheckBox("Reverse Sorting");
		
		add(pane, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel(new FlowLayout());
		buttonPane.add(isReverse);
		buttonPane.add(button);
		add(buttonPane, BorderLayout.SOUTH);
	}
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				makeGUI();
			}
		});
	}
	private static void makeGUI()
	{
		new gui();
	}
	//функция переворачивания строк
	private String [] reverseWords(String [] str)
	{
		int j = 0;
		String [] result = new String[str.length];
		for (String x: str)
		{
			String tmp = "";
			for (int i = x.length() - 1; i >= 0; --i)
			{
				tmp += x.charAt(i); 
			}
			result[j++] = tmp;
		}
		return result;
	}
	//Проверка строки на корректность
	private String[] correctWords(String[] str)
	{
		int j = 0;
		String [] result = new String[str.length];
		for(String x: str)
		{
			String tmp = x;
			boolean isEng = false, isRus = false;
			for (int i = 0; i < x.length(); ++i)
			{	
				int currentChar = x.toUpperCase().charAt(i);
				if ((currentChar >= 'A' && currentChar <= 'Z' ) || (currentChar >= 'a' && currentChar <= 'z') ) isEng = true;
				if ((currentChar >= 'А' && currentChar <= 'Я') || (currentChar >= 'а' && currentChar <= 'я') ) isRus = true;		
			}
			if (isRus && isEng)
			{
				tmp += "(rus + eng)";
			}
			result[j++] = tmp;
		}
		return result;
	}
}
