package com.bizwink.images;
/*
 * ImageServlet
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

import java.io.*;
import java.io.StringWriter;
import javax.servlet.http.*;
import javax.servlet.*;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import com.bizwink.images.ImageProducer;

/**
 *  Simple servlet to use with Image I/O producer
 *  QueryString should be the name of a class
 *  implementing ImageProducer
 */
public class ImageServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        try
        {
            ImageProducer imageProducer = (ImageProducer) Class.forName(request.getQueryString()).newInstance();
            String type = imageProducer.createImage(response.getOutputStream());
            response.setContentType(type);
        }
        catch (Exception e)
        {
            throw new ServletException(e);
        }
    }
}
