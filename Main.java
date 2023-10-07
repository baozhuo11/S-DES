import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class Main implements ActionListener{
    JFrame jFrame;
    JTextField jTextField1, jTextField2,jTextField3,jTextField5,jTextField4;
    JButton code,decode,attack;
    JRadioButton asc;

    public Main() {

        jTextField1 = new JTextField(20);
        jTextField2 = new JTextField(20);
        jTextField3 = new JTextField(20);
        jTextField4 = new JTextField(8);
        jTextField5 = new JTextField(8);
        // 将jTextField2对象设置为不可编辑
        //jTextField2.setEditable(false);
        // 调用其监听方法
        jTextField1.addActionListener(this);

        code = new JButton("加密");
        decode = new JButton("解密");
        attack = new JButton("暴力破解");
        asc = new JRadioButton("ASCII");
        jFrame = new JFrame();
        // 添加组件
        jFrame.add(jTextField1);
        jFrame.add(jTextField3);
        jFrame.add(jTextField2);
        jFrame.add(jTextField4);
        jFrame.add(jTextField5);

        jFrame.add(code);
        jFrame.add(decode);
        jFrame.add(attack);
        jFrame.add(asc);
        code.addActionListener(this);
        decode.addActionListener(this);
        attack.addActionListener(this);
        // 设置布局
        jFrame.setLayout(new FlowLayout());
        // 设置为可见
        jFrame.setVisible(true);
        // 设置窗体大小
        jFrame.setSize(400, 100);
        // 设置窗体在屏幕上的位置
        jFrame.setLocationRelativeTo(null);
        // 设置窗口标题
        jFrame.setTitle("S-DES");
        //窗口可关闭
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 实现接口中的方法，进行具体的事件处理
    public void actionPerformed(ActionEvent evt) {
        if ((evt.getSource() == code)&&(!asc.isSelected())) {

            //jTextField1.setText("");
            // 把第一个输入框清空
            String text = jTextField1.getText();
            String key = jTextField3.getText();
            Sdes.SDES(key);
            String plaintext = Sdes.encrypt(text);
            if(plaintext.length()<8){
                for(int i=0;i<8-plaintext.length();i++)
                    plaintext="0"+plaintext;
            }
            jTextField2.setText(plaintext);
        }
        else if ((evt.getSource() == decode)&&(!asc.isSelected())) {
            String text = jTextField1.getText();
            String key = jTextField3.getText();
            Sdes.SDES(key);
            String plaintext = Sdes.decrypt(text);
            if(plaintext.length()<8){
                for(int i=0;i<8-plaintext.length();i++)
                    plaintext="0"+plaintext;
            }
            jTextField2.setText(plaintext);
        }
        //暴力破解
        else if (evt.getSource() == attack) {
            String t1 = jTextField4.getText();
            String t2 = jTextField5.getText();
            Sdes.attackcode(t1,t2);
        }
        //ascii加密
        else if ((evt.getSource() == code)&&(asc.isSelected())) {
            String text = jTextField1.getText();
            String key = jTextField3.getText();
            Sdes.SDES(key);
            StringBuffer result= new StringBuffer("");
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                int plaintext = (int) c;
                String binary_plaintext = Sdes.BaseSystem_2(plaintext);
                String plaintext1 = Sdes.encrypt(binary_plaintext);
                Integer intp = Integer.parseInt(plaintext1);
                int intp1 = intp.intValue();
                result.append((char)intp1);
            }
            String m=result.toString();
            jTextField2.setText(m);
        }
        //ascii解密
        else if ((evt.getSource() == decode)&&(asc.isSelected())) {
            String text = jTextField1.getText();
            String key = jTextField3.getText();
            Sdes.SDES(key);
            StringBuffer result= new StringBuffer("");
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                int plaintext = (int) c;
                String binary_plaintext = Sdes.BaseSystem_2(plaintext);
                String plaintext1 = Sdes.decrypt(binary_plaintext);
                Integer intp = Integer.parseInt(plaintext1);
                int intp1 = intp.intValue();
                result.append((char)intp1);
            }
            String m=result.toString();
            jTextField2.setText(m);
        }


        else {
            jTextField1.setText("无效操作");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}