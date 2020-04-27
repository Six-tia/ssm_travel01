package com.tiaedu.travel.common.exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiaedu.travel.common.web.JsonResult;
/**
 * 通过此注解声明此类为一个全局异常处理类型
 * 通过此类处理所有控制层异常
 * 这是一个增强的 Controller。使用这个 Controller ，可以实现三个方面的功能：
 * 1.全局异常处理
 * 2.全局数据绑定
 * 3.全局数据预处理
 * */
@ControllerAdvice
public class ControllerExceptionHandler {
	/**当spring发现系统出现异常了,且异常的
	 * 类型为ServiceException类型,此时就会
	 * 回调此方法,并将异常值传递给这个方法,
	 * 这时我们就可以在此方法中对业务异常进行
	 * 统一处理,例如封装到jsonResult,然后
	 * 写到客户端告诉用户.
	 * @ExceptionHandler 注解用来指明异常的处理类型，
	 * 即如果这里指定为 NullpointerException，则数组越界异常就不会进到这个方法中来
	 * */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public JsonResult handleServiceException(ServiceException e){
		e.printStackTrace();
		//将异常封装到JsonResult
		return new JsonResult(e);
		//this.state=ERROR;
		//this.message=e.getMessage();
	}
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView  handleRuntimeException(RuntimeException e) {
		System.out.println("handleRuntimeException");
		ModelAndView mv=new ModelAndView("error");
		mv.addObject("exp", e.getMessage());
		return mv;
	}
}
