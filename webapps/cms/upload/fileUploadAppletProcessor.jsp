<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.io.File" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page import="org.apache.commons.fileupload.FileUpload" %>
<%@ page import="org.apache.commons.fileupload.DiskFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>

<html>
<head>
</head>
<body >
hello world

<%
		// This example uses the Jakarta Common's FileUpload package.
		// Info and files are located here:
		// http://jakarta.apache.org/commons/fileupload/index.html

		// Note that an alternative library for dealing with uploads is
		// located here http://www.servlets.com/cos/index.html

		// ### Struts users ###
		// when using Mike's JUpload applet, I did not define a form in
		// my struts-config.xml file.
		// Instead, within my action, i use the same code that is listed below.

		// maximum size that will be stored in memory
		// files larger than this will be written to disk "temporarly"
		int sizeThresholdUploadFileMemory = 1;

		// maximum size before a FileUploadException will be thrown
		int maxUploadFileSize = 1000000;

		// save the file to the root of the drive where web server is
		// running; change as appropriate
		String COMPLETE_UPLOAD_DIRECTORY = "d:\\sites";

		// the location for temporary saving uploaded files that exceed
		// the threshold sizeThresholdUploadFileMemory
		String tempDirectory = "c:\\inetpub";

		// Check that we have a file upload request
		boolean isMultipart;
		isMultipart = FileUpload.isMultipartContent(request);
		System.out.println("isMultipart = ["+isMultipart+"]");

		if (isMultipart == true)
		{
			// Create a new file upload handler
			DiskFileUpload upload = new DiskFileUpload();

			// Set upload parameters
			upload.setSizeThreshold(sizeThresholdUploadFileMemory);
			upload.setSizeMax(maxUploadFileSize);
			upload.setRepositoryPath(tempDirectory);

			// Parse the request
			// returns a list of "FileItem"
			List items = upload.parseRequest(request);

			// Process the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext())
			{
				FileItem fi = (FileItem) iter.next();

				String fieldName = fi.getFieldName();
				boolean isFormField = fi.isFormField();

				System.out.println("fieldName = ["+fieldName+"]");
				System.out.println("isFormField = ["+isFormField+"]");

				if (isFormField == true)
				{
					System.out.println("the field ["+fieldName+"] is a REGULAR form field");

					// Process a regular form field
					String value = fi.getString();

					System.out.println("fieldName = ["+fieldName+"]");
					System.out.println("value = ["+value+"]");
				}  // end of IF
				else
				{
					System.out.println("the field ["+fieldName+"] is a FILE form field");

					String filename = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();

					System.out.println("filename = ["+filename+"]");
					System.out.println("contentType = ["+contentType+"]");
					System.out.println("isInMemory = ["+isInMemory+"]");
					System.out.println("sizeInBytes = ["+sizeInBytes+"]");

					// save to file
					String uploadFilename;
					File uploadFile;

					uploadFilename =
								COMPLETE_UPLOAD_DIRECTORY
								+ filename;
					System.out.println("uploadFilename = ["+uploadFilename+"]");

					uploadFile = new File(uploadFilename);

					fi.write(uploadFile);
					System.out.println("saved as ["+uploadFilename+"]");

					//FileUploadService.processUploadedPhoto(uploadFilename);

				}  // end of ELSE
			}  // end of WHILE

		}  // end of IF
		else
		{
			System.out.println("not multipart");
		} // end of ELSE


%>

</body>
</html>
