import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Scoreframe extends JFrame {
	class grades extends JFrame{
							ImageIcon Bronze = new ImageIcon("Bronze.png");
							ImageIcon Silver = new ImageIcon("Silver.png");
							ImageIcon Gold = new ImageIcon("Gold.png");
							ImageIcon Platinum = new ImageIcon("Platinum.png");
							ImageIcon Diamond= new ImageIcon("Diamond.png");
							
							JLabel B=new JLabel(Bronze);
							JLabel S=new JLabel(Silver);
							JLabel G=new JLabel(Gold);
							JLabel P=new JLabel(Platinum);
							JLabel D=new JLabel(Diamond);
							
							ImageIcon B2 = new ImageIcon("Bronze.png");
							ImageIcon S2 = new ImageIcon("Silver.png");
							ImageIcon G2 = new ImageIcon("Gold.png");
							ImageIcon P2 = new ImageIcon("Platinum.png");
							ImageIcon D2= new ImageIcon("Diamond.png");
							
							JLabel Br2=new JLabel(Bronze);
							JLabel Si2=new JLabel(Silver);
							JLabel go2=new JLabel(Gold);
							JLabel Pl2=new JLabel(Platinum);
							JLabel Di2=new JLabel(Diamond);
			
							grades()
							{
								setTitle("등급");
								setLayout(null);
								setBackground(Color.WHITE);
								setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								
								class gradesPanel extends JPanel
								{
									gradesPanel(int presentScore)
									{
										setLayout(null);
										if(presentScore<50)
										{	
											JLabel k = new JLabel("당신은 브론즈 입니다");
											k.setBounds(95, 20, 200, 15);
											add(k);
											Br2.setBounds(100,35,100,100);
											add(Br2);
										}
										else if(50<=presentScore&&presentScore<100)
										{	
											JLabel k = new JLabel("당신은 실버 입니다");
											k.setBounds(95, 20, 200, 15);
											add(k);
											Si2.setBounds(100,35,100,100);
											add(Si2);
										}
										else if(100<=presentScore&&presentScore<200)
										{	
											JLabel k = new JLabel("당신은 골드 입니다");
											k.setBounds(95, 20, 200, 15);
											add(k);
											go2.setBounds(100,35,100,100);
											add(go2);
										}
										else if(200<=presentScore&&presentScore<400)
										{	
											JLabel k = new JLabel("당신은 플레티넘 입니다");
											k.setBounds(95, 20, 200, 15);
											add(k);
											Pl2.setBounds(100,35,100,100);
											add(Pl2);
										}
										else
										{	
											JLabel k = new JLabel("당신은 다이아 입니다");
											k.setBounds(95, 20, 200, 15);
											add(k);
											Di2.setBounds(100,35,100,100);
											add(Di2);
										}
									}
								}								
								gradesPanel gP=new gradesPanel(100);
								gP.setBounds(0, 0, 300, 300);
								add(gP);
								JLabel l1 = new JLabel("브론즈(50점 미만)");
								JLabel l2 = new JLabel("실버(50점 이상 100점 미만)");
								JLabel l3 = new JLabel("골드(100점 이상 200점 미만)");
								JLabel l4 = new JLabel("플레티넘(200점 이상 400점 미만)");
								JLabel l5 = new JLabel("다이아(400점 이상)");
								
								B.setBounds(10,300,100,100); 
								S.setBounds(10,350,100,100);
								G.setBounds(10,400,100,100);
								P.setBounds(10,450,100,100);
								D.setBounds(10,500,100,100);
								l1.setBounds(100, 350, 300, 20);
								l2.setBounds(100, 400, 300, 20);
								l3.setBounds(100, 450, 300, 20);
								l4.setBounds(100, 500, 300, 20);
								l5.setBounds(100, 550, 300, 20);
								
								add(B);
								add(S);
								add(G);
								add(P);
								add(D);
								
								add(l1);
								add(l2);
								add(l3);
								add(l4);
								add(l5);
								setSize(300,700);
								setVisible(true);
							}
						}
	}