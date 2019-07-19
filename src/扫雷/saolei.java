package ɨ��;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class saolei implements ActionListener {
	//�����ڳ�ʼ��
	JFrame frame = new JFrame("ɨ��С��Ϸ");//JFrame������
	JButton reset = new JButton("����");//JButton���尴ť
	Container container = new Container(); 
	
	//��Ϸ�����ݽṹ
	final int row = 18;//���岻���final����
	final int col = 18;//���岻���final����
	final int number_lei = 38;//���岻���final�׵ĸ���
	JButton[][] lei_button = new JButton[row][col];//�����ά�����Ű�ť
	int[][] lei_counts = new int[row][col];//����ÿ����ť��Χ�м����׼����ֻ���Ϊ�׵�����
	int leicode = 9;//�����״��������
	
	//���캯��
	public saolei(){
		//��ʾ����
		frame.setBounds(200,50,800,600);//�趨��С
		frame.setResizable(false);//���ܸı��С
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�ر���Ϊ
		frame.setLayout(new BorderLayout());//BorderLayout����
		
		//���������ť
		reset.setBackground(Color.blue);//���ð�ť����ɫ
		reset.setOpaque(true);//���ò�͸����		
		reset.addActionListener(this);//����ť��ʱ�������
		frame.add(reset, BorderLayout.NORTH);//���밴ť����NORTH����λ��
		
		//���container��������һϵ�а�ť
		frame.add(container, BorderLayout.CENTER);//������������CENTER����λ��
		container.setLayout(new GridLayout(row,col));//��container���Grid���񲼾�
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				JButton button = new JButton();//��ʼ����ǰ��ť
				lei_button[i][j] = button;//����ǰ�С���ǰ�еİ�ť��ֵ
				lei_button[i][j].setOpaque(true);//���ð�ť��ʼ���ɼ�
				lei_button[i][j].setBackground(Color.gray);//���ð�ť����ɫΪ��ɫ
				lei_button[i][j].addActionListener(this);//����ť��ʱ�������
				container.add(lei_button[i][j]);//��button�ŵ���������
			}
		}
		
		addlei();//����		
		callei();//���㰴ť��Χ�׵ĸ���
		
		frame.setVisible(true);//��ʾ����	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		JButton btn = (JButton)e.getSource();//�õ���ǰ�¼��İ�ť
		if(btn.equals(reset)){
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					lei_button[i][j].setText("");//��ť����Ϊ��
					lei_button[i][j].setEnabled(true);//��ť���ÿ��Ե㿪
					lei_button[i][j].setBackground(Color.gray);//��ť���ñ���ɫ
					lei_counts[i][j] = 0;//��ť����������0
				}
			}
			addlei();//��������
			callei();//���¼��㰴ť�����ֻ���Ϊ��
		}
		else{
			int count = 0;
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					if(btn.equals(lei_button[i][j])){
						count = lei_counts[i][j];//����ǰ����İ�ť����ť�ϵ�����
						//�ж��Ƿ�Ϊ��
						if(count == leicode){
							losegame();//�������
							JOptionPane.showMessageDialog(frame, "You lose!");//����㵽���򵯳��Ի�����ʾ����
						}
						else{
							open(i, j);//�򿪰�ť
							win();//Ӯ�ñ���
						}
					}
				}
			}		
		}
	}
	
	//����
	void addlei(){
		Random random = new Random();//��ʼ�������
		int randrow, randcol;//��������к���
		for(int i = 0; i < number_lei; i++){
			randrow = random.nextInt(row);//nextInt�������һ��0��row������
			randcol = random.nextInt(col);//nextInt�������һ��0��col������
			//�жϸ�λ���Ƿ�����
			if(lei_counts[randrow][randcol] == leicode)
				i--;
			else
				lei_counts[randrow][randcol] = leicode;//����λ�����ó��׵�����
		}
	}
	
	//���㰴ť��Χ�׵ĸ���
	void callei(){
		int count;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				count = 0;
				if(lei_counts[i][j] == leicode)
					continue;//����ѭ��������continue֮������ִ��
				if(i > 0 && j > 0 && lei_counts[i - 1][j - 1] == leicode)
					count++;
				if(i > 0 && lei_counts[i - 1][j] == leicode)
					count++;
				if(i > 0 && j < (col - 1) && lei_counts[i - 1][j + 1] == leicode)
					count++;
				if(j > 0 && lei_counts[i][j - 1] == leicode)
					count++;
				if(j < (col - 1) && lei_counts[i][j + 1] == leicode)
					count++;
				if(i < (row - 1) && j > 0 && lei_counts[i + 1][j - 1] == leicode)
					count++;
				if(i < (row - 1) && lei_counts[i + 1][j] == leicode)
					count++;
				if(i < (row - 1) && j < (col - 1) && lei_counts[i + 1][j + 1] == leicode)
					count++;
				lei_counts[i][j] = count;
			}
		}
	}
	
	//�㵽�׾������Ϸ
	void losegame(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				int count = lei_counts[i][j];
				if(count == leicode){
					lei_button[i][j].setBackground(Color.red);//���ð�ť����ɫΪ��ɫ
					lei_button[i][j].setText("X");//���㵽�İ�ť�����ʾ�ı�����ʾ����
					lei_button[i][j].setEnabled(false);//��ť������Ժ����ٱ����
				}
				else{
					lei_button[i][j].setBackground(Color.white);//���ð�ť����ɫΪ��ɫ
					lei_button[i][j].setText(count + "");//��ʾ��Χ�м�����,+""��Ϊ�����ӿո�ͱ���ַ���
					lei_button[i][j].setEnabled(false);//��ť������Ժ����ٱ����
				}
			}
		}
	}
	
	//�򿪵㵽�İ�ť�����Ϊ0�����Χ�����׵İ�ť
	void open(int i, int j){
		//�����ť�����ˣ��򷵻�
		if(lei_button[i][j].isEnabled() == false)
			return;
		lei_button[i][j].setEnabled(false);//��ť������Ժ����ٱ����	
		lei_button[i][j].setBackground(Color.green);//���ð�ť����ɫΪ��ɫ
		//������µİ�ť����Ϊ0�����еݹ����Χ��������İ�ť����,��ťΪ0�Ĳ���ʾ
		if(lei_counts[i][j] == 0){
			if(i > 0 && j > 0 && lei_counts[i - 1][j - 1] != leicode)
				open(i - 1, j - 1);
			if(i > 0 && lei_counts[i - 1][j] != leicode)
				open(i - 1, j);
			if(i > 0 && j < (col - 1) && lei_counts[i - 1][j + 1] != leicode)
				open(i - 1, j + 1);
			if(j > 0 && lei_counts[i][j - 1] != leicode)
				open(i, j - 1);
			if(j < (col - 1) && lei_counts[i][j + 1] != leicode)
				open(i, j + 1);
			if(i < (row - 1) && j > 0 && lei_counts[i + 1][j - 1] != leicode)
				open(i + 1, j - 1);
			if(i < (row - 1) && lei_counts[i + 1][j] != leicode)
				open(i + 1, j);
			if(i < (row - 1) && j < (col - 1) && lei_counts[i + 1][j + 1] != leicode)
				open(i + 1, j + 1);
		}
		else{
			lei_button[i][j].setText(lei_counts[i][j] + "");//��ʾ��Χ�м�����,+""��Ϊ�����ӿո�ͱ���ַ���
		}
	}
	
	//�ж��Ƿ�Ӯ�ñ��������Ӯ�򵯳��Ի���
	void win(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				//����а�ť��û�е㿪�Ҳ����׵��򷵻�
				if(lei_button[i][j].isEnabled() == true && lei_counts[i][j] != leicode)
					return;
			}
		}
		JOptionPane.showMessageDialog(frame, "You win!");//���û��δ�򿪵��򵯳��Ի�����ʾӮ��
	}
	
	//������
	public static void main(String[] args){	
		saolei Saolei = new saolei();
	}
}
