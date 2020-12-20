package com.learn.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

//控制文件上传方法的类
@Controller
@RequestMapping("file")
public class FileUploadController {

    /**
     * 使用传统方式的文件上传
     * @param request 通过HttpServletRequest请求获取到文件
     * @return 返回成功页面
     * @throws Exception
     */
    @RequestMapping("traditionFileUpload")
    public String traditionFileUpload(HttpServletRequest request) throws Exception {
        System.out.println("文件正在上传...");

        //使用fileupload组件完成文件上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);     //上传文件夹的路径

        if (!file.exists())
            file.mkdirs();  //如果该路径不存在，则新建一个文件夹

        //解析request对象获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        //解析request
        List<FileItem> items = upload.parseRequest(request);    //返回一个装有文件项目的集合
        //遍历
        for (FileItem item : items) {
            //判断item是否是上传文件项
            if (item.isFormField()) {
                //普通表单项
            } else {
                //上传文件项
                String fileName = item.getName();   //获取文件名称
                //设置唯一文件名
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName = uuid + "_" + fileName;

                item.write(new File(path, fileName));   //完成文件上传
                item.delete();      //删除临时文件
            }
        }

        return "success";
    }

    /**
     * 使用springMVC的方式上传文件
     * @param request 通过HttpServletRequest请求获取到文件
     * @param springMVCUpload 获取上传的文件，该处的参数名必须与表单中，上传文件选项的name一致
     *                        对应jsp：<input type="file" name="springMVCUpload"/>
     * @return 返回成功结果
     * @throws IOException
     */
    @RequestMapping("springMVCFileUpload")
    public String springMVCFileUpload(HttpServletRequest request, MultipartFile springMVCUpload) throws IOException {
        System.out.println("springMVCFileUpload...");

        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);     //上传文件夹的路径

        if (!file.exists())
            file.mkdirs();  //如果该路径不存在，则新建一个文件夹

        //获取文件名
        String fileName = springMVCUpload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileName = uuid + "_" + fileName;

        //文件上传
        springMVCUpload.transferTo(new File(path, fileName));

        return "success";
    }
}
