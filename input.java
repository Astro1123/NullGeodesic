import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class input extends JFrame implements ActionListener {
  outside out;
  JTextField textmag;
  JTextField textlx;
  JTextField textly;
  JTextField textdeg;
  JTextField textdt;
  JTextField textlcount;
  JTextField textnacount;
  JTextField textddeg;
  JTextField textdy;
  JTextField textsign;
  JTextField texttype;
  JTextField textoutput;
  JTextField textcf;
  JTextField textbmet;
  JTextField textfilename;
  
  public static void main(String args[]) {
  	input in = new input("Input");
  	in.setVisible(true);
  }
  
  input(String title) {
  	out = new outside();
  	out.inputvar(out.read());
    setTitle(title);
    setBounds(100, 100, 960, 640);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel panel0 = new JPanel();
    panel0.setLayout(new FlowLayout());
	JPanel panel1 = new JPanel();
	panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
	JPanel panel2 = new JPanel();
	panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
	panel0.add(panel1);
	panel0.add(panel2);
	panel.add(panel0);
	
	JPanel pmag = magline();
    panel1.add(pmag);
	JPanel plx = lxline();
    panel1.add(plx);
	JPanel ply = lyline();
    panel1.add(ply);
	JPanel pdeg = degline();
    panel1.add(pdeg);
	JPanel pdt = dtline();
    panel1.add(pdt);
    JPanel plcount = lcountline();
    panel1.add(plcount);
	JPanel pnacount = nacountline();
    panel1.add(pnacount);
	JPanel pddeg = ddegline();
    panel2.add(pddeg);
	JPanel pdy = dyline();
    panel2.add(pdy);
	JPanel psign = signline();
    panel2.add(psign);
	JPanel ptype = typeline();
    panel2.add(ptype);
	JPanel poutput = outputline();
    panel2.add(poutput);
	JPanel pcf = cfline();
    panel2.add(pcf);
	JPanel pbmet = bmetline();
    panel2.add(pbmet);
	JPanel pfilename = filenameline();
    panel2.add(pfilename);
    JPanel p = btnline();
    panel.add(p);
    
    JScrollPane scrollpane = new JScrollPane(panel);
    getContentPane().add(scrollpane, BorderLayout.CENTER);
  }
  
  private JPanel filenameline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("filename : ");
    textfilename = new JTextField(String.valueOf(out.filename),20);
    p.add(label);
    p.add(textfilename);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("出力ファイル名");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel bmetline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("bmet : ");
    textbmet = new JTextField(String.valueOf(out.bmet),20);
    p.add(label);
    p.add(textbmet);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("衝突パラメータbの計算方法 (0 or 1)");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  private JPanel cfline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("cf : ");
    textcf = new JTextField(String.valueOf(out.cf),20);
    p.add(label);
    p.add(textcf);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("ブラックホールに衝突する光の色を変える (Yes : 1 , No : 0)");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel outputline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("output : ");
    textoutput = new JTextField(String.valueOf(out.output),20);
    p.add(label);
    p.add(textoutput);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("出力しない : 0 , ターミナルに出力 : 1 , ファイルに出力 : 2");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel typeline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("type : ");
    texttype = new JTextField(String.valueOf(out.type),20);
    p.add(label);
    p.add(texttype);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("0 : 角度を変化 , 1 : y座標を変化");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel signline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("sign : ");
    textsign = new JTextField(String.valueOf(out.sign),20);
    p.add(label);
    p.add(textsign);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("ブラックホール方向 : 1 , ブラックホールと逆方向 : -1");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel dyline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("dy : ");
    textdy = new JTextField(String.valueOf(out.dy),20);
    p.add(label);
    p.add(textdy);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("y座標の変化 ( AsB で A√B )");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel ddegline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("ddeg : ");
    textddeg = new JTextField(String.valueOf(out.ddeg),20);
    p.add(label);
    p.add(textddeg);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("角度変化");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel nacountline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("nacount : ");
    textnacount = new JTextField(String.valueOf(out.nacount),20);
    p.add(label);
    p.add(textnacount);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("シミュレーションの計算回数の上限");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel lcountline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("lcount : ");
    textlcount = new JTextField(String.valueOf(out.lcount),20);
    p.add(label);
    p.add(textlcount);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("位置or角度を変化させる回数");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel dtline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("dt : ");
    textdt = new JTextField(String.valueOf(out.dt),20);
    p.add(label);
    p.add(textdt);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("シミュレーションの時間間隔");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel degline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("deg : ");
    textdeg = new JTextField(String.valueOf(out.deg),20);
    p.add(label);
    p.add(textdeg);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("光を放つ角度");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel lyline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("ly : ");
    textly = new JTextField(String.valueOf(out.ly),20);
    p.add(label);
    p.add(textly);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("光源のy座標");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel lxline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("lx : ");
    textlx = new JTextField(String.valueOf(out.lx),20);
    p.add(label);
    p.add(textlx);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("光源のx座標");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel magline() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JLabel label = new JLabel("mag : ");
    textmag = new JTextField(String.valueOf(out.mag),20);
    p.add(label);
    p.add(textmag);
    panel.add(p);
  	JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    JLabel label2 = new JLabel("拡大率 (値が大きいほど拡大)");
    p2.add(label2);
    panel.add(p2);
    return panel;
  }
  
  private JPanel btnline() {
  	JPanel p = new JPanel();
    p.setLayout(new FlowLayout());
    JButton btnc1 = new JButton("Cace 1");
    btnc1.addActionListener(this);
    btnc1.setActionCommand("Case 1");
    JButton btnc2 = new JButton("Cace 2");
    btnc2.addActionListener(this);
    btnc2.setActionCommand("Case 2");
    JButton btnc3 = new JButton("Cace 3");
    btnc3.addActionListener(this);
    btnc3.setActionCommand("Case 3");
    JButton btnsave = new JButton("Save");
    btnsave.addActionListener(this);
    btnsave.setActionCommand("Save");
    JButton btnrun = new JButton("Run");
    btnrun.addActionListener(this);
    btnrun.setActionCommand("Run");
    JButton btnquit = new JButton("Quit");
    btnquit.addActionListener(this);
    btnquit.setActionCommand("Quit");
    p.add(btnc1);
    p.add(btnc2);
    p.add(btnc3);
    p.add(btnsave);
    p.add(btnrun);
    p.add(btnquit);
    return p;
  }

  private String[] gettext() {
  	String[] str = new String[out.Line];
  	try {
  		str[0] = String.valueOf(Double.parseDouble(textmag.getText()));
  	} catch(NumberFormatException e) {
  		str[0] = "0.3";
  	}
  	try {
  		str[1] = String.valueOf(Double.parseDouble(textlx.getText()));
  	} catch(NumberFormatException e) {
  		str[1] = "10.0";
  	}
  	try {
  		str[2] = String.valueOf(Double.parseDouble(textly.getText()));
  	} catch(NumberFormatException e) {
  		str[2] = "0.0";
  	}
  	try {
  		str[3] = String.valueOf(Double.parseDouble(textdeg.getText()));
  	} catch(NumberFormatException e) {
  		str[3] = "0.0";
  	}
  	try {
  		str[4] = String.valueOf(Double.parseDouble(textdt.getText()));
  	} catch(NumberFormatException e) {
  		str[4] = "0.005";
  	}
  	try {
  		str[5] = String.valueOf(Integer.parseInt(textlcount.getText()));
  	} catch(NumberFormatException e) {
  		str[5] = "40";
  	}
  	try {
  		str[6] = String.valueOf(Integer.parseInt(textnacount.getText()));
  	} catch(NumberFormatException e) {
  		str[6] = "100000";
  	}
  	try {
  		str[7] = String.valueOf(Double.parseDouble(textddeg.getText()));
  	} catch(NumberFormatException e) {
  		str[7] = "-0.5";
  	}
  	try {
  		str[8] = String.valueOf(Double.parseDouble(textdy.getText()));
  	} catch(NumberFormatException e) {
  		try {
  			String regex = "s";
  			String[] result = textdy.getText().split(regex, 0);
  			str[8] = String.valueOf(Double.parseDouble(result[0])*Math.sqrt(Double.parseDouble(result[1])));
  		} catch (NumberFormatException er) {
	  		str[8] = "0.1732050807568877";
	  	} catch (PatternSyntaxException er) {
	  		str[8] = "0.1732050807568877";
	  	}
  	}
  	try {
  		str[9] = String.valueOf(Integer.parseInt(textsign.getText()));
  	} catch(NumberFormatException e) {
  		str[9] = "1";
  	}
  	try {
  		str[10] = String.valueOf(Integer.parseInt(texttype.getText()));
  	} catch(NumberFormatException e) {
  		str[10] = "1";
  	}
  	try {
  		str[11] = String.valueOf(Integer.parseInt(textoutput.getText()));
  	} catch(NumberFormatException e) {
  		str[11] = "0";
  	}
  	try {
  		str[12] = String.valueOf(Integer.parseInt(textcf.getText()));
  	} catch(NumberFormatException e) {
  		str[12] = "0";
  	}
  	try {
  		str[13] = String.valueOf(Integer.parseInt(textbmet.getText()));
  	} catch(NumberFormatException e) {
  		str[13] = "0";
  	}
  	str[14] = textfilename.getText();
  	return str;
  }

  private void settext() {
  	textmag.setText(String.valueOf(out.mag));
  	textlx.setText(String.valueOf(out.lx));
  	textly.setText(String.valueOf(out.ly));
  	textdeg.setText(String.valueOf(out.deg));
  	textdt.setText(String.valueOf(out.dt));
  	textlcount.setText(String.valueOf(out.lcount));
  	textnacount.setText(String.valueOf(out.nacount));
  	textddeg.setText(String.valueOf(out.ddeg));
  	textdy.setText(String.valueOf(out.dy));
  	textsign.setText(String.valueOf(out.sign));
  	texttype.setText(String.valueOf(out.type));
  	textoutput.setText(String.valueOf(out.output));
  	textcf.setText(String.valueOf(out.cf));
  	textbmet.setText(String.valueOf(out.bmet));
  	textfilename.setText(out.filename);
  }

  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
  	if (cmd.equals("Run")){
    	out.outputvar(gettext());
  		out.open();
  	} else if (cmd.equals("Save")){
    	out.outputvar(gettext());
  	} else if (cmd.equals("Quit")){
    	System.exit(0);
  	} else if (cmd.equals("Case 1")){
  		out.mag = 0.3;
  		out.lx = 10.0;
  		out.ly = 0.0;
  		out.deg = 20.0;
  		out.dt = 0.005;
  		out.lcount = 40;
  		out.nacount = 100000;
  		out.ddeg = -0.5;
  		out.dy = 0.1732050807568877;
  		out.sign = 1;
 		out.type = 0;
  		out.output = 0;
  		out.cf = 0;
  		out.bmet=1;
  		out.filename = "resalt.txt";
  		settext();
  	} else if (cmd.equals("Case 2")){
  		out.mag = 0.3;
  		out.lx = 10.0;
  		out.ly = 0.0;
  		out.deg = 0.0;
  		out.dt = 0.005;
  		out.lcount = 40;
  		out.nacount = 100000;
  		out.ddeg = -0.5;
  		out.dy = 0.1732050807568877;
  		out.sign = 1;
 		out.type = 1;
  		out.output = 0;
  		out.cf = 1;
  		out.bmet=0;
  		out.filename = "resalt.txt";
  		settext();
  	} else if (cmd.equals("Case 3")){
  		out.mag = 0.3;
  		out.lx = 10.0;
  		out.ly = 0.0;
  		out.deg = -90.0;
  		out.dt = 0.005;
  		out.lcount = 72;
  		out.nacount = 100000;
  		out.ddeg = 5.0;
  		out.dy = 0.1732050807568877;
  		out.sign = 1;
 		out.type = 0;
  		out.output = 0;
  		out.cf = 0;
  		out.bmet=1;
  		out.filename = "resalt.txt";
  		settext();
  	}
  }
}

