package com.bizwink.task;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Pattern;

//import com.edarong.butt.dao.ExchangeRateDao;
//import com.edarong.butt.dao.impl.ExchangeRateDaoImpl;
//import com.edarong.butt.domain.ExchangeRate;
//import com.edarong.persistence.TransactionManager;

public class ItfTimerTaskExchRate extends TimerTask {
    //public static void main(String args[]) {
    public void run() {
        HttpURLConnection con = null;
        BufferedReader br = null;
        StringBuffer buf = null;

        try {
            String url123 = "http://www.boc.cn/sourcedb/whpj/";
            URL url = new URL(url123);
            con = (HttpURLConnection) (url.openConnection());
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            br = new BufferedReader(new InputStreamReader(con.getInputStream(),
                    "utf-8"));

            FileWriter fw = new FileWriter("D:\\tx.txt"); // 创建保存文件所在的路径
            String i;
            buf = new StringBuffer();
            while ((i = br.readLine()) != null) {
                buf.append(i + "\r\n");
            }
            fw.write(buf.toString());
            fw.close();
            br.close();

            // 分析出汇率数据
            int posi = buf
                    .indexOf("<table width=\"800\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\" bgcolor=\"#EAEAEA\">");
            String tbuf = buf
                    .substring(posi
                            + "<table width=\"800\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\" bgcolor=\"#EAEAEA\">"
                            .length());
            posi = tbuf.indexOf("</table>");
            tbuf = tbuf.substring(0, posi);

            Pattern p = Pattern.compile("</tr>", Pattern.CASE_INSENSITIVE);
            String[] wh = p.split(tbuf);
            List exchRates = new ArrayList();
/*
            for (int j = 1; j < wh.length; j++) {
                ExchangeRate exchangeRate = new ExchangeRate();;
                posi = wh[j].indexOf(">");
                String whdata = wh[j].substring(posi + 1);
                p = Pattern.compile("</td>", Pattern.CASE_INSENSITIVE);
                String[] hv = p.split(whdata);
                String[] hvdata = new String[hv.length];
                for (int k = 0; k < hv.length; k++) {
                    if (hv[k] != null) {
                        hv[k] = hv[k].trim();
                        posi = hv[k].lastIndexOf(">");
                        hvdata[k] = hv[k].substring(posi + 1);
                        switch (k) {
                            case 0:
                                exchangeRate.setCurrency(hvdata[k]);
                                break;
                            case 1:
                                if (hvdata[k] != null && hvdata[k] != "")
                                    if (!hvdata[k].isEmpty())
                                        exchangeRate.setHbuy(Float.parseFloat(hvdata[k]));
                                    else
                                        exchangeRate.setHbuy(0f);
                                else
                                    exchangeRate.setHbuy(0f);
                                break;
                            case 2:
                                if (hvdata[k] != null && hvdata[k] != "")
                                    if (!hvdata[k].isEmpty())
                                        exchangeRate.setCbuy(Float.parseFloat(hvdata[k]));
                                    else
                                        exchangeRate.setCbuy(0f);
                                else
                                    exchangeRate.setCbuy(0f);
                                break;
                            case 3:
                                if (hvdata[k] != null && hvdata[k] != "")
                                    if (!hvdata[k].isEmpty())
                                        exchangeRate.setHsale(Float.parseFloat(hvdata[k]));
                                    else
                                        exchangeRate.setHsale(0f);
                                else
                                    exchangeRate.setHsale(0f);
                                break;
                            case 4:
                                if (hvdata[k] != null && hvdata[k] != "")
                                    if (!hvdata[k].isEmpty())
                                        exchangeRate.setCsale(Float.parseFloat(hvdata[k]));
                                    else
                                        exchangeRate.setCsale(0f);
                                else
                                    exchangeRate.setCsale(0f);
                                break;
                            case 5:
                                if (hvdata[k] != null && hvdata[k] != "")
                                    if (!hvdata[k].isEmpty())
                                        exchangeRate.setMiddle(Float.parseFloat(hvdata[k]));
                                    else
                                        exchangeRate.setMiddle(0f);
                                else
                                    exchangeRate.setMiddle(0f);
                                break;
                            case 6:
                                if (hvdata[k] != null) {
                                    String[] aa = hvdata[k].split("-");
                                    Calendar cal = Calendar.getInstance();
                                    cal.set(Integer.parseInt(aa[0])-1900,Integer.parseInt(aa[1])-1,Integer.parseInt(aa[2]));
                                    exchangeRate.setThedate(new java.util.Date(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)));
                                    //SimpleDateFormat fmt1=new SimpleDateFormat("yyyy/MM/dd");
                                    //System.out.println("the date=" + fmt1.format(exchangeRate.getThedate()));
                                } else
                                    exchangeRate.setThedate(new java.sql.Date(System.currentTimeMillis()));
                                break;
                            case 7:
                                if (hvdata[k] != null) {
                                    String[] aa = hvdata[k].split(":");
                                    exchangeRate.setThetime(new java.sql.Time(Integer.parseInt(aa[0]),Integer.parseInt(aa[1]),Integer.parseInt(aa[2])));
                                } else
                                    exchangeRate.setThetime(new java.sql.Time(System.currentTimeMillis()));
                                break;
                        }

                    }
                }

                exchRates.add(exchangeRate);
            }

            //保存数据入库
            ExchangeRateDao srv = new ExchangeRateDaoImpl();
            for(int kk=0; kk<exchRates.size()-1; kk++) {
                ExchangeRate exchangeRate = (ExchangeRate)exchRates.get(kk);
                srv.insertExchangeRateInfo(exchangeRate);
                System.out.println(exchangeRate.getCurrency() + "==" + exchangeRate.getHbuy() + "==" + exchangeRate.getMiddle());
            }

            //提交对数据库的修改
            TransactionManager.commitTransactions();
*/

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
