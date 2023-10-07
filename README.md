 # S-DES系统
 # 第1关：基本测试
 # 1.1 gui界面设计
       使用java技术设计gui界面，具有明文，密文，密钥的输入框和结果的输出框.
 ![image](https://github.com/baozhuo11/S-DES/blob/main/GUI.png)
 # 1.2  加密
       测试明文：10101010

       密钥：1010101010

       测试密文：01101011

       加密测试结果为
![image](https://github.com/baozhuo11/S-DES/blob/main/%E5%8A%A0%E5%AF%86.png)
       
 # 1.3  解密
       同解密相同，测试结果为
![image](https://github.com/baozhuo11/S-DES/blob/main/%E8%A7%A3%E5%AF%86.png)
       
 # 第2关：交叉测试
       对测试明文：00111010

       密钥：1001001110

       测试密文：10010001
       
       测试结果如下
  ![image](https://github.com/baozhuo11/S-DES/blob/main/%E4%BA%A4%E5%8F%89%E9%AA%8C%E8%AF%812.png)
       
 # 第3关：扩展功能
       在交互界面中，选择ASCII字符加密解密按钮，则可输入ASCII编码字符串进行使用，具体显示与第一关相似。
  ![image]()
          
 # 第4关: 暴力破解
       假设你找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用暴力破解的方法找到正确的密钥Key。
  ![image]()
       
 # 第5关：封闭测试
       根据第4关的结果，进一步分析，对于你随机选择的一个明密文对，是不是有不止一个密钥Key？进一步扩展，对应明文空间任意给定的明文分组P_{n}，是否会出现选择不同的密钥K_{i}\ne K_{j}加密得到相同密文C_n的情况？
![image]()
