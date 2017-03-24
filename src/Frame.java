/**
 * this class represents the Game of Life
 * the differences between what we were asked to do and the game itself are
 * 
 * 1 - represents life - instead of a fully colored cell
 * 0 - represents death - instead of a blank cell
 * 
 * instead of a loot to continue the game , the user just need to press the big button on the middle to move
 * forward to the next stage of the game
 * 
 * stages are counted and displayed on the screen in the middle - so the player always knows which stage is displayed
 * the game will exit when the X button on the top left corner is clicked
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JTable matrix;
	private JTextField textField;
	private int rows = 10; //define the size of the table to build - will affect the temp table on rePaint()
	private int cols = 10; //define the size of the table to build - will affect the temp table on rePaint()
	final int LIFE = 1; //will represent life
	final int DEATH = 0; //will represent death
	int stage = 1; //will represent the stage that the game is in
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame
	 */
	public Frame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/WindowBuilder/resources/MatrixIcon.png")));
		setTitle("Game of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 577);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBackground(new Color(0, 0, 128));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(0, 0, 139));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		
		/*
		 * create Matrix
		 */
		matrix = new JTable(); //create a new jtable to act as the board of the game
		matrix.setGridColor(Color.BLACK); //set grid color of table to black
		matrix.setModel(new DefaultTableModel(rows,cols)); //set the table's size
		fillTable(); //fill the table with random 1's or 0's

		
		/*
		 * set constant size for each cell on the table
		 */
		matrix.getColumnModel().getColumn(0).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(0).setMinWidth(20);
		matrix.getColumnModel().getColumn(1).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(1).setMinWidth(20);
		matrix.getColumnModel().getColumn(2).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(2).setMinWidth(20);
		matrix.getColumnModel().getColumn(3).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(3).setMinWidth(20);
		matrix.getColumnModel().getColumn(4).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(4).setMinWidth(20);
		matrix.getColumnModel().getColumn(5).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(5).setMinWidth(20);
		matrix.getColumnModel().getColumn(6).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(6).setMinWidth(20);
		matrix.getColumnModel().getColumn(7).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(7).setMinWidth(20);
		matrix.getColumnModel().getColumn(8).setPreferredWidth(22);
		matrix.getColumnModel().getColumn(8).setMinWidth(20);
		matrix.getColumnModel().getColumn(9).setPreferredWidth(20);
		matrix.getColumnModel().getColumn(9).setMinWidth(20);
		matrix.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		
		
		/*
		 * create text field
		 */
		textField = new JTextField();
		textField.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 14));
		textField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textField.setColumns(10);
		textField.setHorizontalAlignment(SwingConstants.CENTER); //align text to the center of the textfield
		textField.setText("Welcome to the Game of Life!! - Initial level is displayed");
		
		/*
		 * create jbutton - to act as the button that will continue the game
		 */
		JButton nextButton = new JButton("For the next step - press here");
		nextButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(124, 252, 0), null, null, null));
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				textField.setText("Displaying Stage " + stage);
				rePaint();
				stage ++;
			}
		});
		
		/*
		 * create the matrix logo on the right
		 */
		JLabel Logo1 = new JLabel("");
		Logo1.setIcon(new ImageIcon(Frame.class.getResource("/WindowBuilder/resources/matrix1.png")));
		
		/*
		 * create the Game of Life logo on the left
		 */
		JLabel golLogo = new JLabel("");
		golLogo.setIcon(new ImageIcon(Frame.class.getResource("/WindowBuilder/resources/gol_logo2.png")));
		
		JLabel lblCreatedByAlex = new JLabel("Created By: Alex Cherniak");
		lblCreatedByAlex.setForeground(new Color(255, 255, 0));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addComponent(golLogo, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(matrix, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(Logo1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(97, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(661, Short.MAX_VALUE)
					.addComponent(lblCreatedByAlex)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(137)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(nextButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
					.addContainerGap(162, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(golLogo)
						.addComponent(Logo1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addComponent(matrix, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(49)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
					.addComponent(lblCreatedByAlex)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	//a Method to fill the table with random 1's or 0's that will represent death(0) or life(1)
	private void fillTable()
	{	
		Random rn = new Random();
		int min = 0; //minimum value to set in the table - represents no life
		int max = 1; //max value to set in the table - represents life
		int rows =10 , col = 10;
		for(int i=0; i<rows;i++)
			for(int j=0;j<col;j++)
			{
				matrix.setValueAt(rn.nextInt(max - min +1)+min,i,j); //fill the matrix with random numbers 1 or 0
			}
	}
	
	/*
	 * the repaint method as described on the task
	 * will repaint / change the values on the displayed matrix according to the rules of the game of life
	 */
	public void rePaint()
	{
		int tempVal; //temporary int
		JTable temp = new JTable();
		/*
		 * will create a temp table the same size as the one displayed on the screen (matrix)
		 */
		temp.setModel(new DefaultTableModel(rows,cols));
		
		
		/*
		 * now we need to check each cell on the matrix and determine if he lives or dies on the next round
		 * a new painting will be created to a temp matrix
		 * after the whole temp matrix is complete with the next step - it will be copied back to the 
		 * displayed matrix and the new scheme will be shown to the player
		 */
		for(int i=0;i<=cols-1;i++)
		{
			for(int j=0;j<=rows-1;j++)
			{
				tempVal = Integer.parseInt(matrix.getValueAt(j, i).toString()); //extract the value from the matrix cell
				if(tempVal < LIFE) //means its 0 and need to check for birth in the next round
				{
					if(checkLiveNeighbors(j , i) == 3) //should be birth on that spot
					{
						temp.setValueAt(LIFE, j, i); //set life on temp matrix
					}
					else
					{
						temp.setValueAt(DEATH, j, i); //set death on temp matrix
					}
				}
				if(tempVal == LIFE)//there is life on that cell - need to check if it will keep existing or die
				{
					if(checkLiveNeighbors(j, i) == 2 || checkLiveNeighbors(j, i) == 3) //need to keep existing
					{
						temp.setValueAt(LIFE, j, i); //set life on temp matrix
					}
					else //the cell dies
					{
						temp.setValueAt(DEATH, j, i); //set death on temp matrix
					}
				}
			}
		}
		
		/*
		 * at this stage the temp matrix should be complete with all the values of the next stage
		 */
		
		//copy all the data from the temp matrix to the visible matrix
		for(int i=0;i<=cols-1;i++)
		{
			for(int j=0;j<=rows-1;j++)
			{
				matrix.setValueAt(Integer.parseInt(temp.getValueAt(j, i).toString()), j, i);
			}
		}
		
	}
	
	/*
	 * a method that will return the number of live neighbors that the current cell has
	 */
	private int checkLiveNeighbors(int row , int col)
	{
		int numberOfLiveNeighbors = 0;
		
		/*
		 * check corners
		 */
		if(row == 0 && col == 0) //top left corner - only has 3 neighbors
		{
			if(checkLife(row, col+1)) //neighbor to the right
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col+1)) //neighbor to the right + down
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col)) //neighbor below
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		if(row == 0 && col == cols-1) //top right corner - only has 3 neighbors
		{
			if(checkLife(row, col-1)) //neighbor to the left
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col-1)) //neighbor to the left + down
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col)) //neighbor below
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		if(row == rows-1 && col == cols-1) //bottom right corner - only has 3 neighbors
		{
			if(checkLife(row, col-1)) //neighbor to the left
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col-1)) //neighbor to the left + up
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col)) //neighbor above
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		if(row == rows-1 && col == 0) //bottom left corner - only has 3 neighbors
		{
			if(checkLife(row, col+1)) //neighbor to the right
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col+1)) //neighbor to the right + up
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col)) //neighbor above
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		/*
		 * check cells that are close to the edge - 5 neighbors
		 */
		if(row == 0) //the top row AND not in one of the corners
		{
			if(checkLife(row, col+1)) //right neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col)) //neighbor below
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row, col-1)) //left neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col+1)) //neighbor down + right
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col-1)) //neighbor down + left
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		if(row == rows-1) //the bottom row AND not in one of the corners
		{
			if(checkLife(row, col+1)) //right neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col)) //neighbor above
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row, col-1)) //left neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col+1)) //neighbor up + right
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col-1)) //neighbor up + left
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		if(col == 0) //the left col AND not in one of the corners
		{
			if(checkLife(row, col+1)) //right neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col)) //neighbor below
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col)) //neighbor above
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col+1)) //neighbor down + right
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col+1)) //neighbor up + right
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		if(col == cols-1) //the right col AND not in one of the corners
		{
			if(checkLife(row, col-1)) //left neighbor
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col)) //neighbor below
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col)) //neighbor above
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row+1, col-1)) //neighbor down + left
			{
				numberOfLiveNeighbors++;
			}
			if(checkLife(row-1, col-1)) //neighbor up + left
			{
				numberOfLiveNeighbors++;
			}
			return numberOfLiveNeighbors;
		}
		
		/*
		 * check middle cells - 8 neighbors - will only get to this stage if passed cell is not on corners
		 * or on one of the edges
		 */
		if(checkLife(row, col+1)) //right neighbor
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row+1, col+1)) //right + down neighbor
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row-1, col+1)) //right + up neighbor
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row+1, col)) //neighbor below
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row-1, col)) //neighbor above
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row, col-1)) //left neighbor
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row+1, col-1)) //left + down neighbor
		{
			numberOfLiveNeighbors++;
		}
		if(checkLife(row-1, col-1)) //left + up neighbor
		{
			numberOfLiveNeighbors++;
		}
		return numberOfLiveNeighbors;
		
	}
	
	/*
	 * method checks the life on that cell
	 * return true when there is a life 
	 * return false when there is no life
	 */
	private boolean checkLife(int row , int col)
	{
		if(Integer.parseInt(matrix.getValueAt(row, col).toString()) == LIFE)
		{
			return true;
		}
		return false;
	}
}
