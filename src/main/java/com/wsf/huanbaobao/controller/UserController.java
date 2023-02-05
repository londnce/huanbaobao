package com.wsf.huanbaobao.controller;

import com.google.code.kaptcha.Constants;
import com.wsf.huanbaobao.controller.ex.*;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.service.UserService;
import com.wsf.huanbaobao.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;

    /**
     * 用户注册
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据，
     * spring boot会将前端的url地址中的参数名和pojo类的属性名进行比较，
     * 如果这俩个名称相同，则将值注入到pojo类中对应的属性上
     * @param user
     * @return
     */
    @PostMapping
    public JsonResult<Void> reg(User user,HttpSession session,String code){
        //从session取出验证码
        String validCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //判断验证码是否一致
        if (!validCode.equals(code)){
            throw new ValidCodeNotMatchException("验证码错误,请重试！");
        }
        //执行插入操作
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /**
     * 用户登录
     * 2.接收数据方式：请求处理方法的参数列表设置为非pojo类型来接收前端的数据，
     * spring boot会直接将请求的参数名和方法的参数名直接进行比较，
     * 如果这俩个名称相同，则自动完成值的依赖注入
     * @param user
     * @param code
     * @return
     */
    @GetMapping
    public JsonResult<User> login(User user, HttpSession session, String code){
        //将存储在session的kaptcha所生成的验证码取出
        String validCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //判断验证码是否一致
        if (!validCode.equals(code)){
            throw new ValidCodeNotMatchException("验证码错误,请重试！");
        }
        //执行登录操作
        User loginUser = userService.login(user);
        //分别将用户的session保存到服务端
        session.setAttribute("uid",loginUser.getUid());
        session.setAttribute("username",loginUser.getUsername());
        //优化一下传回前端的user数据，有些字段是不需要的。
        //只将用户名和uid进行回传
        User newUser = new User();
        newUser.setUsername(loginUser.getUsername());
        newUser.setUid(loginUser.getUid());
        newUser.setGender(loginUser.getGender());
        newUser.setPhone(loginUser.getPhone());
        newUser.setEmail(loginUser.getEmail());
        newUser.setAvatar(loginUser.getAvatar());
        return new JsonResult<>(OK,newUser);
    }

    /**
     * 用户修改密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @PostMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    /**
     * 获取session中的用户的数据
     * @param session
     * @return
     */
    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<>(OK, data);
    }

    /**
     * 修改个人信息
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        Integer uid = getUidFromSession(session);
        String modifiedUser = getUsernameFromSession(session);
        userService.changeInfo(uid, modifiedUser, user);
        return new JsonResult<>(OK);
    }

    /**
     * 文件最大上传限制
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 限制文件上传的类型
     */
    public static final List<String> AVATAR_TYPES = new ArrayList<>();
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    /**
     * 修改用户头像
     * MultipartFile接口时springmvc提供的一个接口，这个接口为我们包装了获取文件类型的数据（
     * 任何类型的file都可以接收），spring boot它又整合了springmvc，只需要在处理请求的方法参数列表上声明一个参数
     * 为MultipartFile的参数，然后spring boot自动将传递给服务的文件数据赋值给这个参数。
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入到请求处理方法的某个参数上，如果名称不一致则可以使用@RequestParam
     * 注解进行标记和映射
     * @param session
     * @param file
     * @return
     */
    @PostMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){
        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("上传头像不能为空");
        }
        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }
        //判断文件上传的类型是否是规定的后缀类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        //获取到这个文件名称，UUID工具来将生成一个新的字符串作为文件名
        String originalFileName = file.getOriginalFilename();
        System.out.println("originalFileName=" + originalFileName);
        int lastIndexOf = originalFileName.lastIndexOf(".");
        String suffix = originalFileName.substring(lastIndexOf);
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;

        //上传的文件.../upload/文件.规定的后缀类型
        String parent = System.getProperty("user.dir") + "/src/main/resources/static/images/img/" + filename;
        //虚拟创建目标文件
        File destFile = new File(parent);

        //获取目标文件的上级目录
        File parentFile = destFile.getParentFile();

        if (!parentFile.exists()){
            //代表文件的上级目录不存在，进行创建
            parentFile.mkdirs();
        }
        //参数file中数据写入到这个文件中
        try {
            file.transferTo(destFile);  //将file文件中的数据写入到dest文件中
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径src/upload/test.png
        String avatar = "http://localhost:" + port + "/user/down/" + filename;
        userService.changeAvatar(avatar,uid);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK, avatar);
    }

    /**
     * 文件上传和下载
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping("/down/{name}")
    public ResponseEntity<byte[]> fileUpload(@PathVariable("name") String fileName) throws IOException {
        //读取文件
        String downFilePath = System.getProperty("user.dir") + "/src/main/resources/static/images/img/" + fileName;
        if (downFilePath != null){
            //创建一个输入流读入需要下载的文件
            FileInputStream inputStream = new FileInputStream(new File(downFilePath));
            //创建一个和文件所需字节大小一致的byte字节数组
            byte[] fileBytes = new byte[inputStream.available()];
            //将读入的流写入字节数据
            inputStream.read(fileBytes);
            //创建HttpHeaders对象设置响应头信息
            HttpHeaders headers = new HttpHeaders();
            //设置要下载方式以及下载文件的名字
            //Content-Disposition 通知客户端以下载的方式接受数据
            //attachment;filename= 设置下载的文件的名字
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            //设置响应状态码
            HttpStatus statusCode = HttpStatus.OK;
            //创建ResponseEntity对象
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(fileBytes, headers, statusCode);
            //关闭输入流
            inputStream.close();
            //将需要下载的文件以字节数组的方式响应出去
            return responseEntity;
        }
        return null;
    }

    //处理用户退出登录的请求
    @GetMapping("/exit")
    public JsonResult<Void> exitUserLoginStatus(HttpSession session){
        session.removeAttribute("username");
        session.removeAttribute("uid");
        return new JsonResult<>(OK);
    }
}
