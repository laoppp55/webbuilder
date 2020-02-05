package com.bizwink.images;
/*
 * JIMIProducer
 *
 * Copyright (c) 2000 Ken McCrary, All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies.
 *
 * KEN MCCRARY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. KEN MCCRARY
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Frame;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.FontMetrics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;

/**
 *  An image producer which draws pie charts using
 *  the JIMI toolkit
 */
public class JIMIProducer implements ImageProducer
{
    final static int ImageWidth  = 370;
    final static int ImageHeight = 370;
    final static int PieWidth    = 300;
    final static int PieHeight   = 300;
    final static int Radius      = PieWidth/2;
    final static int Inset       = 35;

    /**
     *   Construct the JIMI image producer
     *
     *
     */
    public JIMIProducer( )
    {
        this.graphics = graphics;
        currentTheta = 0;

        Frame f = new Frame();
        f.setVisible(true);
        image = f.createImage(ImageWidth, ImageHeight);
        graphics = image.getGraphics();
        f.setVisible(false);

        drawPieGraph();

        drawSlice( "Pepsi",  108);
        drawSlice( "Coke", 72 );
        drawSlice( "Sprite",  36);
        drawSlice( "7-UP", 72 );
        drawSlice( "Jolt", 72);
    }

    /**
     *   Create image using JIMI toolkit
     *
     */
    public String createImage(OutputStream stream) throws IOException
    {

        try
        {
            JimiWriter writer = Jimi.createJimiWriter("image/png", stream);
            writer.setSource(image);
            writer.putImage(stream);
        }
        catch (JimiException je)
        {
            throw new IOException(je.getMessage());
        }

        return ("image/png");
    }

    /**
     *
     *
     */
    public void drawPieGraph()
    {
        graphics.setColor(Color.blue);
        graphics.drawOval(Inset, Inset, PieWidth, PieHeight);
    }

    /**
     *  Create a slice in the pie chart
     *
     */
    public void drawSlice(String label, int degrees)
    {
        Point edge = new Point(PieWidth + Inset, PieHeight/2 + Inset);
        Point center = new Point(PieWidth/2 + Inset, PieHeight/2 + Inset);

        // Find the center point of the circle
        graphics.drawLine(center.x, center.y, edge.x, edge.y);

        //***************************************************************
        // Convert to radians
        // 1 degree = pi/180 radians
        //***************************************************************
        double theta = degrees * (3.14/180);
        currentTheta += theta;

        //***************************************************************
        // Convert to x/y
        // x = r cos @
        // y = r sin @
        //***************************************************************
        double x = Radius * Math.cos(currentTheta);
        double y = Radius * Math.sin(currentTheta);

        Point mark2 = new Point(center);
        mark2.translate((int)x,(int)y);

        graphics.drawLine(center.x, center.y, mark2.x, mark2.y);

        //***************************************************************
        // Color the slice
        //***************************************************************
        graphics.setColor(colors[colorIndex++]);


        graphics.fillArc(Inset,
                Inset,
                PieWidth,
                PieHeight,
                -1 * lastAngle,
                -1 * degrees);

        //***************************************************************
        // Label the slice, at the midpoint
        //***************************************************************
        double labelTheta = currentTheta - theta/2;
        double labelX = Radius * Math.cos(labelTheta);
        double labelY = Radius * Math.sin(labelTheta);

        Point labelPoint = new Point( center );
        labelPoint.translate( (int) labelX, (int) labelY);

        graphics.setColor(Color.black);
        FontMetrics metrics = graphics.getFontMetrics();
        int stringWidth = metrics.stringWidth(label);
        int charWidth = metrics.charWidth('M');

        if ( labelPoint.x > Inset + PieWidth/2 )
        {
            // right side label
            graphics.drawString(label,
                    labelPoint.x + charWidth,
                    labelPoint.y);
        }
        else
        {
            // left side label
            graphics.drawString(label,
                    labelPoint.x - stringWidth - charWidth,
                    labelPoint.y);

        }

        lastAngle += degrees;

    }

    /**
     *  Test entrypoint
     *
     */
    public static void main(String[] args)
    {
        try
        {
            // Create an image in memory
            /*JIMIProducer jimiTest = new JIMIProducer();

            // Write the image to memory
            FileOutputStream stream = new FileOutputStream("piechart.png");
            jimiTest.createImage(stream);
            stream.close();
            */
            resizeImage resize_image = new resizeImage();
            resize_image.createThumbnailBy_jmagick(0,"","","D:\\webbuilder5\\webapps\\cms\\sites\\wangjian_coosite_com\\brief\\images\\pic3037j6wa.JPG","150X120","s");
            System.exit(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private Graphics graphics;
    private Image image;
    private double currentTheta;
    private Color[] colors = {Color.red,
            Color.blue,
            Color.green,
            Color.pink,
            Color.orange,
            Color.yellow};
    private int colorIndex;
    private int lastAngle = 0;
}
