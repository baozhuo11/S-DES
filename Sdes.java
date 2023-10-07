import java.io.IOException;
public class Sdes {
    /**
     * @param args
     */
    // two keys K1 and K2
    static int K1 = 0;
    static int K2 = 0;
    /*some parameters*/
    static int P10[] = new int[]{3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    static int P8[] = new int[]{6, 3, 7, 4, 8, 5, 10, 9};
    static int P4[] = new int[]{2, 4, 3, 1};
    static int IP[] = new int[]{2, 6, 3, 1, 4, 8, 5, 7};
    static int IPI[] = new int[]{4, 1, 3, 5, 7, 2, 8, 6};
    static int EP[] = new int[]{4, 1, 2, 3, 2, 3, 4, 1};
    static int S0[][] = {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 2},
    };
    static int S1[][] = {
            {0, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 0},
            {2, 1, 0, 3},
    };

    //根据数组交换
    static int Permute(int num, int p[], int pmax) {
        int result = 0;
        for (int i = 0; i < p.length; i++) {
            result <<= 1;
            result |= (num >> (pmax - p[i])) & 1;
        }
        return result;
    }

    //生成k1,k2
    static void SDES(String Key) {
        int K = Integer.parseInt(Key, 2);
        K = Permute(K, P10, 10);
        int th = 0, tl = 0;
        th = (K >> 5) & 0x1f;//取Key的高5位
        tl = K & 0x1f;     //取Key的低5位
        //LS-1
        th = ((th & 0xf) << 1) | ((th & 0x10) >> 4);//循环左移一位
        tl = ((tl & 0xf) << 1) | ((tl & 0x10) >> 4);//循环左移一位
        K1 = Permute((th << 5) | tl, P8, 10);     //生成K1
        //System.out.println("K1:" + Integer.toString(K1, 2));
        //LS-2
        th = ((th & 0x07) << 2) | ((th & 0x18) >> 3);//循环左移二位
        tl = ((tl & 0x07) << 2) | ((tl & 0x18) >> 3);//循环左移二位
        K2 = Permute((th << 5) | tl, P8, 10);          //生成K2
        //System.out.println("K2:" + Integer.toString(K2, 2));
    }

    //f函数
    static int F(int R, int K) {
        int t = Permute(R, EP, 4) ^ K;
        int t0 = (t >> 4) & 0xf;
        int t1 = t & 0xf;
        t0 = S0[((t0 & 0x8) >> 2) | (t0 & 1)][(t0 >> 1) & 0x3];
        t1 = S1[((t1 & 0x8) >> 2) | (t1 & 1)][(t1 >> 1) & 0x3];
        t = Permute((t0 << 2) | t1, P4, 4);
        return t;
    }

    //fk函数
    static int fk(int input, int k) {
        int l = (input >> 4) & 0xf;
        int r = input & 0xf;
        return ((l ^ F(r, k)) << 4) | r;
    }

    //switch function
    static int SW(int x) {
        return ((x & 0xf) << 4) | ((x >> 4) & 0xf);
    }
    //十进制转换为二进制数
    public static String BaseSystem_2(int Scanner){
        int n = Scanner;
        String result = "";
        boolean minus = false;

        //如果该数字为负数，那么进行该负数+1之后的绝对值的二进制码的对应位取反，然后将它保存在result结果中
        if(n < 0){
            minus = true;
            n = Math.abs(n + 1);
        }
        while(true){
            int remainder = (!minus && n % 2 == 0) || (minus && n % 2 == 1) ? 0 : 1;
            //将余数保存在结果中
            result = remainder + result;
            n /= 2;
            if(n == 0){
                break;
            }
        }
        //判断是否为负数，如果是负数，那么前面所有位补1
        if(minus){
            n = result.length();
            for(int i = 1; i <= 32 - n; i++){
                result = 1 + result;
            }
        }
        if(result.length()<8){
            for(int i=0;i<8-result.length();i++)
                result="0"+result;
        }
        return result;
    }

    //加密
    static String encrypt(String input) {
        int m = Integer.parseInt(input, 2);
        m = Permute(m, IP, 8);
        m = fk(m, K1);
        m = SW(m);
        m = fk(m, K2);
        m = Permute(m, IPI, 8);
        return Integer.toString(m, 2);
    }

    //解密
    static String decrypt(String input) {
        int m = Integer.parseInt(input, 2);
        m = Permute(m, IP, 8);
        m = fk(m, K2);
        m = SW(m);
        m = fk(m, K1);
        m = Permute(m, IPI, 8);
        return Integer.toString(m, 2);
    }
    static void attackcode(String text1,String text2){
        long startTime,endTime;
        startTime=System.currentTimeMillis();//获取开始时间
        for(int i=0;i<=1023;i++)
        {
            String key = BaseSystem_2(i);
            SDES(key);
            String m=encrypt(text1);

            if(m.length()<8){
            for(int a=0;a<8-m.length();a++)
                m="0"+m;
            }
            //System.out.println(m);
            if(text2.equals(m)&&(key.length()==10)){
                System.out.println("秘钥为："+key);
            }
        }
        endTime=System.currentTimeMillis();//获取结束时间
        System.out.println("总耗时："+(endTime-startTime)+"ms");
    }
//测试用的main函数
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String plaintext,ciphertext,key;
        java.util.Scanner scan = new java.util.Scanner(System.in);
        System.out.println("1:加密,2:解密,3:退出,请输入一个数字来选择:");
        int  mode=scan.nextInt();
        while(true){
            if(mode==1){
                System.out.println("请输入明文:");
                plaintext=scan.next();
                System.out.println("请输入密钥:");
                key=scan.next();
                SDES(key);
                ciphertext=encrypt(plaintext);
                //如果密文不足8位，补足8位
                if(ciphertext.length()<8){
                    for(int i=0;i<8-ciphertext.length();i++)
                        ciphertext="0"+ciphertext;
                }
                System.out.println("加密后的密文为:"+ciphertext);
            }
            else if(mode==2){
                System.out.println("请输入密文:");
                ciphertext=scan.next();
                System.out.println("请输入密钥:");
                key=scan.next();
                SDES(key);
                plaintext=decrypt(ciphertext);
                if(plaintext.length()<8){
                    for(int i=0;i<8-plaintext.length();i++)
                        plaintext="0"+plaintext;
                }
                System.out.println("解密后的明文为:"+plaintext);
            }
            else if(mode==3) break;
            System.out.println("1:加密,2:解密,3:退出,请输入一个数字来选择:");
            mode=scan.nextInt();
        }
    }
}
