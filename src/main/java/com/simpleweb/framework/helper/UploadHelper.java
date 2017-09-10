package com.simpleweb.framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.simpleweb.framework.domain.FieldParam;
import com.simpleweb.framework.domain.FileParam;
import com.simpleweb.framework.domain.FormParam;
import com.simpleweb.framework.util.FileUtils;
import com.simpleweb.framework.util.StreamUtils;

public class UploadHelper {

	/**
	 * 文件上传对象
	 */
	private static ServletFileUpload servletFileUpload;

	/**
	 * 初始化操作
	 */
	public static void init(ServletContext servletContext) {
		// 临时文件的存放目录
		File repository = (File) servletContext
				.getAttribute("javax.srevlet.context.tempdir");

		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));

		// 文件上传的大小限制
		int uploadLimit = ConfigHelper.getFileUploadSizeLimit();

		if (uploadLimit != 0) {
			servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
		}

	}

	/**
	 * 判断请求是否是Multipart
	 * 
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		return ServletFileUpload.isMultipartContent(request);
	}

	/**
	 * 解析出请求的参数
	 * 
	 * @param request
	 * @return
	 */
	public static FormParam createParam(HttpServletRequest request) {

		List<FileParam> fileParams = new ArrayList<FileParam>();

		List<FieldParam> fieldParams = new ArrayList<FieldParam>();

		try {
			Map<String, List<FileItem>> parseParameterMap = servletFileUpload
					.parseParameterMap(request);

			if (parseParameterMap != null && parseParameterMap.size() > 0) {
				for (Map.Entry<String, List<FileItem>> entry : parseParameterMap
						.entrySet()) {
					String fieldName = entry.getKey();
					List<FileItem> fileIList = entry.getValue();

					if (fileIList != null && fileIList.size() > 0) {
						for (FileItem fileItem : fileIList) {
							// 判断是否是普通表单字段
							if (fileItem.isFormField()) {
								// 普通的表单字段
								String fieldValue = fileItem.getString("UTF-8");
								fieldParams.add(new FieldParam(fieldName,
										fieldValue));
							} else {
								// 文件字段

								String fileName = FileUtils
										.getRealFileName(new String(fileItem
												.getName().getBytes(), "UTF-8"));

								if (fileName != null
										&& fieldName.trim().length() > 0) {
									long fileSize = fileItem.getSize();
									String contentType = fileItem
											.getContentType();
									InputStream in = fileItem.getInputStream();

									fileParams
											.add(new FileParam(fieldName,
													fileName, fileSize,
													contentType, in));
								}

							}

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new FormParam(fileParams, fieldParams);

	}

	/**
	 * 上传文件
	 * 
	 * @param basePath
	 * @param fileParam
	 */
	public static void uploadFile(String basePath, FileParam fileParam) {

		if (fileParam != null) {
			try {
				String filePath = basePath + fileParam.getFileName();
				FileUtils.createFile(filePath);

				InputStream in = new BufferedInputStream(
						fileParam.getInputStream());

				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(filePath));

				StreamUtils.copyStream(in, out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 批量的上传文件
	 * 
	 * @param basePath
	 * @param fileParams
	 */
	public static void uploadFile(String basePath, List<FileParam> fileParams) {

		if (fileParams != null && fileParams.size() > 0) {
			for (FileParam fileParam : fileParams) {
				uploadFile(basePath, fileParam);
			}
		}

	}

}
