package 扫雷;

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
	//主窗口初始化
	JFrame frame = new JFrame("扫雷小游戏");//JFrame画窗口
	JButton reset = new JButton("重来");//JButton定义按钮
	Container container = new Container(); 
	
	//游戏的数据结构
	final int row = 18;//定义不变的final行数
	final int col = 18;//定义不变的final列数
	final int number_lei = 38;//定义不变的final雷的个数
	JButton[][] lei_button = new JButton[row][col];//定义二维数组存放按钮
	int[][] lei_counts = new int[row][col];//定义每个按钮周围有几颗雷即数字或者为雷的数字
	int leicode = 9;//定义雷代表的数字
	
	//构造函数
	public saolei(){
		//显示窗口
		frame.setBounds(200,50,800,600);//设定大小
		frame.setResizable(false);//不能改变大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭行为
		frame.setLayout(new BorderLayout());//BorderLayout布局
		
		//添加重来按钮
		reset.setBackground(Color.blue);//设置按钮背景色
		reset.setOpaque(true);//设置不透明度		
		reset.addActionListener(this);//给按钮加时间监听器
		frame.add(reset, BorderLayout.NORTH);//加入按钮放在NORTH布局位置
		
		//添加container容器，放一系列按钮
		frame.add(container, BorderLayout.CENTER);//加入容器放在CENTER布局位置
		container.setLayout(new GridLayout(row,col));//向container添加Grid网格布局
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				JButton button = new JButton();//初始化当前按钮
				lei_button[i][j] = button;//给当前列、当前行的按钮赋值
				lei_button[i][j].setOpaque(true);//设置按钮初始不可见
				lei_button[i][j].setBackground(Color.gray);//设置按钮背景色为灰色
				lei_button[i][j].addActionListener(this);//给按钮加时间监听器
				container.add(lei_button[i][j]);//将button放到容器里面
			}
		}
		
		addlei();//埋雷		
		callei();//计算按钮周围雷的个数
		
		frame.setVisible(true);//显示出来	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		JButton btn = (JButton)e.getSource();//得到当前事件的按钮
		if(btn.equals(reset)){
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					lei_button[i][j].setText("");//按钮重置为空
					lei_button[i][j].setEnabled(true);//按钮重置可以点开
					lei_button[i][j].setBackground(Color.gray);//按钮重置背景色
					lei_counts[i][j] = 0;//按钮数字重新置0
				}
			}
			addlei();//重新埋雷
			callei();//重新计算按钮的数字或者为雷
		}
		else{
			int count = 0;
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					if(btn.equals(lei_button[i][j])){
						count = lei_counts[i][j];//给当前点击的按钮赋按钮上的数字
						//判断是否为雷
						if(count == leicode){
							losegame();//输掉比赛
							JOptionPane.showMessageDialog(frame, "You lose!");//如果点到雷则弹出对话框提示输了
						}
						else{
							open(i, j);//打开按钮
							win();//赢得比赛
						}
					}
				}
			}		
		}
	}
	
	//埋雷
	void addlei(){
		Random random = new Random();//初始化随机数
		int randrow, randcol;//定义随机行和列
		for(int i = 0; i < number_lei; i++){
			randrow = random.nextInt(row);//nextInt随机产生一个0到row的行数
			randcol = random.nextInt(col);//nextInt随机产生一个0到col的列数
			//判断该位置是否有雷
			if(lei_counts[randrow][randcol] == leicode)
				i--;
			else
				lei_counts[randrow][randcol] = leicode;//给改位置设置成雷的数字
		}
	}
	
	//计算按钮周围雷的个数
	void callei(){
		int count;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				count = 0;
				if(lei_counts[i][j] == leicode)
					continue;//跳出循环，结束continue之后的语句执行
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
	
	//点到雷就输掉游戏
	void losegame(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				int count = lei_counts[i][j];
				if(count == leicode){
					lei_button[i][j].setBackground(Color.red);//设置按钮背景色为红色
					lei_button[i][j].setText("X");//给点到的按钮添加显示文本，显示是雷
					lei_button[i][j].setEnabled(false);//按钮被点击以后不能再被点击
				}
				else{
					lei_button[i][j].setBackground(Color.white);//设置按钮背景色为白色
					lei_button[i][j].setText(count + "");//显示周围有几颗雷,+""因为整数加空格就变成字符了
					lei_button[i][j].setEnabled(false);//按钮被点击以后不能再被点击
				}
			}
		}
	}
	
	//打开点到的按钮，如果为0则打开周围不是雷的按钮
	void open(int i, int j){
		//如果按钮被打开了，则返回
		if(lei_button[i][j].isEnabled() == false)
			return;
		lei_button[i][j].setEnabled(false);//按钮被点击以后不能再被点击	
		lei_button[i][j].setBackground(Color.green);//设置按钮背景色为绿色
		//如果按下的按钮数字为0，则有递归把周围除雷以外的按钮都打开,按钮为0的不显示
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
			lei_button[i][j].setText(lei_counts[i][j] + "");//显示周围有几颗雷,+""因为整数加空格就变成字符了
		}
	}
	
	//判断是否赢得比赛，如果赢则弹出对话框
	void win(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				//如果有按钮还没有点开且不是雷的则返回
				if(lei_button[i][j].isEnabled() == true && lei_counts[i][j] != leicode)
					return;
			}
		}
		JOptionPane.showMessageDialog(frame, "You win!");//如果没有未打开的则弹出对话框提示赢了
	}
	
	//主函数
	public static void main(String[] args){	
		saolei Saolei = new saolei();
	}
}
