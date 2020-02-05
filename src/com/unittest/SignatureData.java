package com.unittest;

import com.bizwink.cms.articleListmanager.IArticleListManager;
import com.bizwink.cms.articleListmanager.articleListPeer;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.KeyStore;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class SignatureData {
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        StringBuffer t = new StringBuffer();

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * Transform the specified Hex String into a byte array.
     * this is signature program
     * hello word
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }

        return bytes;
    }

    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public String sign(String srcStr) throws Exception {

        // KeyStore store = KeyStore.getInstance("JKS");
        KeyStore store = KeyStore.getInstance("PKCS12");
        FileInputStream stream = new FileInputStream("c:\\xinde.pfx"); // jks�ļ���
        String passwd = "622689"; // jks�ļ�����
        store.load(stream, passwd.toCharArray());
        // ��ȡjks֤�����
        Enumeration en = store.aliases();
        String pName = null;

        while (en.hasMoreElements()) {
            String n = (String) en.nextElement();
            if (store.isKeyEntry(n)) {
                pName = n;
            }
        }

        // ��ȡ֤���˽Կ
        PrivateKey key = (PrivateKey) store.getKey(pName, passwd.toCharArray());
        // ����ǩ�����
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(key);
        signature.update(srcStr.getBytes());
        byte[] signedData = signature.sign();
        // ���ֽ�����ת����HEX�ַ���

        return bytesToHexStr(signedData);
    }

    //读取流;
    private static String loadSteam(InputStream inputStream) throws IOException
    {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null)
        {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*com.unittest.SignatureData s = new com.unittest.SignatureData();
        //s.run();
        String myinfo = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509";
        try {
           System.out.println(s.sign(myinfo));
        } catch (Exception exp) {
            exp.printStackTrace();
        }*/
        /*Thread runner=null;
        //多线程测试程序
        PoolTest[] poolTest = new PoolTest[2000];
        for (int i=0;i<2000;i++) {
            poolTest[i] = new PoolTest(i);
            runner = new Thread(poolTest[i]);
            runner.start();
        }*/

        //RSYNC远程同步程序
        //命令行;
        String[] cmd = {"chmod","-R","775",args[0]};
        try {
            //调用getRuntime().exec产生一个本地进程,并返回一个Process子类实例;
            Process process = Runtime.getRuntime().exec(cmd);
            //输出命令结果;
            System.out.println(loadSteam(process.getInputStream()));
            //输出错误信息;
            System.err.println(loadSteam(process.getErrorStream()));
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        //处理一个目录下所有子目录中包含的文件
        /*ArrayList list = new ArrayList();
        list.add("D:\\leads\\gdmoa\\src\\com");
        while (list.size() != 0) {
            int len = list.size()-1;
            String f = (String)list.get(len);
            list.remove(len);
            File  file = new File(f);
            String[] fs = file.list();
            for(int i=0; i<fs.length; i++) {
                String ff = file.getAbsolutePath() + File.separator + fs[i];
                File lf = new File(ff);
                if (lf.isDirectory())
                    list.add(ff);
                else {
                    try{
                        BufferedReader bfile = new BufferedReader(new FileReader(ff));
                        String m = "";
                        String buf = "";
                        while ((m = bfile.readLine()) != null)
                        {
                            Pattern p = Pattern.compile("\\/\\*[0-9, ]*\\*\\/", Pattern.CASE_INSENSITIVE);
                            java.util.regex.Matcher matcher = p.matcher(m);
                            m = matcher.replaceFirst("");
                            if (m.indexOf("/* Location:")>-1)
                                break;
                            else {
                                buf = buf + m + "\r\n";
                            }
                        }
                        bfile.close();

                        //写回原文件
                        BufferedWriter output = new BufferedWriter(new FileWriter(ff));
                        output.write(buf);
                        output.close();
                    } catch (FileNotFoundException exp) {
                        exp.printStackTrace();
                    } catch (IOException exp) {

                    }
                }
            }
        }*/
    }
}