class outside {
  int Line;
  double mag;
  double lx;
  double ly;
  double deg;
  double dt;
  int lcount;
  int nacount;
  double ddeg;
  double dy;
  int sign;
  int type;
  int output;
  int cf;
  int bmet;
  String filename;
  
  outside() {
  	Line = 16;
  }
  
  public int open() {
  	PlatformUtils pu = new PlatformUtils();
  	String command = "";
  	if (pu.isWindows()) {
  		command = "NullGeodesic";
  	} else if (pu.isLinux()) {
  		command = "./NullGeodesic.out";
  	} else {
  		command = "./NullGeodesic";
  	}
  	try {
  	  Process p = Runtime.getRuntime().exec(command);
   	  p.waitFor();
  	  p.destroy();
  	  return 0;
  	} catch (IOException e) {
      e.printStackTrace();
      return -1;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return -1;
    }
  }
  
  public void outputvar(String[] str) {
  	try{
      File file = new File("data.txt");

      if (checkBeforeWritefile(file)){
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < Line-1; i++) {
            bw.write(str[i]);
            bw.newLine();
        }

        bw.close();
      }else{
        System.out.println("ファイルに書き込めません");
      }
    }catch(IOException e){
      System.out.println(e);
    }
  }
  
  public void inputvar(String[] str) {
  	mag = Double.parseDouble(str[0]);
  	lx = Double.parseDouble(str[1]);
  	ly = Double.parseDouble(str[2]);
  	deg = Double.parseDouble(str[3]);
  	dt = Double.parseDouble(str[4]);
  	lcount = Integer.parseInt(str[5]);
  	nacount = Integer.parseInt(str[6]);
  	ddeg = Double.parseDouble(str[7]);
  	dy = Double.parseDouble(str[8]);
  	sign = Integer.parseInt(str[9]);
  	type = Integer.parseInt(str[10]);
  	output = Integer.parseInt(str[11]);
  	cf = Integer.parseInt(str[12]);
  	bmet = Integer.parseInt(str[13]);
  	filename = str[14];
  }
  
  public String[] read() {
  	String[] str = new String[Line];
  	
    try{
      File file = new File("data.txt");

      if (checkBeforeReadfile(file)){
        BufferedReader br = new BufferedReader(new FileReader(file));

        int i=0;
        while((str[i] = br.readLine()) != null){
          //System.out.println(str[i]);
          i++;
        }

        br.close();
      }else{
        System.out.println("ファイルが見つからないか開けません");
      }
    }catch(FileNotFoundException e){
      System.out.println(e);
    }catch(IOException e){
      System.out.println(e);
    }
    return str;
  }

  private static boolean checkBeforeReadfile(File file){
    if (file.exists()){
      if (file.isFile() && file.canRead()){
        return true;
      }
    }

    return false;
  }
  
  private static boolean checkBeforeWritefile(File file){
    if (file.exists()){
      if (file.isFile() && file.canWrite()){
        return true;
      }
    }

    return false;
  }
}

class PlatformUtils {
 
  private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
 
  public static boolean isLinux() {
    return OS_NAME.startsWith("linux");
  }
 
  public static boolean isMac() {
    return OS_NAME.startsWith("mac");
  }
 
  public static boolean isWindows() {
    return OS_NAME.startsWith("windows");
  }
 
  public static boolean isSunOS() {
    return OS_NAME.startsWith("sunos");
  }
}