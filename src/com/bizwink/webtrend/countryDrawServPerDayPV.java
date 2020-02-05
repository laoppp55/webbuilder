package com.bizwink.webtrend;

//Compile by Eric.March 25,2003
// Source File Name:   DrawPieServ.java

import com.bizwink.webtrend.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.util.*;
import java.sql.*;
import java.lang.*;

public class countryDrawServPerDayPV extends HttpServlet{
/**
 * Services the HTTP request
 *
 * @param req The request from the client
 * @param resp The response from the servlet
 * @throws IOException to be by way of Exception
 * @throws ServletException
 */
 public void service(HttpServletRequest req,HttpServletResponse resp)
      throws ServletException,java.io.IOException
      {
       try{
            //Get the input stream for reading data from the client
            DataInputStream in = new DataInputStream(req.getInputStream());

            //We'll be sending binary data back to the client so
            //set the content type appropriately
            resp.setContentType("application/octet-stream");

            //Data will always be written  to a byte array buffer so
            //that we can tell the client the length of the data
            ByteArrayOutputStream byteOut = new  ByteArrayOutputStream();

            //Create the output stream to be used to write the
            //data to our buffer
            DataOutputStream out = new DataOutputStream(byteOut);

            //Read the data from the client
            boolean ordinal = in.readBoolean();
            String thedate = in.readUTF();
            String userid = in.readUTF();

            //Evaluate the ordinal.true is reserved for initializing
            //the server
            if(ordinal == true)
            {
                  try{
                        //Creat a new instance of the server object
                    IWebtrendManager webtrendMgr = WebtrendPeer.getInstance();
                    Webtrend webtrend = new Webtrend();
                    List list = webtrendMgr.searchcountryid(userid, thedate);
                    int countryid = 0;
                    int[][] count = new int[200][200];
                    String[] city = new String[15];

                    for(int i=0;i<list.size();i++){
                        webtrend = (Webtrend)list.get(i);
                        countryid = webtrend.getIDCountry();
                    switch(countryid){
                      case 0:
                          count[0][0] = count[0][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[0][1] = 0;
                          break;
                      case 1:
                          count[1][0] = count[1][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[1][1] = 1;
                          break;
                      case 2:
                          count[2][0] = count[2][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[2][1] = 2;
                          break;
                      case 3:
                          count[3][0] = count[3][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[3][1] = 3;
                          break;
                      case 4:
                          count[4][0] = count[4][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[4][1] = 4;
                          break;
                      case 5:
                          count[5][0] = count[5][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[5][1] = 5;
                          break;
                      case 6:
                          count[6][0] = count[6][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[6][1] = 6;
                          break;
                      case 7:
                          count[7][0] = count[7][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[7][1] = 7;
                          break;
                      case 8:
                          count[8][0] = count[8][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[8][1] = 8;
                          break;
                      case 9:
                          count[9][0] = count[9][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[9][1] = 9;
                          break;
                      case 10:
                          count[10][0] = count[10][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[10][1] = 10;
                          break;
                      case 11:
                          count[11][0] = count[11][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[11][1] = 11;
                          break;
                      case 12:
                          count[12][0] = count[12][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[12][1] = 12;
                          break;
                      case 13:
                          count[13][0] = count[13][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[13][1] = 13;
                          break;
                      case 14:
                          count[14][0] = count[14][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[14][1] = 14;
                          break;
                      case 15:
                          count[15][0] = count[15][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[15][1] = 15;
                          break;
                      case 16:
                          count[16][0] = count[16][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[16][1] = 16;
                          break;
                      case 17:
                          count[17][0] = count[17][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[17][1] = 17;
                          break;
                      case 18:
                          count[18][0] = count[18][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[18][1] = 18;
                          break;
                      case 19:
                          count[19][0] = count[19][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[19][1] = 19;
                          break;
                      case 20:
                          count[20][0] = count[20][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[20][1] = 20;
                          break;
                      case 21:
                          count[21][0] = count[21][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[21][1] = 21;
                          break;
                      case 22:
                          count[22][0] = count[22][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[22][1] = 22;
                          break;
                      case 23:
                          count[23][0] = count[23][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[23][1] = 23;
                          break;
                      case 24:
                          count[24][0] = count[24][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[24][1] = 24;
                          break;
                      case 25:
                          count[25][0] = count[25][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[25][1] = 25;
                          break;
                      case 26:
                          count[26][0] = count[26][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[26][1] = 26;
                          break;
                      case 27:
                          count[27][0] = count[27][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[27][1] = 27;
                          break;
                      case 28:
                          count[28][0] = count[28][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[28][1] = 28;
                          break;
                      case 29:
                          count[29][0] = count[29][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[29][1] = 29;
                          break;
                      case 30:
                          count[30][0] = count[30][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[30][1] = 30;
                          break;
                      case 31:
                          count[31][0] = count[31][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[31][1] = 31;
                          break;
                      case 32:
                          count[32][0] = count[32][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[32][1] = 32;
                          break;
                      case 33:
                          count[33][0] = count[33][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[33][1] = 33;
                          break;
                      case 34:
                          count[34][0] = count[34][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[34][1] = 34;
                          break;
                      case 35:
                          count[35][0] = count[35][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[35][1] = 35;
                          break;
                      case 36:
                          count[36][0] = count[36][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[36][1] = 36;
                          break;
                      case 37:
                          count[37][0] = count[37][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[37][1] = 37;
                          break;
                      case 38:
                          count[38][0] = count[38][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[38][1] = 38;
                          break;
                      case 39:
                          count[39][0] = count[39][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[39][1] = 39;
                          break;
                      case 40:
                          count[40][0] = count[40][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[40][1] = 40;
                          break;
                      case 41:
                          count[41][0] = count[41][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[41][1] = 41;
                          break;
                      case 42:
                          count[42][0] = count[42][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[42][1] = 42;
                          break;
                      case 43:
                          count[43][0] = count[43][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[43][1] = 43;
                          break;
                      case 44:
                          count[2][0] = count[44][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[44][1] = 44;
                          break;
                      case 45:
                          count[45][0] = count[45][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[45][1] = 45;
                          break;
                      case 46:
                          count[46][0] = count[46][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[46][1] = 46;
                          break;
                      case 47:
                          count[47][0] = count[47][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[47][1] = 47;
                          break;
                      case 48:
                          count[48][0] = count[48][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[48][1] = 48;
                          break;
                      case 49:
                          count[49][0] = count[49][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[49][1] = 49;
                          break;
                      case 50:
                          count[50][0] = count[50][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[50][1] = 50;
                          break;
                      case 51:
                          count[51][0] = count[51][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[51][1] = 51;
                          break;
                      case 52:
                          count[52][0] = count[52][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[52][1] = 52;
                          break;
                      case 53:
                          count[53][0] = count[53][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[53][1] = 53;
                          break;
                      case 54:
                          count[54][0] = count[54][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[54][1] = 54;
                          break;
                      case 55:
                          count[55][0] = count[55][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[55][1] = 55;
                          break;
                      case 56:
                          count[56][0] = count[56][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[56][1] = 56;
                          break;
                      case 57:
                          count[57][0] = count[57][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[57][1] = 57;
                          break;
                      case 58:
                          count[58][0] = count[58][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[58][1] = 58;
                          break;
                      case 59:
                          count[59][0] = count[59][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[59][1] = 59;
                          break;
                      case 60:
                          count[60][0] = count[60][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[60][1] = 60;
                          break;
                      case 61:
                          count[61][0] = count[61][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[61][1] = 61;
                          break;
                      case 62:
                          count[62][0] = count[62][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[62][1] = 62;
                          break;
                      case 63:
                          count[63][0] = count[63][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[63][1] = 63;
                          break;
                      case 64:
                          count[64][0] = count[64][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[64][1] = 64;
                          break;
                      case 65:
                          count[65][0] = count[65][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[65][1] = 65;
                          break;
                      case 66:
                          count[66][0] = count[66][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[66][1] = 66;
                          break;
                      case 67:
                          count[67][0] = count[67][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[67][1] = 67;
                          break;
                      case 68:
                          count[68][0] = count[68][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[68][1] = 68;
                          break;
                      case 69:
                          count[69][0] = count[69][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[69][1] = 69;
                          break;
                      case 70:
                          count[70][0] = count[70][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[70][1] = 70;
                          break;
                      case 71:
                          count[71][0] = count[71][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[71][1] = 71;
                          break;
                      case 72:
                          count[72][0] = count[72][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[72][1] = 72;
                          break;
                      case 73:
                          count[73][0] = count[73][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[73][1] = 73;
                          break;
                      case 74:
                          count[74][0] = count[74][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[74][1] = 74;
                          break;
                      case 75:
                          count[75][0] = count[75][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[75][1] = 75;
                          break;
                      case 76:
                          count[76][0] = count[76][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[76][1] = 76;
                          break;
                      case 77:
                          count[77][0] = count[77][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[77][1] = 77;
                          break;
                      case 78:
                          count[78][0] = count[78][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[78][1] = 78;
                          break;
                      case 79:
                          count[79][0] = count[79][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[79][1] = 79;
                          break;
                      case 80:
                          count[80][0] = count[80][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[80][1] = 80;
                          break;
                      case 81:
                          count[819][0] = count[81][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[81][1] = 81;
                          break;
                      case 82:
                          count[82][0] = count[82][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[82][1] = 82;
                          break;
                      case 83:
                          count[83][0] = count[83][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[83][1] = 83;
                          break;
                      case 84:
                          count[84][0] = count[84][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[84][1] = 84;
                          break;
                      case 85:
                          count[85][0] = count[85][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[85][1] = 85;
                          break;
                      case 86:
                          count[86][0] = count[86][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[86][1] = 86;
                          break;
                      case 87:
                          count[87][0] = count[87][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[87][1] = 87;
                          break;
                      case 88:
                          count[88][0] = count[88][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[88][1] = 88;
                          break;
                      case 89:
                          count[89][0] = count[89][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[89][1] = 89;
                          break;
                      case 90:
                          count[90][0] = count[90][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[90][1] = 90;
                          break;
                      case 91:
                          count[91][0] = count[91][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[91][1] = 91;
                          break;
                      case 92:
                          count[92][0] = count[92][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[92][1] = 92;
                          break;
                      case 93:
                          count[93][0] = count[93][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[93][1] = 93;
                          break;
                      case 94:
                          count[94][0] = count[94][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[94][1] = 94;
                          break;
                      case 95:
                          count[95][0] = count[95][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[95][1] = 95;
                          break;
                      case 96:
                          count[96][0] = count[96][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[96][1] = 96;
                          break;
                      case 97:
                          count[97][0] = count[97][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[97][1] = 97;
                          break;
                      case 98:
                          count[98][0] = count[98][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[98][1] = 98;
                          break;
                      case 99:
                          count[99][0] = count[99][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[99][1] = 99;
                          break;
                      case 100:
                          count[100][0] = count[100][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[100][1] = 100;
                          break;
                      case 101:
                          count[101][0] = count[101][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[101][1] = 101;
                          break;
                      case 102:
                          count[102][0] = count[102][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[102][1] = 102;
                          break;
                      case 103:
                          count[103][0] = count[103][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[103][1] = 103;
                          break;
                      case 104:
                          count[104][0] = count[104][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[104][1] = 104;
                          break;
                      case 105:
                          count[105][0] = count[105][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[105][1] = 105;
                          break;
                      case 106:
                          count[106][0] = count[106][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[106][1] = 106;
                          break;
                      case 107:
                          count[107][0] = count[107][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[107][1] = 107;
                          break;
                      case 108:
                          count[108][0] = count[108][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[108][1] = 108;
                          break;
                      case 109:
                          count[109][0] = count[109][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[109][1] = 109;
                          break;
                      case 110:
                          count[110][0] = count[110][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[110][1] = 110;
                          break;
                      case 111:
                          count[111][0] = count[111][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[111][1] = 111;
                          break;
                      case 112:
                          count[112][0] = count[112][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[112][1] = 112;
                          break;
                      case 113:
                          count[113][0] = count[113][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[113][1] = 113;
                          break;
                      case 114:
                          count[114][0] = count[114][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[114][1] = 114;
                          break;
                      case 115:
                          count[115][0] = count[115][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[115][1] = 115;
                          break;
                      case 116:
                          count[116][0] = count[116][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[116][1] = 116;
                          break;
                      case 117:
                          count[117][0] = count[117][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[1173][1] = 117;
                          break;
                      case 118:
                          count[118][0] = count[118][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[118][1] = 118;
                          break;
                      case 119:
                          count[119][0] = count[119][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[119][1] = 119;
                          break;
                      case 120:
                          count[120][0] = count[120][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[120][1] = 120;
                          break;
                      case 121:
                          count[121][0] = count[121][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[121][1] = 121;
                          break;
                      case 122:
                          count[122][0] = count[122][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[122][1] = 122;
                          break;
                      case 123:
                          count[123][0] = count[123][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[123][1] = 123;
                          break;
                      case 124:
                          count[124][0] = count[124][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[124][1] = 124;
                          break;
                      case 125:
                          count[125][0] = count[125][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[125][1] = 125;
                          break;
                      case 126:
                          count[126][0] = count[126][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[126][1] = 126;
                          break;
                      case 127:
                          count[127][0] = count[127][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[127][1] = 127;
                          break;
                      case 128:
                          count[128][0] = count[128][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[128][1] = 128;
                          break;
                      case 129:
                          count[129][0] = count[129][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[129][1] = 129;
                          break;
                      case 130:
                          count[130][0] = count[130][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[130][1] = 130;
                          break;
                      case 131:
                          count[131][0] = count[131][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[131][1] = 131;
                          break;
                      case 132:
                          count[132][0] = count[132][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[132][1] = 132;
                          break;
                      case 133:
                          count[133][0] = count[133][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[133][1] = 133;
                          break;
                      case 134:
                          count[134][0] = count[134][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[134][1] = 134;
                          break;
                      case 135:
                          count[135][0] = count[135][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[135][1] = 135;
                          break;
                      case 136:
                          count[136][0] = count[136][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[136][1] = 136;
                          break;
                      case 137:
                          count[137][0] = count[137][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[137][1] = 137;
                          break;
                      case 138:
                          count[138][0] = count[138][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[138][1] = 138;
                          break;
                      case 139:
                          count[139][0] = count[139][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[139][1] = 139;
                          break;
                      case 140:
                          count[140][0] = count[140][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[140][1] = 140;
                          break;
                      case 141:
                          count[141][0] = count[141][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[141][1] = 141;
                          break;
                      case 142:
                          count[142][0] = count[142][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[142][1] = 142;
                          break;
                      case 143:
                          count[143][0] = count[143][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[143][1] = 143;
                          break;
                      case 144:
                          count[144][0] = count[144][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[144][1] = 144;
                          break;
                      case 145:
                          count[145][0] = count[145][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[145][1] = 145;
                          break;
                      case 146:
                          count[146][0] = count[146][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[146][1] = 146;
                          break;
                      case 147:
                          count[147][0] = count[147][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[147][1] = 147;
                          break;
                      case 148:
                          count[148][0] = count[148][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[148][1] = 148;
                          break;
                      case 149:
                          count[149][0] = count[149][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[149][1] = 149;
                          break;
                      case 150:
                          count[150][0] = count[150][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[150][1] = 150;
                          break;
                      case 151:
                          count[151][0] = count[151][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[151][1] = 151;
                          break;
                      case 152:
                          count[152][0] = count[152][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[152][1] = 152;
                          break;
                      case 153:
                          count[153][0] = count[153][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[153][1] = 153;
                          break;
                      case 154:
                          count[154][0] = count[154][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[154][1] = 154;
                          break;
                      case 155:
                          count[155][0] = count[155][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[155][1] = 155;
                          break;
                      case 156:
                          count[156][0] = count[156][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[156][1] = 156;
                          break;
                      case 157:
                          count[157][0] = count[157][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[157][1] = 157;
                          break;
                      case 158:
                          count[158][0] = count[158][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[158][1] = 158;
                          break;
                      case 159:
                          count[159][0] = count[159][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[159][1] = 159;
                          break;
                      case 160:
                          count[160][0] = count[160][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[160][1] = 160;
                          break;
                      case 161:
                          count[161][0] = count[161][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[161][1] = 161;
                          break;
                      case 162:
                          count[162][0] = count[162][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[162][1] = 162;
                          break;
                      case 163:
                          count[163][0] = count[163][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[163][1] = 163;
                          break;
                      case 164:
                          count[164][0] = count[164][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[164][1] = 164;
                          break;
                      case 165:
                          count[165][0] = count[165][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[165][1] = 165;
                          break;
                      case 166:
                          count[166][0] = count[166][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[166][1] = 166;
                          break;
                      case 167:
                          count[167][0] = count[167][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[167][1] = 167;
                          break;
                      case 168:
                          count[168][0] = count[168][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[168][1] = 168;
                          break;
                      case 169:
                          count[169][0] = count[169][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[169][1] = 169;
                          break;
                      case 170:
                          count[170][0] = count[170][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[170][1] = 170;
                          break;
                      case 171:
                          count[171][0] = count[171][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[171][1] = 171;
                          break;
                      case 172:
                          count[172][0] = count[172][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[172][1] = 172;
                          break;
                      case 173:
                          count[173][0] = count[173][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[173][1] = 173;
                          break;
                      case 174:
                          count[174][0] = count[174][0] + Integer.parseInt(webtrend.getUCount().trim());
                          count[174][1] = 174;
                        break;
                    }
                    }

                    int i = 0;
                    int j = 0;
                    int cpv = 0;
                    int cty = 0;
                    for(i=0;i<199;i++){
                        for(j=0;j<199-i;j++){
                            if(count[j][0]<count[j+1][0]){
                                cpv = count[j][0];
                                cty = count[j][1];
                                count[j][0] = count[j+1][0];
                                count[j][1] = count[j+1][1];
                                count[j+1][0] = cpv;
                                count[j+1][1] = cty;
                            }
                        }
                    }

                    int displayNum = 0;
                    for(i=1;i<200;i++)
                    {
                      if (count[i][0] != 0)
                        displayNum = displayNum + 1;
                    }
                    displayNum = displayNum + 1;


                    for(i=0;i<displayNum;i++){
                        if(count[i][0] != 0){
                            webtrend = webtrendMgr.searchcountry(count[i][1]);
                            city[i] = webtrend.getCtry();
                        }
                        }

                        //Send the response back to the client indicating
                        //that the server object is ready for method calls.
                        out.writeBoolean(true);
                        out.writeInt(displayNum);

                        //Write the data to our internal buffer
                        for(i=0; i<displayNum; i++) {
                              out.writeBoolean(true);
                              out.writeInt(count[i][0]);
                              out.writeUTF(city[i]);
                        }
                  }
                  catch(Exception e)
                  {
                     System.out.println(e);
                  }
            }

            //Flush the contents of the output stream to the
            //byte array
            out.flush();

            //Get the buffer that is holding our response
            byte[] buf = byteOut.toByteArray();

            //Notify the client how much data is being sent
            resp.setContentLength(buf.length);

            //Send the buffer to the client
            ServletOutputStream servletOut = resp.getOutputStream();

            //Wrap up
            servletOut.write(buf);
            servletOut.close();
      }
      catch(IOException ex){
            //Re-throw as a tunnel exception
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
      }
    }
}
